/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.common.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides I/O operations
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class IOUtil {

  /**
   * 默认 Buffer 流大小
   */
  public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 KB
  /**
   * 默认 IMAGE 流大小
   */
  public static final int DEFAULT_IMAGE_TOTAL_SIZE = 500 * 1024; // 500 Kb
  /**
   * {@value}
   */
  public static final int CONTINUE_LOADING_PERCENTAGE = 75;

  private IOUtil() {
  }

  /**
   * Copies stream, fires progress events by listener, can be interrupted by listener.
   */
  public static boolean copy(InputStream is, OutputStream os, CopyListener listener)
      throws IOException {
    return copy(is, os, listener, DEFAULT_BUFFER_SIZE);
  }

  /**
   * Copies stream, fires progress events by listener, can be interrupted by listener.
   */
  public static boolean copy(InputStream is, OutputStream os, CopyListener listener, int bufferSize)
      throws IOException {
    int current = 0;
    int total = is.available();
    if (total <= 0) {
      total = DEFAULT_IMAGE_TOTAL_SIZE;
    }

    final byte[] bytes = new byte[bufferSize];
    int count;
    if (shouldStopLoading(listener, current, total)) {
      return false;
    }
    while ((count = is.read(bytes, 0, bufferSize)) != -1) {
      os.write(bytes, 0, count);
      current += count;
      if (shouldStopLoading(listener, current, total)) {
        return false;
      }
    }
    os.flush();
    return true;
  }

  private static boolean shouldStopLoading(CopyListener listener, int current, int total) {
    if (listener != null) {
      boolean shouldContinue = listener.onBytesCopied(current, total);
      if (!shouldContinue) {
        if (100 * current / total < CONTINUE_LOADING_PERCENTAGE) {
          return true; //如果加载超过指定百分比，那么就应该继续加载
        }
      }
    }
    return false;
  }

  public static void close(Closeable closeable) {
    if (null == closeable) {
      return;
    }
    try {
      closeable.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Listener and controller for copy process
   */
  public static interface CopyListener {

    /**
     * @param current Loaded bytes
     * @param total Total bytes for loading
     * @return <b>true</b> - if copying should be continued; <b>false</b> - if copying should be
     * interrupted
     */
    boolean onBytesCopied(int current, int total);
  }
}
