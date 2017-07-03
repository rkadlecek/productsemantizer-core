package sk.kadlecek.productsemantizer.bean.api;

public class FileUploadResult {

    /**
     * Original filename of the file.
     */
    private String originalFilename;

    /**
     * FileName of the file in the server storage.
     */
    private String storageFilename;

    public FileUploadResult() { super(); }

    public FileUploadResult(String originalFilename, String storageFilename) {
        this.originalFilename = originalFilename;
        this.storageFilename = storageFilename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStorageFilename() {
        return storageFilename;
    }

    public void setStorageFilename(String storageFilename) {
        this.storageFilename = storageFilename;
    }
}
