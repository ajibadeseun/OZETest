package com.ozetest.www

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {
    private lateinit var favoritesList: RecyclerView
    private lateinit var progressBar: ProgressBar
    lateinit var viewModel: MainViewModel
    lateinit var mainListAdapter: MainListAdapter
    lateinit var favoritesAdapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesList = view.findViewById(R.id.favorites_list)
        progressBar = view.findViewById(R.id.progress_bar)

        setupViewModel()
//        setupList()
//        setupView()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    private fun setupView() {
//        Using RxJava and Paging API
//        lifecycleScope.launch {
//            viewModel.favoriteListData.subscribe {
//                mainListAdapter.submitData(lifecycle, it)
//            }
//        }
//         Using Flow (Coroutine) and Paging API
//        lifecycleScope.launch{
//            viewModel.favoriteListData.collectLatest {
//                mainListAdapter.submitData(it)
//            }
//        }
    }



    private fun setupList() {
        mainListAdapter = MainListAdapter(requireActivity(), viewModel)
        favoritesList.apply {
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
                MainViewModelFactory(application = requireActivity().application)
            )[MainViewModel::class.java]


        //Using LiveData
        viewModel.getItem().observe(this.viewLifecycleOwner) { it ->
            it.let {
                favoritesAdapter = FavoritesAdapter(requireActivity(), it, viewModel)
                favoritesList.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    setHasFixedSize(true)
                    adapter = favoritesAdapter
                }
                progressBar.visibility = View.GONE
            }
        }


    }
}