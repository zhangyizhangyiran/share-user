/*
 * Copyright (C) 2013 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coolqi.lib.ble;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class L {

    private static String generateTag() {
        if (!Config.debugEnable()) {
            return "ShareSoe";
        }
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClassName = caller.getClassName();
        callerClassName = callerClassName.substring(callerClassName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClassName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    public static void d(String content) {
        if (!Config.debugEnable()) {
            return;
        }
        Log.d(generateTag(), content);
        appendFile(content);
    }

    public static void d(String content, Throwable throwable) {
        if (!Config.debugEnable()) {
            return;
        }
        Log.d(generateTag(), content, throwable);
        appendFile(content);
        appendFile(throwable.toString());
    }

    public static void v(String content) {
        if (!Config.debugEnable()) {
            return;
        }
        Log.v(generateTag(), content);
        appendFile(content);
    }

    public static void w(String content) {
        if (!Config.debugEnable()) {
            return;
        }
        Log.w(generateTag(), content);
        appendFile(content);
    }

    public static void e(String content) {
        if (!Config.debugEnable()) {
            return;
        }
        Log.e(generateTag(), content);
        appendFile(content);
    }

    public static void i(String content) {
        if (!Config.debugEnable()) {
            return;
        }
        Log.i(generateTag(), content);
        appendFile(content);
    }


    public static void appendFile(String src) {
        if (!Config.saveLogEnable()) {
            return;
        }
        if (TextUtils.isEmpty(Config.getLogFilePath())) {
            return;
        }
        src = src + "\n";
        File logFile = new File(Config.getLogFilePath());
        FileWriter writer = null;
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            writer = new FileWriter(logFile, true);
            writer.append(src);
        } catch (Exception e) {
            L.d("日志文件保存失败", e);
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void printBytes(String header, byte[] bytes) {
        if (bytes==null){
            e(header + " : null");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%x", b) + ",");
        }
        sb.delete(sb.length() - 1, sb.length());
        e(header + " : "+sb);
    }
}