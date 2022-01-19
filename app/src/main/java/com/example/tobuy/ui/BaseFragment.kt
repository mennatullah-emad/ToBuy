package com.example.tobuy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tobuy.AppDatabase
import com.example.tobuy.arch.ToBuyViewModel

abstract class BaseFragment : Fragment() {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: ToBuyViewModel by activityViewModels()
}