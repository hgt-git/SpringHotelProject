package jProject.controllers;

import jProject.forms.ImgForm;
import jProject.services.ImgService;
import jProject.services.RoomTypeService;
import jProject.storage.StorageFileNotFoundException;
import jProject.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/files")
public class FileUploadController {

    @Autowired
    private ImgService imgService;

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) {

        try {


            model.addAttribute("files", storageService
                    .loadAll()
                    .map(path ->
                            MvcUriComponentsBuilder
                                    .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                    .build().toString())
                    .collect(Collectors.toList()));
        }
        catch (Exception e){

        }

        return "uploadForm";
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

    @Autowired
    RoomTypeService roomTypeService;

    @PostMapping("/")

        public String handleFileUpload(@RequestParam int roomTypeId, @ModelAttribute("fileForm") MultipartFile file, RedirectAttributes redirectAttributes) {


        if(file.getContentType().equalsIgnoreCase("image/jpg")||file.getContentType().equalsIgnoreCase("image/jpeg")||file.getContentType().equalsIgnoreCase("image/png")) {

            storageService.store(file);
            ImgForm imgForm = new ImgForm();
            imgForm.setFileType(file.getOriginalFilename().split("\\.")[1]);
            imgForm.setImageCurrentName(imgService.imageId()+"."+file.getOriginalFilename().split("\\.")[1]);
            imgForm.setImageOriginalName(file.getOriginalFilename().split("\\.")[0]);
            imgForm.setRoomId(roomTypeService.findById(roomTypeId));
            imgService.addNewImg(imgForm);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully created new room !");
        }
        else if(file.isEmpty()){redirectAttributes.addFlashAttribute("message",
                "Image must be specified!");}
        else{redirectAttributes.addFlashAttribute("message",
                "Only images allowed!");}
        return "redirect:/admin/rooms";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}