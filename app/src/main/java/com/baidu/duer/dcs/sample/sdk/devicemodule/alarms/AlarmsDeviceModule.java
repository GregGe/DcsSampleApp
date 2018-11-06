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
package com.baidu.duer.dcs.sample.sdk.devicemodule.alarms;

import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.SetAlarmPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.SetTimerPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.ShowAlarmsPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.ShowTimersPayload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by caoyushu01 on 2017/7/26.
 */

public class AlarmsDeviceModule extends BaseDeviceModule {
    private List<IAlarmDirectiveListener> listeners;

    public AlarmsDeviceModule (IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext () {
        return null;
    }

    @Override
    public void handleDirective (Directive directive) throws HandleDirectiveException {
        String headerName = directive.getName();
        try {
            if (ApiConstants.Directives.SetAlarm.NAME.equals(headerName)) {
                SetAlarmPayload payload = (SetAlarmPayload) directive.getPayload();
                fireSetAlarm(payload);
            } else if (ApiConstants.Directives.ShowAlarms.NAME.equals(headerName)) {
                ShowAlarmsPayload payload = (ShowAlarmsPayload) directive.getPayload();
                fireShowAlarms(payload);
            } else if (ApiConstants.Directives.SetTimer.NAME.equals(headerName)) {
                SetTimerPayload payload = (SetTimerPayload) directive.getPayload();
                fireSetTimer(payload);
            } else if (ApiConstants.Directives.ShowTimers.NAME.equals(headerName)) {
                ShowTimersPayload payload = (ShowTimersPayload) directive.getPayload();
                fireShowTimers(payload);
            } else {
                String message = "alarm cannot handle the directive";
                throw (new HandleDirectiveException(
                        HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
            }
        } catch (HandleDirectiveException e) {
            e.printStackTrace();
        }
    }


    public void addAlarmListener(IAlarmDirectiveListener listener) {
        listeners.add(listener);
    }

    public void removeAlarmListener(IAlarmDirectiveListener listener) {
        if (listeners != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.SetAlarm.NAME, SetAlarmPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.ShowTimers.NAME, ShowTimersPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetTimer.NAME, SetTimerPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.ShowTimers.NAME, ShowTimersPayload.class);
        return map;
    }

    @Override
    public void release () {
        listeners.clear();
    }


    private void fireSetAlarm(SetAlarmPayload payload) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onSetAlarmDirectiveReceived(payload);
        }
    }

    private void fireShowAlarms(ShowAlarmsPayload payload) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onShowAlarmsDirectiveReceived(payload);
        }
    }

    private void fireSetTimer(SetTimerPayload payload) {
        for (int i = 0; i < listeners.size(); ++ i) {
            listeners.get(i).onSetTimerDirectiveReceived(payload);
        }
    }

    private void fireShowTimers(ShowTimersPayload payload) {
        for (int i = 0; i < listeners.size(); ++ i) {
            listeners.get(i).onShowTimersDirectiveReceived(payload);
        }
    }


    public interface IAlarmDirectiveListener {
        void onSetAlarmDirectiveReceived(SetAlarmPayload setAlarmPayload);

        void onShowAlarmsDirectiveReceived(ShowAlarmsPayload showAlarmsPayload);

        void onSetTimerDirectiveReceived(SetTimerPayload setTimerPayload);

        void onShowTimersDirectiveReceived(ShowTimersPayload showTimersPayload);
    }
}
