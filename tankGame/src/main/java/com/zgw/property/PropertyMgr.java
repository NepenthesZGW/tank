package com.zgw.property;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

    private PropertyMgr(){ }
    public  static final String get(String key){ return properties == null ? null : (String)properties.get(key); }
    private static final Properties properties=new Properties();
    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
            System.out.println("--------配置文件加载完毕-------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
