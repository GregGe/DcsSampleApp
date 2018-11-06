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
package com.baidu.duer.dcs.sample.sdk;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.dcs.acl.AsrParam;
import com.baidu.duer.dcs.api.IDcsSdk;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.api.config.DcsConfig;
import com.baidu.duer.dcs.api.config.DefaultSdkConfigProvider;
import com.baidu.duer.dcs.api.config.SdkConfigProvider;
import com.baidu.duer.dcs.sample.R;
import com.baidu.duer.dcs.sample.sdk.devicemodule.asr.AsrDeviceModule;
import com.baidu.duer.dcs.util.AsrType;
import com.baidu.duer.dcs.util.devicemodule.asr.message.HandleAsrResultPayload;

import java.io.File;

/**
 * Created by liangxianlan on 13/04/2018.
 * <p>
 * 集成离线语音识别步骤：
 * <p>
 * 一、libbd_model_easr_dat.so是离线资源文件，离线资源文件可以预置到data中，也可以放到sdcard下
 * <p>
 * 二、离线参数设置
 * 1. 设置离线资源文件绝对路径，例如放在sdcard中：
 * AsrParam.ASR_OFFLINE_ENGINE_DAT_FILE_PATH = Environment.getExternalStorageDirectory() + "/libbd_model_easr_dat.so";
 * 2.打开离线标点开关，0关闭，1打开
 * AsrParam.ASR_OFFLINE_PUNCTUATION_SETTING_VALUE = 1;
 * 3.设置为离线模式，0在线，1离线。注意，从离线切换到在线时，AsrParam.ASR_DECODER需要设置为0。
 * (1). AsrParam.ASR_DECODER = 1;
 * (2). asrMode = DcsConfig.ASR_MODE_OFFLINE
 * (3). asrOnly = true
 * 4.设置离线license，离线license没有设置，第一次识别需要联网在线鉴权
 * AsrParam.ASR_OFFLINE_ENGINE_LICENSE_FILE_PATH = "assets://temp_license_2018-07-04";
 * <p>
 * 注：离线asr不支持longspeech
 *
 *
 * 2018.07.13
 * 增加离线识别：通讯录和应用程序名称的识别
 * 1. asrMode = DcsConfig.ASR_MODE_OFFLINE_SEMANTIC
 * 2. AsrParam.ASR_OFFLINE_PUNCTUATION_SETTING_VALUE = 0(无标点)
 */

public class SDKAsrOffLineActivity extends SDKBaseActivity {

    private LinearLayout topLinearLayout;
    private TextView asrContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        // 设置离线资源文件绝对路径，这里是放在sdcard中
        // libbd_model_easr_dat.so在extras/offlineresource/下
        String offLineSourceFilePath = Environment.getExternalStorageDirectory() + "/libbd_model_easr_dat.so";
        if (!new File(offLineSourceFilePath).exists()) {
            Toast.makeText(SDKAsrOffLineActivity.this,
                    "离线资源文件：" + offLineSourceFilePath + "不存在",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        AsrParam.ASR_OFFLINE_ENGINE_DAT_FILE_PATH = offLineSourceFilePath;
        // 打开离线标点开关
        AsrParam.ASR_OFFLINE_PUNCTUATION_SETTING_VALUE = 2;
        // 设置为离线模式:1
        AsrParam.ASR_DECODER = 1;
        // 离线识别时，较长vad检测开关，检测时间1300ms
        AsrParam.ASR_OFFLINE_VAD_LONG = true;
        // 设置离线 license
//        AsrParam.ASR_OFFLINE_ENGINE_LICENSE_FILE_PATH = "assets://temp_license_2018-07-04";
    }

    @Override
    public AsrType getAsrType() {
        return AsrType.AUTO;
    }

    @Override
    public int getAsrMode() {
        return DcsConfig.ASR_MODE_OFFLINE;
    }

    @Override
    public boolean enableWakeUp() {
        return false;
    }

    @Override
    protected SdkConfigProvider getSdkConfigProvider() {
        return new DefaultSdkConfigProvider() {
            @Override
            public String clientId() {
                return CLIENT_ID;
            }

            @Override
            public int pid() {
                return 708;
            }

            @Override
            public boolean asrOnly() {
                // 设置为离线模式-3
                return true;
            }

            @Override
            public boolean longSpeech() {
                return false;
            }
        };
    }

    private void initViews() {
        topLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);
        topLinearLayout.removeAllViews();
        asrContentView = new TextView(this);
        final Button button = new Button(this);
        button.setText("识别联系人和应用名称：关闭");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("识别联系人和应用名称：开启");
                AsrParam.ASR_OFFLINE_PUNCTUATION_SETTING_VALUE = 0;
                getInternalApi().setAsrMode(DcsConfig.ASR_MODE_OFFLINE_SEMANTIC);
                AsrParam.ASR_OFFLINE_CONTACTS = "张三\n小李\n王老师";
                AsrParam.ASR_OFFLINE_APPNAMES = "微信\n王者荣耀\n手机百度";
            }
        });
        topLinearLayout.addView(button, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        topLinearLayout.addView(asrContentView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void addOtherDeviceModule(IDcsSdk dcsSdk, IMessageSender messageSender) {
        super.addOtherDeviceModule(dcsSdk, messageSender);
        // 单ASR，不返回度秘结果
        AsrDeviceModule asrDeviceModule = new AsrDeviceModule(messageSender);
        asrDeviceModule.addAsrListener(new AsrDeviceModule.IAsrListener() {
            @Override
            public void onHandleAsrResult(HandleAsrResultPayload payload) {
                asrContentView.setText(payload.getContent());
            }
        });
        dcsSdk.putDeviceModule(asrDeviceModule);
    }
}
