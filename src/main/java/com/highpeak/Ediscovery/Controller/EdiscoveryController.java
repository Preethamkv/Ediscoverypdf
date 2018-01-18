package com.highpeak.Ediscovery.Controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.ws.rs.core.UriBuilder;
import javax.xml.ws.Response;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/ediscovery")
public class EdiscoveryController {

    @RequestMapping(value = "/pdf",method = RequestMethod.GET,produces = "application/pdf")
    public ResponseEntity<byte[]>  showPdf() throws IOException{

        URL url=new URL("https://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf");
        URLConnection urlConn = url.openConnection();
        InputStream in = (url.openStream());
        ByteArrayOutputStream pdf= new ByteArrayOutputStream();

        byte[] buffer=new byte[(1024)];
        int byteread;
        while ((byteread=in.read(buffer))!=-1){
            pdf.write(buffer,0,byteread);
        }
        byte[] pdfContents=pdf.toByteArray();


       /* Path path = Paths.get(url.getFile());
        byte[] pdfContents = null;
        try {
            pdfContents = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename ="test.pdf";
        headers.add("Content-Disposition", "inline;filename=test.pdf");
        //headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
                pdfContents, headers, HttpStatus.OK);
        return response;

    }

}
