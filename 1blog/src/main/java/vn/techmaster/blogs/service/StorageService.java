package vn.techmaster.blogs.service;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.blogs.exception.StorageException;

@Service
public class StorageService {
    
    @Value("src/main/resource/static/photos")
    private String path;

    public void uploadFile(MultipartFile file){
        if(file.isEmpty()){
            throw new StorageException("failed to store empty file");
        }
        String fileName = file.getOriginalFilename();
        try {
            var is = file.getInputStream();
            Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            var msg = String.format("Failed to store file %s ", fileName);
            throw new StorageException(msg, e);
        }
    }

    public void uploadFiles(MultipartFile[] files){
        for(var file : files){
            uploadFile(file);
        }
    }

    public void uploadAndSaveFileWithNewName(MultipartFile file, String newName){
        if(file.isEmpty()){
            throw  new  StorageException("Failed to store empty file");
        }
        try {
            var is = file.getInputStream();
            Files.copy(is, Paths.get(path + newName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            var msg = String.format("Failed to store file %s", newName);
            throw new StorageException(msg, e);
        }
    }
}
