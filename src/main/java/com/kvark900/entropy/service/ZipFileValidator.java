package com.kvark900.entropy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

/**
 * Created by Keno&Kemo on 03.02.2018..
 */
@Service
public class ZipFileValidator {
    private MultipartFileConverter multipartFileConverter;

    public ZipFileValidator() {
    }

    @Autowired
    public ZipFileValidator(MultipartFileConverter multipartFileConverter) {
        this.multipartFileConverter = multipartFileConverter;
    }

    public boolean isValid(final MultipartFile multipartFile) {
        File file = multipartFileConverter.convert(multipartFile);
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(file);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            file.delete();
            try {
                if (zipfile != null) {
                    zipfile.close();
                    zipfile = null;
                }
            } catch (IOException e) {
            }
        }
    }
}
