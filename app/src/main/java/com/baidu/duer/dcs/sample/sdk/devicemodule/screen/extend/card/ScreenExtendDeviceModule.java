/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card;

import android.text.TextUtils;

import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderAirQualityPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderAudioListPlayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderDatePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderPlayerInfoPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderStockPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderTrafficRestrictionPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderWeatherPayload;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.util.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.baidu.duer.dcs.util.message.HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION;

public class ScreenExtendDeviceModule extends BaseDeviceModule {

    private final List<IRenderExtendListener> listeners = new ArrayList<>();
    private List<IScreenExtensionListener> mExtensionListeners;

    public ScreenExtendDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.mExtensionListeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.header.getName();
        if (ApiConstants.Directives.RenderWeather.NAME.equals(name)
                || ApiConstants.Directives.RenderDate.NAME.equals(name)
                || ApiConstants.Directives.RenderStock.NAME.equals(name)
                || ApiConstants.Directives.RenderAirQuality.NAME.equals(name)
                || ApiConstants.Directives.RenderTrafficRestriction.NAME.equals(name)
                || ApiConstants.Directives.RenderPlayerInfo.NAME.equals(name)) {
            handleExtendCardDirective(directive);
        } else if (TextUtils.equals(ApiConstants.Directives.RenderPlayerInfo.NAME, name)
                || ApiConstants.Directives.RenderAudioList.NAME.equals(name)) {
            handleRenderPlayer(directive);
        } else {
            String message = "VoiceOutput cannot handle the directive";
            throw new HandleDirectiveException(UNSUPPORTED_OPERATION, message);
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.RenderWeather.NAME, RenderWeatherPayload
                .class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderDate.NAME, RenderDatePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderStock.NAME, RenderStockPayload
                .class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderAirQuality.NAME,
                RenderAirQualityPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderTrafficRestriction.NAME,
                RenderTrafficRestrictionPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderPlayerInfo.NAME,
                RenderPlayerInfoPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderAudioList.NAME,
                RenderAudioListPlayload.class);
        return map;
    }

    private void handleRenderPlayer(Directive directive) {
        if (TextUtils.equals(directive.header.getName(), ApiConstants.Directives.RenderPlayerInfo
                .NAME)) {
            RenderPlayerInfoPayload payload = (RenderPlayerInfoPayload) directive.getPayload();
        }
        if (TextUtils.equals(directive.header.getName(), ApiConstants.Directives.RenderAudioList
                .NAME)) {
            RenderAudioListPlayload renderAudioListPlayload = (RenderAudioListPlayload) directive
                    .getPayload();
        }

    }


    private void handleExtendCardDirective(Directive directive) {
        if (TextUtils.equals(directive.header.getName(), ApiConstants.Directives.RenderPlayerInfo
                .NAME)) {
            RenderPlayerInfoPayload mPayload = (RenderPlayerInfoPayload) directive.getPayload();
            if (mPayload != null) {
                handleExtendCardPayload(mPayload);
            }
        } else if (TextUtils.equals(directive.header.getName(), ApiConstants.Directives
                .RenderAudioList.NAME)) {
            RenderAudioListPlayload renderAudioListPlayload = (RenderAudioListPlayload) directive
                    .getPayload();
        }
        for (IRenderExtendListener listener : listeners) {
            listener.onRenderDirective(directive);
        }
    }

    @Override
    public void release() {
        listeners.clear();
        if (mExtensionListeners != null) {
            mExtensionListeners.clear();
        }
    }

    public void addExtensionListener(IScreenExtensionListener mListener) {
        if (mExtensionListeners != null) {
            mExtensionListeners.add(mListener);
        }

    }

    private void handleExtendCardPayload(RenderPlayerInfoPayload payload) {
        if (mExtensionListeners == null) {
            return;
        }
        for (IScreenExtensionListener mExtensionListener : mExtensionListeners) {
            mExtensionListener.onRenderPlayerInfo(payload);
        }


    }

    public void addListener(IRenderExtendListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IRenderExtendListener listener) {
        listeners.remove(listener);
    }

    public interface IRenderExtendListener {
        /**
         * Handle extend card payload.
         */
        void onRenderDirective(Directive directive);
    }

    public interface IScreenExtensionListener {

        void onRenderPlayerInfo(RenderPlayerInfoPayload renderPlayerInfoPayload);

        void onRenderAudioList(RenderAudioListPlayload renderAudioListPlayload);
    }
}