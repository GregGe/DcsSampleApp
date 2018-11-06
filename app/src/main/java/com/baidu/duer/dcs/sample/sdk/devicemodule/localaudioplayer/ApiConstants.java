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

/**
 * Created by longyin01 on 17/9/26.
 */

public class ApiConstants {

    public static final String NAMESPACE = "ai.dueros.device_interface.extensions.local_audio_player";
    public static final String NAME = "LocalAudioPlayerInterface";

    public static final class Events {
        public static final class PlaybackState {
            public static final String NAME = PlaybackState.class.getSimpleName();
        }
    }

    public static final class Directives {
        public static final class SearchAndPlayMusic {
            public static final String NAME = SearchAndPlayMusic.class.getSimpleName();
        }

        public static final class SearchAndPlayUnicast {
            public static final String NAME = SearchAndPlayUnicast.class.getSimpleName();
        }

        public static final class SearchAndPlayRadio {
            public static final String NAME = SearchAndPlayRadio.class.getSimpleName();
        }

        public static final class Next {
            public static final String NAME = Next.class.getSimpleName();
        }

        public static final class Previous {
            public static final String NAME = Previous.class.getSimpleName();
        }

        public static final class Pause {
            public static final String NAME = Pause.class.getSimpleName();
        }

        public static final class Stop {
            public static final String NAME = Stop.class.getSimpleName();
        }

        public static final class Play {
            public static final String NAME = Play.class.getSimpleName();
        }

        public static final class SetPlaybackMode {
            public static final String NAME = SetPlaybackMode.class.getSimpleName();
        }

    }

}
