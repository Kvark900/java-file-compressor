package com.kvark900.entropy.controller;

import com.kvark900.entropy.service.FileCompressor;
import com.kvark900.entropy.service.FileDecompressor;
import com.kvark900.entropy.service.FileDownloader;
import com.kvark900.entropy.service.ZipFileValidator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by Keno&Kemo on 29.12.2017..
 */
@Controller
public class MainController {

    private FileCompressor fileCompressor;
    private FileDecompressor fileDecompressor;
    private FileDownloader fileDownloader;
    private ZipFileValidator zipFileValidator;

    @Autowired
    public MainController(FileCompressor fileCompressor, FileDecompressor fileDecompressor, FileDownloader fileDownloader, ZipFileValidator zipFileValidator) {
        this.fileCompressor = fileCompressor;
        this.fileDecompressor = fileDecompressor;
        this.fileDownloader = fileDownloader;
        this.zipFileValidator = zipFileValidator;
    }

    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/compressFile", method = RequestMethod.POST)
    public String compressFile(@RequestParam("fileToCompress") MultipartFile file, Model model,
                              HttpServletResponse response) throws IOException {

        if(file.isEmpty()){
            model.addAttribute("noFileSelectedToCompress", true);
            return "home";
        }

        else {
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
            String compressedFilePath = FilenameUtils.getBaseName(fileLocation) + ".zip";

            //compressing file
            File compressedFile = new File(compressedFilePath);
            fileCompressor.compress(file.getInputStream(), file.getOriginalFilename(), compressedFile);

            //downloading compressed file
            fileDownloader.downloadFile(compressedFile, response);

            compressedFile.delete();

            return null;
        }
    }

    @RequestMapping(value = "/decompressFile", method = RequestMethod.POST)
    public String decompressFile(@RequestParam("fileToDecompress") MultipartFile file, Model model,
                               MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {

        if(file.isEmpty()){
            model.addAttribute("noFileSelectedToDeCompress", true);
            return "home";
        }
        if (!zipFileValidator.isValid(file)){
            model.addAttribute("invalidZipFile", true);
            return "home";
        }

        else{
            //decompressing file
            File decompressedFile = fileDecompressor.decompress(file.getInputStream());

            //downloading decompressed file
            fileDownloader.downloadFile(decompressedFile, response);

            decompressedFile.delete();
            return null;
        }
    }
}
