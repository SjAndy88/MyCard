package com.example.mycard.utils;

import ohos.app.Context;
import ohos.net.NetCapabilities;
import ohos.net.NetHandle;
import ohos.net.NetManager;

public class NetworkUtil {

    /**
     * 网络是否连接
     *
     * @param context 上下文
     * @return true表示网络已经连接，并且可以上网。false表示网络没连接，或者网络连接了，但是不能上网
     */
    public static boolean isNetworkConnectedInternet(Context context) {
        NetManager netManager = NetManager.getInstance(context);
        NetCapabilities netCapabilities = netManager.getNetCapabilities(netManager.getDefaultNet());
        // NetCapabilities.NET_CAPABILITY_VALIDATED表示连接了网络，并且可以上网
        return netCapabilities.hasCap(NetCapabilities.NET_CAPABILITY_VALIDATED);
    }

    /**
     * 是否是WiFi连接
     *
     * @param context 上下文
     * @return true表示WiFi连接，并且可以上网。false表示WiFi没连接，或者WiFi连接了，但是不能上网
     */
    public static boolean isWifiConnectedInternet(Context context) {
        // 获取网络管理对象
        NetManager netManager = NetManager.getInstance(context);
        // 获取NetCapabilities对象
        NetCapabilities netCapabilities = netManager.getNetCapabilities(netManager.getDefaultNet());
        // NetCapabilities.NET_CAPABILITY_VALIDATED表示连接了网络，并且可以上网
        return netCapabilities.hasCap(NetCapabilities.NET_CAPABILITY_VALIDATED) &&
                netCapabilities.hasBearer(NetCapabilities.BEARER_WIFI) ||
                netCapabilities.hasBearer(NetCapabilities.BEARER_WIFI_AWARE);
    }

    /**
     * 是否是数据网络连接
     *
     * @param context 上下文
     * @return true表示数据网络连接，并且可以上网。false表示数据网络没连接，或者数据网络连接了，但是不能上网
     */
    public static boolean isMobileConnectedInternet(Context context) {
        NetManager netManager = NetManager.getInstance(context);
        NetCapabilities netCapabilities = netManager.getNetCapabilities(netManager.getDefaultNet());
        // NetCapabilities.NET_CAPABILITY_VALIDATED表示连接了网络，并且可以上网
        return netCapabilities.hasCap(NetCapabilities.NET_CAPABILITY_VALIDATED) &&
                netCapabilities.hasBearer(NetCapabilities.BEARER_CELLULAR);
    }

    /**
     * 这种方式也可以判断网络是否连接
     *
     * @param context 上下文
     * @return 只要网络已经连接，就返回true，不管能不能上网。有时我们连接了一个网络，但由于网络已经到期（该交钱了）或者使用的代理不可用，设备虽然连接了网络，但是不能上网
     */
    public static boolean isNetworkConnected(Context context) {
        NetManager netManager = NetManager.getInstance(context);
        NetHandle[] allNets = netManager.getAllNets();
        return allNets != null && allNets.length > 0;
    }

    /**
     * 是否是WiFi连接
     *
     * @param context 上下文
     * @return 只要是WiFi连接，就返回true，不管能不能上网。有时我们连接了一个网络，但由于网络已经到期（该交钱了）或者使用的代理不可用，设备虽然连接了网络，但是不能上网
     */
    public static boolean isWifiConnected(Context context) {
        // 获取网络管理对象
        NetManager netManager = NetManager.getInstance(context);
        // 获取NetCapabilities对象
        NetCapabilities netCapabilities = netManager.getNetCapabilities(netManager.getDefaultNet());
        return netCapabilities.hasBearer(NetCapabilities.BEARER_WIFI) ||
                netCapabilities.hasBearer(NetCapabilities.BEARER_WIFI_AWARE);
    }

    /**
     * 是否是数据网络连接
     *
     * @param context 上下文
     * @return 只要是数据网络连接，就返回true，不管能不能上网。有时我们连接了一个网络，但由于网络已经到期（该交钱了），设备虽然连接了网络，但是不能上网
     */
    public static boolean isMobileConnected(Context context) {
        NetManager netManager = NetManager.getInstance(context);
        NetCapabilities netCapabilities = netManager.getNetCapabilities(netManager.getDefaultNet());
        return netCapabilities.hasBearer(NetCapabilities.BEARER_CELLULAR);
    }
}