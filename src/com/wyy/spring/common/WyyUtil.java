package com.wyy.spring.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wyy
 * @Date 2022/02/25
 */
public class WyyUtil {
    private static List<String> fileNameList = new ArrayList<>();

    public static String getBeanNameByClass(String className) {
        return className.substring(0, 1).toLowerCase().concat(className.substring(1));
    }

    public static String getClassFullPath(String javaPath, String fileName) {

        return javaPath + '.' + fileName.substring(0, fileName.indexOf(".class"));
    }

    public static List<String> getAllFilePath(File directory, String currentPath) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                String fileName = file.getName();
                if (!file.isDirectory()) {
                    fileNameList.add(currentPath + '.' + fileName.substring(0, fileName.indexOf(".class")));
                } else {
                    getAllFilePath(file, currentPath + '.' + fileName);
                }
            }
        } else {
            throw new RuntimeException("扫描路径不是文件夹");
        }
        return fileNameList;
    }

}
