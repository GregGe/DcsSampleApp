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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.duer.dcs.api.DcsSdkBuilder;
import com.baidu.duer.dcs.api.IDcsSdk;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.api.config.DefaultSdkConfigProvider;
import com.baidu.duer.dcs.api.config.SdkConfigProvider;
import com.baidu.duer.dcs.api.recorder.AudioRecordImpl;
import com.baidu.duer.dcs.api.recorder.BaseAudioRecorder;
import com.baidu.duer.dcs.devicemodule.custominteraction.CustomUserInteractionDeviceModule;
import com.baidu.duer.dcs.framework.DcsSdkImpl;
import com.baidu.duer.dcs.framework.ILoginListener;
import com.baidu.duer.dcs.framework.InternalApi;
import com.baidu.duer.dcs.framework.TtsOnlineInfo;
import com.baidu.duer.dcs.oauth.api.code.OauthCodeImpl;
import com.baidu.duer.dcs.oauth.api.silent.SilentLoginImpl;
import com.baidu.duer.dcs.tts.TtsImpl;
import com.baidu.duer.dcs.util.TtsInfoHelper;
import com.baidu.duer.dcs.util.dispatcher.DialogRequestIdHandler;
import com.baidu.duer.dcs.util.util.DcsGlobalConfig;
import com.baidu.duer.dcs.util.util.StandbyDeviceIdUtil;


/**
 * Created by guxiuzhong@baidu.com on 2017/9/6.
 */
public class SDKTtsActivity extends AppCompatActivity implements View.OnClickListener {

    protected IDcsSdk dcsSdk;
    public static final String CLIENT_ID = BuildConfig.CLIENT_ID;
    protected EditText editText;

