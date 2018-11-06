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
package com.baidu.duer.dcs.sample.sdk.devicemodule.screen;

import com.baidu.duer.dcs.util.util.FileUtil;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Event;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.MessageIdHeader;
import com.baidu.duer.dcs.util.message.Payload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderAudioListPlayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message.RenderPlayerInfoPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.HtmlPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.LinkClickedPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.RenderCardPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.RenderHintPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.RenderVoiceInputTextPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message.ViewStatePayload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Screen模块处理并执行服务下发的指令，如HtmlView指令，以及发送事件，如LinkClicked事件
 * <p>
 * Created by wuruisheng on 2017/5/31.
 */
public class ScreenDeviceModule extends BaseDeviceModule {
    private List<IScreenListener> listeners;
    private String token = "";

    public ScreenDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.ViewState.NAME;
        Header header = new Header(namespace, name);
        Payload payload = new ViewStatePayload(token);
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.header.getName();
        if (name.equals(ApiConstants.Directives.HtmlView.NAME)) {
            handleHtmlPayload(directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.RenderVoiceInputText.NAME)) {
            fireRenderVoiceInputText((RenderVoiceInputTextPayload) directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.RenderCard.NAME)) {
            fireOnRenderCard((RenderCardPayload) directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.RenderHint.NAME)) {
            fireOnRenderHint((RenderHintPayload) directive.getPayload());
        } else {
            String message = "VoiceOutput cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public void release() {
        listeners.clear();
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.HtmlView.NAME, HtmlPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderVoiceInputText.NAME,
                RenderVoiceInputTextPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderCard.NAME, RenderCardPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.RenderHint.NAME, RenderHintPayload.class);
        return map;
    }

    private void handleHtmlPayload(Payload payload) {
        if (payload instanceof HtmlPayload) {
            HtmlPayload htmlPayload = (HtmlPayload) payload;
            token = htmlPayload.getToken();
            fireOnHtmlView(htmlPayload);
        }
    }

    private void sendLinkClickedEvent(String url, String token) {
        String name = ApiConstants.Events.LinkClicked.NAME;
        Header header = new MessageIdHeader(getNameSpace(), name);

        LinkClickedPayload linkClickedPayload = new LinkClickedPayload(url, token);
        Event event = new Event(header, linkClickedPayload);
        if (messageSender != null) {
            messageSender.sendEvent(event, null);
        }
    }

    public void addScreenListener(IScreenListener listener) {
        listeners.add(listener);
    }

    public void removeScreenListener(IScreenListener listener) {
        listeners.remove(listener);
    }

    private void fireOnHtmlView(HtmlPayload payload) {
        for (IScreenListener listener : listeners) {
            listener.onHtmlPayload(payload);
        }
    }

    private void fireRenderVoiceInputText(RenderVoiceInputTextPayload payload) {
        if (payload.type == RenderVoiceInputTextPayload.Type.FINAL) {
            FileUtil.appendStrToFileNew("ASR-FINAL-RESULT:" + payload.text
                    + "," + System.currentTimeMillis() + "\n");
        }

        for (IScreenListener listener : listeners) {
            listener.onRenderVoiceInputText(payload);
        }
    }

    private void fireOnRenderCard(RenderCardPayload renderCardPayload) {
        for (IScreenListener listener : listeners) {
            listener.onRenderCard(renderCardPayload);
        }
    }

    private void fireOnRenderHint(RenderHintPayload payload) {
        for (IScreenListener listener : listeners) {
            listener.onRenderHint(payload);
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public interface IScreenListener {
        /**
         * 接收到RenderVoiceInputText指令时回调
         *
         * @param payload 内容
         */
        void onRenderVoiceInputText(RenderVoiceInputTextPayload payload);

        /**
         * 接收到HtmlView指令时回调
         *
         * @param htmlPayload 内容
         */
        void onHtmlPayload(HtmlPayload htmlPayload);


        /**
         * 接收到RenderCard指令回调
         *
         * @param renderCardPayload
         */
        void onRenderCard(RenderCardPayload renderCardPayload);

        /**
         * 接收到Hint指令
         *
         * @param renderHintPayload
         */
        void onRenderHint(RenderHintPayload renderHintPayload);
    }

    public interface IScreenExtensionListener {

        void onRenderPlayerInfo(RenderPlayerInfoPayload renderPlayerInfoPayload);

        void onRenderAudioList(RenderAudioListPlayload renderAudioListPlayload);
    }
}