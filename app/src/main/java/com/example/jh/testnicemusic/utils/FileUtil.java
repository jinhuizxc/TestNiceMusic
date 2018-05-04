package com.example.jh.testnicemusic.utils;

import android.os.Environment;

import com.lzx.musiclibrary.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by xian on 2018/1/13.
 */

public class FileUtil {
    /**
     * 创建日志文件
     */
    public static File createLogFile(String fileName) {
        return createFile(fileName, "/NiceMusic/log");
    }

    /**
     * 向文件中写入内容
     */
    public static void writeFile(String filePathAndName, String fileContent) {
        try {
            OutputStream outputStream = new FileOutputStream(filePathAndName);
            OutputStreamWriter out = new OutputStreamWriter(outputStream);
            out.write(fileContent);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createFile(String fileName, String path) {
        String filePath = Environment.getExternalStorageDirectory().getPath() + path;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File imageFile = new File(file, fileName);
        if (imageFile.exists()) {
            boolean isDelete = imageFile.delete();
            LogUtil.i("isDelete = " + isDelete);
        }
        try {
            boolean isCreate = imageFile.createNewFile();
            LogUtil.i("isCreate = " + isCreate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }
}
