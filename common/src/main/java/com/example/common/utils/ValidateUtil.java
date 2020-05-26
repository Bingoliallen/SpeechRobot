package com.example.common.utils;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xxdr on 2018/1/10.
 */

public class ValidateUtil {

  private static final Pattern MOBILE_PATTERN = Pattern
      .compile("^(\\+?0*86-?)?(0+)?(1[23456789]\\d{9})$");
  private static final Pattern ZIP_PATTERN = Pattern.compile("^[1-9][0-9]{5}$");
  private static final Pattern EMAIL_EASY_PATTERN = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
  private static final Pattern EMAIL_COMPLEX_PATTERN = Pattern
      .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
  private static final Pattern AGE_PATTERN = Pattern.compile("[0-9]+");
  private static final Pattern INT_PATTERN = Pattern.compile("^-?[1-9]\\d*$");
  private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");

  private ValidateUtil() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * 判断是否是手机号码
   */
  public static boolean isMobile(String phone) {
    return !TextUtils.isEmpty(phone) && MOBILE_PATTERN.matcher(phone).matches();
  }

  /**
   * 判断是否是身份证号码
   */
  public static boolean isIDNumber(String id) {
    return IDCardUtil.isIDNumber(id);
  }

  /**
   * 判断邮编
   */
  public static boolean isZipCode(String post) {
    return ZIP_PATTERN.matcher(post).matches();
  }

  /**
   * 判断邮箱是否合法
   */
  public static boolean isEmail(String email) {
    if (null == email || "".equals(email)) {
      return false;
    }
    Matcher m = EMAIL_COMPLEX_PATTERN.matcher(email);
    return m.matches();
  }

  /**
   * 是否是int
   */
  public static boolean isInt(String s) {
    return !TextUtils.isEmpty(s) && INT_PATTERN.matcher(s).find();
  }

  /**
   * 是否为数字
   */
  public static boolean isNumber(String str) {
    return NUMBER_PATTERN.matcher(str).find();
  }


  /**
   * 判断年龄
   */
  public static boolean validateAge(String age) {
    boolean result = false;
    if (age.isEmpty()) {
      result = true;
    } else if (AGE_PATTERN.matcher(age).matches()) {
      int ageInt = Integer.parseInt(age);
      if (0 < ageInt && ageInt < 200) {
        result = true;
      }
    }
    return result;
  }
}
