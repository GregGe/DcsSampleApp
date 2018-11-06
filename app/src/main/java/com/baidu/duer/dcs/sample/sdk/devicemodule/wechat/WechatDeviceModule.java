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
package com.baidu.duer.dcs.sample.sdk.devicemodule.wechat;

import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.sample.sdk.devicemodule.wechat.message.ReadWechatMessagePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.wechat.message.SendWechatByNamePayload;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Wechat 控制模块处理并执行服务下发的SendWechatByName，ReadWechatMessage指令。
 * <p>
 * Created by wenzongliang on 2017/8/23.
 */
public class WechatDeviceModule extends BaseDeviceModule {
    public static final String MESSAGE_TYPE_TEXT = "TEXT";
    public static final String MESSAGE_TYPE_MY_LOCATION = "MY_LOCATION";
    private final List<IWechatListener> wechatListeners;
    private IWechatListener wechatListener;

    public WechatDeviceModule() {
        super(ApiConstants.NAMESPACE);
        this.wechatListeners = new CopyOnWriteArrayList<IWechatListener>();
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String directiveName = directive.getName();
        if (directiveName.equals(ApiConstants.Directives.SendWechatByName.NAME)) {
            handleSendWechatByNamePayload(directive.getPayload());
        } else if (directiveName.equals(ApiConstants.Directives.ReadWechatMessage.NAME)) {
            handleReadWechatMessagePayload(directive.getPayload());
        } else {
            String message = "Wechat cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.SendWechatByName.NAME, SendWechatByNamePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.ReadWechatMessage.NAME, ReadWechatMessagePayload.class);
        return map;
    }

    private void handleSendWechatByNamePayload(Payload payload) {
        if (payload instanceof SendWechatByNamePayload) {
            SendWechatByNamePayload sendWechatByNamePayloadPayload = (SendWechatByNamePayload) payload;
            fireOnReceiveSendWechatByName(sendWechatByNamePayloadPayload.messageType,
                    sendWechatByNamePayloadPayload.contactName,
                    sendWechatByNamePayloadPayload.messageContent,
                    sendWechatByNamePayloadPayload.token);
        }
    }

    private void handleReadWechatMessagePayload(Payload payload) {
        if (payload instanceof ReadWechatMessagePayload) {
            ReadWechatMessagePayload readWechatMessagePayload = (ReadWechatMessagePayload) payload;
            fireOnReceiveReadWechatMessage(readWechatMessagePayload.contactName);
        }
    }

    @Override
    public void release() {

    }

    private void fireOnReceiveSendWechatByName(String messageType, String contactName,
                                               String messageContent, String token) {
        for (IWechatListener listener : wechatListeners) {
            listener.onReceiveSendWechatByName(messageType, contactName, messageContent, token);
        }
    }

    private void fireOnReceiveReadWechatMessage(String contactName) {
        for (IWechatListener listener : wechatListeners) {
            listener.onReceiveReadWechatMessage(contactName);
        }
    }

    public void addWechatListener(IWechatListener listener) {
        this.wechatListeners.add(listener);
    }

    public void removeWechatListener(IWechatListener listener) {
        wechatListeners.remove(listener);
    }

    public interface IWechatListener {
        /**
         * 收到SendWechatByName指令时回调
         */
        void onReceiveSendWechatByName(String messageType, String contactName,
                                       String messageContent, String token);

        /**
         * 收到ReadWechatMessage指令时回调
         */
        void onReceiveReadWechatMessage(String contactName);
    }
}
