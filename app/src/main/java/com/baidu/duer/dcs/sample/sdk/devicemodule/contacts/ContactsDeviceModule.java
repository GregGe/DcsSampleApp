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
package com.baidu.duer.dcs.sample.sdk.devicemodule.contacts;

import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.Event;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.MessageIdHeader;
import com.baidu.duer.dcs.util.message.Payload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.contacts.message.CreateContactFailedPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.contacts.message.CreateContactPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.contacts.message.SearchContactPayload;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by caoyushu01 on 2017/7/28.
 */

public class ContactsDeviceModule extends BaseDeviceModule {
    private List<IContactsListener> listeners;

    public ContactsDeviceModule (IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientContext clientContext () {
        return null;
    }

    @Override
    public void handleDirective (Directive directive) throws HandleDirectiveException {
        String headerName = directive.getName();
        Payload payload = directive.getPayload();
        if (ApiConstants.Directives.CreateContact.NAME.equals(headerName)) {
            fireOnCreateContact((CreateContactPayload) payload);
        } else if (ApiConstants.Directives.SearchContact.NAME.equals(headerName)) {
            fireOnSearchContact((SearchContactPayload) payload);
        } else {
            String message = "contact cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }

    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String, Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.SearchContact.NAME, SearchContactPayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.CreateContact.NAME, CreateContactPayload.class);
        return map;
    }

    @Override
    public void release () {
        listeners.clear();
    }

    public void addContactsListener(IContactsListener listener) {
        if (listeners != null) {
            listeners.add(listener);
        }
    }

    public void removeContactsListener(IContactsListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public void sendCreateContactFailedEvent (String token) {
        Header header = new MessageIdHeader(ApiConstants.NAMESPACE, "CreateContactFailed");
        Payload payload = new CreateContactFailedPayload(token);
        Event event = new Event(header, payload);
        messageSender.sendEvent(event, null);
    }

    private void fireOnSearchContact(SearchContactPayload payload) {
        for (IContactsListener listener : listeners) {
            listener.onSearchContact(payload);
        }
    }

    private void fireOnCreateContact(CreateContactPayload payload) {
        for (IContactsListener listener : listeners) {
            listener.onCreateContact(payload);
        }
    }

    public interface IContactsListener {
        void onCreateContact(CreateContactPayload payload);
        void onSearchContact(SearchContactPayload payload);
    }
}
