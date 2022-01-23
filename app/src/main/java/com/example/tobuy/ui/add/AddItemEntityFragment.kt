package com.example.tobuy.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentAddItemEntityBinding
import com.example.tobuy.intity.ItemEntity
import com.example.tobuy.ui.BaseFragment
import java.util.*

class AddItemEntityFragment : BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtn.setOnClickListener {
            saveItemEntityToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){ complete->
            if (complete){
                Toast.makeText(requireActivity(), "Item saved!", Toast.LENGTH_SHORT).show()
                binding.titleEt.text = null
                binding.titleEt.requestFocus()

                mainActivity.showKeyboard()

                binding.descriptionEt.text = null
                binding.radioGroup.check(R.id.radioButtonLow)
            }
        }
        // show keyboard and default select title et
        mainActivity.showKeyboard()
        binding.titleEt.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompleteLiveData.postValue(false)
    }

    private fun saveItemEntityToDatabase() {
        val itemTitle = binding.titleEt.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = "* Required field"
            return
        }
        binding.titleTextField.error = null

        var itemDescription: String? = binding.descriptionEt.text.toString().trim()
        if (itemDescription?.isEmpty() == true) {
            itemDescription = null
        }

        val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = "" //todo update this later when we have categories in the app
        )

        sharedViewModel.insertItem(itemEntity)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}