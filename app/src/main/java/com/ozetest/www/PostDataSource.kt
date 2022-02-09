package com.ozetest.www

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class PostDataSource(private val repository: GithubRepository) :
    RxPagingSource<Int, Github>() {


    override fun getRefreshKey(state: PagingState<Int, Github>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Github>> {

        val currentLoadingPageKey = params.key ?: 1
        val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

        return repository.getDataFromDatabase(currentLoadingPageKey).subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Github>> {
                LoadResult.Page(
                    data = it,
                    prevKey = prevKey,
                    nextKey = currentLoadingPageKey.plus(1)
                )
            }
            .onErrorReturn { e ->
                LoadResult.Error(e)
            }


//        return apiService
//            // Single-based network load
//            .getUsers(currentLoadingPageKey)
//            .subscribeOn(Schedulers.io())
//            .map<LoadResult<Int, Github>> {
//                LoadResult.Page(
//                    data = it.items,
//                    prevKey = prevKey,
//                    nextKey = currentLoadingPageKey.plus(1)
//                )
//            }
//            .onErrorReturn { e ->
//                LoadResult.Error(e)
//            }
    }
}


