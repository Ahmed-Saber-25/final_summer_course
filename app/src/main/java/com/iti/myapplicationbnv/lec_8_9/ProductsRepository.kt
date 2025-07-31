package com.iti.myapplicationbnv.lec_8_9

import android.util.Log

interface ProductsRepository {
    suspend fun getAllProducts(): List<ProductDM>?
}

class ProductsRepositoryImpl(private val productService: ProductService) : ProductsRepository {
    override suspend fun getAllProducts(): List<ProductDM>? {
        return try {
            val result = productService.listAll().products ?: emptyList()
            Log.d("SimpleRepository", "API result: $result")
            result
        } catch (e: Exception) {
            Log.e("SimpleRepository", "Error fetching quotes: ${e.localizedMessage}")
            emptyList()
        }
    }
}