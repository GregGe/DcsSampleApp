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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.duer.dcs.api.DcsSdkBuilder;
import com.baidu.duer.dcs.api.IConnectionStatusListener;
import com.baidu.duer.dcs.api.IDcsSdk;
import com.baidu.duer.dcs.api.IDialogStateListener;
import com.baidu.duer.dcs.api.IFinishedDirectiveListener;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.api.INearAsrEndListener;
import com.baidu.duer.dcs.api.ISwitchAsrNearFarListener;
import com.baidu.duer.dcs.api.IVoiceRequestListener;
import com.baidu.duer.dcs.api.config.DcsConfig;
import com.baidu.duer.dcs.api.config.DefaultSdkConfigProvider;
import com.baidu.duer.dcs.api.config.SdkConfigProvider;
import com.baidu.duer.dcs.api.player.ITTSPositionInfoListener;
import com.baidu.duer.dcs.api.recorder.AudioRecordImpl;
import com.baidu.duer.dcs.api.recorder.BaseAudioRecorder;
import com.baidu.duer.dcs.api.wakeup.BaseWakeup;
import com.baidu.duer.dcs.api.wakeup.IWakeupAgent;
import com.baidu.duer.dcs.api.wakeup.IWakeupProvider;
import com.baidu.duer.dcs.componentapi.SimpleResponseListener;
import com.baidu.duer.dcs.devicemodule.custominteraction.CustomUserInteractionDeviceModule;
import com.baidu.duer.dcs.devicemodule.form.Form;
import com.baidu.duer.dcs.devicemodule.playbackcontroller.PlaybackControllerDeviceModule;
import com.baidu.duer.dcs.framework.DcsSdkImpl;
import com.baidu.duer.dcs.framework.ILoginListener;
import com.baidu.duer.dcs.framework.InternalApi;
import com.baidu.duer.dcs.framework.internalapi.IDirectiveReceivedListener;
import com.baidu.duer.dcs.framework.internalapi.IErrorListener;
import com.baidu.duer.dcs.framework.location.Location;
import com.baidu.duer.dcs.location.ILocation;
import com.baidu.duer.dcs.location.LocationImpl;
import com.baidu.duer.dcs.oauth.api.code.OauthCodeImpl;
import com.baidu.duer.dcs.oauth.api.silent.SilentLoginImpl;
import com.baidu.duer.dcs.sample.BuildConfig;
import com.baidu.duer.dcs.sample.R;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.ScreenDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.ScreenExtendDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderAudioListPlayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderPlayerInfoPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.HtmlPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.RenderCardPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.RenderHintPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.RenderVoiceInputTextPayload;
import com.baidu.duer.dcs.systeminterface.IOauth;
import com.baidu.duer.dcs.util.DcsErrorCode;
import com.baidu.duer.dcs.util.HttpProxy;
import com.baidu.duer.dcs.util.api.IDcsRequestBodySentListener;
import com.baidu.duer.dcs.util.dispatcher.DialogRequestIdHandler;
import com.baidu.duer.dcs.util.message.DcsRequestBody;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Payload;
import com.baidu.duer.dcs.util.util.CommonUtil;
import com.baidu.duer.dcs.util.util.FileUtil;
import com.baidu.duer.dcs.util.util.LogUtil;
import com.baidu.duer.dcs.util.util.NetWorkUtil;
import com.baidu.duer.dcs.util.util.StandbyDeviceIdUtil;
import com.baidu.duer.dcs.widget.DcsWebView;
import com.baidu.duer.kitt.KittWakeUpImpl;
import com.baidu.duer.kitt.WakeUpConfig;
import com.baidu.duer.kitt.WakeUpWord;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 远近场测试页面
 * <p>
 * 录音权限问题，需要自己处理
 * VAD和touch模式动态切换的demo
 */
