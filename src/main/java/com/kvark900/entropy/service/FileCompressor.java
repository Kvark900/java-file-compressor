package com.kvark900.entropy.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Keno&Kemo on 28.01.2018..
 */
@Service
public class FileCompressor {

    public void compress(InputStream inputStream, String fileName, File compressed) throws IOException {
        FileOutputStream fos = new FileOutputStream(compressed.getName());
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        final byte[] bytes = new byte[1024];
        int length;
        while((length = inputStream.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        inputStream.close();
        zipOut.close();
        fos.close();
    }
}
