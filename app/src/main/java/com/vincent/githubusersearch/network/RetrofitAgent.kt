package com.vincent.githubusersearch.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ��l��Retrofit����A�H�Χ���baseUrl
 */
internal object RetrofitAgent {

    private var retrofit: Retrofit? = null

    fun getRetrofit(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = newRetrofit(baseUrl)
        }
        else if (retrofit!!.baseUrl().toString() != baseUrl) {
            retrofit = newRetrofit(baseUrl)
        }
        return retrofit!!
    }

    private fun newRetrofit(baseUrl: String): Retrofit {
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

        return retrofit!!
    }
}