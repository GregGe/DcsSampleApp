/*
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
package com.baidu.duer.dcs.sample.sdk.devicemodule.imagerecognition;

import com.baidu.duer.dcs.sample.sdk.devicemodule.imagerecognition.message.StartUploadScreenShotPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.imagerecognition.message.UploadScreenShotPayload;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Event;
import com.baidu.duer.dcs.util.message.MessageIdHeader;
import com.baidu.duer.dcs.util.message.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Image Recognition模块
 * 接收处理执行服务下发的UploadScreenShot指令，
 * 上报StartUploadScreenShot事件.
 * </p>
 * Created by wenzongliang on 2017/8/2.
 */
public class ImageRecognitionDeviceModule extends BaseDeviceModule {
    public static final String TAG = ImageRecognitionDeviceModule.class.getSimpleName();

    /**
     * 取图类型，人脸识别
     */
    public static final String TYPE_FACE = "FACE";
    /**
     * 取图类型， logo识别
     */
    public static final String TYPE_LOGO = "LOGO";
    /**
     * 取图类型，花卉识别
     */
    public static final String TYPE_FLOWER = "FLOWER";
    /**
     * 取图类型， 汽车识别
     */
    public static final String TYPE_CAR = "CAR";

    private final List<IImageRecognitionListener> listeners;

    public ImageRecognitionDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<IImageRecognitionListener>();
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String directiveName = directive.getName();
        if (directiveName.equals(ApiConstants.Directives.UploadScreenShot.NAME)) {
            UploadScreenShotPayload payload = (UploadScreenShotPayload) directive.getPayload();
            fireOnUploadScreenShot(directive, payload.getToken(), payload.getType());
        } else {
            String message = "ImageRecognition cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.UploadScreenShot.NAME, UploadScreenShotPayload.class);
        return map;
    }

    public void sendStartUploadScreenShotEvent(String token, String type, String url) {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.StartUploadScreenShot.NAME;
        MessageIdHeader header = new MessageIdHeader(namespace, name);
        Payload payload = new StartUploadScreenShotPayload(token, type, url);
        Event event = new Event(header, payload);
        messageSender.sendEvent(event, null);
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void fireOnUploadScreenShot(Directive directive, String token, String type) {
        for (IImageRecognitionListener listener : listeners) {
            listener.onUploadScreenShot(directive, token, type);
        }
    }

    /**
     * 添加图片识别监听
     *
     * @param listener listener
     */
    public void addImageRecognitionListener(IImageRecognitionListener listener) {
        this.listeners.add(listener);
    }

    /**
     * 移除图片识别监听
     *
     * @param listener listener
     */
    public void removeImageRecognitionListener(IImageRecognitionListener listener) {
        listeners.remove(listener);
    }


    public interface IImageRecognitionListener {
        /**
         * 收到上传图片指令时回调
         */
        void onUploadScreenShot(Directive directive, String token, String type);
    }
}
