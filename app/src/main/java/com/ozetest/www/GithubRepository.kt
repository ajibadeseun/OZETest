package com.ozetest.www

import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

@OptIn(DelicateCoroutinesApi::class)
class GithubRepository(private val dao: GithubDao, private val apiService: ApiService) {
    fun getDataFromDatabase(pageNumber: Int): Single<List<Github>> {
        GlobalScope.launch {
            getDataFromServer(pageNumber)
        }
        return dao.getAllUsers()
    }

  fun getFavoritesFromDatabase(): LiveData<List<Github>> {
        return dao.getFavorites()
    }

    fun getFavoritesFromDatabaseSingle(): Single<List<Github>> {
        return dao.getFavoritesSingle()
    }


    private fun getDataFromServer(pageNumber: Int)  {
        GlobalScope.launch {
            val data = apiService.getUsers(pageNumber)
            data?.blockingGet()?.items?.let { saveDataToDatabase(it) }
        }
    }

    private fun saveDataToDatabase(data: List<Github>) = dao.insert(data)
}