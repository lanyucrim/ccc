package com.lanyu.controller;


import com.lanyu.common.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/common")
@RestController
@Slf4j
public class CommonController {

    @Value("${lanyu.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file)
    {

        File dir=new File(basePath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String fileName=UUID.randomUUID().toString()+suffix;
        log.info(file.toString());
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse httpServletResponse) throws IOException {
        try {
            FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));
            ServletOutputStream outputS=httpServletResponse.getOutputStream();
            httpServletResponse.setContentType("image/jpeg");

            int len=0;
            byte[] bytes=new byte[1024];

            while ((len=fileInputStream.read(bytes))!=-1)
            {
                outputS.write(bytes,0,len);
                outputS.flush();
            }


            outputS.close();
            fileInputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
