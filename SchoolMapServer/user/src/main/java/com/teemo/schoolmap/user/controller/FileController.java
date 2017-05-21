package com.teemo.schoolmap.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Teemo on 2017.05.21.
 */
@RestController
@RequestMapping("api/file")
public class FileController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public void uploadFile(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()){
            return;
        }
        try {
            StringBuilder path = new StringBuilder();
            path.append(".//user//src//main//resources//static//image//").append(file.getOriginalFilename());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path.toString())));
            out.write(file.getBytes());
            out.flush();
            out.close();
            logger.info("uploadFile={}", path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
