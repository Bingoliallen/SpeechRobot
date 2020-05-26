/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-09-07 17:28:13
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.example.common.utils.encrypt;

import android.text.TextUtils;
import android.util.Base64;
import com.example.common.utils.IOUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Base64 工具类
 */
public class Base64Util {

  /**
   * Base64加密
   */
  public static String encode(String string) {
    if (TextUtils.isEmpty(string)) {
      return "";
    }
    return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
  }

  /**
   * Base64解密
   */
  public static String decode(String string) {
    if (TextUtils.isEmpty(string)) {
      return "";
    }
    return new String(Base64.decode(string, Base64.DEFAULT));
  }

  /**
   * Base64加密
   */
  public static String encode(File file) {
    if (null == file) {
      return "";
    }

    FileInputStream inputFile = null;
    try {
      inputFile = new FileInputStream(file);
      byte[] buffer = new byte[(int) file.length()];
      inputFile.read(buffer);
      return Base64.encodeToString(buffer, Base64.DEFAULT);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtil.close(inputFile);
    }
    return "";
  }

  /**
   * Base64解密
   */
  public static File decode(String filePath, String content) {
    if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(content)) {
      return null;
    }

    FileOutputStream fos = null;
    File desFile = new File(filePath);
    try {
      byte[] decodeBytes = Base64.decode(content.getBytes(), Base64.DEFAULT);
      fos = new FileOutputStream(desFile);
      fos.write(decodeBytes);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtil.close(fos);
    }
    return desFile;
  }
}
