package com.example.tobuy.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentHomeBinding
import com.example.tobuy.ui.BaseFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            //todo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}