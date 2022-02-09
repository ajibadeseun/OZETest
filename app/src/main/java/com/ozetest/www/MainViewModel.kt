package com.ozetest.www

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import kotlinx.coroutines.*

@OptIn(ExperimentalCoroutinesApi::class)

public class MainViewModel(application: Application) : ViewModel() {
    private val repository: GithubRepository
    private val githubDao: GithubDao

    init {
        githubDao = GithubRoomDatabase.getDatabase(application)?.githubDao()!!
        repository = GithubRepository(githubDao, ApiService.getApiService())
    }

    val listData = Pager(PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
        maxSize = 30,
        prefetchDistance = 5,
        initialLoadSize = 10
    )) {
        PostDataSource(repository)
    }.observable.cachedIn(viewModelScope)

    val favoriteListData = Pager(PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
        maxSize = 30,
        prefetchDistance = 5,
        initialLoadSize = 10
    )){
        FavoritesPostDataSource(repository)
    }.flow.cachedIn(viewModelScope)



    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */

    fun updateItem(github: Github?) {
        GlobalScope.launch {
            githubDao.updateUser(github)
        }
    }
    fun deleteItem(github: Github?){
        GlobalScope.launch {
            githubDao.delete(github)
        }
    }
    fun deleteAllItems(){
        GlobalScope.launch {
            githubDao.deleteAll()
        }
    }
    fun getItem() = repository.getFavoritesFromDatabase()


}