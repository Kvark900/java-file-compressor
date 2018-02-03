package com.kvark900.entropy.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Keno&Kemo on 03.02.2018..
 */
@Service
public class MultipartFileConverter {

    public File convert(MultipartFile file)
    {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = null;
        try {
            convFile.createNewFile();
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convFile;
    }
}
