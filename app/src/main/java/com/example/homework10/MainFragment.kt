package com.example.homework10

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework10.databinding.FragmentMainBinding
import kotlinx.coroutines.*


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    val countriesList = mutableListOf<Countries>()

    lateinit var countriesAdapter: CountriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        initAdapter()


        CoroutineScope(Dispatchers.IO).launch {
            countries()

            CoroutineScope(Dispatchers.Main).launch {
                countriesAdapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    private suspend fun countries() {
        val result = RetrofitService.retrofitService().getCountry()

        if (result.isSuccessful) {
            countriesList.addAll(result.body()!!)

        }
    }

    private fun initAdapter() {
        countriesAdapter = CountriesAdapter(countriesList)
        binding.rvJson.layoutManager = LinearLayoutManager(context)
        binding.rvJson.adapter = countriesAdapter
    }
}