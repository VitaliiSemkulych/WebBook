package com.example.demo.repository;

import com.example.demo.model.FileInfo;
import org.springframework.data.repository.CrudRepository;


public interface FileInfoRepository extends CrudRepository<FileInfo,Long> {
}
