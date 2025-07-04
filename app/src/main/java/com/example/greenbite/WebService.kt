package com.example.greenbite

import com.example.greenbite.admin.Employee
import com.example.greenbite.checker.Order
import com.example.greenbite.checker.OrderReq
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getTopMenus(): List<Product>

    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/category/name/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") category: String): List<Product>

    @POST("users")
    suspend fun createUser(@Body user: User): User?

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User): User?

    @GET("users/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User?

    @POST("login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): User?

    @GET("postcode")
    suspend fun getPostcodes(): List<Postcode>

    @DELETE("users/{userID}")
    suspend fun deleteUser(@Path("userID") userID: Int): Response<Unit>

//    @PUT("users/{userID}")
//    suspend fun updateUser(@Path("userID") userID: Int, @Body updatedData: Map<String, Any>): Response<Unit>

    @POST("topup/token/{id}")
    suspend fun getSnapToken(@Path("id") id: Int, @Body amountRequest: AmountRequest): Response<com.example.greenbite.SnapTokenResponse>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    //rating
    @POST("ratings")
    suspend fun addRating(@Body rating: Rating)

    @GET("ratings/user/{userID}")
    suspend fun getUserRatings(@Path("userID") userID: Int): List<Rating>

    @GET("/orders/{userid}")
    suspend fun getOrderByUserId(@Path("userid") userId: Int): List<Order>

    @GET("postcode-location")
    suspend fun getCoordinates(@Query("postcode") postcode: String): Postcode

    @POST("orders")
    suspend fun createOrder(@Body order: OrderReq)

    //EMPLOYEE
    @GET("orders")
    suspend fun getAllOrders(): List<Order>

    @PUT("orders/{id}/accept")
    suspend fun acceptOrder(@Path("id") id: Int, @Body body: Map<String, Int>)

    @PUT("orders/{id}/reject")
    suspend fun rejectOrder(@Path("id") id: Int)

    @PUT("orders/{id}/ship")
    suspend fun shipOrder(@Path("id") id: Int)

    @PUT("orders/{id}/finish")
    suspend fun finishOrder(@Path("id") id: Int)



    @GET("orders/status/pending")
    suspend fun getAllPendingOrder(): List<Order>

    @GET("orders/status/active")
    suspend fun getActiveOrders(): List<Order>

    @GET("orders/status/cooking")
    suspend fun getCookingOrders(): List<Order>

    @GET("orders/status/shipping")
    suspend fun getShippingOrders(): List<Order>

    @GET("orders/status/completed")
    suspend fun getCompletedOrders(): List<Order>

    //ADMIN
    //crud employee
    @GET("employees")
    suspend fun getEmployees(): List<Employee>
    @PUT("employees/{userID}")
    suspend fun updateEmployees(@Path("userID") userID: Int, @Body user: User)

    //crud menu
    @DELETE("products/{productID}")
    //crud menu
    suspend fun deleteProduct(@Path("productID") productID: Int)
    @PUT("products/{productID}")
    suspend fun updateProduct(@Path("productID") productID: Int, @Body product: Product)
    @POST("products")
    suspend fun addProduct(@Body product: Product)

    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: String): Order
}