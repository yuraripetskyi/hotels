package com.ua.hotels.service;

import com.ua.hotels.dao.ImageDAO;
import com.ua.hotels.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {
    private final ImageDAO repository;
    private final ResourceLoader resourceLoader;
    public static String UPLOAD_PATH = System.getProperty("user.dir")
            + File.separator
            + "src"
            + File.separator
            + "main"
            + File.separator
            + "resources"
            + File.separator
            + "static"
            + File.separator
            + "images"
            + File.separator;

    @Autowired
    public ImageService(ImageDAO repository, ResourceLoader resourceLoader) {
        this.repository = repository;
        this.resourceLoader = resourceLoader;
    }

    public void save(Image image){
        repository.save(image);
    }


    public Resource findOneImage(String filename) {
        return resourceLoader.getResource("file:" + UPLOAD_PATH + filename);
    }

    public void createImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {

                Files.copy(file.getInputStream(), Paths.get(UPLOAD_PATH, file.getOriginalFilename()));
        }
    }

    public void deleteImage(String filename){
        Image byName = repository.findByName(filename);
        try {
            Files.delete(Paths.get(UPLOAD_PATH, filename));
        } catch (IOException e) {
            System.out.println ("----------");
        }
        repository.deleteById(byName.getId());
    }


}
