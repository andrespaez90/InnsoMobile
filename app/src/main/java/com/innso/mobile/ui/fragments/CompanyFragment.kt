package com.innso.mobile.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.innso.mobile.R
import com.innso.mobile.databinding.FragmentCompanyBinding
import com.innso.mobile.ui.BaseFragment
import com.innso.mobile.utils.StringUtils
import com.innso.mobile.viewModels.company.CompanyViewModel

class CompanyFragment : BaseFragment() {

    private lateinit var viewModel: CompanyViewModel

    private lateinit var binding: FragmentCompanyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company, container, false)
        initViewModel()
        binding.viewModel = viewModel
        return binding.root
    }

    fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(CompanyViewModel::class.java)
        viewModel.startActivity().observe(this, Observer { startActivity(it) })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.textViewCompanyName.text = StringUtils.setSpannablesFromRegex(getString(R.string.company_name),
                "^(.*?)\\n", StyleSpan(Typeface.BOLD), RelativeSizeSpan(1.2f))
        binding.textViewCompanyNit.text = StringUtils.setSpannablesFromRegex(getString(R.string.company_nit), "(.*):", StyleSpan(Typeface.BOLD))
    }
}
