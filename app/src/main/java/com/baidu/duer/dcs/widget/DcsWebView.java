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
package com.baidu.duer.dcs.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * for htmlView指令
 * <p>
 * Created by guxiuzhong@baidu.com on 2017/9/7.
 */
public class DcsWebView extends WebView {
    public DcsWebView(Context context) {
        super(context);
        init(context);
    }

    public DcsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DcsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT < 19) {
            removeJavascriptInterface("searchBoxJavaBridge_");
        }
        WebSettings localWebSettings = this.getSettings();
        try {
            localWebSettings.setAllowFileAccess(false);
            localWebSettings.setSupportZoom(false);
            localWebSettings.setBuiltInZoomControls(false);
            localWebSettings.setUseWideViewPort(true);
            localWebSettings.setDomStorageEnabled(true);
            localWebSettings.setLoadWithOverviewMode(true);
            localWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            localWebSettings.setPluginState(WebSettings.PluginState.ON);
            // 启用数据库
            localWebSettings.setDatabaseEnabled(true);
            // 设置定位的数据库路径
            String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
            localWebSettings.setGeolocationDatabasePath(dir);
            localWebSettings.setGeolocationEnabled(true);
            localWebSettings.setJavaScriptEnabled(true);
            localWebSettings.setSavePassword(false);
            String agent = localWebSettings.getUserAgentString();
            localWebSettings.setUserAgentString(agent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setWebViewClient(new MyWebViewClient());
    }

    private final class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (loadListener != null) {
                loadListener.onPageStarted();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (loadListener != null) {
                loadListener.onPageFinished();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 当发生证书认证错误时，采用默认的处理方法handler.cancel()，停止加载问题页面
            handler.cancel();
        }
    }

    public interface LoadListener {
        void onPageStarted();

        void onPageFinished();
    }

    private LoadListener loadListener;

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }
}
