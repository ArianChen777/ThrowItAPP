package com.throwit.app.data.repository

import com.throwit.app.data.api.ApiService
import com.throwit.app.data.model.*
import com.throwit.app.data.api.ScreenshotAnalysisResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun uploadScreenshot(screenshot: MultipartBody.Part): Flow<Result<ScreenshotAnalysisResponse>> = flow {
        try {
            val response = apiService.uploadScreenshot(screenshot)
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("上传截图失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "上传截图失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getTodayActivities(): Flow<Result<List<Activity>>> = flow {
        try {
            val response = apiService.getTodayActivities()
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("获取今日活动失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "获取今日活动失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getActivityStats(): Flow<Result<ActivityStatsResponse>> = flow {
        try {
            val response = apiService.getActivityStats()
            if (response.isSuccessful && response.body()?.success == true) {
                response.body()?.data?.let { 
                    emit(Result.success(it))
                } ?: emit(Result.failure(Exception("获取活动统计失败")))
            } else {
                emit(Result.failure(Exception(response.body()?.message ?: "获取活动统计失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}