package com.innso.mobile.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.innso.mobile.R
import com.innso.mobile.databinding.ActivityHomeBinding
import com.innso.mobile.ui.fragments.NewsFragment
import com.innso.mobile.viewModels.HomeViewModel


class HomeActivity : BaseActivity() {

    private val newsFragmen: NewsFragment = NewsFragment()

    private lateinit var binding: ActivityHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        initViewModel()
        replaceFragment(newsFragmen)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        subscribeViewModel(viewModel, binding.root)
    }
}
