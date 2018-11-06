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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baidu.duer.dcs.sample.BuildConfig;
import com.baidu.duer.dcs.sample.R;

/**
 * Created by guxiuzhong@baidu.com on 2017/9/6.
 */
public class SDKAsrTypeActivity extends AppCompatActivity {

    private static final String TAG = SDKAsrTypeActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_asr_layout);
        setTitle(BuildConfig.APP_TITLE);
    }


    public void onClickSwitchVadAndTouch(View view) {
        startActivity(new Intent(this, SDKNearFarAsrActivity.class));
    }



    public void onClickAsrOffLine(View view) {
        startActivity(new Intent(this, SDKAsrOffLineActivity.class));
    }

}