package com.baidu.duer.dcs.sample.sdk.wakeup;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.baidu.duer.dcs.api.recorder.BaseAudioRecorder;
import com.baidu.duer.dcs.api.wakeup.BaseWakeup;
import com.baidu.duer.dcs.api.wakeup.IStopWakeupListener;
import com.baidu.duer.dcs.util.util.SystemServiceManager;
import com.baidu.duer.kitt.WakeUpConfig;
import com.baidu.duer.kitt.WakeUpWord;

import java.io.File;
import java.util.concurrent.LinkedBlockingDeque;

public class DroiWakeUp extends BaseWakeup {
    // 初始化唤醒词成功
    private static final int WAKEUP_INIT_SUCCEED = 0;
    // 唤醒词
    public static final String WAKEUP_WORD = "老板老板";
    // 唤醒词声学模型模型文件
    private static final String WAKEUP_FILENAME = "libbdEasrS1MergeNormal.so";

    // 初始化唤醒词的返回值
    private int wakeUpInitialRet;

    private WakeUpNative wakeUpNative;

    private BaseAudioRecorder audioRecorder;
    private Handler handler;
    private Context context;
    private LinkedBlockingDeque<byte[]> linkedBlockingDeque;
    private BaseAudioRecorder.IRecorderListener recorderListener;

    // Decode消费线程
    private WakeUpDecodeThread wakeUpDecodeThread;

    public DroiWakeUp(BaseAudioRecorder audioRecorder) {
        this(SystemServiceManager.getAppContext(), audioRecorder);
    }


    public DroiWakeUp(Context context, BaseAudioRecorder audioRecorder) {
        this.context = context;
        this.audioRecorder = audioRecorder;

        handler = new Handler(Looper.getMainLooper());
        this.wakeUpNative = new WakeUpNative();
        linkedBlockingDeque = new LinkedBlockingDeque<>(300);
        recorderListener = new BaseAudioRecorder.IRecorderListener() {
            @Override
            public void onData(byte[] bytes) {
                linkedBlockingDeque.add(bytes);
            }

            @Override
            public void onError(String s) {

            }
        };
    }

    @Override
    public void initWakeup(WakeUpConfig wakeUpConfig) {
        super.initWakeup(wakeUpConfig);
        // 方法1：加载声学模型文件,当作so库进行加载到nativeLibraryDir目录中
        // 方法2：当然你也可以放到assets目录或者raw下，然后进行拷贝到应用的私有目录或者sd卡
        // 方法2需要处理声学模型文件覆盖安装问题，以及不可预料的拷贝失败问题！
        String path = this.context.getApplicationInfo().nativeLibraryDir
                + File.separatorChar + WAKEUP_FILENAME;
        LogUtil.d(TAG, "wakeup path:" + path);
        LogUtil.d(TAG, "wakeup exists:" + new File(path).exists());
        // 1.初始化唤醒词，0 是初始化成功
        wakeUpInitialRet = wakeUpNative.wakeUpInitial(WAKEUP_WORD, path, 0);
        if (wakeUpInitialRet == 0) {
            fireOnInitWakeUpSucceed();
        } else {
            fireOnInitWakeUpFailed(wakeUpInitialRet + "");
        }
        LogUtil.d(TAG, "wakeUpInitialRet:" + wakeUpInitialRet);
    }

    @Override
    public void startWakeup() {

        linkedBlockingDeque.clear();
        if (audioRecorder != null) {
            audioRecorder.addRecorderListener(recorderListener);
        }

        if (wakeUpDecodeThread != null && wakeUpDecodeThread.isStart()) {
            LogUtil.d(TAG, "wakeup wakeUpDecodeThread  is Started !");
            return;
        }
        // 2.开始唤醒
        if (wakeUpInitialRet == WAKEUP_INIT_SUCCEED) {
            startDecodeThread();
        } else {
            LogUtil.d(TAG, "wakeup wakeUpInitialRet failed, not startWakeUp ");
        }
    }

    /**
     * 开始音频解码进行唤醒操作
     */

    private void wakeup() {
        LogUtil.d(TAG, "droi wakeup startWakeup !");
        if (wakeUpDecodeThread != null) {
            try {
                wakeUpDecodeThread.join();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            } finally {
                startDecodeThread();
            }
        } else {
            startDecodeThread();
        }

    }

    private void startDecodeThread() {
        wakeUpDecodeThread = new WakeUpDecodeThread(linkedBlockingDeque, wakeUpNative, handler);
        wakeUpDecodeThread.setWakeUpListener(new WakeUpDecodeThread.IWakeUpListener() {
            @Override
            public void onWakeUpSucceed() {
                // 唤醒成功
                fireOnWakeUpSucceed(wakeUpConfig.getWakeUpWords().get(0));
            }
        });
        wakeUpDecodeThread.startWakeUp();
    }

    @Override
    public void stopWakeup(IStopWakeupListener iStopWakeupListener) {
        LogUtil.d(TAG, "wakeup stopWakeup!");
        if (this.wakeUpDecodeThread != null) {
            this.wakeUpDecodeThread.stopWakeUp();
        }

        if (this.audioRecorder != null) {
            this.audioRecorder.removeRecorderListener(this.recorderListener);
        }

        this.handler.removeCallbacksAndMessages(null);
        if (iStopWakeupListener != null) {
            iStopWakeupListener.onStopWakeup();
        }
    }

    @Override
    public void release() {
        super.release();
        int ret = wakeUpNative.wakeUpFree();
        LogUtil.d(TAG, "wakeUpFree-ret:" + ret);
    }
}
