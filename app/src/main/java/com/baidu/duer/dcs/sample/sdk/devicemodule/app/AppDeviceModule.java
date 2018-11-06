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
package com.baidu.duer.dcs.sample.sdk.devicemodule.app;

import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Event;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.MessageIdHeader;
import com.baidu.duer.dcs.util.message.Payload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.message.AppStatePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.message.LaunchAppFailedPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.message.LaunchAppPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.message.TryLaunchAppPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.IAppLauncher;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.AppInfo;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 打开应用新版协议
 * http://icode.baidu.com/repos/baidu/duer/open-platform-api-doc/blob/master:dueros-conversational-service/device-interface/app-private.md
 * <p>
 *
 * Created by caoyushu01 on 2017/7/20.
 */
public class AppDeviceModule extends BaseDeviceModule {

    private IAppLauncher appLauncher;
    private List<IAppListener> listeners;
    private String lastToken;

    public AppDeviceModule(IMessageSender messageSender, IAppLauncher appLauncher) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.appLauncher = appLauncher;
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.AppState.NAME;
        Header header = new Header(namespace, name);
        Payload payload = getState();
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String headerName = directive.getName();
        Payload payload = directive.getPayload();
        if (ApiConstants.Directives.TryLaunchApp.NAME.equals(headerName)) {
            fireOnTryLaunchApp((TryLaunchAppPayload) payload);
        } else if (ApiConstants.Directives.LaunchApp.NAME.equals(headerName)) {
            fireOnLaunchApp((LaunchAppPayload) payload);
        } else {
            String message = "launch app cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.TryLaunchApp.NAME, TryLaunchAppPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.LaunchApp.NAME, LaunchAppPayload.class);
        return map;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    public void sendLaunchAppFailedEvent() {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.LaunchAppFailed.NAME;
        MessageIdHeader header = new MessageIdHeader(namespace, name);
        Payload payload = new LaunchAppFailedPayload(lastToken);
        Event event = new Event(header, payload);
        messageSender.sendEvent(event, null);
    }

    private void fireOnTryLaunchApp(TryLaunchAppPayload payload) {
        lastToken = payload.getToken();
        for (IAppListener listener : listeners) {
            listener.onTryLaunchApp(payload);
        }
    }

    private void fireOnLaunchApp(LaunchAppPayload payload) {
        lastToken = payload.getToken();
        for (IAppListener listener : listeners) {
            listener.onLaunchApp(payload);
        }
    }

    public void addAppListener(IAppListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeAppListener(IAppListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public interface IAppListener {
        void onTryLaunchApp(TryLaunchAppPayload payload);
        void onLaunchApp(LaunchAppPayload payload);
    }

    public IAppLauncher getAppLauncher() {
        return appLauncher;
    }

    /**
     * 只有需要使用deeplink方式打开的app才需要上传
     */
    private AppStatePayload getState() {
        List<AppInfo> contextInfos = appLauncher.getAppList();
        return new AppStatePayload(contextInfos);
    }
}
