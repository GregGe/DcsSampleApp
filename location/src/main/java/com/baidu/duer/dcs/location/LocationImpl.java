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

import android.content.Context;
import android.text.TextUtils;

import com.baidu.duer.dcs.location.util.LocationPreferenceUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import org.json.JSONObject;

import java.util.ArrayList;

/***
 * 实现定位的端能力
 *
 * @author yangrui09
 */
public class LocationImpl implements ILocation {

    private static final String SAVE_PREFILE = "com.baidu.duer.dcs.sample.sdk.location.LocationImpl.SAVE_PREFILE";

    /**
     * 三分钟更新一次地理位置
     */
    private static final int LOCATION_UPDATE_INTERVAL = 3 * 60 * 1000;

    /**
     * 实现实时位置回调监听
     */
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                if (location != null) {
                    // gps，或者网络定位成功
                    if (location.getLocType() == BDLocation.TypeGpsLocation
                            || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        JSONObject locationJson = toJsonObject(location);
                        // 保存到本地
                        saveLocation(locationJson);
                        // 转换为可以提供给外层的对象
                        LocationInfo locationInfo = toLocationInfo(locationJson);
                        // 返回位置信息给监听器
                        returnLocToLisenter(locationInfo);
                    }
                    // 其他情况属于有问题
                    else {
                        returnErrToListener(LocationListener.OTHER_ERROR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<LocationListener> callListenerArr;

    private ArrayList<LocationListener> getCallListenterArr() {
        if (callListenerArr == null) {
            callListenerArr = new ArrayList<>();
        }
        return callListenerArr;
    }

    /**
     * 监听百度定位sdk位置变化
     */
    private LocationClient mLocationClient;
    private BDLocationListener myListener = new MyLocationListener();

    private Context context;

    /***
     * 初始化方法，在主线程中调用
     *
     * @param context
     *            Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
     */
    public LocationImpl(Context context) {
        if (context != null) {
            this.context = context;
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationMode.Hight_Accuracy);    // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            option.setCoorType("bd09ll");   // 可选，默认gcj02，设置返回的定位结果坐标系，
            option.disableCache(true);
            option.setScanSpan(LOCATION_UPDATE_INTERVAL);   // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            option.setIsNeedAddress(true);  // 可选，设置是否需要地址信息，默认不需要
            option.setOpenGps(false);   // 可选，默认false,设置是否使用gps
            option.setLocationNotify(true); // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            option.setIgnoreKillProcess(true);  // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mLocationClient = new LocationClient(context);
            mLocationClient.setLocOption(option);
            mLocationClient.registerLocationListener(myListener);
            mLocationClient.start();
        }
    }

    @Override
    public void requestLocation(boolean isUseCache) {
        // 判断是否使用缓存的位置信息
        if (isUseCache) {
            LocationInfo locationInfo = getSaveLocation();
            if (locationInfo != null) {
                returnLocToLisenter(locationInfo);
            } else {
                // 开始定位
                mLocationClient.requestLocation();
            }
        } else {
            // 开始定位
            mLocationClient.requestLocation();
        }
    }

    @Override
    public void addLocationListener(LocationListener listener) {
        if (listener != null) {
            getCallListenterArr().add(listener);
        }
    }

    @Override
    public void delLocationListener(LocationListener listener) {
        if (listener != null) {
            getCallListenterArr().remove(listener);
        }
    }

    @Override
    public LocationInfo getLocationInfo() {
        LocationInfo locationInfo = getSaveLocation();
        if (locationInfo == null) {
            requestLocation(false);
        }
        return (locationInfo == null ? new LocationInfo() : locationInfo);
    }

    @Override
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    /**
     * 返回位置信息给监听器
     */
    private void returnLocToLisenter(LocationInfo locationInfo) {
        for (int idx = 0; idx < getCallListenterArr().size(); idx++) {
            // try 防止某一处监听导致整个代码crash
            try {
                LocationListener locationListener = getCallListenterArr().get(idx);
                locationListener.onReceiveLocation(locationInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回错误信息给监听器
     */
    private void returnErrToListener(int errCode) {
        if (callListenerArr == null || callListenerArr.size() == 0) {
            return;
        }
        for (LocationListener locationListener : callListenerArr) {
            // try 防止某一处监听导致整个代码crash
            try {
                locationListener.onError(errCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void release() {
        getCallListenterArr().clear();
        // 释放所有的调用监听
        if (myListener != null) {
            mLocationClient.unRegisterLocationListener(myListener);
        }
        stopLocation();
    }

    /***
     * 将直接获取到的百度地图sdk对象转换为json对象
     * @param location
     * @return
     */
    private JSONObject toJsonObject(BDLocation location) {
        try {
            JSONObject locationJson = new JSONObject();
            locationJson.put("time", location.getTime());
            locationJson.put("longitude", location.getLongitude());
            locationJson.put("latitude", location.getLatitude());
            locationJson.put("radius", location.getRadius());
            locationJson.put("addressStr", location.getAddress());
            locationJson.put("province", location.getProvince());
            locationJson.put("city", location.getCity());
            locationJson.put("street", location.getStreet());
            locationJson.put("streetStr", location.getStreet());
            locationJson.put("streetNo", location.getStreetNumber());
            locationJson.put("district", location.getDistrict());
            locationJson.put("cityCode", location.getCityCode());
            locationJson.put("coorType", location.getCoorType());
            locationJson.put("country", location.getCountry());
            locationJson.put("countryCode", location.getCountryCode());
            return locationJson;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }

    /**
     * 将位置信息json，转换为可供外层调用的LocationInfo对象
     *
     * @param localJson
     * @return
     */
    private LocationInfo toLocationInfo(JSONObject localJson) {
        LocationInfo locationInfo = new LocationInfo();
        try {
            locationInfo.time = localJson.optLong("time");
            locationInfo.longitude = localJson.optDouble("longitude");
            locationInfo.latitude = localJson.optDouble("latitude");
            locationInfo.radius = localJson.optDouble("radius");
            locationInfo.addressStr = localJson.optString("addressStr");
            locationInfo.province = localJson.optString("province");
            locationInfo.city = localJson.optString("city");
            locationInfo.street = localJson.optString("street");
            locationInfo.streetStr = localJson.optString("streetStr");
            locationInfo.streetNo = localJson.optString("streetNo");
            locationInfo.district = localJson.optString("district");
            locationInfo.cityCode = localJson.optString("cityCode");
            locationInfo.coorType = localJson.optString("coorType");
            locationInfo.country = localJson.optString("country");
            locationInfo.countryCode = localJson.optString("countryCode");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationInfo;
    }

    /***
     * 保存位置信息
     * @param localJson
     */
    private void saveLocation(JSONObject localJson) {
        try {
            LocationPreferenceUtil.put(context, SAVE_PREFILE, localJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取保存到本地的位置信息，没有则返回null
     *
     * @return 没有返回null
     */
    private LocationInfo getSaveLocation() {
        try {
            LocationInfo locationInfo = new LocationInfo();
            String saveLocalString = (String) LocationPreferenceUtil.get(context, SAVE_PREFILE, "");
            if (!TextUtils.isEmpty(saveLocalString)) {
                JSONObject saveLocalJson = new JSONObject(saveLocalString);
                locationInfo = toLocationInfo(saveLocalJson);
            }
            return locationInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
