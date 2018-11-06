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
package com.baidu.duer.dcs.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.duer.dcs.sample.sdk.SDKAsrAutoWithOutWakeUpActivity;
import com.baidu.duer.dcs.sample.sdk.SDKAsrAutoWithWakeUpActivity;
import com.baidu.duer.dcs.sample.sdk.SDKAsrTouchActivity;
import com.baidu.duer.dcs.sample.sdk.SDKAsrTypeActivity;
import com.baidu.duer.dcs.util.http.HttpConfig;
import com.baidu.duer.dcs.util.util.DcsGlobalConfig;

import java.lang.reflect.Field;

/**
 * Created by guxiuzhong@baidu.com on 2017/9/6.
 */
public class SDKMainActivity extends AppCompatActivity {

    private static final String TAG = SDKMainActivity.class.getName();
    private View mDbpView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_sample_main);
        setTitle(BuildConfig.APP_TITLE);
    }

    // dbp config
    public void onClickDbp(View view) {
        if (mDbpView == null) {
            final View vsDbp = findViewById(R.id.vs_dbp);
            mDbpView = ((ViewStub) vsDbp).inflate();
            ((Button) view).setText("再次点击确认Bot id配置");
        } else {
            // 确认配置信息
            final String botId = ((EditText) mDbpView.findViewById(R.id.bot_id)).getText().toString();
            if (TextUtils.isEmpty(botId)) {
                ((Button) view).setText("Bot id不能为空, 请输入有效的Bot id, 然后点击确认");
                HttpConfig.setDCSEndpoint(HttpConfig.HTTPS_PREFIX + HttpConfig.DCS_HOST_DUEROS);
                HttpConfig.setACLEndpoint(HttpConfig.HTTPS_PREFIX + HttpConfig.ACL_HOST_DUEROS);
                HttpConfig.globalDebugBotId = null;
            } else {
                HttpConfig.setDCSEndpoint(HttpConfig.HTTPS_PREFIX + HttpConfig.DBP_HOST_DUEROS);
                HttpConfig.setACLEndpoint(HttpConfig.HTTPS_PREFIX + HttpConfig.ACL_OPEN_HOST_DUEROS);
                HttpConfig.globalDebugBotId = "f15be387-1348-b71b-2ae5-8f19f2375ea1";
                ((Button) view).setText("配置成功，从下面选择你的操作");
            }
        }
    }


    // ---only在线
    public void onClickAutoWithWakeUp(View view) {
        startActivity(new Intent(this, SDKAsrAutoWithWakeUpActivity.class));
    }

    public void onClickAutoWithOutWakeUp(View view) {
        startActivity(new Intent(this, SDKAsrAutoWithOutWakeUpActivity.class));
    }

    public void onClickTouch(View view) {
        startActivity(new Intent(this, SDKAsrTouchActivity.class));
    }

    public void onClickAsrType(View view) {
        startActivity(new Intent(this, SDKAsrTypeActivity.class));
    }


    public void onClickOthers(View view) {
        startActivity(new Intent(this, SDKTtsActivity.class));
    }


    public void onClickAbout(View view) { // 仅供方便测试查看
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("关于");
        View contentView = LayoutInflater.from(this).inflate(R.layout.sdk_about_layout, null);
        initAboutView(contentView);
        alertDialogBuilder.setView(contentView);
        alertDialogBuilder.show();
    }

    /**
     * 【关于】弹窗的内容
     *  后续若想在demo中展示关键的属性（比如版本号，CLIENT_ID之类的）可以在这添加即可
     * @param view
     */
    private void initAboutView(View view) {

        ((TextView) view.findViewById(R.id.dcs_ver_tx)).setText(DcsGlobalConfig.VERSION_NAME + "");
        ((TextView) view.findViewById(R.id.dcs_channel_tx)).setText(BuildConfig.FLAVOR);

        ((TextView) view.findViewById(R.id.dcs_clientid_tx)).setText(BuildConfig.CLIENT_ID + "");
        ((TextView) view.findViewById(R.id.dcs_pid_tx)).setText(BuildConfig.PID + "");
        ((TextView) view.findViewById(R.id.dcs_logPrintLevel_tx)).setText(DcsGlobalConfig.LOG_LEVEL + "");

        ((TextView) view.findViewById(R.id.dcs_asrver_tx)).setText(getAsrSDKVersion() + "");


    }

    /**
     * 获取语音SDK的版本号
     * 目前语音SDK的版本号在com.baidu.speech.asr.SpeechConstant中
     * 但acl和puffer链路对应的字段却不一样，这里通过反射去获取。
     * @return
     */
    private String getAsrSDKVersion() {
        String ver = "NULL";
        Class speechContant = null;
        Field versioFiled;
        try {
            speechContant = Class.forName("com.baidu.speech.asr.SpeechConstant");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (speechContant != null) {
            // 默认先找ACL链路的SDK_VERSION字段，找不到再找PUFFER的VERSION_NAME
            try {
                versioFiled = speechContant.getField("SDK_VERSION"); // acl的版本字段
                versioFiled.setAccessible(true);
                ver = (String)versioFiled.get(null);
            } catch (NoSuchFieldException ex) {
                try {
                    versioFiled = speechContant.getField("VERSION_NAME"); // puffer的版本字段
                    versioFiled.setAccessible(true);
                    ver = (String)versioFiled.get(null);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return ver;
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Exception ignored) {
            // LEFT-DO-NOTHING
        }
    }
}