    package com.example.greenbite

    import android.util.Log
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.launch

    class UsersViewModel:ViewModel() {
        private val _activeUser = MutableLiveData<User>()
        val activeUser: LiveData<User>
            get() = _activeUser

        private val _users = MutableLiveData<List<User>>()
        val users: LiveData<List<User>>
            get() = _users

        private val _postcodes = MutableLiveData<List<Postcode>>()
        val postcodes: LiveData<List<Postcode>> = _postcodes

        private val _selectedPostcode = MutableLiveData<Postcode?>()
        val selectedPostcode: LiveData<Postcode?> = _selectedPostcode

        fun init(){

        }

        fun fetchPostcodes() {
            viewModelScope.launch {
                try {
                    val response = App.retrofitService.getPostcodes()
                    _postcodes.postValue(response)
                } catch (e: Exception) {
                    Log.e("UsersViewModel", "Error fetching postcodes: ${e.message}")
                }
            }
        }

        fun setSelectedPostcode(postcode: Postcode) {
            _selectedPostcode.value = postcode
        }

        fun registerUser(user: User, onResult: (Boolean, String) -> Unit) {
            viewModelScope.launch {
                try {
                    Log.d("Registration", "Sending user data: $user")

                    val response = App.retrofitService.createUser(user)

                    Log.d("Registration", "Raw response: ${response.raw()}")
                    Log.d("Registration", "Response headers: ${response.headers()}")

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Registration", "Success response body: $responseBody")

                        val message = responseBody?.message ?: "Registration successful!"
                        onResult(true, message)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("Registration", "Error body: $errorBody")
                        Log.e("Registration", "Error code: ${response.code()}")

                        val errorMessage = try {
                            if (errorBody != null) {
                                val moshi = com.squareup.moshi.Moshi.Builder().build()
                                val adapter = moshi.adapter(UserRegistrationResponse::class.java)
                                val errorResponse = adapter.fromJson(errorBody)
                                errorResponse?.message ?: getDefaultErrorMessage(response.code())
                            } else {
                                getDefaultErrorMessage(response.code())
                            }
                        } catch (e: Exception) {
                            Log.e("Registration", "Error parsing error response", e)
                            getDefaultErrorMessage(response.code())
                        }

                        onResult(false, errorMessage)
                    }
                } catch (e: Exception) {
                    Log.e("Registration", "Exception: ${e.message}", e)
                    onResult(false, "Network error: ${e.message}")
                }
            }
        }

        private fun getDefaultErrorMessage(code: Int): String {
            return when (code) {
                409 -> "Email is already registered"
                400 -> "Invalid input data"
                422 -> "Validation failed"
                500 -> "Server error"
                else -> "Registration failed"
            }
        }

        fun checkEmailExists(email: String, onResult: (Boolean) -> Unit) {
            viewModelScope.launch {
                try {
                    val response = App.retrofitService.getUserByEmail(email)

                    if (response.isSuccessful && response.body() != null) {
                        Log.d("Login", "Email exists: $email")
                        onResult(true)
                    } else if (response.code() == 404) {
                        Log.d("Login", "Email not found: $email")
                        onResult(false)
                    } else {
                        Log.e("Login", "Error checking email: ${response.code()}")
                        onResult(false)
                    }
                } catch (e: Exception) {
                    Log.e("Login", "Exception checking email: ${e.message}", e)
                    onResult(false)
                }
            }
        }
        fun loginUser(email: String, password: String, onResult: (Boolean, String, User?) -> Unit) {
            viewModelScope.launch {
                try {
                    val loginRequest = LoginRequest(email, password)
                    val response = App.retrofitService.loginUser(loginRequest)

                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val user = loginResponse.user
                            _activeUser.value = user
                            Log.d("Login", "Login successful for: $email, Role: ${user.role}")
                            onResult(true, loginResponse.message, user)
                        } else {
                            Log.e("Login", "Login response body is null")
                            onResult(false, "Login failed", null)
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("Login", "Login failed: $errorBody")

                        val errorMessage = when (response.code()) {
                            404 -> "Email not registered"
                            401 -> "Incorrect password"
                            else -> "Login failed"
                        }
                        onResult(false, errorMessage, null)
                    }
                } catch (e: Exception) {
                    Log.e("Login", "Exception during login: ${e.message}", e)
                    onResult(false, "Network error: ${e.message}", null)
                }
            }
        }

        fun checkLogin(email: String, password: String, onResult: (Boolean) -> Unit) {
            loginUser(email, password) { success, _, _ ->
                onResult(success)
            }
        }

        fun setActiveUser(email: String) {
            viewModelScope.launch {
                try {
                    val response = App.retrofitService.getUserByEmail(email)
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            _activeUser.value = user!!
                            Log.d("Login", "Active user set: ${user.email}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Login", "Error setting active user: ${e.message}", e)
                }
            }
        }

        fun setActiveUser(user: User) {
            _activeUser.value = user
        }

        fun getUserRoleName(role: Int): String {
            return when (role) {
                1 -> "Customer"
                2 -> "Employee"
                3 -> "Admin"
                else -> "Unknown"
            }
        }

        fun hasRole(requiredRole: Int): Boolean {
            return _activeUser.value?.role == requiredRole
        }

        fun isLoggedIn(): Boolean {
            return _activeUser.value != null!!
        }

        fun logout() {
            _activeUser.value = null
        }

        fun updateUser(user: User) {
            viewModelScope.launch {
                val currentUser = _activeUser.value ?: return@launch
                val updatedUser = App.retrofitService.updateUser(currentUser.userID!!, user)
                _activeUser.value = updatedUser.copy(userID = currentUser.userID, role = currentUser.role)
            }
        }

        fun refreshActiveUser() {
            val currentUser = _activeUser.value ?: return
            viewModelScope.launch {
                try {
                    val response = App.retrofitService.getUserByEmail(currentUser.email)
                    if (response.isSuccessful) {
                        response.body()?.let { user ->
                            _activeUser.value = user
                        }
                    }
                } catch (e: Exception) {
                    Log.e("UserRefresh", "Error refreshing user: ${e.message}", e)
                }
            }
        }
        fun deleteUser(userID: Int){
            viewModelScope.launch {
                App.retrofitService.deleteUser(userID)
            }
        }
    }