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

/**
 * Created by caoyushu01 on 2017/7/26.
 */

public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.extensions.device_control";
    public static final String NAME = "DeviceControlInterface";

    public static final class Events {
    }

    public static final class Directives {
        public static final class SetPortraitLock {
            public static final String NAME = SetPortraitLock.class.getSimpleName();
        }
        public static final class SetWifi {
            public static final String NAME = SetWifi.class.getSimpleName();
        }
        public static final class SetPhoneMode {
            public static final String NAME = SetPhoneMode.class.getSimpleName();
        }
        public static final class SetHotspot {
            public static final String NAME = SetHotspot.class.getSimpleName();
        }
        public static final class SetVibration {
            public static final String NAME = SetVibration.class.getSimpleName();
        }
        public static final class SetGps {
            public static final String NAME = SetGps.class.getSimpleName();
        }
        public static final class SetCellular {
            public static final String NAME = SetCellular.class.getSimpleName();
        }
        public static final class SetVpn {
            public static final String NAME = SetVpn.class.getSimpleName();
        }
        public static final class SetAssistiveTouch {
            public static final String NAME = SetAssistiveTouch.class.getSimpleName();
        }
        public static final class SetNfc {
            public static final String NAME = SetNfc.class.getSimpleName();
        }
        public static final class SetSynchronization {
            public static final String NAME = SetSynchronization.class.getSimpleName();
        }
        public static final class SetCellularMode {
            public static final String NAME = SetCellularMode.class.getSimpleName();
        }
        public static final class SetPhonePower {
            public static final String NAME = SetPhonePower.class.getSimpleName();
        }
        public static final class AdjustBrightness {
            public static final String NAME = AdjustBrightness.class.getSimpleName();
        }
        public static final class SetBrightness {
            public static final String NAME = SetBrightness.class.getSimpleName();
        }
        public static final class SetBluetooth {
            public static final String NAME = SetBluetooth.class.getSimpleName();
        }

    }
}
