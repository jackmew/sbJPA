/**
 * 
 */
package org.jack.sbJpa.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jack.sbJpa.model.Image;
import org.jack.sbJpa.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jackho
 *
 */
@Controller
public class FileUploadController {
    
	@Autowired
	ImageRepository ir;
	
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, 
    		@RequestParam("contentType") String contentType , 
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {

            	byte[] bytes = file.getBytes();
            	String fileName = name+"-pic";
            	
            	Image image = new Image();
            	image.setBytes(bytes);
            	image.setContentType(contentType);
            	image.setFileName(fileName);
            	
            	ir.save(image);
            	
            	
                return "You successfully save " + fileName + " into H2 !";
            } catch (Exception e) {
                return "You failed to save " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to save " + name + " because the file was empty.";
        }
    }
    
    @RequestMapping(value="getFile")
    public @ResponseBody String getFile() {
    	
    	List<Image> files = ir.findAll();
    	
    	String fileName = files.get(0).getFileName();
    	
    	System.out.println(fileName);
    	
    	return "getFile";
    }
    
}
