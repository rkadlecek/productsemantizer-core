package sk.kadlecek.productsemantizer.service;

import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sk.kadlecek.productsemantizer.constant.ApplicationConstant;

import javax.annotation.PostConstruct;

@Service("ConfigurationService")
public class PropsConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(PropsConfigurationServiceImpl.class);

    private CompositeConfiguration configuration;

    @PostConstruct
    public void loadConfiguration() throws ConfigurationException {

        try {
            configuration = new CompositeConfiguration();
            String confPath = getSemantizerHome() + ApplicationConstant.APP_CONFIG_FOLDER;
            FileBasedConfigurationBuilder<FileBasedConfiguration> fileBuilder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure((new Parameters()).fileBased()
                                .setBasePath(confPath)
                                .setFileName(ApplicationConstant.APP_CONFIG_FILENAME));


            configuration.addConfiguration(fileBuilder.getConfiguration());

        } catch(ConfigurationException cex) {
            // loading of the configuration file failed
            logger.error("Failed to load application configuration from '{}': {}",
                    ApplicationConstant.APP_CONFIG_FILENAME, cex.getMessage());
            throw cex;
        }
    }

    private String getSemantizerHome() {
        return System.getProperty(ApplicationConstant.SEMANTIZER_HOME);
    }

    /*@Override
    public String getDatabaseUri() {
        String defaultUri = "jdbc:h2:" + getSemantizerHome() + ApplicationConstant.Default.DB_URI_PATH_SUFFIX +
                ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
        return configuration.getString(ApplicationConstant.Configuration.DATABASE_URI, defaultUri);
    }*/

    @Override
    public String getFileStoragePath() {
        String defaultPath = getSemantizerHome() + ApplicationConstant.Default.FILE_STORAGE_PATH_SUFFIX;
        return configuration.getString(ApplicationConstant.Configuration.FILE_STORAGE_PATH, defaultPath);
    }

    @Override
    public String getOdalicBaseUri() {
        return configuration.getString(ApplicationConstant.Configuration.ODALIC_BASE_URI);
    }

    @Override
    public String getOdalicUsername() {
        return configuration.getString(ApplicationConstant.Configuration.ODALIC_USERNAME);
    }

    @Override
    public String getOdalicPassword() {
        return configuration.getString(ApplicationConstant.Configuration.ODALIC_PASSWORD);
    }


}
