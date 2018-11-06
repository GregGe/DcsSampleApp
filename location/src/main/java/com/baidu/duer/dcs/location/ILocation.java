/*
 * *
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.baidu.duer.dcs.location;

/**
 * 要实现此接口 1、需要将实现类命名为 LocationImpl 2、放置在 com.baidu.duersdk.location下 3、实现构造方法
 * public LocationImpl(Context context)
 *
 * @author yangrui09
 */
public interface ILocation {
    /**
     * 定义实现类的类名
     */
    public static final String CLASSIMPLNAME = "com.baidu.duer.sdk.location.LocationImpl";

    /**
     * 定义默认实现类的类名
     */
    public static final String CLASSDEFAULTNAME = "com.baidu.duer.sdk.location.NullLocationImpl";

    /**
     * 定位信息类
     */
    public static class LocationInfo {
        /**
         * 当前时间
         */
        public long time;
        /**
         * 经度
         */
        public double longitude;
        /**
         * 维度
         */
        public double latitude;
        /**
         * 精度半径
         */
        public double radius;
        /**
         * 地址
         */
        public String addressStr = "";
        /**
         * 省份
         */
        public String province = "";
        /**
         * 城市
         */
        public String city = "";
        /**
         * 街道
         */
        public String street = "";
        /**
         * 街道号
         */
        public String streetNo = "";
        /**
         * 区县
         */
        public String district = "";
        /**
         * 城市编码
         */
        public String cityCode = "";
        // add by nifuchen
        /**
         * 坐标系信息
         */
        public String coorType = "";
        /**
         * 国家名
         */
        public String country = "";
        /**
         * 国家码
         */
        public String countryCode = "";
        /**
         * 获取当前定位信息的街道地址
         *
         * @return 当前定位信息的街道地址
         */
        public String streetStr = "";
    }

    /**
     * 定位处理的监听类
     */
    public interface LocationListener {
        /**
         * 服务器返回错误
         */
        int SERVER_ERROR = 1;
        /**
         * 请求过于频繁
         */
        int REQUEST_FREQUENT = 2;
        /**
         * SDK尚未初始化
         */
        int SDK_NOT_INIT = 3;
        /**
         * 其它错误
         */
        int OTHER_ERROR = 4;

        /**
         * 当得到位置信息后被调用
         *
         * @param locationInfo 定位信息，参考LocationInfo的类定义
         */
        void onReceiveLocation(LocationInfo locationInfo);

        /**
         * 处理定位错误
         *
         * @param errCode 错误码
         */
        void onError(int errCode);
    }

    /**
     * 获取当前缓存的位置信息，如果本地有缓存，直接返回缓存信息 如果没有缓存，立刻返回一个空的信息类，并且开始一次定位
     *
     * @return LocationInfo
     */
    public LocationInfo getLocationInfo();

    /**
     * 请求一次定位，请求的结果可以通过设置LocationListener监听得到
     * 定位成功后，需要调用stopLocation，如果不调用stopLocation， 则每隔3分钟定位一次
     *
     * @param isUseCache true:使用缓存直接返回结果,没有缓存则开启一次定位 <br>
     *                   false:不使用缓存直接开始一次定位
     */
    public void requestLocation(boolean isUseCache);

    /**
     * 添加位置监听接口
     *
     * @param listener
     */
    public void addLocationListener(LocationListener listener);

    /**
     * 删除监听器
     *
     * @param listener
     */
    public void delLocationListener(LocationListener listener);

    /**
     * 停止定位
     */
    public void stopLocation();

    /**
     * 释放
     */
    public void release();
}
