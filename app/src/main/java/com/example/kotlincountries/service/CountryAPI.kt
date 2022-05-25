package com.example.kotlincountries.service

import com.example.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    @GET("atilsamancioglu/IA19-DataSetCountries/blob/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>
}