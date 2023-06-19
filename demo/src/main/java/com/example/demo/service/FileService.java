package com.example.demo.service;

import com.example.demo.model.FileData;
import com.example.demo.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
public class FileService {

    private final FileRepository fileRepository;


    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileData saveFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")){
                throw new Exception("File Name Is Invalid Path" + fileName);
            }
        FileData fileData = new FileData(fileName,file.getContentType(),file.getBytes());
            return fileRepository.save(fileData);
        }catch (Exception e){
            throw new Exception("Failed to Save FIle"+ fileName);
        }
    }

    public FileData getFile(String fileId) throws Exception {
        return fileRepository.findById(fileId).orElseThrow(()-> new Exception("FIle not found with ID"+fileId));
    }
}
