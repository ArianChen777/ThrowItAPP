package com.throwit.app.service.impl;

import com.throwit.app.service.ScreenshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 截图服务实现类
 */
@Slf4j
@Service
public class ScreenshotServiceImpl implements ScreenshotService {
    
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/webp"
    );
    
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final int MAX_WIDTH = 1080;
    private static final int MAX_HEIGHT = 1920;
    private static final float COMPRESSION_QUALITY = 0.8f;
    
    @Override
    public byte[] compressImage(MultipartFile image) {
        try {
            // 验证图片
            if (!validateImage(image)) {
                throw new IllegalArgumentException("图片格式不支持或文件过大");
            }
            
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(image.getInputStream());
            if (originalImage == null) {
                throw new IllegalArgumentException("无法读取图片文件");
            }
            
            log.debug("原始图片尺寸：{}x{}", originalImage.getWidth(), originalImage.getHeight());
            
            // 计算压缩后的尺寸
            Dimension newDimension = calculateNewDimension(originalImage.getWidth(), originalImage.getHeight());
            
            // 压缩图片
            BufferedImage compressedImage = resizeImage(originalImage, (int)newDimension.getWidth(), (int)newDimension.getHeight());
            
            // 转换为字节数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(compressedImage, "jpg", baos);
            byte[] compressedData = baos.toByteArray();
            
            log.debug("图片压缩完成：{}x{}, 原始大小：{} bytes, 压缩后大小：{} bytes", 
                    compressedImage.getWidth(), compressedImage.getHeight(), 
                    image.getSize(), compressedData.length);
            
            return compressedData;
            
        } catch (IOException e) {
            log.error("图片压缩失败", e);
            throw new RuntimeException("图片处理失败", e);
        }
    }
    
    @Override
    public boolean validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            log.warn("图片文件为空");
            return false;
        }
        
        // 检查文件大小
        if (image.getSize() > MAX_FILE_SIZE) {
            log.warn("图片文件过大：{} bytes", image.getSize());
            return false;
        }
        
        // 检查文件类型
        String contentType = image.getContentType();
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            log.warn("不支持的图片格式：{}", contentType);
            return false;
        }
        
        return true;
    }
    
    /**
     * 计算压缩后的尺寸
     */
    private Dimension calculateNewDimension(int originalWidth, int originalHeight) {
        double widthRatio = (double) MAX_WIDTH / originalWidth;
        double heightRatio = (double) MAX_HEIGHT / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);
        
        // 如果原图已经小于最大尺寸，则不放大
        if (ratio > 1.0) {
            ratio = 1.0;
        }
        
        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);
        
        return new Dimension(newWidth, newHeight);
    }
    
    /**
     * 调整图片尺寸
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        
        return resizedImage;
    }
}