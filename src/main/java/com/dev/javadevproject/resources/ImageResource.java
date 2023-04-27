package com.dev.javadevproject.resources;

import com.dev.javadevproject.dto.auth.JwtResponse;
import com.dev.javadevproject.entities.ImageEntity;
import com.dev.javadevproject.repositories.ImageRepo;
import com.dev.javadevproject.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/image")
public class ImageResource {
    @Autowired
    ImageService imageService;

    @Autowired
    private ImageRepo imageRepo;

    @GetMapping(value = "/show", produces = MediaType.IMAGE_JPEG_VALUE)
    public  @ResponseBody byte[] show(@RequestParam Integer imageId) throws IOException {
        var image = imageRepo.findById(imageId);
        if(image.isEmpty())
            return imageRepo.findById(-1).get().getData();
        return image.get().getData();
    }

    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam Integer id, @RequestPart("file") MultipartFile file) throws IOException{
        return ResponseEntity.ok(imageService.create(id, file.getBytes()).getId());
    }
}
