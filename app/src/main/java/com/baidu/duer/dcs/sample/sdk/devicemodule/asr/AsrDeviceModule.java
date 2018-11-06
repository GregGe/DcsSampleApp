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
package com.baidu.duer.dcs.sample.sdk.devicemodule.asr;

import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.devicemodule.asr.AsrApiConstants;
import com.baidu.duer.dcs.util.devicemodule.asr.message.HandleAsrResultPayload;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.util.message.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 处理ASR结果，仅当SdkConfigProvider中asrOnly()方法返回true时生效
 *
 * @see com.baidu.duer.dcs.api.config.SdkConfigProvider
 *
 * Created by longyin01 on 2018/2/27.
 */

public class AsrDeviceModule extends BaseDeviceModule {

    private List<IAsrListener> listeners;

    public AsrDeviceModule(IMessageSender messageSender) {
        super(AsrApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.header.getName();
        Payload payload = directive.getPayload();
        if (AsrApiConstants.Directives.HandleAsrResult.NAME.equals(name)) {
            fireHandleAsrResult((HandleAsrResultPayload) payload);
        } else {
            String message = "VoiceOutput cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + AsrApiConstants.Directives.HandleAsrResult.NAME, HandleAsrResultPayload.class);
        return map;
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    public void addAsrListener(IAsrListener listener) {
        listeners.add(listener);
    }

    public void removeAsrListener(IAsrListener listener) {
        listeners.remove(listeners);
    }

    private void fireHandleAsrResult(HandleAsrResultPayload payload) {
        for (IAsrListener listener : listeners) {
            listener.onHandleAsrResult(payload);
        }
    }

    public interface IAsrListener {
        void onHandleAsrResult(HandleAsrResultPayload payload);
    }
}
