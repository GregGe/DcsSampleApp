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
package com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyushu01 on 2017/7/18.
 */

public class SmsInfo implements Serializable {
    public static String TYPE_NAME = "SmsName";
    public static String TYPE_NUMBER = "SmsNumber";
    // 当前次短信服务的类型
    private String type = "";
    // 联系人id
    private String uid = "";
    // 联系人名称、电话号码展示名称
    private String name = "";
    // sim卡 index，可选字段
    private String simIndex = "";
    // 运营商信息，可选字段
    private String carrierOprator = "";
    // 对应的电话信息
    private List<NumberInfo> phoneNumbersList = new ArrayList<>();

    // 对应的短信消息内容
    private String messageContent = "";

    /*一个电话号条目的展示信息*/
    public static class NumberInfo implements Serializable {
        private String phoneNumber;
        private String numberType;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNumberType() {
            return numberType;
        }

        public void setNumberType(String numberType) {
            this.numberType = numberType;
        }
    }

    public static String getTypeName() {
        return TYPE_NAME;
    }

    public static void setTypeName(String typeName) {
        TYPE_NAME = typeName;
    }

    public static String getTypeNumber() {
        return TYPE_NUMBER;
    }

    public static void setTypeNumber(String typeNumber) {
        TYPE_NUMBER = typeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimIndex() {
        return simIndex;
    }

    public void setSimIndex(String simIndex) {
        this.simIndex = simIndex;
    }

    public String getCarrierOprator() {
        return carrierOprator;
    }

    public void setCarrierOprator(String carrierOprator) {
        this.carrierOprator = carrierOprator;
    }

    public List<NumberInfo> getPhoneNumbersList() {
        return phoneNumbersList;
    }

    public void setPhoneNumbersList(List<NumberInfo> phoneNumbersList) {
        this.phoneNumbersList = phoneNumbersList;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
