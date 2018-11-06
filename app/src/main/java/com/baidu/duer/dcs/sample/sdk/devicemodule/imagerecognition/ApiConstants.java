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
package com.baidu.duer.dcs.sample.sdk.devicemodule.imagerecognition;

import java.io.Serializable;

/**
 * 定义了表示Image Recognition模块的namespace、name，以及其事件、指令的name
 * <p>
 * Created by wenzongliang on 2017/8/2.
 */
public class ApiConstants implements Serializable {
    public static final String NAMESPACE = "ai.dueros.device_interface.image_recognition";
    public static final String NAME = "ImageRecognitionInterface";

    public static final class Events {
        public static final class StartUploadScreenShot {
            public static final String NAME = StartUploadScreenShot.class.getSimpleName();
        }
    }

    public static final class Directives {
        public static final class UploadScreenShot {
            public static final String NAME = UploadScreenShot.class.getSimpleName();
        }
    }
}
