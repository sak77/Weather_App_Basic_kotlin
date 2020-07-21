package com.saket.weather_app_basic_kotlin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saket.weather_app_basic_kotlin.databinding.CityListItemBinding
import com.saket.weather_app_basic_kotlin.model.City

class CityListAdapter : ListAdapter<City, CityListAdapter.CityViewHolder>(MyItemCallback()) {

    class CityViewHolder(val binding: CityListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(city: City) {
            binding.city = city
        }

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
        holder.bind(currCity)
    }

    //My DiffUtil itemcallback
    class MyItemCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.cityName.equals(newItem.cityName)
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.equals(newItem)
        }

    }
}