package com.tenessine.intocleanarchitecture.presentation

import MainViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.tenessine.intocleanarchitecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var factory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        factory = MainViewModelFactory.getInstance()
        viewModel.apply {
            setName("Awanama")
            message.observe(this@MainActivity) {
                binding?.tvWelcome?.text = it.welcomeMessage
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}