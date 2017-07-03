package sk.kadlecek.productsemantizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.kadlecek.productsemantizer.bean.api.FileUploadResult;
import sk.kadlecek.productsemantizer.service.FileStorageService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/files")
public class FileController extends AbstractController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        Assert.notNull(fileStorageService, "FileStorageService must not be null!");
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public @ResponseBody FileUploadResult upload(@RequestParam("file") MultipartFile file, HttpServletResponse response)
        throws Exception {
        return respondWithCreated(response, () -> fileStorageService.save(file));
    }


}
