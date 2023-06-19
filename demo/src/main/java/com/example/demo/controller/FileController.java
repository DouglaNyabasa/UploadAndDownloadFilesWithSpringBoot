package com.example.demo.controller;

import com.example.demo.dto.ResponseData;
import com.example.demo.model.FileData;
import com.example.demo.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        FileData fileData = null;
        String downloadURL = "";
        fileData = fileService.saveFile(file);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileData.getId())
                .toUriString();
        return new ResponseData(fileData.getFiletype(), downloadURL, file.getContentType(), file.getSize() );
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {

        FileData fileData = null;
        fileData = fileService.getFile(fileId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(fileData.getFiletype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"fileData; filename =\""+fileData.getFileName() + "\"")
                .body(new ByteArrayResource(fileData.getFileData()));
    }
}
