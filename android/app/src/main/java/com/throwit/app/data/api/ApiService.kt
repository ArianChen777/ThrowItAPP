package com.throwit.app.data.api

import com.throwit.app.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("api/v1/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>
    
    @POST("api/v1/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<LoginResponse>>
    
    @GET("api/v1/user/info")
    suspend fun getUserInfo(): Response<ApiResponse<User>>
    
    @PUT("api/v1/user/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<ApiResponse<User>>
    
    @Multipart
    @POST("api/v1/app/upload")
    suspend fun uploadScreenshot(
        @Part screenshot: MultipartBody.Part
    ): Response<ApiResponse<ScreenshotAnalysisResponse>>
    
    @GET("api/v1/activity/today")
    suspend fun getTodayActivities(): Response<ApiResponse<List<Activity>>>
    
    @GET("api/v1/activity/stats")
    suspend fun getActivityStats(): Response<ApiResponse<ActivityStatsResponse>>
    
    @GET("api/v1/user/settings/reminders")
    suspend fun getRemindSettings(): Response<ApiResponse<RemindSettingsResponse>>
    
    @PUT("api/v1/user/settings/reminders")
    suspend fun updateRemindSettings(@Body request: RemindSettingsRequest): Response<ApiResponse<RemindSettingsResponse>>
}

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class UpdateProfileRequest(
    val nickname: String,
    val avatar: String
)

data class ScreenshotAnalysisResponse(
    val activityType: String,
    val appName: String,
    val content: String,
    val needReminder: Boolean,
    val reminderContent: String?
)

data class RemindSettingsRequest(
    val entertainmentThreshold: Int,
    val reminderContent: String,
    val aiCharacter: String
)

data class RemindSettingsResponse(
    val entertainmentThreshold: Int,
    val reminderContent: String,
    val aiCharacter: String
)