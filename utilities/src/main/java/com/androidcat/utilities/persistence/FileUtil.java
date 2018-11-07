package com.androidcat.utilities.persistence;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by androidcat on 2018/7/5.
 */

public class FileUtil {
  private static final String ASSETS_PATH = "file:///android_asset/";

  //读取SD卡中文件的方法
  //定义读取文件的方法:
  public static String readFromSD(String path ,String filename) {
    return readFromSD(path+ File.separator+filename);
  }

  public static String readFromSD(String path) {
    StringBuilder sb = new StringBuilder("");
    try {
      //打开文件输入流
      FileInputStream input = new FileInputStream(path);
      byte[] temp = new byte[1024];

      int len = 0;
      //读取文件内容:
      while ((len = input.read(temp)) > 0) {
        sb.append(new String(temp, 0, len));
      }
      //关闭输入流
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  private static final String SEPARATOR = File.separator;//路径分隔符
  public static void copyFilesFromAssets(Context context, String assetsPath, String storagePath) {
    String temp = "";

    if (TextUtils.isEmpty(storagePath)) {
      return;
    } else if (storagePath.endsWith(SEPARATOR)) {
      storagePath = storagePath.substring(0, storagePath.length() - 1);
    }

    if (TextUtils.isEmpty(assetsPath) || assetsPath.equals(SEPARATOR)) {
      assetsPath = "";
    } else if (assetsPath.endsWith(SEPARATOR)) {
      assetsPath = assetsPath.substring(0, assetsPath.length() - 1);
    }

    AssetManager assetManager = context.getAssets();
    try {
      File file = new File(storagePath);
      if (!file.exists()) {//如果文件夹不存在，则创建新的文件夹
        file.mkdirs();
      }

      // 获取assets目录下的所有文件及目录名
      String[] fileNames = assetManager.list(assetsPath);
      if (fileNames.length > 0) {//如果是目录 apk
        for (String fileName : fileNames) {
          if (!TextUtils.isEmpty(assetsPath)) {
            temp = assetsPath + SEPARATOR + fileName;//补全assets资源路径
          }

          String[] childFileNames = assetManager.list(temp);
          if (!TextUtils.isEmpty(temp) && childFileNames.length > 0) {//判断是文件还是文件夹：如果是文件夹
            copyFilesFromAssets(context, temp, storagePath + SEPARATOR + fileName);
          } else {//如果是文件
            InputStream inputStream = assetManager.open(temp);
            readInputStream(storagePath + SEPARATOR + fileName, inputStream);
          }
        }
      } else {//如果是文件 doc_test.txt或者apk/app_test.apk
        InputStream inputStream = assetManager.open(assetsPath);
        if (assetsPath.contains(SEPARATOR)) {//apk/app_test.apk
          assetsPath = assetsPath.substring(assetsPath.lastIndexOf(SEPARATOR), assetsPath.length());
        }
        readInputStream(storagePath + SEPARATOR + assetsPath, inputStream);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * 读取输入流中的数据写入输出流
   *
   * @param storagePath 目标文件路径
   * @param inputStream 输入流
   */
  public static void readInputStream(String storagePath, InputStream inputStream) {
    File file = new File(storagePath);
    try {
      if (file.exists()){
        if (file.delete()) {
          // 1.建立通道对象
          FileOutputStream fos = new FileOutputStream(file);
          // 2.定义存储空间
          byte[] buffer = new byte[inputStream.available()];
          // 3.开始读文件
          int lenght = 0;
          while ((lenght = inputStream.read(buffer)) != -1) {// 循环从输入流读取buffer字节
            // 将Buffer中的数据写到outputStream对象中
            fos.write(buffer, 0, lenght);
          }
          fos.flush();// 刷新缓冲区
          // 4.关闭流
          fos.close();
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (inputStream != null){
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 读取assets下的txt文件，返回utf-8 String
   * @param context
   * @param fileName 不包括后缀
   * @return
   */
  public static String readAssetsTxt(Context context,String fileName){
    try {
      //Return an AssetManager instance for your application's package
      InputStream is = context.getAssets().open(fileName);
      int size = is.available();
      // Read the entire asset into a local byte buffer.
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      // Convert the buffer into a string.
      String text = new String(buffer, "utf-8");
      // Finally stick the string into the text view.
      return text;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
