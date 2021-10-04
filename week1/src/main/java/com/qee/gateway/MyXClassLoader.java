package com.qee.gateway;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: learning
 * @Package: com.qee
 * @ClassName: MyClassLoader
 * @Description:
 * @Date: 2021/9/14 7:52 下午
 * @Version: 1.0
 */
public class MyXClassLoader extends URLClassLoader {

    private List<File> allFiles = new ArrayList<>();


    private Map<String, File> fileNameMap = new HashMap<>();

    public MyXClassLoader(URL[] urls) {
        super(urls);
        try {
            File dir = new File(getURLs()[0].toURI());
            findFiles(dir, ".xlass");
            for (File file : allFiles) {
                String path = getURLs()[0].toURI().getPath();
                String fileName = file.getPath();
                fileName = fileName.substring(fileName.indexOf(path) + path.length());
                fileNameMap.put(fileName, file);

            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Illegal Argument Exception");
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            File file = fileNameMap.get(name + ".xlass");
            if (file == null) {
                return null;
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int size = bufferedInputStream.available();
            byte[] buf = new byte[size];
            bufferedInputStream.read(buf, 0, size);

            for (int i = 0; i < size; i++) {
                buf[i] = (byte) (255 - buf[i]);
            }
            return defineClass(name, buf, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void findFiles(File file, String suffix) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                return;
            }
            for (File childFile : listFiles) {
                findFiles(childFile, suffix);
            }
        } else if (file.getName().endsWith(suffix)) {
            this.allFiles.add(file);
        }
    }


}
