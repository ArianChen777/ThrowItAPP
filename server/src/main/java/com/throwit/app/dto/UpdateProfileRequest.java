package com.throwit.app.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * 更新用户资料请求DTO
 */
@Data
public class UpdateProfileRequest {
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Size(max = 20, message = "手机号长度不能超过20个字符")
    private String phone;
    
    @Size(max = 255, message = "头像URL长度不能超过255个字符")
    private String avatarUrl;
}