package com.ozetest.www

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.Scheduler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var homeList: RecyclerView
    private lateinit var progressBar: ProgressBar
    lateinit var viewModel: MainViewModel
    lateinit var mainListAdapter: MainListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeList = view.findViewById(R.id.home_list)
        progressBar = view.findViewById(R.id.progress_bar)

        setupViewModel()
        setupList()
        setupView()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setupView() {
        lifecycleScope.launch {
           viewModel.listData.subscribe {
               mainListAdapter.submitData(lifecycle,it)
           }
        }
    }

    private fun setupList() {
        mainListAdapter = MainListAdapter(requireActivity(),viewModel)
        homeList.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = mainListAdapter
        }
        mainListAdapter.withLoadStateFooter(
            FooterAdapter()
        )
        mainListAdapter.addLoadStateListener {
            if (it.refresh == LoadState.Loading) {
                // show progress view
                progressBar.visibility = View.VISIBLE
            } else {
                //hide progress view
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(application =  requireActivity().application)
            )[MainViewModel::class.java]
    }
}


