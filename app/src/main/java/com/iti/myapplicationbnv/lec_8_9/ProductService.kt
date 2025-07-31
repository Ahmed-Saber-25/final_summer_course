package com.iti.myapplicationbnv.lec_8_9

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("/products")
    suspend fun listAll(): ProductResponse
    @GET("/products/{productid}")
    suspend fun getProductById(@Path("productid") productid:String): ProductDM
}
