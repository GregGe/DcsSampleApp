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
package com.baidu.duer.dcs.sample.sdk.offlinetts;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.duer.dcs.api.DcsSdkBuilder;
import com.baidu.duer.dcs.api.IDcsSdk;
import com.baidu.duer.dcs.api.config.DefaultSdkConfigProvider;
import com.baidu.duer.dcs.api.config.SdkConfigProvider;
import com.baidu.duer.dcs.api.recorder.AudioRecordImpl;
import com.baidu.duer.dcs.api.recorder.BaseAudioRecorder;
import com.baidu.duer.dcs.framework.DcsSdkImpl;
import com.baidu.duer.dcs.framework.InternalApi;
import com.baidu.duer.dcs.oauth.api.code.OauthCodeImpl;
import com.baidu.duer.dcs.oauth.api.silent.SilentLoginImpl;
import com.baidu.duer.dcs.sample.BuildConfig;
import com.baidu.duer.dcs.sample.R;
import com.baidu.duer.dcs.tts.TtsImpl;
import com.baidu.duer.dcs.util.util.StandbyDeviceIdUtil;

public class LocalTTSActivity extends AppCompatActivity {
    public static final String CLIENT_ID = BuildConfig.CLIENT_ID;

    private EditText editText;
    protected IDcsSdk dcsSdk;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tts);
        editText = (EditText) this.findViewById(R.id.edit);
        handler = new Handler();

        SdkConfigProvider sdkConfigProvider = new DefaultSdkConfigProvider() {
            @Override
            public String clientId() {
                return CLIENT_ID;
            }

            @Override
            public int pid() {
                return BuildConfig.PID;
            }

            @Override
            public String appKey() {
                return BuildConfig.APP_KEY;
            }
        };
        BaseAudioRecorder audioRecorder = new AudioRecordImpl();
        DcsSdkBuilder builder = new DcsSdkBuilder();
        dcsSdk = builder.withSdkConfig(sdkConfigProvider)
                .withAudioRecorder(audioRecorder)
                .withOauth(new OauthCodeImpl(CLIENT_ID, this))
                // StandbyDeviceIdUtil.getStandbyDeviceId()为sdk提供的方法，但是不保证所有的设别都是唯一的
                // 如果开发者清晰的知道自己设备的唯一id，可以按照自己的规则传入
                .withDeviceId(StandbyDeviceIdUtil.getStandbyDeviceId())
                .build();
        /**
         *
         * pid,key; apikey,secretkey;  两对不能同时为空，必须保证有一对设置
         *
         */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TtsImpl impl = getInternalApi().initLocalTts(getApplicationContext(), null, null,
                        "zYs2hGZyfV4GqDu1rTmWMbjqI2DMjG1b",
                        "WEDIKCt4zAS9n5UYS6WvWmx4H3dIWTRw", "10321182", null);
                impl.setSpeaker(2);
                String textFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/libbd_etts_text.dat.so";
                String speechMode = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/libbd_etts_speech_male.dat.so";
                impl.loadSpeechModel(speechMode, textFile);
                getInternalApi().setVolume(0.8f);
            }
        }, 200);
    }

    public void play(View view) {
        String inputText = editText.getText().toString().trim();
        if (TextUtils.isEmpty(inputText)) {
            Toast.makeText(this, getResources().getString(R.string.inputed_text_cannot_be_empty),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 收起键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        getInternalApi().speakOfflineRequest(inputText);
    }

    public InternalApi getInternalApi() {
        return ((DcsSdkImpl) dcsSdk).getInternalApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        dcsSdk.release();
    }

}
