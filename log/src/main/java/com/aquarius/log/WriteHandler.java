package com.aquarius.log;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WriteHandler extends Handler {
    @NonNull
    private final String folder;
    private final int maxFileSize;
    /** 年-月-日 显示格式 */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";
    WriteHandler(@NonNull Looper looper, @NonNull String folder, int maxFileSize) {
        super(LogUtil.checkNotNull(looper));
        this.folder = LogUtil.checkNotNull(folder);
        this.maxFileSize = maxFileSize;
    }

    @SuppressWarnings("checkstyle:emptyblock")
    @Override
    public void handleMessage(@NonNull Message msg) {
        String content = (String) msg.obj;

        FileWriter fileWriter = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
        String fileName = sdf.format(new Date());
                File logFile = getLogFile(folder, fileName);

        try {
            fileWriter = new FileWriter(logFile, true);

            writeLog(fileWriter, content);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e1) { /* fail silently */ }
            }
        }
    }

    /**
     * This is always called on a single background thread.
     * Implementing classes must ONLY write to the fileWriter and nothing more.
     * The abstract class takes care of everything else including close the stream and catching IOException
     *
     * @param fileWriter an instance of FileWriter already initialised to the correct file
     */
    private void writeLog(@NonNull FileWriter fileWriter, @NonNull String content) throws IOException {
        LogUtil.checkNotNull(fileWriter);
        LogUtil.checkNotNull(content);

        fileWriter.append(content);
    }

    private File getLogFile(@NonNull String folderName, @NonNull String fileName) {
        LogUtil.checkNotNull(folderName);
        LogUtil.checkNotNull(fileName);

        File folder = new File(folderName);
        if (!folder.exists()) {
            //TODO: What if folder is not created, what happens then?
            folder.mkdirs();
        }

//        int newFileCount = 0;
        File newFile;
        File existingFile = null;

        newFile = new File(folder, String.format("%s.txt", fileName));
        while (newFile.exists()) {
            existingFile = newFile;
//            newFileCount++;
            newFile = new File(folder, String.format("%s.txt", fileName));
        }

        if (existingFile != null) {
            if (existingFile.length() >= maxFileSize) {
                return newFile;
            }
            return existingFile;
        }

        return newFile;
    }
}
