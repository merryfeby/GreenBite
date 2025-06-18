package com.example.greenbite

import com.example.greenbite.checker.Order
import com.midtrans.sdk.corekit.internal.network.model.response.SnapTokenResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class TopMenu(
    val id_menu:Int,
    val name_menu: String,
    val price_menu:Int,
    val image_menu:String,
    val category_menu:String,
    val rating_menu:Int,
    val totalrating_menu:Int,
)

@JsonClass(generateAdapter = true)
data class UserRegistrationResponse(
    @Json(name = "message")
    val message: String
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "message")
    val message: String,
    @Json(name = "user")
    val user: User
)

@JsonClass(generateAdapter = true)
data class AmountRequest(
    @Json(name = "amount")
    val amount: Int
)

@JsonClass(generateAdapter = true)
data class SnapTokenResponse(
    @Json(name = "token") val token: String,
    @Json(name = "orderId") val orderId: Int
)

interface WebService {
    //CUSTOMER
    @GET("topmenus")
    suspend fun getTopMenus(): List<TopMenu>

    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/category/name/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") category: String): List<Product>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<UserRegistrationResponse>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User): User

    @GET("users/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<User>

    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("postcode")
    suspend fun getPostcodes(): List<Postcode>

//    @POST("topup/token/{id}")
//    suspend fun getSnapToken(@Path("id") id: Int, @Body amountRequest: AmountRequest)

    @POST("topup/token/{id}")
    suspend fun getSnapToken(@Path("id") id: Int, @Body amountRequest: AmountRequest): Response<com.example.greenbite.SnapTokenResponse>

    //EMPLOYEE
    @GET("orders")
    suspend fun getAllOrders(): List<Order>


    //ADMIN
}