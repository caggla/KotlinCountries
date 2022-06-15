package com.example.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {
    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
//        val country = Country("Turkey","Asia","Ankara","TL","Turkish","www.ss.com")
//        val country2 = Country("France","Europe","Paris","EUR","French","www.ss.com")
//        val country3 = Country("Germany","Europe","Berlin","EUR","Deutsch","www.ss.com")
//
//        val countryList = arrayListOf<Country>(country,country2,country3)
//        countries.value = countryList // MutableLiveData oldugu için .value dedik.
//        countryError.value = false
//        countryLoading.value = false

        //ARTIK SERVİSİMİZİ BURADA KULLANABİLİRİZZ.

        getDataFromAPI()
        
    }
    private fun getDataFromAPI(){
        countryLoading.value = true
        disposable.add(
            countryApiService.getData() //burada sunscribeOn async bir sekılde çalısır ve yeni thread açmak ister , observe on ise datayı nerede gözlemleyeceğimizi ister o da main thread ister.
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace() //hatayı gösterir.
                    }

                }) //observe ldedik ama observer kim ? onu burada subscribe lıyoruz . :D İçine observer istiyor.
        )

    }
}