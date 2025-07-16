package com.throwit.app.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 截图服务接口
 * 负责接收和处理上传的截图
 */
public interface ScreenshotService {
    
    /**
     * 压缩图片
     * 
     * @param image 原始图片文件
     * @return 压缩后的图片字节数组
     */
    byte[] compressImage(MultipartFile image);
    
    /**
     * 验证图片格式和大小
     * 
     * @param image 图片文件
     * @return true-验证通过，false-验证失败
     */
    boolean validateImage(MultipartFile image);
}