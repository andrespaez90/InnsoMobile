package com.innso.mobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.innso.mobile.R
import com.innso.mobile.databinding.FragmentNewsBinding
import com.innso.mobile.ui.BaseFragment
import com.innso.mobile.ui.adapters.GenericAdapter
import com.innso.mobile.ui.interfaces.GenericItemView
import com.innso.mobile.ui.itemViews.news.ItemGenericNews
import com.innso.mobile.ui.models.list.GenericAdapterFactory
import com.innso.mobile.ui.models.list.GenericItemAbstract
import com.innso.mobile.ui.models.news.GenericNews
import com.innso.mobile.viewModels.HomeViewModel

class NewsFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        initViewModel()
        initListeners()
        initRecyclerView()
        return binding.root
    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        viewModel.topLinesChange().observe(this, Observer { updateView(it) })
    }

    private fun updateView(news: List<GenericNews>) {
        listAdapter.clear()
        news.forEach { listAdapter.addItem(GenericItemAbstract(it)) }
    }

    private fun initListeners(){
        binding.textViewTitle.setOnClickListener { viewModel.loadNews() }
    }

    private fun initRecyclerView() {
        val list = binding.recyclerViewList
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter
    }

    private val listAdapter: GenericAdapter by lazy {
        GenericAdapter(object : GenericAdapterFactory() {
            override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GenericItemView<*> {
                val item = ItemGenericNews(viewGroup.context)
                item.setOnClickListener { viewModel.openLink( (((it as ItemGenericNews).data) as GenericNews).linkNews) }
                return item
            }
        })
    }
}
