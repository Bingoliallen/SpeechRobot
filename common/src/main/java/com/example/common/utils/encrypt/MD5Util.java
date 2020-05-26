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
 * Last modified 2017-09-07 17:29:17
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.example.common.utils.encrypt;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.example.common.utils.IOUtil;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 */
public class MD5Util {


  /**
   * MD5编码
   */
  public static String encode(@NonNull String string) {
    return TextUtils.isEmpty(string) ? "" : encode(string, "");
  }


  /**
   * MD5编码(加盐)
   */
  public static String encode(@NonNull String string, String slat) {
    if (TextUtils.isEmpty(string)) {
      return "";
    }
    return encode((string + slat).getBytes());
  }

  /**
   * MD5编码(加盐)
   */
  public static String encode(@NonNull byte[] bytes) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      return EncryptUtil.byte2Hex(md5.digest(bytes));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * MD5编码(多次)
   */
  public static String encode(@NonNull String string, int times) {
    if (TextUtils.isEmpty(string)) {
      return "";
    }

    String md5 = string;
    for (int i = 0; i < times; i++) {
      md5 = encode(md5);
    }
    return md5;
  }


  /**
   * MD5编码(文件)
   */
  public static String encode(@NonNull File file) {
    if (!file.isFile()) {
      return "";
    }
    MessageDigest digest = null;
    FileInputStream in = null;
    byte buffer[] = new byte[1024];
    int len;
    try {
      digest = MessageDigest.getInstance("MD5");
      in = new FileInputStream(file);
      while ((len = in.read(buffer, 0, 1024)) != -1) {
        digest.update(buffer, 0, len);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } finally {
      IOUtil.close(in);
    }
    BigInteger bigInt = new BigInteger(1, digest.digest());
    return bigInt.toString(16);
  }
}
