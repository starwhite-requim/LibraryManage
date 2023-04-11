package com.se.librarymanagesystem.controller;

import com.se.librarymanagesystem.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${common.baseURL}")
    private String baseURL;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String originalFileName = file.getOriginalFilename();

        String sufix = originalFileName.substring(originalFileName.lastIndexOf("."));

        String filename = UUID.randomUUID().toString()+sufix;
        try {
            file.transferTo(new File(baseURL+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            try {
                outputStream = response.getOutputStream();
                inputStream = new FileInputStream(new File(baseURL+name));
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len=inputStream.read(bytes))!=-1){
                    outputStream.write(bytes,0,len);
                    outputStream.flush();
                }

            }
            finally {
                if (outputStream != null){
                    outputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
