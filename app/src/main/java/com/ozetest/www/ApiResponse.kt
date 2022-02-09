package com.ozetest.www

import com.squareup.moshi.Json

data class ApiResponse(
    @Json(name = "total_count")
    val totalCount: Int,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    val items : List<Github>
)
