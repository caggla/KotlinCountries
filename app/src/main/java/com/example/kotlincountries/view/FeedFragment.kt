package com.example.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountries.R
import com.example.kotlincountries.adapter.CountryAdapter
import com.example.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        countryList.layoutManager = LinearLayoutManager(context) //listeyi alt alta getirmesi içn bunu kullandık .gridlayoutmanager da kullanabilirdik yanyana için.
        countryList.adapter = countryAdapter
        //şimdi refreshlediğimiz verileri gözlemlememiz gerekiyor. observe ile.mutablelivedata nın getirdği bir özellik.aşağıya yeni fonk . yazalım.
        observeLiveData()

    }
    private fun observeLiveData() { //aşağıda observe yazınca life cycle owner ın kim old nu soracak , owner this yani bu fragment . observer lambda gösterimi isticek.
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            //mesela burada countries i observelemek istedik . neyi istersek onu gözlemleyebiliriz.
            countries?.let {
                countryList.visibility = View.VISIBLE // eğer boş degil ise error ve loadingi göstermmeize gerek yok onları gizleyecegız listeyi göstereceğiz.
                countryAdapter.updateCountryList(countries) // böylece hem obswrve ediyoruz hem de değişiklik olursa onu alıyoruz.
            } // if countries var ise yani boş değil ise;
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {//burada boolean veriyor liste değil if ;
                if (it) {
                    countryError.visibility = View.VISIBLE // eğer boş degil ise error ve loadingi göstermmeize gerek yok onları gizleyecegız listeyi göstereceğiz.
                }// true ise
                else {
                    countryError.visibility = View.GONE // eğer boş degil ise error ve loadingi göstermmeize gerek yok onları gizleyecegız listeyi göstereceğiz.
                }

            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner,Observer{ loading->
            loading?.let{
                if (it) {
                    countryLoading.visibility = View.VISIBLE // eğer boş degil ise error ve loadingi göstermmeize gerek yok onları gizleyecegız listeyi göstereceğiz.
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                }// true ise
                else {
                    countryLoading.visibility = View.GONE // eğer boş degil ise error ve loadingi göstermmeize gerek yok onları gizleyecegız listeyi göstereceğiz.
                }
            }
        })
    }
}