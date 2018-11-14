package top.poul.utils;

/**
 * 地球工具类
 */
public class EarthUtil {


    private static final double EARTH_RADIUS = 6378.137;//地球半径 单位km

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 获取经纬度之间的直线距离
     * https://www.cnblogs.com/ycsfwhh/archive/2010/12/20/1911232.html
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return  单位km
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.sin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}
