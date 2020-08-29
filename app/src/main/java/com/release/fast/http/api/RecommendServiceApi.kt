package com.release.fast.http.api


import com.release.fast.mvp.model.RecommendPageBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
interface RecommendServiceApi {

//    @GET("it")
//    fun getRecommendData(@Query("key") key: String, @Query("num") num: Int): Observable<RecommendPageBean>

    @GET("company/announcement/list")
    fun getRecommendData(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int,
        @Query("userId") userId: String,
        @Query("noticeType") noticeType: Int,
        @Query("status") num: Int
    ): Observable<RecommendPageBean>
}
