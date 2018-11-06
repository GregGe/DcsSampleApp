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

/**
 * Created by caoyushu01 on 2017/7/26.
 * 定义alerts for smart phone的闹钟模块，与其他硬件产品的alerts区分开来
 */

public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.android.alerts";
    public static final String NAME = "AlarmInterface";

    public static final class Events {
    }

    public static final class Directives {
        public static final class SetAlarm {
            public static final String NAME = SetAlarm.class.getSimpleName();
        }

        public static final class ShowAlarms {
            public static final String NAME = ShowAlarms.class.getSimpleName();
        }

        public static final class SetTimer {
            public static final String NAME = SetTimer.class.getSimpleName();
        }

        public static final class ShowTimers {
            public static final String NAME = ShowTimers.class.getSimpleName();
        }
    }
}
