package com.cdr.cdrfile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class FileController {

    @Value("${uploaded_folder}")
    private String uploadedFolder;

    @RequestMapping(value = "/download/{audioName}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity playAudio(HttpServletRequest request, HttpServletResponse response, @PathVariable("audioName") String wav) throws FileNotFoundException {

        String str[] = wav.split("-") ;

        String year = str[3].substring(0,4);
        String month = str[3].substring(4,6);
        String day = str[3].substring(6,8);

        String file = uploadedFolder + "/" + year + "/" + month + "/" + day + "/" + wav;

        long length = new File(file).length();

        InputStreamResource inputStreamResource = new InputStreamResource( new FileInputStream(file));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);

    }

}
