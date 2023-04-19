package com.tntlinking.tntdev.other;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static long dateToStamp(String s) {
        //设置时间模版
        if (!TextUtils.isEmpty(s)) {
            if (s.contains("-")) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    s = s + "-01 00:00:00";
                    Date date = simpleDateFormat.parse(s);
                    return date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        return 0;


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
/** ---------------------------生成简单二维码------------------------------------------------------*/
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

    /**
     * 向二维码中间添加logo图片(图片合成)
     *
     * @param srcBitmap   原图片（生成的简单二维码图片）
     * @param logoBitmap  logo图片
     * @param logoPercent 百分比 (用于调整logo图片在原图片中的显示大小, 取值范围[0,1] )
     * @return
     */
    private static Bitmap addLogo(Bitmap srcBitmap, Bitmap logoBitmap, float logoPercent) {
        if (srcBitmap == null) {
            return null;
        }
        if (logoBitmap == null) {
            return srcBitmap;
        }
        //传值不合法时使用0.2F
        if (logoPercent < 0F || logoPercent > 1F) {
            logoPercent = 0.2F;
        }

        /** 1. 获取原图片和Logo图片各自的宽、高值 */
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        /** 2. 计算画布缩放的宽高比 */
        float scaleWidth = srcWidth * logoPercent / logoWidth;
        float scaleHeight = srcHeight * logoPercent / logoHeight;

        /** 3. 使用Canvas绘制,合成图片 */
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.scale(scaleWidth, scaleHeight, srcWidth / 2, srcHeight / 2);
        canvas.drawBitmap(logoBitmap, srcWidth / 2 - logoWidth / 2, srcHeight / 2 - logoHeight / 2, null);

        return bitmap;
    }

    /**
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @param logoBitmap             logo图片
     * @param logoPercent            logo所占百分比
     * @return
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height, String character_set,
                                            String error_correction_level, String margin, int color_black,
                                            int color_white, Bitmap logoBitmap, float logoPercent) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
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

            /** 5.为二维码添加logo图标 */
            if (logoBitmap != null) {
                return addLogo(bitmap, logoBitmap, logoPercent);
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ---------------------------生成简单二维码------------------------------------------------------
     */
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
        if (!TextUtils.isEmpty(date)) {
            if (date.contains("-")) {
                String[] split = date.split("-");
                return Integer.parseInt(split[0]);
            } else {
                return 2001;
            }
        } else {
            return 2022;
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
        if (!TextUtils.isEmpty(date)) {
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
        } else {
            return "1";
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
     * 从给到的时间数据 切割只要年月份 2022-10-30 00:00:00 -> 2022-10-30
     * 得到前面的年月日
     *
     * @param date
     * @return
     */
    public static String splitDateToDay(String date) {
        if (!TextUtils.isEmpty(date) && date.contains(" ")) {
            String time = date.split(" ")[0];
            return time;
        } else {
            return date;
        }

    }

    /**
     * 从给到的时间数据 切割只要年月份 2022-10-30 00:00:00 -> 00:00:00
     * 得到后面的时间
     *
     * @param date
     * @return
     */
    public static String splitDateToTime(String date) {
        if (!TextUtils.isEmpty(date) && date.contains(" ")) {
            String time = date.split(" ")[1];
            return time;
        } else {
            return date;
        }
    }


    /**
     * 从给到的时间数据 切割只要年月份 2022-01-02  -> 00:00:00
     *
     * @param date
     * @return
     */
    public static String ChangeDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            if (date.contains("-0")) {
                String time = date.replace("-0", ".0");
                return time;
            } else if (date.contains("-")) {
                String time = date.replace("-", ".");
                return time;
            } else {
                return date;
            }
        } else {
            return date;
        }
    }

    /**
     * @param str 去掉字符串后面多余的 0
     * @return
     */
    public static String StripZeros(String str) {
        if (!TextUtils.isEmpty(str)) {
            //若是String类型，也可以先转为BigDecimal
            BigDecimal value = new BigDecimal(str);
            //去除多余0
            BigDecimal noZeros = value.stripTrailingZeros();
            //BigDecimal => String
            return noZeros.toPlainString();
        } else {
            return "";
        }
    }

    //判断返回过来的薪资是否是为空，为空返回默认0.00
    public static String isNullSalary(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0.00";
        } else {
            return str;
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

    public static String formatName(String name) {
        String mName = "朋友";
        if (!TextUtils.isEmpty(name)) {
            if (name.length() > 2) {
                mName = name.substring(name.length() - 2);
            } else {
                return name;
            }
        } else {
            return mName;
        }
        return mName;
    }

    public static int splitYearMonth(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("-")) {
                String[] split = str.split("-");
                String i = Integer.parseInt(split[0]) + "";
                String j = Integer.parseInt(split[1]) + "";
                return Integer.parseInt(i + j);
            }
        } else {
            return 0;
        }
        return 0;
    }

    public static String formatMoney(double str) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        String format = decimalFormat.format(str);
        return format;
    }

    public static String formatMoney(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (Double.valueOf(str) != 0) {
                Double aDouble = Double.valueOf(str);
                DecimalFormat df = new DecimalFormat("###,###,###,###.00");
                String format = df.format(aDouble);
                return format;
            }

        }
        return "0.00";
    }

    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
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
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void Log(String msg) {
        if (msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.e("EasyHttp", msg);
//            EasyLog.print(msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.e("EasyHttp", logContent);
//                EasyLog.print(logContent);
            }
            Log.e("EasyHttp", msg);// 打印剩余日志
//            EasyLog.print(msg);
        }
    }

    public static void e(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = 2000;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(TAG + i, msg.substring(start, end));
                start = end;
                end = end + 2000;
            } else {
                Log.e(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }


}


