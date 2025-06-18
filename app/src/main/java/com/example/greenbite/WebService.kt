package com.example.greenbite

import com.example.greenbite.admin.Employee
import com.example.greenbite.checker.Order
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
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

    @GET("users/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<User>

    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("postcode")
    suspend fun getPostcodes(): List<Postcode>

    @DELETE("users/{userID}")
    suspend fun deleteUser(@Path("userID") userID: Int): Response<Unit>

    @PUT("users/{userID}")
    suspend fun updateUser(@Path("userID") userID: Int, @Body updatedData: Map<String, Any>): Response<Unit>

    //EMPLOYEE
    @GET("orders")
    suspend fun getAllOrders(): List<Order>

    //ADMIN
    //crud employee
    @GET("employees")
    suspend fun getEmployees(): List<Employee>
    @PUT("employees/{userID}")
    suspend fun updateEmployees(@Path("userID") userID: Int, @Body user: User)

    @DELETE("products/{productID}")
    suspend fun deleteProduct(@Path("productID") productID: Int)
    @PUT("products/{productID}")
    suspend fun updateProduct(@Path("productID") productID: Int, @Body product: Product)
    @POST("products")
    suspend fun addProduct(@Body product: Product)
}