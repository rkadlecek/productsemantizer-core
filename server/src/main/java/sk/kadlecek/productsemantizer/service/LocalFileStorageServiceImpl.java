package sk.kadlecek.productsemantizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import sk.kadlecek.productsemantizer.bean.api.FileUploadResult;
import sk.kadlecek.productsemantizer.exception.FileStorageException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("FileStorageService")
public class LocalFileStorageServiceImpl implements FileStorageService {

    private final ConfigurationService configurationService;

    private static final String FILE_EXTENSION = ".csv";
    private static final int FOLDER_LEVELS = 2;

    @Autowired
    public LocalFileStorageServiceImpl(ConfigurationService configurationService) {
        Assert.notNull(configurationService, "ConfigurationService must not be null!");
        this.configurationService = configurationService;
    }

    @Override
    public FileUploadResult save(MultipartFile file) throws FileStorageException {
        String fileName = generateStorageFileName();
        String innerStoragePath = generateInnerStoragePath(fileName);

        String storagePath = generateStoragePath(configurationService.getFileStoragePath(), innerStoragePath);
        ensureDirectoryExists(storagePath);

        String filePath = assembleStoragePathAndFileName(storagePath, fileName);
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
            return new FileUploadResult(file.getOriginalFilename(), assembleStoragePathAndFileName(innerStoragePath, fileName));
        }catch (IOException e) {
            throw new FileStorageException("Failed to save file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public File getRepositoryFile(String repositoryFilePath) throws FileStorageException {
        String filePath = assembleStoragePathAndFileName(configurationService.getFileStoragePath(), repositoryFilePath);
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return new File(filePath);
            }else {
                throw new FileStorageException("Failed to retrieve file: " + repositoryFilePath + ". File does not exist.");
            }
        }catch (Exception e) {
            throw new FileStorageException("Failed to retrieve file: " + repositoryFilePath, e);
        }
    }

    private void ensureDirectoryExists(String path) throws FileStorageException {
        File dir = new File(path);
        try {
            if (!dir.exists()) dir.mkdirs();
        } catch (Exception e) {
            throw new FileStorageException("Failed to create directory: " + path, e);
        }
    }

    private String generateStorageFileName() {
        return UUID.randomUUID().toString().replaceAll("-", "") + FILE_EXTENSION;
    }

    /**
     * Generates the inner levels of filesystem path.
     * E.g. ("abcd1234.dat") -> "a/b/abcd1234.dat"
     * @param fileName
     * @return
     */
    private String generateInnerStoragePath(String fileName) {
        return File.separator + String.join(File.separator, fileName.substring(0, FOLDER_LEVELS).split(""));
    }

    /**
     * Generates the inner levels of filesystem path.
     * E.g. ("storagePath","a/b") -> "storagePath/a/b"
     * @param storagePath
     * @param innerStoragePath
     * @return
     */
    private String generateStoragePath(String storagePath, String innerStoragePath) {
        return ensurePathDoesNotEndWithSeparator(storagePath) + innerStoragePath;
    }

    private String assembleStoragePathAndFileName (String storagePath, String fileName) {
        return ensurePathEndsWithSeparator(storagePath) + fileName;
    }

    private String ensurePathEndsWithSeparator(String path) {
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        return path;
    }

    private String ensurePathDoesNotEndWithSeparator(String path) {
        if (path.endsWith(File.separator)) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        return path;
    }
}
