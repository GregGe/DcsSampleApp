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
package com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer;

import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.ClientContextPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.NextPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.PausePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.PlayPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.PreviousPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.SearchAndPlayMusicPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.SearchAndPlayRadioPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.SearchAndPlayUnicastPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.SetPlaybackModePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message.StopPayload;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by longyin01 on 17/9/26.
 */

public class LocalAudioPlayerDeviceModule extends BaseDeviceModule {

    private List<ILocalAudioPlayerListener> listeners;
    private PayloadGenerator payloadGenerator;

    public LocalAudioPlayerDeviceModule(IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.PlaybackState.NAME;
        Header header = new Header(namespace, name);
        Payload payload;
        if (payloadGenerator != null) {
            payload = payloadGenerator.generateContextPayload();
        } else {
            payload = new ClientContextPayload();
        }
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.getName();
        Payload payload = directive.getPayload();
        if (ApiConstants.Directives.SearchAndPlayMusic.NAME.equals(name)) {
            if (payload instanceof SearchAndPlayMusicPayload) {
                handleSearchAndPlayMusic((SearchAndPlayMusicPayload) payload);
            }
        } else if (ApiConstants.Directives.SearchAndPlayUnicast.NAME.equals(name)) {
            if (payload instanceof SearchAndPlayUnicastPayload) {
                handleSearchAndPlayUnicast((SearchAndPlayUnicastPayload) payload);
            }
        } else if (ApiConstants.Directives.SearchAndPlayRadio.NAME.equals(name)) {
            if (payload instanceof SearchAndPlayRadioPayload) {
                handleSearchAndPlayRadio((SearchAndPlayRadioPayload) payload);
            }
        } else if (ApiConstants.Directives.Next.NAME.equals(name)) {
            if (payload instanceof NextPayload) {
                handleNext((NextPayload) payload);
            }
        } else if (ApiConstants.Directives.Previous.NAME.equals(name)) {
            if (payload instanceof PreviousPayload) {
                handlePrevious((PreviousPayload) payload);
            }
        } else if (ApiConstants.Directives.Pause.NAME.equals(name)) {
            if (payload instanceof PausePayload) {
                handlePause((PausePayload) payload);
            }
        } else if (ApiConstants.Directives.Stop.NAME.equals(name)) {
            if (payload instanceof StopPayload) {
                handleStop((StopPayload) payload);
            }
        } else if (ApiConstants.Directives.Play.NAME.equals(name)) {
            if (payload instanceof PlayPayload) {
                handlePlay((PlayPayload) payload);
            }
        } else if (ApiConstants.Directives.SetPlaybackMode.NAME.equals(name)) {
            if (payload instanceof SetPlaybackModePayload) {
                handleSetPlaybackMode((SetPlaybackModePayload) payload);
            }
        } else {
            String message = "localAudioPlayer cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.SearchAndPlayMusic.NAME, SearchAndPlayMusicPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SearchAndPlayUnicast.NAME, SearchAndPlayUnicastPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SearchAndPlayRadio.NAME, SearchAndPlayRadioPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.Next.NAME, NextPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.Previous.NAME, PreviousPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.Pause.NAME, PausePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.Stop.NAME, StopPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.Play.NAME, PlayPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.SetPlaybackMode.NAME, SetPlaybackModePayload.class);
        return map;
    }

    @Override
    public void release() {
        listeners.clear();
    }

    public void addLocalAudioPlayerListener(ILocalAudioPlayerListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removeLocalAudioPlayerListener(ILocalAudioPlayerListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void setPayloadGenerator(PayloadGenerator generator) {
        payloadGenerator = generator;
    }

    private void handleSearchAndPlayMusic(SearchAndPlayMusicPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onSearchAndPlayMusic(payload);
        }
    }

    private void handleSearchAndPlayUnicast(SearchAndPlayUnicastPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onSearchAndPlayUnicast(payload);
        }
    }

    private void handleSearchAndPlayRadio(SearchAndPlayRadioPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onSearchAndPlayRadio(payload);
        }
    }

    private void handleNext(NextPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onNext(payload);
        }
    }

    private void handlePrevious(PreviousPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onPrevious(payload);
        }
    }

    private void handlePause(PausePayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onPause(payload);
        }
    }

    private void handleStop(StopPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onStop(payload);
        }
    }

    private void handlePlay(PlayPayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onPlay(payload);
        }
    }

    private void handleSetPlaybackMode(SetPlaybackModePayload payload) {
        for (ILocalAudioPlayerListener listener : listeners) {
            listener.onSetPlaybackMode(payload);
        }
    }

    public interface ILocalAudioPlayerListener {
        void onSearchAndPlayMusic(SearchAndPlayMusicPayload payload);

        void onSearchAndPlayUnicast(SearchAndPlayUnicastPayload payload);

        void onSearchAndPlayRadio(SearchAndPlayRadioPayload payload);

        void onNext(NextPayload payload);

        void onPrevious(PreviousPayload payload);

        void onPause(PausePayload payload);

        void onStop(StopPayload payload);

        void onPlay(PlayPayload payload);

        void onSetPlaybackMode(SetPlaybackModePayload payload);
    }

    public interface PayloadGenerator {
        ClientContextPayload generateContextPayload();
    }

}
