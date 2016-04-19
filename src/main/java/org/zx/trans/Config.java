package org.zx.trans;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config
{
    Properties props;

    String transPrefix = null;

    public Config()
    {
        props = new Properties();
        load();
    }

    private void load()
    {
        props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("./config.properties");
            props.load(fis);
        } catch (IOException e) {
            System.out.println("config.properties not found");
            System.exit(-1);
        }
    }

    public Translatable getTranslator()
    {
        String translatorName = (String) props.get("trans.translator");
        transPrefix = translatorName;
        Translatable translator = null;
        try {
            Class<?> translatorClass = Class.forName("org.zx.trans.translator." + ucfirst(translatorName) + "Translator");
            translator = (Translatable) translatorClass.newInstance();
            translator.setConfig(this);
        } catch (ClassNotFoundException e) {
            System.out.println("Translator " + translatorName + " not found");
            System.exit(-1);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return translator;
    }

    public String getProp(String name)
    {
        return props.getProperty(name);
    }

    public String getTransProp(String name)
    {
        return getProp("trans." + transPrefix + "." + name);
    }

    private String ucfirst(String subject)
    {
        return Character.toUpperCase(subject.charAt(0)) + subject.substring(1);
    }
}
