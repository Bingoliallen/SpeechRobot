package com.example.common.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * Created by xxdr on 2017/11/20.
 */

/**
 * 身份证号码验证
 * 1、号码的结构
 * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 2、地址码(前六位数）
 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 3、出生日期码（第七位至十四位）
 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 4、顺序码（第十五位至十七位）
 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
 * 顺序码的奇数分配给男性，偶数分配给女性。
 * 5、校验码（第十八位数）
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
 * X 9 8 7 6 5 4 3 2
 */

public class IDCardUtil {

  private static final Pattern ID_CARD_PATTERN = Pattern.compile(
      "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

  private IDCardUtil() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * 功能：身份证的有效验证
   */
  public static boolean isIDNumber(String IDStr) {
    String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4",
        "2"};
    String Ai;
    // ================ 号码的长度 15位或18位 ================
    if (IDStr.length() != 15 && IDStr.length() != 18) {
      return false;
    }
    // =======================(end)========================

    // ================ 数字 除最后以为都为数字 ================
    if (IDStr.length() == 18) {
      Ai = IDStr.substring(0, 17);
    } else {
      Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
    }
    if (!ValidateUtil.isNumber(Ai)) {
      return false;
    }
    // =======================(end)========================

    // ================ 出生年月是否有效 ================
    String strYear = Ai.substring(6, 10);// 年份
    String strMonth = Ai.substring(10, 12);// 月份
    String strDay = Ai.substring(12, 14);// 月份
    if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
      return false;
    }
    GregorianCalendar gc = new GregorianCalendar();
    @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
    try {
      if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
          || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime())
          < 0) {
        return false;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
      return false;
    }
    if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
      return false;
    }
    // =====================(end)=====================

    // ================ 地区码时候有效 ================
    Hashtable<String, String> h = GetAreaCode();
    if (h.get(Ai.substring(0, 2)) == null) {
      return false;
    }
    // ==============================================

    // ================ 判断最后一位的值 ================
    int TotalmulAiWi = 0;
    for (int i = 0; i < 17; i++) {
      TotalmulAiWi = TotalmulAiWi
          + Integer.parseInt(String.valueOf(Ai.charAt(i)))
          * Integer.parseInt(Wi[i]);
    }
    int modValue = TotalmulAiWi % 11;
    String strVerifyCode = ValCodeArr[modValue];
    Ai = Ai + strVerifyCode;

    return !(IDStr.length() == 18 && !Ai.equals(IDStr));
    // =====================(end)=====================
  }

  /**
   * 功能：设置地区编码
   */
  private static Hashtable<String, String> GetAreaCode() {
    Hashtable<String, String> hashTable = new Hashtable<>();
    hashTable.put("11", "北京");
    hashTable.put("12", "天津");
    hashTable.put("13", "河北");
    hashTable.put("14", "山西");
    hashTable.put("15", "内蒙古");
    hashTable.put("21", "辽宁");
    hashTable.put("22", "吉林");
    hashTable.put("23", "黑龙江");
    hashTable.put("31", "上海");
    hashTable.put("32", "江苏");
    hashTable.put("33", "浙江");
    hashTable.put("34", "安徽");
    hashTable.put("35", "福建");
    hashTable.put("36", "江西");
    hashTable.put("37", "山东");
    hashTable.put("41", "河南");
    hashTable.put("42", "湖北");
    hashTable.put("43", "湖南");
    hashTable.put("44", "广东");
    hashTable.put("45", "广西");
    hashTable.put("46", "海南");
    hashTable.put("50", "重庆");
    hashTable.put("51", "四川");
    hashTable.put("52", "贵州");
    hashTable.put("53", "云南");
    hashTable.put("54", "西藏");
    hashTable.put("61", "陕西");
    hashTable.put("62", "甘肃");
    hashTable.put("63", "青海");
    hashTable.put("64", "宁夏");
    hashTable.put("65", "新疆");
    hashTable.put("71", "台湾");
    hashTable.put("81", "香港");
    hashTable.put("82", "澳门");
    hashTable.put("91", "国外");
    return hashTable;
  }

  /**
   * 功能：判断字符串是否为日期格式
   */
  private static boolean isDate(String strDate) {
    return ID_CARD_PATTERN.matcher(strDate).find();
  }
}
