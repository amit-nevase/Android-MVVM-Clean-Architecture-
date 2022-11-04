package com.amit.assignment.data.source.remote

import com.amit.assignment.domain.model.MarvelResponse
import com.amit.assignment.util.Constant.MARVEL_API_HASH
import com.amit.assignment.util.Constant.MARVEL_API_PUBLIC_KEY
import com.amit.assignment.util.Constant.MARVEL_API_TS
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/v1/public/characters?apikey=$MARVEL_API_PUBLIC_KEY&ts=$MARVEL_API_TS&hash=$MARVEL_API_HASH")
    fun getCharacterList(@Query("offset") offset: Int): Single<MarvelResponse>
}
