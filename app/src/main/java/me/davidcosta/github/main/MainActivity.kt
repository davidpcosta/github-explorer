package me.davidcosta.github.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.davidcosta.github.databinding.ActivityMainBinding
import me.davidcosta.github.di.ViewModelFactory
import javax.inject.Inject
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.github.viewmodel.MainViewModel
import me.davidcosta.github.MyApplication
import me.davidcosta.github.utils.LoadState

class MainActivity: AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var repositoryAdapter = RepositoryAdapter()
    private var loadingAdapter = LoadStateAdapter().apply {
        submitList(listOf(true))
    }
    private var concatAdapter = ConcatAdapter(repositoryAdapter, loadingAdapter)

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplication.applicationContext().applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.onCreate(savedInstanceState)

        binding.activityMainRepositoryList.apply {
            adapter = this@MainActivity.concatAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        }

        binding.activityMainRetryButton.setOnClickListener {
            showLoading()
            mainViewModel.loadMoreRepositories()
        }

        binding.activityMainRepositoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mainViewModel.loadMoreRepositories()
                }
            }
        })

        observeRepositoryList()
        observeIsLoadMoreRepositoryFinished()

        restoreOrLoadRepositories()
    }

    private fun showRepositoryList() {
        binding.activityMainRepositoryList.visibility = View.VISIBLE
        binding.activityMainRepositoryProgress.visibility = View.GONE
        binding.activityMainRetryButton.visibility = View.GONE
    }

    private fun showLoading() {
        binding.activityMainRepositoryProgress.visibility = View.VISIBLE
        binding.activityMainRepositoryList.visibility = View.GONE
        binding.activityMainRetryButton.visibility = View.GONE
    }

    private fun showRetryButton() {
        binding.activityMainRetryButton.visibility = View.VISIBLE
        binding.activityMainRepositoryProgress.visibility = View.GONE
        binding.activityMainRepositoryList.visibility = View.GONE
    }

    private fun restoreOrLoadRepositories() {
        with(mainViewModel) {
            if(hasRepositoryData) {
                repositoryAdapter.submitList(repositoryVOList)
                showRepositoryList()
            } else {
                loadRepositories()
            }
        }
    }

    private fun observeRepositoryList() {
        mainViewModel
            .repositoryVOListData
            .observe(this) {
                when(it.status) {
                    LoadState.LOADING -> {}

                    LoadState.SUCCESS -> {
                        this.repositoryAdapter.submitList(it.data)
                        showRepositoryList()
                        isLoading = false
                    }

                    LoadState.ERROR -> {
                        isLoading = false
                        showRetryButton()
                    }
                }
            }
    }

    private fun observeIsLoadMoreRepositoryFinished() {
        mainViewModel
            .hasNextPageData
            .observe(this) { hasNextPage ->
                if (!hasNextPage) {
                    concatAdapter.removeAdapter(loadingAdapter)
                    isLoading = false
                }
            }
    }
}