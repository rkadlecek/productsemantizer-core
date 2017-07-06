package sk.kadlecek.productsemantizer.service;

import org.apache.commons.configuration2.ex.ConfigurationException;

public interface ConfigurationService {

    void loadConfiguration() throws ConfigurationException;

    //String getDatabaseUri();

    String getFileStoragePath();

    String getOdalicBaseUri();

    String getOdalicUsername();

    String getOdalicPassword();
}
