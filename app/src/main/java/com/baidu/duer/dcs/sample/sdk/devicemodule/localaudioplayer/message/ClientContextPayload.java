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
package com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by longyin01 on 17/9/26.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ClientContextPayload extends Payload implements Serializable {

    public AudioFileTag audioFileTag;
    public String playerActivity;
    public STATUS status = STATUS.CLOSED;
    public String playerName;

    public enum STATUS {
        FOREGROUND,
        BACKGROUND,
        CLOSED
    }

    public ClientContextPayload() {
    }

    public ClientContextPayload(AudioFileTag audioFileTag,
                                String playerActivity,
                                STATUS status,
                                String playerName) {
        this.audioFileTag = audioFileTag;
        this.playerActivity = playerActivity;
        this.status = status;
        this.playerName = playerName;
    }
}
