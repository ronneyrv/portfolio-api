package com.ronney.portfolioapi.service;


import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary
                    .uploader()
                    .upload(
                            file.getBytes(),
                            Map.of()
                    );
            return uploadResult
                    .get("secure_url")
                    .toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem. erro: " + e.getMessage());
        }
    }
}
