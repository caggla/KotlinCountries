package com.example.kotlincountries.service

import com.example.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder() // retrofit api olusuturyoruz
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // bu satırda modelimizi nasıl kullanacağımızı belirttik
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Rxjava kullanacagımızı belirttik.
        .build()
        .create(CountryAPI::class.java) // bu satırda da bu sınıf ile countryapi sınıfımızı birbirine bağladık buildden sonra . :D

    fun getData() : Single<List<Country>> {
        return api.getCountries()
    }
//şimdi bu sınıftan bie obje olusutrup datayı çekmemiz lazım. Obje olusturarak başka yerde işlem yapabiliriz.


}