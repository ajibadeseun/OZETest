package com.ozetest.www

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
public interface GithubDao {
    @Query("SELECT * FROM users_table")
    fun getAllUsers(): Single<List<Github>>

    @Query("SELECT * FROM users_table  WHERE isFavorite = 1")
    fun getFavorites(): LiveData<List<Github>>

    @Query("SELECT * FROM users_table  WHERE isFavorite = 1")
    fun getFavoritesSingle(): Single<List<Github>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(githubUsers: List<Github>?)

    @Update
    fun updateUser(user: Github?)

    @Delete
    suspend fun delete(github: Github?)

    @Query("DELETE FROM users_table")
    fun deleteAll()
}