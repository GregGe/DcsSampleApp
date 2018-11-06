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
package com.baidu.duer.dcs.sample.sdk.devicemodule.sms;

import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SelectRecipientPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SendSmsByNamePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SendSmsByNumberPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SmsClientContextPayload;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by caoyushu01 on 2017/7/18.
 */

public class SmsDeviceModule extends BaseDeviceModule {

    private List<ISmsListener> listeners;

    public SmsDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.SmsState.NAME;
        Header header = new Header(namespace, name);
        // 当前短信服务需传递空payload
        SmsClientContextPayload payload = new SmsClientContextPayload();
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String headerName = directive.getName();
        Payload payload = directive.getPayload();
        if (ApiConstants.Directives.SendSmsByName.NAME.equals(headerName)) {
            fireOnSendSmsByName((SendSmsByNamePayload) payload);
        } else if (ApiConstants.Directives.SelectRecipient.NAME.equals(headerName)) {
            fireOnSelectRecipient((SelectRecipientPayload) payload);
        } else if (ApiConstants.Directives.SendSmsByNumber.NAME.equals(headerName)) {
            fireOnSendSmsByNumber((SendSmsByNumberPayload) payload);
        } else {
            String message = "sms cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.SendSmsByName.NAME, SendSmsByNamePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SelectRecipient.NAME, SelectRecipientPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SendSmsByNumber.NAME, SendSmsByNumberPayload.class);
        return map;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void fireOnSendSmsByName(SendSmsByNamePayload payload) {
        for (ISmsListener listener : listeners) {
            listener.onSendSmsByName(payload);
        }
    }

    private void fireOnSelectRecipient(SelectRecipientPayload payload) {
        for (ISmsListener listener : listeners) {
            listener.onSelectRecipient(payload);
        }
    }

    private void fireOnSendSmsByNumber(SendSmsByNumberPayload payload) {
        for (ISmsListener listener : listeners) {
            listener.onSendSmsByNumber(payload);
        }
    }

    public void addSmsListener(ISmsListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeSmsListener(ISmsListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    // 短信指令监听器，用于Android应用层监听短信指令的到来
    public interface ISmsListener {
        void onSendSmsByName(SendSmsByNamePayload payload);

        void onSelectRecipient(SelectRecipientPayload payload);

        void onSendSmsByNumber(SendSmsByNumberPayload payload);
    }
}