public class SDKNearFarAsrActivity extends AppCompatActivity implements
        View.OnClickListener {
    public static final String TAG = "DCS-SDK";
    public static final String CLIENT_ID = /*"d8ITlI9aeTPaGcxKKsZit8tq"*/BuildConfig.CLIENT_ID;
    // 唤醒配置
    // 格式必须为：浮点数，用','分隔，每个模型对应3个灵敏度
    // 例如有2个模型,就需要6个灵敏度，0.35,0.35,0.40,0.45,0.45,0.55
    private static final String WAKEUP_RES_PATH = "snowboy/common.res";
    private static final String WAKEUP_UMDL_PATH = "snowboy/xiaoduxiaodu_all_11272017.umdl";
    private static final String WAKEUP_SENSITIVITY = "0.35,0.35,0.40";
    private static final String WAKEUP_HIGH_SENSITIVITY = "0.45,0.45,0.55";
    // 唤醒成功后是否需要播放提示音
    private static final boolean ENABLE_PLAY_WARNING = true;
    private static final int REQUEST_CODE = 123;
    protected EditText textInput;
    protected Button sendButton;
    protected IDcsSdk dcsSdk;
    protected ScreenDeviceModule screenDeviceModule;
    private Button nextButton;
    private Button preButton;
    private Button playButton;
    private Button voiceButton;
    private Button cancelVoiceButton;
    private boolean isPlaying;
    private TextView textViewWakeUpTip;
    private LinearLayout mTopLinearLayout;
    private DcsWebView dcsWebView;
    private ILocation location;
    // for dcs统计-demo
    private long duerResultT;
    // for dcs统计-demo
    private TextView textViewRenderVoiceInputText;
    private IWakeupAgent.IWakeupAgentListener wakeupAgentListener;
    private IDialogStateListener dialogStateListener;
    private IDialogStateListener.DialogState currentDialogState = IDialogStateListener.DialogState.IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_main);
        setTitle(BuildConfig.APP_TITLE);
        initViews();
        initPermission();
        initSdk();
        sdkRun();
        initListener();
        initLocation();
        // 默认vad模式，可以随意切换
        getInternalApi().switchAsrToFar(audioRecorder, new ISwitchAsrNearFarListener() {
            @Override
            public void onEnd() {
                LogUtil.dc(TAG, "switch-init-switchAsrToFar-onEnd");
                isAsrNear = false;
                textViewWakeUpTip.setVisibility(View.VISIBLE);
                // 唤醒
                initWakeUpAgentListener();
            }
        });

        // 默认touch模式，可以随意切换