    private LayoutInflater mLayoutInflator;
    private AlertDialog mAlertDialog;
    private View mContentView;
    private EditText volumeEt;
    private EditText speedEt;
    private EditText pitchEt;
    private EditText speakerEt;
    private EditText aueEt;
    private EditText rateEt;
    private EditText xmlEt;
    private Button resetBtn;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_others_layout);
        setTitle(BuildConfig.APP_TITLE);
        editText = (EditText) findViewById(R.id.et_tts_others);
        editText.setSelection(editText.getText().length());
        handler = new Handler();
        initView();
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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TtsImpl impl = getInternalApi().initLocalTts(getApplicationContext(), null, null,
                        BuildConfig.TTS_APIKEY,
                        BuildConfig.TTS_SERCERTKEY, BuildConfig.TTS_APPID, null);
                impl.setSpeaker(2);
                String textFile = getApplicationInfo().nativeLibraryDir + "/libbd_etts_text.dat.so";
                String speechMode = getApplicationInfo().nativeLibraryDir + "/" + TtsImpl.SPEECH_MODEL_NAME_GEZI;
                impl.loadSpeechModel(speechMode, textFile);
                getInternalApi().setVolume(0.8f);
            }
        }, 200);

        getInternalApi().login(new ILoginListener() {
            @Override
            public void onSucceed(String accessToken) {
                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                dcsSdk.run(null);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getApplicationContext(), "login fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "login cancel", Toast.LENGTH_SHORT).show();
            }
        });
        IMessageSender messageSender = getInternalApi().getMessageSender();

        // 在线返回文本的播报，eg:你好，返回你好的播报
        DialogRequestIdHandler dialogRequestIdHandler =
                ((DcsSdkImpl) dcsSdk).getProvider().getDialogRequestIdHandler();
        CustomUserInteractionDeviceModule customUserInteractionDeviceModule =
                new CustomUserInteractionDeviceModule(messageSender, dialogRequestIdHandler);
        dcsSdk.putDeviceModule(customUserInteractionDeviceModule);
        hideKeyboard();
    }


    public InternalApi getInternalApi() {
        return ((DcsSdkImpl) dcsSdk).getInternalApi();
    }


    public void onClickTts(View view) {
        String inputText = editText.getText().toString().trim();
        if (TextUtils.isEmpty(inputText)) {
            Toast.makeText(this, getResources().getString(R.string.inputed_text_cannot_be_empty),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 收起键盘
        hideKeyboard();
        getInternalApi().speakOfflineRequest(inputText);
    }


    public void onClickUpdateOnlineTts(View view) {
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }


    public void onClickOnelineTts(View view) {
        String inputText = editText.getText().toString().trim();
        if (TextUtils.isEmpty(inputText)) {
            Toast.makeText(this, getResources().getString(R.string.inputed_text_cannot_be_empty),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 收起键盘
        hideKeyboard();
        getInternalApi().speakRequest(inputText);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }


    private void initView() {
        mLayoutInflator = LayoutInflater.from(this);
        mContentView = mLayoutInflator.inflate(R.layout.sdk_tts_update_dialog, null);
        volumeEt = (EditText) mContentView.findViewById(R.id.tts_info_volume_value);
        speedEt = (EditText) mContentView.findViewById(R.id.tts_info_speed_value);
        pitchEt = (EditText) mContentView.findViewById(R.id.tts_info_pitch_value);
        speakerEt = (EditText) mContentView.findViewById(R.id.tts_info_speaker_value);
        aueEt = (EditText) mContentView.findViewById(R.id.tts_info_aue_value);
        rateEt = (EditText) mContentView.findViewById(R.id.tts_info_rate_value);
        xmlEt = (EditText) mContentView.findViewById(R.id.tts_info_xml_value);
        resetBtn = (Button) mContentView.findViewById(R.id.btn_tts_reset);
        resetBtn.setOnClickListener(this);

        mAlertDialog = new AlertDialog.Builder(this)
                .setView(mContentView)
                .setPositiveButton("确定", getPositiveButtonListener())
                .setNegativeButton("取消", getNegativeButtonListener())
                .create();
    }


    private DialogInterface.OnClickListener getPositiveButtonListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fireOnClickPositive();
            }
        };
    }

    private DialogInterface.OnClickListener getNegativeButtonListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fireOnClickNegative();
            }
        };
    }

    private void fireOnClickPositive() {
        TtsOnlineInfo ttsInfo = new TtsOnlineInfo();
        ttsInfo.setVolume(volumeEt.getText().toString());
        ttsInfo.setSpeed(speedEt.getText().toString());
        ttsInfo.setPitch(pitchEt.getText().toString());
        ttsInfo.setSpeaker(speakerEt.getText().toString());
        ttsInfo.setAue(aueEt.getText().toString());
        ttsInfo.setRate(rateEt.getText().toString());
        ttsInfo.setXml(xmlEt.getText().toString());
        if (ttsInfo.getAue() != TtsOnlineInfo.AUDIO_FORMAT_MP3) {
            Toast.makeText(SDKTtsActivity.this, "Dcs Sdk 暂不支持非mp3的解码播放", Toast.LENGTH_LONG).show();
            return;
        }

        getInternalApi().updateOnlineTtsInfo(CLIENT_ID, ttsInfo, new TtsInfoHelper.IUpdateTtsCallback() {
            @Override
            public void success() {
                Toast.makeText(SDKTtsActivity.this, "更新TTS参数成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(String errMsg) {
                Toast.makeText(SDKTtsActivity.this, "更新TTS参数失败", Toast.LENGTH_LONG).show();
            }
        });
        hideKeyboard();
        mAlertDialog.dismiss();
    }

    private void fireOnClickNegative() {
        hideKeyboard();
        mAlertDialog.dismiss();
    }

    private void resetAll() {
        volumeEt.getEditableText().clear();
        speedEt.getEditableText().clear();
        pitchEt.getEditableText().clear();
        speakerEt.getEditableText().clear();
        aueEt.getEditableText().clear();
        rateEt.getEditableText().clear();
        xmlEt.getEditableText().clear();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_tts_reset) {
            resetAll();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        dcsSdk.release();

    }
}