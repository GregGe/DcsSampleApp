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
package com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall;

import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall.message.PhonecallByNamePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall.message.PhonecallByNumberPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall.message.SelectCalleePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall.message.TelephoneStateClientContextPayload;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by caoyushu01 on 17/7/6.
 */

public class PhoneCallDeviceModule extends BaseDeviceModule {

    private List<IPhoneCallListener> listeners;

    public PhoneCallDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    /**
     * 端能力声明接口
     *
     * @return
     */
    @Override
    public ClientContext clientContext() {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.TelephoneState.NAME;
        Header header = new Header(namespace, name);
        // 当前电话服务需传递空payload
        TelephoneStateClientContextPayload payload = new TelephoneStateClientContextPayload();
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String headerName = directive.getName();
        Payload payload = directive.getPayload();
        if (ApiConstants.Directives.PhonecallByName.NAME.equals(headerName)) {
            fireOnPhonecallByName((PhonecallByNamePayload) payload);
        } else if (ApiConstants.Directives.SelectCallee.NAME.equals(headerName)) {
            fireOnSelectCallee((SelectCalleePayload) payload);
        } else if (ApiConstants.Directives.PhonecallByNumber.NAME.equals(headerName)) {
            fireOnPhonecallByNumber((PhonecallByNumberPayload) payload);
        } else {
            String message = "phone cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.PhonecallByName.NAME, PhonecallByNamePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SelectCallee.NAME, SelectCalleePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.PhonecallByNumber.NAME, PhonecallByNumberPayload.class);
        return map;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void fireOnPhonecallByName(PhonecallByNamePayload payload) {
        for (IPhoneCallListener listener : listeners) {
            listener.onPhoneCallByName(payload);
        }
    }

    private void fireOnSelectCallee(SelectCalleePayload payload) {
        for (IPhoneCallListener listener : listeners) {
            listener.onSelectCallee(payload);
        }
    }

    private void fireOnPhonecallByNumber(PhonecallByNumberPayload payload) {
        for (IPhoneCallListener listener : listeners) {
            listener.onPhoneCallByNumber(payload);
        }
    }

    public void addPhoneCallListener(IPhoneCallListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removePhoneCallListener(IPhoneCallListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }


    // 电话指令监听器，用于Android应用层监听电话指令的到来
    public interface IPhoneCallListener {
        void onPhoneCallByName(PhonecallByNamePayload payload);

        void onSelectCallee(SelectCalleePayload payload);

        void onPhoneCallByNumber(PhonecallByNumberPayload payload);
    }
}
