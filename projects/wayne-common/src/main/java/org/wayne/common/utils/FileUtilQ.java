package org.wayne.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @Description:
 * @author: lwq
 */
@Slf4j
public class FileUtilQ {

    /**
     * 方法 1：使用 FileWriter 写文件
     */
    public static void fileWriterMethod(String filepath, String content) throws IOException {
        final File file = new File(filepath);
        if (file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.append(content);
        }
    }

    /**
     * 读文件
     */
    public static String read(String filePath){
        StringBuilder res = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String str;
            while ((str = in.readLine()) != null) {
                res.append(str);
            }
        } catch (IOException e) {
            log.error("读取文件出错",e);
        }
        return res.toString();
    }

//    public static void main(String[] args) {
//        final String json = read("D:/json.txt");
//        final String all = json.replaceAll("g_page_config = ","").replaceAll("}};","");
//        final String cardInfo = ReUtil.findAll(all, "\"data\":.*?\"bottomsearch\":");
//        final String replace = cardInfo.replace("\"data\":", "").replace("},\"bottomsearch\":", "");
//        final Gson gson = new Gson();
//        final JsonRoot jsonRoot = gson.fromJson(replace, JsonRoot.class);
//        System.out.println(all);
//    }

}
