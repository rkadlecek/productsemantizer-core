package sk.kadlecek.productsemantizer.constant;

public interface ApplicationConstant {

    String SEMANTIZER_HOME = "semantizer.home";
    String APP_CONFIG_FOLDER = "/config";
    String APP_CONFIG_FILENAME = "application.properties";

    interface Configuration {

        String DATABASE_URI = "database.uri";
        String FILE_STORAGE_PATH = "fileStorage.path";
        String ODALIC_BASE_URI = "odalic.baseUri";
        String ODALIC_USERNAME = "odalic.username";
        String ODALIC_PASSWORD = "odalic.password";
    }

    interface Default {
        String DB_URI_PATH_SUFFIX = "/db";
        String FILE_STORAGE_PATH_SUFFIX = "/files/";
    }

}
