package com.dev.javadevproject.services;

import com.dev.javadevproject.entities.ImageEntity;
import com.dev.javadevproject.repositories.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private ImageRepo imageRepo;

    public ImageEntity create(Integer id, byte[] file){
        ImageEntity imageEntity = new ImageEntity();
        if(imageRepo.existsById(id)) imageRepo.deleteById(id);
        imageEntity.setId(id);
        imageEntity.setData(file);
        imageRepo.save(imageEntity);
        return imageEntity;
    }
}
