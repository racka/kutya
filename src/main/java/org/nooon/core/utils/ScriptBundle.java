package org.nooon.core.utils;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.*;
import java.lang.annotation.Annotation;

public class ScriptBundle {

    @Produces
    @ScriptName
    public String getStringConfigValue(InjectionPoint injectionPoint) throws IOException {

        Annotation nameAnnotation = injectionPoint.getAnnotated().getAnnotation(ScriptName.class);
        String scriptName = nameAnnotation != null ? ((ScriptName) nameAnnotation).name() : "";

        if (!scriptName.isEmpty()) {
            return convertStreamToString(getClass().getClassLoader().getResourceAsStream(scriptName));
        }


        return "";
    }

    public String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
