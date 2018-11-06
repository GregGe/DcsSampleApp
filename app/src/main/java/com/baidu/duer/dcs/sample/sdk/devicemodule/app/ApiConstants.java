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

/**
 * Created by caoyushu01 on 2017/7/20.
 */

public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.app";
    public static final String NAME = "AppInterface";

    public static final class Events {
        public static final class AppState {
            public static final String NAME = AppState.class.getSimpleName();
        }

        public static final class LaunchAppFailed {
            public static final String NAME = LaunchAppFailed.class.getSimpleName();
        }
    }

    public static final class Directives {
        public static final class TryLaunchApp {
            public static final String NAME = TryLaunchApp.class.getSimpleName();
        }

        public static final class LaunchApp {
            public static final String NAME = LaunchApp.class.getSimpleName();
        }
    }
}
