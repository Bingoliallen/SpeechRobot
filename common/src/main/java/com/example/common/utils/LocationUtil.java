//package com.example.common.utils;
//
//import android.content.Context;
//
//import com.amap.api.location.AMapLocation;
//import com.amap.api.location.AMapLocationClient;
//import com.amap.api.location.AMapLocationClientOption;
//import com.amap.api.location.AMapLocationListener;
//import com.amap.api.maps2d.model.LatLng;
//
///**
// * Created by xxdr on 2017/11/22.
// */
//
//public class LocationUtils {
//
//    private static final double EARTH_RADIUS = 6378.1370;
//
//    private static AMapLocation sMapLocation;
//
//    //声明AMapLocationClient类对象
//    public static AMapLocationClient mLocationClient = null;
//    //声明AMapLocationClientOption对象
//    public static AMapLocationClientOption mLocationOption = null;
//
//    private LocationUtils() {
//
//    }
//
//    public static void init(Context mContext) {
//        mLocationClient = new AMapLocationClient(mContext);
//        mLocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                sMapLocation = aMapLocation;
//            }
//        });
//        //初始化AMapLocationClientOption对象
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        mLocationOption.setInterval(5000);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否强制刷新WIFI，默认为true，强制刷新。
//        mLocationOption.setWifiActiveScan(false);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
//        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
//        mLocationOption.setHttpTimeOut(20000);
//        //关闭缓存机制
//        mLocationOption.setLocationCacheEnable(false);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
//    }
//
//    public static AMapLocation getMapLocation() {
//        return sMapLocation;
//    }
//
//    public static LatLng getLocation() {
//        if (sMapLocation != null)
//            return new LatLng(sMapLocation.getLatitude(), sMapLocation.getLongitude());
//        return null;
//    }
//
//    public static double getDistanceByTwoPoint(double lat_a, double lng_a, double lat_b, double lng_b) {
//        double radLat1 = (lat_a * Math.PI / 180.0);
//        double radLat2 = (lat_b * Math.PI / 180.0);
//        double a = radLat1 - radLat2;
//        double b = (lng_a - lng_b) * Math.PI / 180.0;
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000) / 10000;
//        return s;
//    }
//
//    private static double rad(double d) {
//        return d * Math.PI / 180.0;
//    }
//
//    public static double getDistanceByOnePoint(double lat_a, double lng_a) {
//        double lat_b = sMapLocation.getLatitude();
//        double lng_b = sMapLocation.getLongitude();
//        double radLat1 = rad(lat_a);
//        double radLat2 = rad(lat_b);
//        double a = radLat1 - radLat2;
//        double b = rad(lng_a) - rad(lng_b);
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
//                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000) / 10000;
//        return s;
//    }
//}
