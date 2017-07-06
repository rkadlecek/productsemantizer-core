package sk.kadlecek.productsemantizer.service;

import org.springframework.web.multipart.MultipartFile;
import sk.kadlecek.productsemantizer.bean.api.FileUploadResult;
import sk.kadlecek.productsemantizer.exception.FileStorageException;

import java.io.File;

public interface FileStorageService {

    FileUploadResult save(MultipartFile file) throws FileStorageException;
    File getRepositoryFile(String repositoryFilePath) throws FileStorageException;

}
