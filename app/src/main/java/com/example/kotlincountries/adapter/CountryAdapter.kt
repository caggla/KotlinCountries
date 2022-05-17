package com.example.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.R
import com.example.kotlincountries.model.Country
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList: ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) { //kodda en son burayı yaptık . Diğerleri adapterü bağlama işiydi.
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion




    }

    override fun getItemCount(): Int { //kaç tane row olusturacaksın?
            return countryList.size
    }
    fun updateCountryList(newCountryList: List<Country>){ // feed fragmentta swipe koymuştuk refresh için . Bunu adaptere bildirmemiz gerekiyor. o yuzden bu fonksıyonu olusturduk.
        countryList.clear() //Güncel olanı silip yeni listeyi getiriyoruz.
        countryList.addAll(newCountryList)
        notifyDataSetChanged() // adapter i yenilemek için kullanıyoruz.

    }

}