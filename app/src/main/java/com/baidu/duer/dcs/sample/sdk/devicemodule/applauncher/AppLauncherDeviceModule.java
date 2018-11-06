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
package com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher;

import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.Payload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.AppInfo;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.AppLauncherStatePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.LaunchAppPayload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by caoyushu01 on 2017/7/20.
 */

public class AppLauncherDeviceModule extends BaseDeviceModule {

    private IAppLauncher appLauncher;
    private List<IAppLauncherListener> listeners;

    public AppLauncherDeviceModule(IMessageSender messageSender, IAppLauncher appLauncher) {
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
        if (ApiConstants.Directives.LaunchApp.NAME.equals(headerName)) {
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
        map.put(getNameSpace() + ApiConstants.Directives.LaunchApp.NAME, LaunchAppPayload.class);
        return map;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void fireOnLaunchApp(LaunchAppPayload payload) {
        for (IAppLauncherListener listener : listeners) {
            listener.onLaunchApp(payload);
        }
    }

    public void addAppLauncherListener (IAppLauncherListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeAppLauncherListener (IAppLauncherListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public interface IAppLauncherListener {
        void onLaunchApp(LaunchAppPayload payload);
    }

    public IAppLauncher getAppLauncher() {
        return appLauncher;
    }

    private AppLauncherStatePayload getState() {
        List<AppInfo> contextInfos = appLauncher.getAppList();
        return new AppLauncherStatePayload(contextInfos);
    }
}