//        getInternalApi().switchAsrToNear(audioRecorder, new ISwitchAsrNearFarListener() {
//            @Override
//            public void onEnd() {
//                LogUtil.dc(TAG, "switch-init-switchAsrToNear-onEnd");
//                textViewWakeUpTip.setVisibility(View.GONE);
//            }
//        });
    }

    protected void initListener() {
        // 设置各种监听器
        dcsSdk.addConnectionStatusListener(connectionStatusListener);
        // 错误
        getInternalApi().addErrorListener(errorListener);
        // event发送
        getInternalApi().addRequestBodySentListener(dcsRequestBodySentListener);
        // 需要定位后赋值，目前是写死的北京的
        getInternalApi().setLocationHandler(locationHandler);
        // 对话状态
        initDialogStateListener();
        // 语音文本同步
        initTTSPositionInfoListener();
        // 唤醒
        initWakeUpAgentListener();
        // 所有指令透传，建议在各自的DeviceModule中处理
        addDirectiveReceivedListener();
        // 指令执行完毕回调
        initFinishedDirectiveListener();
        // 添加近场touch模式下asr识别回调
        addNearAsrEndListener();
    }

    private void addNearAsrEndListener() {
        getInternalApi().addNearAsrEndListener(new INearAsrEndListener() {
            @Override
            public void onEnd() {
                LogUtil.dc(TAG, "switch-addNearAsrEndListener-onEnd");
                getInternalApi().switchAsrToFar(audioRecorder, new ISwitchAsrNearFarListener() {
                    @Override
                    public void onEnd() {
                        LogUtil.dc(TAG, "switch-switchAsrToFar-onEnd");
                        isAsrNear = false;
                        textViewWakeUpTip.setVisibility(View.VISIBLE);
                        // 唤醒回调-切换到touch模式后，会销毁唤醒，需要重新添加
                        initWakeUpAgentListener();
                    }
                });
            }
        });
    }

    private void initLocation() {
        // 定位
        location = new LocationImpl(getApplicationContext());
        location.requestLocation(false);
    }

    protected Location.LocationHandler locationHandler = new Location.LocationHandler() {
        @Override
        public double getLongitude() {
            if (location == null) {
                return 0;
            }
            return location.getLocationInfo().longitude;
        }

        @Override
        public double getLatitude() {
            if (location == null) {
                return 0;
            }
            return location.getLocationInfo().latitude;
        }

        @Override
        public String getCity() {
            if (location == null) {
                return "";
            }
            return location.getLocationInfo().city;
        }

        @Override
        public Location.EGeoCoordinateSystem getGeoCoordinateSystem() {
            return Location.EGeoCoordinateSystem.BD09LL;
        }
    };
    private ScreenDeviceModule.IScreenListener screenListener = new ScreenDeviceModule.IScreenListener() {
        @Override
        public void onRenderVoiceInputText(RenderVoiceInputTextPayload payload) {
            handleRenderVoiceInputTextPayload(payload);
        }

        @Override
        public void onHtmlPayload(HtmlPayload htmlPayload) {
            handleHtmlPayload(htmlPayload);
        }

        @Override
        public void onRenderCard(RenderCardPayload renderCardPayload) {

        }

        @Override
        public void onRenderHint(RenderHintPayload renderHintPayload) {

        }
    };

    private IDcsRequestBodySentListener dcsRequestBodySentListener = new IDcsRequestBodySentListener() {

        @Override
        public void onDcsRequestBody(DcsRequestBody dcsRequestBody) {
            String eventName = dcsRequestBody.getEvent().getHeader().getName();
            LogUtil.dc(TAG, "eventName:" + eventName);
            if (eventName.equals("PlaybackStopped") || eventName.equals("PlaybackFinished")
                    || eventName.equals("PlaybackFailed")) {
                playButton.setText("等待音乐");
                isPlaying = false;
            } else if (eventName.equals("PlaybackPaused")) {
                playButton.setText("暂停中");
                isPlaying = false;
            } else if (eventName.equals("PlaybackStarted") || eventName.equals("PlaybackResumed")) {
                playButton.setText("播放中...");
                isPlaying = true;
            }
        }
    };
    private IErrorListener errorListener = new IErrorListener() {
        @Override
        public void onErrorCode(DcsErrorCode errorCode) {
            LogUtil.dc(TAG, "onErrorCode:" + errorCode);
            if (errorCode.error == DcsErrorCode.VOICE_REQUEST_EXCEPTION) {
                if (errorCode.subError == DcsErrorCode.NETWORK_UNAVAILABLE) {
                    Toast.makeText(SDKNearFarAsrActivity.this,
                            "网络不可用",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(SDKNearFarAsrActivity.this,
                            getResources().getString(R.string.voice_err_msg),
                            Toast.LENGTH_SHORT)
                            .show();
                }

            } else if (errorCode.error == DcsErrorCode.LOGIN_FAILED) {
                // 未登录
                Toast.makeText(SDKNearFarAsrActivity.this,
                        "未登录",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };
    private IConnectionStatusListener connectionStatusListener = new IConnectionStatusListener() {
        @Override
        public void onConnectStatus(ConnectionStatus connectionStatus) {
            LogUtil.dc(TAG, "onConnectionStatusChange: " + connectionStatus);
        }
    };

    /**
     * tts文字同步
     */
    private void initTTSPositionInfoListener() {
        getInternalApi().addTTSPositionInfoListener(new ITTSPositionInfoListener() {
            @Override
            public void onPositionInfo(long pos, long playTimeMs, long mark) {
                LogUtil.dc(TAG, "pos:" + pos + ",playTimeMs:" + playTimeMs + ",mark:" + mark);
            }
        });
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ArrayList<String> toApplyList = new ArrayList<>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this,
                    perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        if (!toApplyList.isEmpty()) {
            String[] tmpList = new String[toApplyList.size()];
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。

    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Exception ignored) {
            // LEFT-DO-NOTHING
        }
    }

    private BaseAudioRecorder audioRecorder;

    protected void initSdk() {
        // 第一步初始化sdk
        // BaseAudioRecorder audioRecorder = new PcmAudioRecorderImpl(); pcm 输入方式
        audioRecorder = new AudioRecordImpl();
        IOauth oauth = getOath();
        // 唤醒
        final BaseWakeup wakeup = new KittWakeUpImpl(audioRecorder);
        // 百度语音团队的离线asr和百度语音团队的唤醒，2个so库冲突，暂时不要用WakeupImpl实现的唤醒功能！！
//        final BaseWakeup wakeup = new WakeupImpl();
        final IWakeupProvider wakeupProvider = new IWakeupProvider() {
            @Override
            public WakeUpConfig wakeUpConfig() {
                // 添加多唤醒词和索引
                // 此处传入的index需要和Snowboy唤醒模型文件一致
                // 例：模型文件中有3个唤醒词，分别为不同语速的"小度小度"，index分别为1-3，则需要按照以下格式添加
                // 唤醒成功后，回调中会包含被唤醒的WakeUpWord
                List<WakeUpWord> wakeupWordList = new ArrayList<>();
                wakeupWordList.add(new WakeUpWord(1, "小度小度"));
                wakeupWordList.add(new WakeUpWord(2, "小度小度"));
                wakeupWordList.add(new WakeUpWord(3, "小度小度"));
                final List<String> umdlPaths = new ArrayList<>();
                umdlPaths.add(WAKEUP_UMDL_PATH);
                return new WakeUpConfig.Builder()
                        .resPath(WAKEUP_RES_PATH)
                        .umdlPath(umdlPaths)
                        .sensitivity(WAKEUP_SENSITIVITY)
                        .highSensitivity(WAKEUP_HIGH_SENSITIVITY)
                        .wakeUpWords(wakeupWordList)
                        .build();
            }

            @Override
            public boolean enableWarning() {
                return ENABLE_PLAY_WARNING;
            }

            @Override
            public String warningSource() {
                // 每次在播放唤醒提示音前调用该方法
                // assets目录下的以assets://开头
                // sd文件或应用私有目录为绝对路径
                return "assets://ding.wav";
            }

            @Override
            public float volume() {
                // 每次在播放唤醒提示音前调用该方法
                // [0-1]
                return 0.8f;
            }

            @Override
            public boolean wakeAlways() {
                return true;
            }

            @Override
            public BaseWakeup wakeupImpl() {
                return wakeup;
            }

            @Override
            public int audioType() {
                // 用户自定义类型
                return AudioManager.STREAM_SYSTEM;
            }
        };


        // SDK配置，ClientId、语音PID、代理等
        SdkConfigProvider sdkConfigProvider = getSdkConfigProvider();

        // 构造dcs sdk
        DcsSdkBuilder builder = new DcsSdkBuilder();
        dcsSdk = builder.withSdkConfig(sdkConfigProvider)
                .withWakeupProvider(wakeupProvider)
                .withOauth(oauth)
                .withAudioRecorder(audioRecorder)
                // 1.withDeviceId设置设备唯一ID
                // 2.强烈建议！！！！
                //   如果开发者清晰的知道自己设备的唯一id，可以按照自己的规则传入
                //   需要保证设置正确，保证唯一、刷机和升级后不变
                // 3.sdk提供的方法，但是不保证所有的设备都是唯一的
                //   StandbyDeviceIdUtil.getStandbyDeviceId()
                //   该方法的算法是MD5（android_id + imei + Mac地址）32位  +  32位UUID总共64位
                //   生成：首次按照上述算法生成ID，生成后依次存储apk内部->存储系统数据库->存储外部文件
                //   获取：存储apk内部->存储系统数据库->存储外部文件，都没有则重新生成
                .withDeviceId(StandbyDeviceIdUtil.getStandbyDeviceId())
                // 设置音乐播放器的实现，sdk 内部默认实现为MediaPlayerImpl
                // .withMediaPlayer(new MediaPlayerImpl(AudioManager.STREAM_MUSIC))
                .build();

        // ！！！！临时配置需要在run之前设置！！！！
        // 设置Oneshot
        getInternalApi().setSupportOneshot(false);
        // 临时配置开始
        // 暂时没有定的API接口，可以通过getInternalApi设置后使用
        // 设置唤醒参数后，初始化唤醒
        // vad 和touch模式 切换，这里不需要初始化了！！！
        // getInternalApi().initWakeUp();
//        getInternalApi().setOnPlayingWakeUpSensitivity(WAKEUP_ON_PLAYING_SENSITIVITY);
//        getInternalApi().setOnPlayingWakeUpHighSensitivity(WAKEUP_ON_PLAYING_HIGH_SENSITIVITY);
        getInternalApi().setAsrMode(DcsConfig.ASR_MODE_ONLINE);
        // 临时配置结束
        // dbp平台
        // getInternalApi().setDebugBot("f15be387-1348-b71b-2ae5-8f19f2375ea1");

        // 第二步：可以按需添加内置端能力和用户自定义端能力（需要继承BaseDeviceModule）
        // 屏幕展示
        IMessageSender messageSender = getInternalApi().getMessageSender();

        // 上屏
        screenDeviceModule = new ScreenDeviceModule(messageSender);
        screenDeviceModule.addScreenListener(screenListener);
        dcsSdk.putDeviceModule(screenDeviceModule);

        ScreenExtendDeviceModule screenExtendDeviceModule = new ScreenExtendDeviceModule(messageSender);
        screenExtendDeviceModule.addExtensionListener(mScreenExtensionListener);
        dcsSdk.putDeviceModule(screenExtendDeviceModule);

        // 在线返回文本的播报，eg:你好，返回你好的播报
        DialogRequestIdHandler dialogRequestIdHandler =
                ((DcsSdkImpl) dcsSdk).getProvider().getDialogRequestIdHandler();
        CustomUserInteractionDeviceModule customUserInteractionDeviceModule =
                new CustomUserInteractionDeviceModule(messageSender, dialogRequestIdHandler);
        dcsSdk.putDeviceModule(customUserInteractionDeviceModule);

        // 扩展自定义DeviceModule,eg...
        addOtherDeviceModule(dcsSdk, messageSender);
    }

    protected void addOtherDeviceModule(IDcsSdk dcsSdk, IMessageSender messageSender) {

    }

    protected SdkConfigProvider getSdkConfigProvider() {
        return new DefaultSdkConfigProvider() {
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
    }

    private String mRenderPlayerInfoToken = null;
    private String mPlayToken = null;
    private ScreenExtendDeviceModule.IScreenExtensionListener mScreenExtensionListener = new ScreenExtendDeviceModule
            .IScreenExtensionListener() {


        @Override
        public void onRenderPlayerInfo(RenderPlayerInfoPayload renderPlayerInfoPayload) {
            // handleRenderPlayerInfoPayload(renderPlayerInfoPayload);
        }

        @Override
        public void onRenderAudioList(RenderAudioListPlayload renderAudioListPlayload) {

        }
    };

    protected void sdkRun() {
        // 第三步，将sdk跑起来
        ((DcsSdkImpl) dcsSdk).getInternalApi().login(new ILoginListener() {
            @Override
            public void onSucceed(String accessToken) {
                dcsSdk.run(null);
                Toast.makeText(SDKNearFarAsrActivity.this.getApplicationContext(), "登录成功", Toast
                        .LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(SDKNearFarAsrActivity.this.getApplicationContext(), "登录失败", Toast
                        .LENGTH_SHORT).show();
                LogUtil.dc(TAG, "login onFailed. ");
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SDKNearFarAsrActivity.this.getApplicationContext(), "登录被取消", Toast
                        .LENGTH_SHORT).show();
                LogUtil.dc(TAG, "login onCancel. ");
                finish();
            }
        });
    }

    private void initViews() {
        textViewWakeUpTip = (TextView) findViewById(R.id.id_tv_wakeup_tip);
        nextButton = (Button) findViewById(R.id.id_next_audio_btn);
        nextButton.setOnClickListener(this);
        preButton = (Button) findViewById(R.id.id_previous_audio);
        preButton.setOnClickListener(this);
        playButton = (Button) findViewById(R.id.id_audio_default_btn);
        playButton.setOnClickListener(this);
        textInput = (EditText) findViewById(R.id.textInput);
        sendButton = (Button) findViewById(R.id.sendBtn);
        sendButton.setOnClickListener(this);
        voiceButton = (Button) findViewById(R.id.voiceBtn);
        voiceButton.setOnClickListener(this);
        cancelVoiceButton = (Button) findViewById(R.id.cancelBtn);
        cancelVoiceButton.setOnClickListener(this);
        textViewRenderVoiceInputText = (TextView) findViewById(R.id.id_tv_RenderVoiceInputText);
        mTopLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);
        dcsWebView = new DcsWebView(this.getApplicationContext());
        mTopLinearLayout.addView(dcsWebView);

        textViewWakeUpTip.setVisibility(View.GONE);
        initDcsWebView();
    }

    private void initDcsWebView() {
        dcsWebView.setLoadListener(new DcsWebView.LoadListener() {
            @Override
            public void onPageStarted() {

            }

            @Override
            public void onPageFinished() {
                if (duerResultT > 0) {
                    // DCSStatisticsImpl.getInstance().reportView(duerResultT, System.currentTimeMillis());
                    Toast.makeText(SDKNearFarAsrActivity.this, (System.currentTimeMillis() - duerResultT)
                            + " ms", Toast.LENGTH_LONG).show();
                    duerResultT = 0;
                }
            }
        });
    }

    public InternalApi getInternalApi() {
        return ((DcsSdkImpl) dcsSdk).getInternalApi();
    }

    private void initWakeUpAgentListener() {
        IWakeupAgent wakeupAgent = getInternalApi().getWakeupAgent();
        if (wakeupAgent != null) {
            wakeupAgentListener = new IWakeupAgent.SimpleWakeUpAgentListener() {
                @Override
                public void onWakeupSucceed(WakeUpWord wakeUpWord) {
                    Toast.makeText(SDKNearFarAsrActivity.this,
                            "唤醒成功，唤醒词是：" + wakeUpWord.getWord(),
                            Toast.LENGTH_SHORT).show();
                }
            };
            wakeupAgent.addWakeupAgentListener(wakeupAgentListener);
        }
    }

    private void beginVoiceRequest(final boolean vad) {
        // 必须先调用cancel
        dcsSdk.getVoiceRequest().cancelVoiceRequest(false, new IVoiceRequestListener() {
            @Override
            public void onSucceed() {
                dcsSdk.getVoiceRequest().beginVoiceRequest(vad);
            }
        });
    }

    private void initDialogStateListener() {
        // 添加会话状态监听
        dialogStateListener = new IDialogStateListener() {
            @Override
            public void onDialogStateChanged(final DialogState dialogState) {
                currentDialogState = dialogState;
                LogUtil.dc(TAG, "onDialogStateChanged: " + dialogState);
                switch (dialogState) {
                    case IDLE:
                        voiceButton.setText(getResources().getString(R.string.stop_record));
                        break;
                    case LISTENING:
                        textViewRenderVoiceInputText.setText("");
                        voiceButton.setText(getResources().getString(R.string.start_record));
                        break;
                    case SPEAKING:
                        voiceButton.setText(getResources().getString(R.string.speaking));
                        break;
                    case THINKING:
                        voiceButton.setText(getResources().getString(R.string.think));
                        break;
                    default:
                        break;
                }
            }
        };
        dcsSdk.getVoiceRequest().addDialogStateListener(dialogStateListener);
    }

    private void addDirectiveReceivedListener() {
        getInternalApi().addDirectiveReceivedListener(new IDirectiveReceivedListener() {
            @Override
            public void onDirective(Directive directive) {
                if (directive == null) {
                    return;
                }
                LogUtil.dc(TAG, "name = " + directive.getName());
                if (directive.getName().equals("Play")) {
                    Payload mPayload = directive.getPayload();
                    if (mPayload instanceof com.baidu.duer.dcs.devicemodule.audioplayer.message.PlayPayload) {
                        com.baidu.duer.dcs.devicemodule.audioplayer.message.PlayPayload.Stream stream =
                                ((com.baidu.duer.dcs.devicemodule.audioplayer.message.PlayPayload) mPayload)
                                        .audioItem.stream;
                        if (stream != null) {
                            mPlayToken = ((com.baidu.duer.dcs.devicemodule.audioplayer.message.PlayPayload) mPayload)
                                    .audioItem.stream.token;
                            LogUtil.dc(TAG, "  directive mToken = " + mPlayToken);
                        }
                    }
                } else if (directive.getName().equals("RenderPlayerInfo")) {
                    Payload mPayload = directive.getPayload();
                    if (mPayload instanceof RenderPlayerInfoPayload) {
                        mRenderPlayerInfoToken = ((RenderPlayerInfoPayload) mPayload).getToken();
                    }
                }
            }
        });
    }

    private void initFinishedDirectiveListener() {
        // 所有指令执行完毕的回调监听
        getInternalApi().addFinishedDirectiveListener(new IFinishedDirectiveListener() {
            @Override
            public void onFinishedDirective() {
                LogUtil.dc(TAG, "所有指令执行完毕");
            }
        });
    }


    private void handleHtmlPayload(HtmlPayload payload) {
        dcsWebView.loadUrl(payload.getUrl());
        duerResultT = System.currentTimeMillis();
    }

    private void handleRenderVoiceInputTextPayload(RenderVoiceInputTextPayload payload) {
        textViewRenderVoiceInputText.setText(payload.text);
        if (payload.type == RenderVoiceInputTextPayload.Type.FINAL) {
            FileUtil.appendStrToFileNew("ASR-FINAL-RESULT:" + payload.text + "," + System
                    .currentTimeMillis() + "\n");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_next_audio_btn:
                if (CommonUtil.isFastDoubleClick()) {
                    return;
                }
                cancelVoiceRequest();
                if (TextUtils.isEmpty(mRenderPlayerInfoToken) || !mRenderPlayerInfoToken.equals(mPlayToken)) {
                    getInternalApi().sendCommandIssuedEvent(PlaybackControllerDeviceModule
                            .CommandIssued
                            .CommandIssuedNext);
                } else {
                    getInternalApi().stopSpeaker();
                    getInternalApi().postEvent(Form.nextButtonClicked(mRenderPlayerInfoToken),
                            new SimpleResponseListener() {
                                @Override
                                public void onSucceed(int statusCode) {
                                    if (statusCode == 204) {
                                        getInternalApi().resumeSpeaker();
                                    }
                                }

                                @Override
                                public void onFailed(DcsErrorCode errorMessage) {
                                    getInternalApi().resumeSpeaker();
                                }
                            });
                }
                break;
            case R.id.id_previous_audio:
                if (CommonUtil.isFastDoubleClick()) {
                    return;
                }
                cancelVoiceRequest();
                if (TextUtils.isEmpty(mRenderPlayerInfoToken) || !mRenderPlayerInfoToken.equals(mPlayToken)) {
                    getInternalApi().sendCommandIssuedEvent(PlaybackControllerDeviceModule
                            .CommandIssued
                            .CommandIssuedPrevious);
                } else {
                    getInternalApi().stopSpeaker();
                    getInternalApi().postEvent(Form.previousButtonClicked(mRenderPlayerInfoToken),
                            new SimpleResponseListener() {
                                @Override
                                public void onSucceed(int statusCode) {
                                    if (statusCode == 204) {
                                        getInternalApi().resumeSpeaker();
                                    }
                                }

                                @Override
                                public void onFailed(DcsErrorCode errorMessage) {
                                    getInternalApi().resumeSpeaker();
                                }
                            });
                }
                break;
            case R.id.id_audio_default_btn:
                if (CommonUtil.isFastDoubleClick()) {
                    return;
                }
                if (TextUtils.isEmpty(mRenderPlayerInfoToken) || !mRenderPlayerInfoToken.equals(mPlayToken)) {
                    if (isPlaying) {
                        getInternalApi().sendCommandIssuedEvent(PlaybackControllerDeviceModule
                                .CommandIssued
                                .CommandIssuedPause);

                    } else {
                        getInternalApi().sendCommandIssuedEvent(PlaybackControllerDeviceModule
                                .CommandIssued
                                .CommandIssuedPlay);
                    }
                    isPlaying = !isPlaying;
                } else {
                    getInternalApi().postEvent(Form.playPauseButtonClicked(mRenderPlayerInfoToken), null);
                }
                break;
            case R.id.sendBtn:
                String inputText = textInput.getText().toString().trim();
                if (TextUtils.isEmpty(inputText)) {
                    Toast.makeText(this, getResources().getString(R.string
                                    .inputed_text_cannot_be_empty),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 清空并收起键盘
                textInput.getEditableText().clear();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
                if (!NetWorkUtil.isNetworkConnected(this)) {
                    Toast.makeText(this,
                            getResources().getString(R.string.err_net_msg),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                getInternalApi().sendQuery(inputText);
                break;
            case R.id.voiceBtn:
                if (!NetWorkUtil.isNetworkConnected(this)) {
                    Toast.makeText(this,
                            getResources().getString(R.string.err_net_msg),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CommonUtil.isFastDoubleClick()) {
                    return;
                }
                // 为了解决频繁的点击 而服务器没有时间返回结果造成的不能点击的bug
                if (currentDialogState == IDialogStateListener.DialogState.LISTENING) {
                    dcsSdk.getVoiceRequest().endVoiceRequest(new IVoiceRequestListener() {
                        @Override
                        public void onSucceed() {

                        }
                    });
                } else {
                    // 手动点击就切换到touch模式
                    getInternalApi().switchAsrToNear(audioRecorder, new ISwitchAsrNearFarListener() {
                        @Override
                        public void onEnd() {
                            LogUtil.dc(TAG, "switch-switchAsrToNear-onEnd");
                            textViewWakeUpTip.setVisibility(View.INVISIBLE);
                            dcsSdk.getVoiceRequest().beginVoiceRequest(false);
                        }
                    });
                }
                break;
            case R.id.cancelBtn:
                // 取消识别，不再返回任何识别结果
                cancelVoiceRequest();
                break;
            default:
                break;
        }
    }

    private void cancelVoiceRequest() {
        dcsSdk.getVoiceRequest().cancelVoiceRequest(new IVoiceRequestListener() {
            @Override
            public void onSucceed() {
                LogUtil.dc(TAG, "cancelVoiceRequest onSucceed");
            }
        });
    }

    private boolean isAsrNear = true;

    private int calculateVolume(byte[] buffer) {
        short[] audioData = new short[buffer.length / 2];
        ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioData);
        double sum = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < audioData.length; i++) {
            sum += audioData[i] * audioData[i];
        }
        // 平方和除以数据总长度，得到音量大小
        double mean = sum / (double) audioData.length;
        final double volume = 10 * Math.log10(mean);
        return (int) volume;
    }

    private void wakeUp() {
        getInternalApi().startWakeup();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.dc(TAG, "onPause");
        // 停止tts，音乐等有关播放.
        getInternalApi().pauseSpeaker();
        // 如果有唤醒，则停止唤醒
        getInternalApi().stopWakeup(null);
        // 取消识别，不返回结果
        cancelVoiceRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 这里是为了展示如何使用下面的2个方法，如果不需要可以不用调用
        LogUtil.dc(TAG, "onRestart");
        // 恢复tts，音乐等有关播放
        getInternalApi().resumeSpeaker();
        // 如果有唤醒，则恢复唤醒
        wakeUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.dc(TAG, "onDestroy");
        // dcsWebView
        dcsWebView.setLoadListener(null);
        mTopLinearLayout.removeView(dcsWebView);
        dcsWebView.removeAllViews();
        dcsWebView.destroy();

        if (screenDeviceModule != null) {
            screenDeviceModule.removeScreenListener(screenListener);
        }
        screenListener = null;

        dcsSdk.getVoiceRequest().removeDialogStateListener(dialogStateListener);
        dialogStateListener = null;

        dcsSdk.removeConnectionStatusListener(connectionStatusListener);
        connectionStatusListener = null;

        getInternalApi().removeErrorListener(errorListener);
        errorListener = null;

        getInternalApi().removeRequestBodySentListener(dcsRequestBodySentListener);
        dcsRequestBodySentListener = null;

        getInternalApi().setLocationHandler(null);
        locationHandler = null;
        location.release();

        // 第3步，释放sdk
        dcsSdk.release();
    }

    protected IOauth getOath() {
        return new OauthCodeImpl(CLIENT_ID, this);
    }
}
