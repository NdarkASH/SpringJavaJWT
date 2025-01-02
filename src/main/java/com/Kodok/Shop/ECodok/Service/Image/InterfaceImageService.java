package com.Kodok.Shop.ECodok.Service.Image;

import com.Kodok.Shop.ECodok.Dto.ImageDto;
import com.Kodok.Shop.ECodok.Model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InterfaceImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long ProductId);
    void updateImage(MultipartFile file, Long ProductId);
}
