package com.ozetest.www

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "users_table")
data class Github(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    @Json(name = "login")
    var userName: String,
    @ColumnInfo(name = "avatar_url")
    @Json(name = "avatar_url")
    var imageUrl: String,
    @ColumnInfo(name = "html_url")
    @Json(name = "html_url")
    var profileUrl: String,
    @ColumnInfo(name = "isFavorite")
    @Transient
    var isFavorite: Boolean = false
)
