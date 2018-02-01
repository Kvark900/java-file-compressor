package com.kvark900.entropy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

/**
 * Created by Keno&Kemo on 07.01.2018..
 */
@Service
public class FileDownloader {
    private IOStreamsCloser ioStreamsCloser;

    public FileDownloader() {
    }

    @Autowired
    public FileDownloader(IOStreamsCloser ioStreamsCloser) {
        this.ioStreamsCloser = ioStreamsCloser;
    }

    public void downloadFile(File compressedFile, HttpServletResponse response ){
        String mimeType= URLConnection.
                guessContentTypeFromName(compressedFile.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype : "+mimeType);

        response.setContentType(mimeType);

        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        response.setHeader("Content-Disposition",
                String.format("attachment; filename=\"%s\"", compressedFile.getName()));
        response.setContentLength((int)compressedFile.length());


        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(compressedFile));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
