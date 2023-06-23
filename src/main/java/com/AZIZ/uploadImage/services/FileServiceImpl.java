package com.AZIZ.uploadImage.services;

import com.AZIZ.uploadImage.exceptions.BadApiRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileNameWithExtension = fileName+extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")){

            File folder = new File(path);

            if(!folder.exists()){
                //create the folder
                folder.mkdirs();
            }
            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        }else {
            throw new BadApiRequest("File with extension"+extension+" is not allowed");
        }



    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path+File.separator+name;

        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;


    }
}
