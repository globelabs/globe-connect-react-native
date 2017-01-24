/*
 * The MIT License
 *
 * Copyright 2016 charleszamora.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ph.com.globe.connect;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.HashMap;

import org.apache.http.client.utils.URIBuilder;

/**
 * Authentication Activity class.
 *
 * @author Charles Zamora czamora@openovate.com
 */
public class AuthenticationActivity extends AppCompatActivity {
    /* Root url */
    private final String ROOT_URL = "http://developer.globelabs.com.ph/";
    /* Dialog url */
    private final String DIALOG_URL = "http://developer.globelabs.com.ph/dialog/oauth";
    /* Access url */
    private final String ACCESS_URL = "http://developer.globelabs.com.ph/oauth/access_token";

    /**
     * On activity create process.
     *
     * @param savedInstanceState instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // call base create
        super.onCreate(savedInstanceState);

        // initialize web view
        final WebView webview = new WebView(this);

        // set content view of the activity
        setContentView(webview);

        // get the app id from intent
        final String appId        = getIntent().getStringExtra("app_id");
        // get the app secret from intent
        final String appSecret    = getIntent().getStringExtra("app_secret");

        // set web view client
        webview.setWebViewClient(new WebViewClient() {
            /**
             * Let's catch all url changes.
             *
             * @param view current view
             * @param url current url
             * @return boolean
             */
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // parse the uri
                final Uri uri = Uri.parse(url);

                // are we still on globe labs?
                if(uri.toString().indexOf(ROOT_URL) != 0) {
                    // get the code
                    String code = uri.getQueryParameter("code");

                    // get access token request
                    try {
                        getAccessToken(appId, appSecret, code);
                    } catch(ApiException | HttpRequestException e) {
                        e.printStackTrace();
                    }

                    return false;
                }

                // load uri
                view.loadUrl(uri.toString());

                return false;
            }

            /**
             * Let's catch all url changes.
             *
             * @param view current view
             * @param request web resource request
             * @return boolean
             */
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // parse the uri
                final Uri uri = Uri.parse(request.getUrl().toString());

                // are we still on globe labs?
                if(uri.toString().indexOf(ROOT_URL) != 0) {
                    // get the code
                    String code = uri.getQueryParameter("code");

                    // get access token request
                    try {
                        getAccessToken(appId, appSecret, code);
                    } catch(ApiException | HttpRequestException e) {
                        e.printStackTrace();
                    }

                    return false;
                }

                // load uri
                view.loadUrl(uri.toString());

                return false;
            }
        });

        try {
            // set dialog url
            String dialogUrl = this.buildUrl(this.DIALOG_URL, appId);

            // load the url
            webview.loadUrl(dialogUrl);
        } catch(ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get access token request.
     *
     * @param appId application id
     * @param appSecret application secret
     * @param code access token request code
     * @throws ApiException
     * @throws HttpRequestException http request exception
     */
    public void getAccessToken(String appId, String appSecret, String code)
            throws ApiException, HttpRequestException {

        // create data key value
        Map<String, Object> data = new HashMap<>();

        // set app id
        data.put("app_id", appId);
        // set app secret
        data.put("app_secret", appSecret);
        // set app code
        data.put("code", code);

        // send request
        new HttpRequest()
        // set url
        .setUrl(this.ACCESS_URL)
        // set data
        .setData(data)
        // set async handler
        .setAsyncHandler(new AsyncHandler() {
            @Override
            public void response(HttpResponse response) throws HttpResponseException {
                // try parsing
                try {
                    // send response
                    Authentication
                    .onAuthSuccess
                    .invoke(response.convertJsonToMap(response.getJsonResponse()));

                    // finish activity
                    finish();
                } catch(HttpResponseException e) {
                    // send response
                    Authentication
                    .onAuthError
                    .invoke(e.getMessage());
                }
            }
        })
        // execute async post
        .execute("post");
    }

    /**
     * Build request url.
     *
     * @param  url target url
     * @param  appId app id
     * @return String
     * @throws ApiException api exception
     */
    protected String buildUrl(String url, String appId) throws ApiException {
        // try parsing url
        try {
            // build url
            url = String.format(url, this.DIALOG_URL);
            // initialize url builder
            URIBuilder builder = new URIBuilder(url);

            // set access token parameter
            builder.setParameter("app_id", appId);

            // build the url
            url = builder.build().toString();

            return url;
        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }
    }
}
