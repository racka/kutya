package org.nooon.core.utils;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.Properties;

@ApplicationScoped
public class TextResources {

    private Properties languageFile;

    public TextResources() {
        try {
            languageFile = new Properties();
            languageFile.load(TextResources.class.getResourceAsStream("/texts.properties"));
        } catch (IOException e) {
            // TODO
            System.out.println(e.getMessage());
        }
    }

    public String get(String key) {
        return languageFile.containsKey(key) ? (String) languageFile.get(key) : key;
    }

    public <E extends Enum> String get(E param) {
        if (param != null) {
            String key = param.getClass().getSimpleName() + "." + param.name();
            return get(key);
        }
        return "";
    }

}
