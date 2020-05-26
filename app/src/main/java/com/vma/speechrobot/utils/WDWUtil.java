package com.vma.speechrobot.utils;

/**
 * Created by Administrator on 2019/1/24.
 */

public class WDWUtil {

    /**
     * @描述：是否是2003的excel，返回true是2003
     * @时间：2014-08-29 下午16:29:11
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    /**
     * @描述：是否是2007的excel，返回true是2007
     * @时间：2014-08-29 下午16:28:20
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }



}
