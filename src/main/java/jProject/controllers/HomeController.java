package jProject.controllers;

import jProject.storage.StorageService;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model) {

        try {
            model.addAttribute("files", storageService.loadAll()
                    .map(path -> MvcUriComponentsBuilder
                            .fromMethodName(HomeController.class, "serveFile", path.getFileName().toString())
                            .build().toString())
                    .collect(Collectors.toList()));
        }
        catch(Exception e){

        }

        return "index";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    private final StorageService storageService;

    @Autowired
    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/Gallery")
    public String listUploadedFiles(Model model)  {
    try {

        model.addAttribute("files", storageService.loadAll()
                .map(path -> MvcUriComponentsBuilder
                                .fromMethodName(HomeController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));
    }
    catch(Exception e){

    }
        return "gallery";
    }



    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping(value = "/accessDenied")
    public String handleAccessDenied(){
        return "login";
    }









}
