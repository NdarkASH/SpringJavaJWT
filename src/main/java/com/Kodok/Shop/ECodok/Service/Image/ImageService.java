package com.Kodok.Shop.ECodok.Service.Image;

import com.Kodok.Shop.ECodok.Dto.ImageDto;
import com.Kodok.Shop.ECodok.Exception.ProductNotFoundException;
import com.Kodok.Shop.ECodok.Exception.ResourceNotFoundException;
import com.Kodok.Shop.ECodok.Model.Image;
import com.Kodok.Shop.ECodok.Model.Product;
import com.Kodok.Shop.ECodok.Repository.ImageRepository;
import com.Kodok.Shop.ECodok.Service.Product.InterfaceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements InterfaceImageService{
    private final ImageRepository imageRepository;
    private final InterfaceProductService interfaceProductService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () ->{
            throw new ResourceNotFoundException("No image found with this Id:" + id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long ProductId) {
        Product product = interfaceProductService.getProductById(ProductId);

        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }


    @Override
    public void updateImage(MultipartFile file, Long ProductId) {
        Image image = getImageById(ProductId);
        try {
        image.setFileName(file.getOriginalFilename());
        image.setFileName(file.getOriginalFilename());
        image.setImage(new SerialBlob(file.getBytes()));
        imageRepository.save(image);
        } catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
