package com.example.common.utils.encrypt;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES/AES/SHA
 */
public class EncryptUtil {

  /**
   * Sha加密
   *
   * @param type   加密类型 ：{@link #SHA224}，{@link #SHA256}，{@link #SHA384}，{@link #SHA512}
   */
  public final static String SHA224 = "sha-224";
  public final static String SHA256 = "sha-256";
  public final static String SHA384 = "sha-384";
  public final static String SHA512 = "sha-512";
  /**
   * Aes加密/解密
   *
   * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
   */
  private final static String SHA1PRNG = "SHA1PRNG";
  private EncryptUtil() {
  }

  /**
   * Des加密/解密
   *
   * @param type 加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
   */
  public static String des(String content, String password, int type) {
    try {
      SecureRandom random = new SecureRandom();
      DESKeySpec desKey = new DESKeySpec(password.getBytes());
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("DES");
      cipher.init(type, keyFactory.generateSecret(desKey), random);

      if (type == Cipher.ENCRYPT_MODE) {
        byte[] byteContent = content.getBytes("utf-8");
        return byte2Hex(cipher.doFinal(byteContent));
      } else {
        byte[] byteContent = hex2Byte(content);
        return new String(cipher.doFinal(byteContent));
      }
    } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
        UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException |
        InvalidKeySpecException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String aes(String content, String password, int type) {
    try {
      KeyGenerator generator = KeyGenerator.getInstance("AES");

      SecureRandom secureRandom;
      if (android.os.Build.VERSION.SDK_INT >= 24) {
        secureRandom = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
      } else if (android.os.Build.VERSION.SDK_INT >= 17) {
        secureRandom = SecureRandom.getInstance(SHA1PRNG, "Crypto");
      } else {
        secureRandom = SecureRandom.getInstance(SHA1PRNG);
      }
      secureRandom.setSeed(password.getBytes());
      generator.init(128, secureRandom);
      SecretKey secretKey = generator.generateKey();
      byte[] enCodeFormat = secretKey.getEncoded();
      SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
      @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES");
      cipher.init(type, key);

      if (type == Cipher.ENCRYPT_MODE) {
        byte[] byteContent = content.getBytes("utf-8");
        return byte2Hex(cipher.doFinal(byteContent));
      } else {
        byte[] byteContent = hex2Byte(content);
        return new String(cipher.doFinal(byteContent));
      }
    } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
        UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException |
        NoSuchProviderException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String sha(String string, String type) {
    if (TextUtils.isEmpty(string)) {
      return "";
    }
    if (TextUtils.isEmpty(type)) {
      type = SHA256;
    }
    try {
      MessageDigest md5 = MessageDigest.getInstance(type);
      byte[] bytes = md5.digest((string).getBytes());
      String result = "";
      for (byte b : bytes) {
        String temp = Integer.toHexString(b & 0xff);
        if (temp.length() == 1) {
          temp = "0" + temp;
        }
        result += temp;
      }
      return result;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 二进位组转十六进制字符串
   */
  public static String byte2Hex(byte buf[]) {
    StringBuilder sb = new StringBuilder();
    for (byte b : buf) {
      String hex = Integer.toHexString(b & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  /**
   * 十六进制字符串转二进位组
   */
  public static byte[] hex2Byte(String hexStr) {
    if (hexStr.length() < 1) {
      return null;
    }
    byte[] result = new byte[hexStr.length() / 2];

    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }
}
