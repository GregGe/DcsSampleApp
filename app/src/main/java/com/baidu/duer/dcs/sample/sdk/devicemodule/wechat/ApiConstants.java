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
package com.baidu.duer.dcs.sample.sdk.devicemodule.wechat;

import java.io.Serializable;

/**
 * wechat控制模块的namespace、name，以及其事件、指令的name
 * <p>
 * Created by wenzongliang on 2017/8/23.
 */
public class ApiConstants implements Serializable {
    public static final String NAMESPACE = "ai.dueros.device_interface.extensions.wechat";
    public static final String NAME = "WechatInterface";

    public static final class Events {
    }

    public static final class Directives {
        public static final class SendWechatByName {
            public static final String NAME = SendWechatByName.class.getSimpleName();
        }

        public static final class ReadWechatMessage {
            public static final String NAME = ReadWechatMessage.class.getSimpleName();
        }
    }
}
