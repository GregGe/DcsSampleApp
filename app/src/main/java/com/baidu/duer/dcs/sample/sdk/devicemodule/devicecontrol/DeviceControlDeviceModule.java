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
package com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol;

import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Payload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.AdjustBrightnessPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetAssistiveTouchPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetBluetoothPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetBrightnessPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetCellularModePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetCellularPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetGpsPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetHotspotPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetNfcPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetPhoneModePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetPhonePowerPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetPortraitLockPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetSynchronizationPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetVibrationPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetVpnPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetWifiPayload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by caoyushu01 on 2017/7/26.
 */

public class DeviceControlDeviceModule extends BaseDeviceModule {

    private List<IDeviceControlListener> listeners;

    public DeviceControlDeviceModule (IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext () {
        return null;
    }

    @Override
    public void handleDirective (Directive directive) throws HandleDirectiveException {
        fireOnDirectiveReceived(directive);
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.AdjustBrightness.NAME, AdjustBrightnessPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetAssistiveTouch.NAME, SetAssistiveTouchPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetBluetooth.NAME, SetBluetoothPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetBrightness.NAME, SetBrightnessPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetCellular.NAME, SetCellularPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetCellularMode.NAME, SetCellularModePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetGps.NAME, SetGpsPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetHotspot.NAME, SetHotspotPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetNfc.NAME, SetNfcPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetPhoneMode.NAME, SetPhoneModePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetPhonePower.NAME, SetPhonePowerPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetPortraitLock.NAME, SetPortraitLockPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetSynchronization.NAME, SetSynchronizationPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetVibration.NAME, SetVibrationPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetVpn.NAME, SetVpnPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetWifi.NAME, SetWifiPayload.class);
        return map;
    }

    @Override
    public void release () {
        listeners.clear();
    }

    private void fireOnDirectiveReceived(Directive directive) throws HandleDirectiveException {
        String name = directive.getName();
        Payload payload =  directive.getPayload();
        for (IDeviceControlListener listener : listeners) {
            if (name.equals(ApiConstants.Directives.AdjustBrightness.NAME)) {
                listener.onAdjustBrightness((AdjustBrightnessPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetAssistiveTouch.NAME)) {
                listener.onSetAssistiveTouch((SetAssistiveTouchPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetBluetooth.NAME)) {
                listener.onSetBluetooth((SetBluetoothPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetBrightness.NAME)) {
                listener.onSetBrightness((SetBrightnessPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetCellular.NAME)) {
                listener.onSetCellular((SetCellularPayload) payload);
            } else if (name.equals((ApiConstants.Directives.SetCellularMode.NAME))) {
                listener.onSetCellularMode((SetCellularModePayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetGps.NAME)) {
                listener.onSetGps((SetGpsPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetHotspot.NAME)) {
                listener.onSetHotspot((SetHotspotPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetNfc.NAME)) {
                listener.onSetNfc((SetNfcPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetPhoneMode.NAME)) {
                listener.onSetPhoneMode((SetPhoneModePayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetPhonePower.NAME)) {
                listener.onSetPhonePower((SetPhonePowerPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetPortraitLock.NAME)) {
                listener.onSetPortraitLock((SetPortraitLockPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetSynchronization.NAME)) {
                listener.onSetSynchronization((SetSynchronizationPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetVibration.NAME)) {
                listener.onSetVibration((SetVibrationPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetVpn.NAME)) {
                listener.onSetVpn((SetVpnPayload) payload);
            } else if (name.equals(ApiConstants.Directives.SetWifi.NAME) ) {
                listener.onSetWifi((SetWifiPayload) payload);
            } else {
                String message = "launch app cannot handle the directive";
                throw (new HandleDirectiveException(
                        HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
            }
        }
    }

    public void addDeviceControlListener(IDeviceControlListener listener) {
        if (listeners != null) {
            listeners.add(listener);
        }
    }

    public void removeDeviceControlListener(IDeviceControlListener listener) {
        if (listeners != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public interface IDeviceControlListener {
        void onAdjustBrightness(AdjustBrightnessPayload payload);
        void onSetAssistiveTouch(SetAssistiveTouchPayload payload);
        void onSetBluetooth(SetBluetoothPayload payload);
        void onSetBrightness(SetBrightnessPayload payload);
        void onSetCellular(SetCellularPayload payload);
        void onSetCellularMode(SetCellularModePayload payload);
        void onSetGps(SetGpsPayload payload);
        void onSetHotspot(SetHotspotPayload payload);
        void onSetNfc(SetNfcPayload payload);
        void onSetPhoneMode(SetPhoneModePayload payload);
        void onSetPhonePower(SetPhonePowerPayload payload);
        void onSetPortraitLock(SetPortraitLockPayload payload);
        void onSetSynchronization(SetSynchronizationPayload payload);
        void onSetVibration(SetVibrationPayload payload);
        void onSetVpn(SetVpnPayload payload);
        void onSetWifi(SetWifiPayload payload);
    }

    public static class SimpleDeviceControlListener implements IDeviceControlListener {
        @Override
        public void onAdjustBrightness(AdjustBrightnessPayload payload) {

        }

        @Override
        public void onSetAssistiveTouch(SetAssistiveTouchPayload payload) {

        }

        @Override
        public void onSetBluetooth(SetBluetoothPayload payload) {

        }

        @Override
        public void onSetBrightness(SetBrightnessPayload payload) {

        }

        @Override
        public void onSetCellular(SetCellularPayload payload) {

        }

        @Override
        public void onSetCellularMode(SetCellularModePayload payload) {

        }

        @Override
        public void onSetGps(SetGpsPayload payload) {

        }

        @Override
        public void onSetHotspot(SetHotspotPayload payload) {

        }

        @Override
        public void onSetNfc(SetNfcPayload payload) {

        }

        @Override
        public void onSetPhoneMode(SetPhoneModePayload payload) {

        }

        @Override
        public void onSetPhonePower(SetPhonePowerPayload payload) {

        }

        @Override
        public void onSetPortraitLock(SetPortraitLockPayload payload) {

        }

        @Override
        public void onSetSynchronization(SetSynchronizationPayload payload) {

        }

        @Override
        public void onSetVibration(SetVibrationPayload payload) {

        }

        @Override
        public void onSetVpn(SetVpnPayload payload) {

        }

        @Override
        public void onSetWifi(SetWifiPayload payload) {

        }
    }
}
