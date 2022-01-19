package com.example.tobuy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tobuy.AppDatabase
import com.example.tobuy.R
import com.example.tobuy.arch.ToBuyViewModel

class MainActivity : AppCompatActivity() {

    private val appDatabase: RoomDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, ""
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))
    }
}