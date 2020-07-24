package com.saket.weather_app_basic_kotlin.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saket.weather_app_basic_kotlin.databinding.CityListItemBinding
import com.saket.weather_app_basic_kotlin.model.City
import java.util.function.Consumer

/**
 * Here we use ListAdapter instead of RecyclerView.Adapter. ListAdapter extends Recyclerview.Adapter.
 * It provides way to selectively refresh recyclerview data using DiffUtils class. Also it has fewer
 * methods to override compared to recyclerview.adapter class. We do not have to manually pass the list
 * of data in the constructor like in Recyclerview.Adapter.
 *
 * All data related code comes from teh viewholder. The adapter itself contains very little viewholder
 * related code.
 *
 * We are using databinding to bind data between the recyclerview item layout and the viewholder class via
 * the adapter class.
 */
private val TAG = "CityListAdapter"
//Passing consumer in constructor to handle city item click events...
class CityListAdapter(val cityClickListener: Consumer<City>) : ListAdapter<City, CityListAdapter.CityViewHolder>(MyItemCallback()) {

    //Taking CityListItemBinding as input parameter and passing binding.root as parameter to parent class constructor
    class CityViewHolder(private val binding: CityListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        //Passing consumer as a click listener
        fun bind(city: City, cityClickListener: Consumer<City>) {
            Log.v(TAG, "Binding current city ${city.cityName}")
            binding.city = city
            binding.root.setOnClickListener(View.OnClickListener {
                cityClickListener.accept(city)
            })
            //binding.executePendingBindings()
        }

        //Instead of passing the itemview to the viewholder class, we pass the list item binding
        //item view can later be derived from the binding..
        companion object {
            fun from(parent: ViewGroup): CityViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CityListItemBinding.inflate(inflater)
                return CityViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currCity = getItem(position)
        holder.bind(currCity, cityClickListener)
    }

    //My DiffUtil itemcallback
    /*
    areItemsTheSame - comapares items using item id.
    areContentsTheSame - compares entire objects.
     */
    class MyItemCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            Log.v(TAG, "areItemsTheSame called ${oldItem.cityName} and ${newItem.cityName}")
            return oldItem.cityName.equals(newItem.cityName)
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            Log.v(TAG, "areContentsTheSame called ${oldItem.cityName} and ${newItem.cityName}")
            return oldItem.equals(newItem)
        }
    }
}