package xin.jiangqiang.blog.util.tools;

import java.util.Date;
import java.util.Random;

/**
 * @author JiangQiang
 * @date 2020/10/21 16:54
 */
public class DateUtil {
    /**
     * long转date
     *
     * @param times
     * @return
     */
    public static Date longToDate(long times) {
        return new Date(times);
    }

    /**
     * date转long
     *
     * @param date
     * @return
     */
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 获取几个小时后的时间
     *
     * @param times 过去的时间
     * @param hours 时间间隔
     * @return 一段时间后的时间戳
     */
    public static Long afterHours(long times, int hours) {
        return times + hours * 60 * 60 * 1000;
    }

    /**
     * 获取几个小时前的时间
     *
     * @param times
     * @param hours
     * @return
     */
    public static Long beforeHours(long times, int hours) {
        return times - hours * 60 * 60 * 1000;
    }


    /**
     * 获取当前时间错+n位长度的随机数
     *
     * @param length 随机数长度
     * @return
     */
    public static String getCurrentTime(int length) {
        Random random = new Random();
        return String.valueOf(System.currentTimeMillis()) + random.nextInt((int) Math.pow(10, length));
    }


    public static void main(String[] args) {
        System.out.println(getCurrentTime(3));
        System.out.println(new Date(1603421009153L));
    }
}
