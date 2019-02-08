package com.innso.mobile.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.innso.mobile.R
import com.innso.mobile.databinding.FragmentProfileBinding
import com.innso.mobile.ui.BaseFragment
import com.innso.mobile.ui.activities.SplashActivity
import com.innso.mobile.viewModels.company.ProfileViewModel

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        binding.viewModel = viewModel
    }

    fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.startActivity().observe(this, Observer { startActivity(it) })
        viewModel.closeSession().observe(this, Observer { this.closeSession() })
    }

    private fun closeSession() {
        val intent = Intent(context, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
