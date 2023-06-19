package com.example.demo.repository;


import com.example.demo.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileData,String> {
}
