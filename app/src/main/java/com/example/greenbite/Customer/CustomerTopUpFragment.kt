package com.example.greenbite.Customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.greenbite.AmountRequest
import com.example.greenbite.App
import com.example.greenbite.R
import com.example.greenbite.UsersViewModel
import com.example.greenbite.databinding.FragmentCustomerTopUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CustomerTopUpFragment : Fragment() {

    private lateinit var binding: FragmentCustomerTopUpBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private var orderId: String? = null
    private var amount: Double? = null
    private var snapToken: String? = null
    private var isProcessingPayment = false
    private lateinit var paymentScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentScope = CoroutineScope(Dispatchers.Main)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_customer_top_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.usersViewModel = usersViewModel

        usersViewModel.activeUser.observe(viewLifecycleOwner) { user ->
            binding.tvMybalanceCredit.text = "Rp ${user?.credit ?: "0"}"
            Log.d("MenuFragment", " user credit: ${user.credit}")
        }

        binding.btnMybalanceBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnMybalanceTopup.setOnClickListener {
            val amountString = binding.etnumberMybalanceAmount.text.toString()
            val localAmount = amountString.toDoubleOrNull()
            if (localAmount == null || localAmount < 1000) {
                Toast.makeText(requireContext(), "Minimum top-up amount is 1000", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = usersViewModel.activeUser.value?.userID ?: run {
                Toast.makeText(requireContext(), "ngga ketemu", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            binding.btnMybalanceTopup.isEnabled = false
            isProcessingPayment = true

            paymentScope.launch(Dispatchers.IO) {
                try {
                    val response = App.retrofitService.getSnapToken(userId, AmountRequest(localAmount.toInt()))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            response.body()?.let { body ->
                                orderId = body.orderId.toString()
                                snapToken = body.token
                                if (snapToken != null) {
                                    val snapUrl = "https://app.sandbox.midtrans.com/snap/v2/vtweb/$snapToken"
                                    Log.d("CustomerTopUpFragment", "Loading Snap URL: $snapUrl")
                                    binding.webView.visibility = View.VISIBLE
                                    binding.webView.settings.javaScriptEnabled = true
                                    binding.webView.webViewClient = object : WebViewClient() {
                                        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                            url?.let {
                                                Log.d("WebView", "Redirect URL: $url")
                                                if (url.contains("/success") || url.contains("transaction_status=settlement")) {
                                                    Toast.makeText(requireContext(), "Payment successful", Toast.LENGTH_SHORT).show()
                                                    resetUI()
                                                    usersViewModel.refreshActiveUser()
                                                    return true
                                                } else if (url.contains("/unfinish") || url.contains("transaction_status=cancel") || url.contains("/error")) {
                                                    Toast.makeText(requireContext(), "Payment cancelled/failed", Toast.LENGTH_SHORT).show()
                                                    resetUI()
                                                    return true
                                                }
                                                return super.shouldOverrideUrlLoading(view, url)
                                            }
                                            return false
                                        }

                                        override fun onPageFinished(view: WebView?, url: String?) {
                                            super.onPageFinished(view, url)
                                        }

                                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                                            super.onReceivedError(view, request, error)
                                            Log.e("WebView", "Error: ${error?.description}")
                                            Toast.makeText(requireContext(), "Failed to load payment page: ${error?.description}", Toast.LENGTH_LONG).show()
                                            resetUI()
                                        }
                                    }
                                    binding.webView.loadUrl(snapUrl)
                                } else {
                                    Toast.makeText(requireContext(), "Snap token tidak diterima", Toast.LENGTH_LONG).show()
                                    resetUI()
                                }
                            } ?: run {
                                Toast.makeText(requireContext(), "Respon tidak valid dari server", Toast.LENGTH_LONG).show()
                                resetUI()
                            }
                        } else {
                            val errorMessage = response.errorBody()?.string() ?: "Kesalahan tidak diketahui"
                            Log.e("CustomerTopUpFragment", "Gagal mendapatkan token: ${response.code()} - $errorMessage")
                            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_LONG).show()
                            resetUI()
                        }
                    }
                } catch (e: HttpException) {
                    Log.e("CustomerTopUpFragment", "HTTP Error: ${e.code()} - ${e.message()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "HTTP ${e.code()}: ${e.message()}", Toast.LENGTH_LONG).show()
                        resetUI()
                    }
                } catch (e: Exception) {
                    Log.e("CustomerTopUpFragment", "Error: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        resetUI()
                    }
                }
            }
        }
    }

    private fun resetUI() {
        if (isAdded) {
            binding.webView.visibility = View.GONE
            binding.webView.stopLoading()
            binding.webView.loadUrl("about:blank")
            binding.etnumberMybalanceAmount.text?.clear()
            orderId = null
            amount = null
            snapToken = null
            isProcessingPayment = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        paymentScope.cancel()
        binding.webView.destroy()
    }

    override fun onResume() {
        super.onResume()
        if (usersViewModel.activeUser.value != null) {
            usersViewModel.refreshActiveUser()
        }
    }
}