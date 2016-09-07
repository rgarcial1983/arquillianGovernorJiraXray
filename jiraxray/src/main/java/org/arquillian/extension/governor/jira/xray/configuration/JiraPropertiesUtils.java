package org.arquillian.extension.governor.jira.xray.configuration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;

public class JiraPropertiesUtils {
    private static final Logger LOG = Logger.getLogger(JiraPropertiesUtils.class.getName());

    private static JiraPropertiesUtils instance = null;

    private Properties propJira = new Properties();

    private static String filePropertiesConfig = "configJiraXray.properties";

    /**
     * @return Instance Class JiraPropertiesUtils
     */
    public static JiraPropertiesUtils getInstance() {        

        if (instance == null) {
            instance = new JiraPropertiesUtils();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    public JiraPropertiesUtils() {        
        try {
            InputStream inputGec = JiraPropertiesUtils.class.getClassLoader().getResourceAsStream(filePropertiesConfig);

            if (inputGec != null) {
                propJira.load(new InputStreamReader(inputGec, "UTF-8"));
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }        
    }

    /**
     * Get value of key
     * @param key
     * @return
     */
    public String getValorKey(String key) {
        return propJira.getProperty(key);
    }
}
