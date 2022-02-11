package com.tntlinking.tntdev.other;


import android.graphics.Bitmap;
import android.text.TextUtils;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.lang.reflect.Type;
import java.util.Hashtable;


/**
 * Created by Dan on 2021/12/7.
 */

public class Utils {

    public static String formatDate(int month) {
        String str = "";
        if (month > 9) {
            str = String.valueOf(month);
        } else {
            str = "0" + month;
        }
        return str;
    }


    public static Object getData(String key, Class cls) {
        String dev = SPUtils.getInstance().getString("dev");
        Class object = GsonUtils.fromJson(dev, (Type) cls);

        return object;
    }

    public static void putData(String key, Object value) {
        String s = GsonUtils.toJson(value);
        SPUtils.getInstance().put(key, s);

    }

    /**
     * 生成简单二维码
     *
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @return BitMap
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            String character_set, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    //格式化年月份去掉前面的0  2020-09-08  得到 2020-9-8
    private String getSimpleDate(String date) {
        int index1 = date.indexOf("-");
        int index2 = date.lastIndexOf("-");
        int month;
        int day;
        int year = Integer.parseInt(date.substring(0, index1));
        if (date.substring(index1 + 1, index1 + 2).equals("0")) {
            month = Integer.parseInt(date.substring(index1 + 2, index1 + 3));
        } else {
            month = Integer.parseInt(date.substring(index1 + 1, index1 + 3));
        }
        if (date.substring(index2 + 1, index2 + 2).equals("0")) {
            day = Integer.parseInt(date.substring(index2 + 2, index2 + 3));
        } else {
            day = Integer.parseInt(date.substring(index2 + 1, index2 + 3));
        }
        String date1 = (year + "-" + month + "-" + day) + "";
        return date1;
    }


    // 格式化月份去前面的0  2020-02-09   的到 2020
    public static String getYear(String date) {
        if (date.contains("-")) {
            int index1 = date.indexOf("-");
            int year;
            String[] split = date.split("-");

            year = Integer.parseInt(split[0]);
            return split[0];
        } else {
            return date;
        }

    }

    // 格式化月份去前面的0  2020-02-09   的到 2020
    public static int getIntYear(String date) {
        if (date.contains("-")) {
            String[] split = date.split("-");
            return Integer.parseInt(split[0]);
        } else {
            return 2001;
        }

    }

    // 格式化月份去前面的0  2020-02-09   的到 2
    public static String getMonth(String date) {
        if (date.contains("-")) {
            int index1 = date.indexOf("-");
            int month;

            if (date.substring(index1 + 1, index1 + 2).equals("0")) {
                month = Integer.parseInt(date.substring(index1 + 2, index1 + 3));
            } else {
                month = Integer.parseInt(date.substring(index1 + 1, index1 + 3));
            }
            return month + "";
        } else {
            return date;
        }

    }

    // 格式化天去前面的0  2020-12-09   的到 9
    public static String getDay(String date) {
        if (date.contains("-")) {
            int index2 = date.lastIndexOf("-");
            int day;
            if (date.substring(index2 + 1, index2 + 2).equals("0")) {
                day = Integer.parseInt(date.substring(index2 + 2, index2 + 3));
            } else {
                day = Integer.parseInt(date.substring(index2 + 1, index2 + 3));
            }
            return day + "";
        } else {
            return date;
        }

    }


    public static String getHours(String date) {
        if (date.contains("-")) {
            int index1 = date.indexOf(":");
            int month;

            if (date.substring(index1 + 1, index1 + 2).equals("0")) {
                month = Integer.parseInt(date.substring(index1 + 2, index1 + 3));
            } else {
                month = Integer.parseInt(date.substring(index1 + 1, index1 + 3));
            }
            return month + "";
        } else {
            return date;
        }

    }

    // 2021-12-23 14:36:00
    public static String getHoursAndMin(String date) {
        if (!TextUtils.isEmpty(date) && date.contains(" ")) {
            String hours = date.split(" ")[1];
            int index = hours.length() - 3;

            if (hours.contains(":00")) {
                String substring = hours.substring(0, index);
                return substring;
            }

        }
        return date;
    }

    /**
     * 从给到的时间数据 切割只要年月份 2022-10-30 00:00:00 -> 2022-10-30
     *
     * @param date
     * @return
     */
    public static String getYearFromDate(String date) {
        if (!TextUtils.isEmpty(date) && date.contains(" ")) {
            String time = date.split(" ")[0];
            return time;
        } else {
            return date;
        }
    }

    /**
     * 从给到的时间数据 切割只要年月份 2022-10-30 00:00:00 -> 00:00:00
     *
     * @param date
     * @return
     */
    public static String getTimeFromDate(String date) {
        if (!TextUtils.isEmpty(date) && date.contains(" ")) {
            String time = date.split(" ")[1];
            return time;
        } else {
            return date;
        }
    }


    /**
     * 从给到的时间数据 切割只要年月份 2022-10-30 00:00:00 -> 00:00:00
     *
     * @param date
     * @return
     */
    public static String ChangeDate(String date) {
        if (date.contains("-0")) {
            String time = date.replace("-0", ".0");
            return time;
        } else if (date.contains("-")) {
            String time = date.replace("-", ".");
            return time;
        } else {
            return date;
        }
    }

    /**
     * 将手机号码18033339999 转换为 180****9999
     *
     * @param phoneNumber
     * @return
     */
    public static String changPhoneNumber(String phoneNumber) {
        StringBuffer sb = new StringBuffer();
        if (phoneNumber.length() > 10) {
            String frontThreeString = phoneNumber.substring(0, 3);
            sb.append(frontThreeString);
            String substring = phoneNumber.substring(3, 7);
            String replace = substring.replace(substring, "****");
            sb.append(replace);
            String lastFourString = phoneNumber.substring(7, 11);
            sb.append(lastFourString);
            return sb.toString();
        } else {
            return "";
        }

    }

}


