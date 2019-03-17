package com.aquarius.common.util;

import android.graphics.Bitmap;

import java.io.*;

public class FileUtil {
    /**
     * @description 运用字节流从文件读取内容(以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件)
     * @author hua.xu
     * @date 2015-12-10下午3:17:15
     * @param
     * @return String
     */
    public static String readFileByBytes(File file) {
        if (!file.exists()) {
            return "";
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            String str = "";
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                str += bis.read(buffer, 0, len);
            }
            bis.close();
            fis.close();

            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @description 运用带缓存的字符流逐行从文件读取内容
     * @author hua.xu
     * @date 2015-12-10下午4:02:39
     * @return String
     */
    public static String readFileByLine(File file) {
        if (!file.exists()) {
            return "";
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = "";
            String temp = null;
            while ((temp = br.readLine()) != null) {
                str += temp;
            }
            br.close();
            fr.close();

            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @description 运用带缓存字节流向文件写入内容
     * @author hua.xu
     * @date 2015-12-10下午3:54:28
     * @param content
     */
    public static void writeFileByBytes(File dir, String content) {
        if (dir.isDirectory()){
            return;
        }
        try {
            // 创建文件
            if (!dir.exists()) {
                dir.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(dir);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(content.getBytes());
            bos.flush();
            bos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 运用字符流向文件写入内容
     * @author hua.xu
     * @date 2015-12-10下午4:18:11
     * @param content
     */
    public static void writeFileByChars(File dir, String content) {
        if (dir.isDirectory()){
            return;
        }
        try {
            // 创建文件
            if (!dir.exists()) {
                dir.createNewFile();
            }

            FileWriter fw = new FileWriter(dir);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 保存图片文件到本地
     * @author hua.xu
     * @date 2015-12-10下午4:18:11
     */
    public static void savePic(Bitmap b, File dir) {
        if (dir.isDirectory()){
            return;
        }
        FileOutputStream fos = null;
        try {
            // 创建文件
            if (!dir.exists()) {
                dir.createNewFile();
            }
            fos = new FileOutputStream(dir);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
