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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStoreException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.http.entity.StringEntityHC4;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;

import org.json.JSONObject;

import android.os.AsyncTask;

/**
 * HttpRequest helper.
 *
 * @author Charles Zamora czamora@openovate.com
 */
public class HttpRequest extends AsyncTask<String, Integer, CloseableHttpResponse> {
    /* Default user agent */
    private final String USER_AGENT = "Mozilla/5.0";

    /* Default content type */
    private final String CONTENT_TYPE = "application/json";

    /* Host url */
    protected String url = null;

    /* Request data */
    protected Map<String, Object> data = null;

    /* Async Handler */
    protected AsyncHandler asyncHandler;

    /**
     * Create HttpRequest without any parameter.
     */
    public HttpRequest() {}

    /**
     * Create HttpRequest with url parameter.
     *
     * @param url target url
     */
    public HttpRequest(String url) {
        this.url = url;
    }

    /**
     * Returns the current data.
     *
     * @return Map
     */
    public Map<String, Object> getData() {
        return this.data;
    }

    /**
     * Set async handler.
     *
     * @param  asyncHandler
     * @return this
     */
    public HttpRequest setAsyncHandler(AsyncHandler asyncHandler) {
        // set async handler
        this.asyncHandler = asyncHandler;

        return this;
    }

    /**
     * Returns the current data
     * as JSONObject.
     *
     * @return JSONObject
     */
    public JSONObject getDataAsJson() {
        return new JSONObject(this.data);
    }

    /**
     * Set request url.
     *
     * @param  url target url
     * @return this
     */
    public HttpRequest setUrl(String url) {
        // set url
        this.url = url;

        return this;
    }

    /**
     * Set request data.
     *
     * @param  data post data
     * @return this
     */
    public HttpRequest setData(Map<String, Object> data) {
        // set request data
        this.data = data;

        return this;
    }

    /**
     * Sends get request to the specified url.
     *
     * @return CloseableHttpResponse
     * @throws HttpRequestException http request exception
     */
    public CloseableHttpResponse sendGet() throws HttpRequestException {
        // try building up
        try {
            // initialize ssl context builder
            SSLContextBuilder builder = new SSLContextBuilder();

            // set trust self signed strategy
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

            // initialize ssl socket connection factory
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(builder.build());

            // default http client
            CloseableHttpClient client = HttpClients
                    .custom()
                    .setSSLSocketFactory(sslSocketFactory)
                    .build();

            // create request method
            HttpGetHC4 request = new HttpGetHC4(this.url);

            // set default header
            request.setHeader("User-Agent", this.USER_AGENT);

            // try request
            try {
                // execute request and get response
                CloseableHttpResponse response = client.execute(request);

                response.close();

                return response;
            } catch (IOException e) {
                throw new HttpRequestException(e.getMessage());
            }
        } catch(NoSuchAlgorithmException e) {
            throw new HttpRequestException(e.getMessage());
        } catch(KeyManagementException e) {
            throw new HttpRequestException(e.getMessage());
        } catch(KeyStoreException e) {
            throw new HttpRequestException(e.getMessage());
        }
    }

    /**
     * Send post request to the specified url.
     *
     * @return CloseableHttpResponse
     * @throws HttpRequestException http request exception
     */
    public CloseableHttpResponse sendPost() throws HttpRequestException {
        // try building up
        try {
            // initialize ssl context builder
            SSLContextBuilder builder = new SSLContextBuilder();

            // set trust self signed strategy
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

            // initialize ssl socket connection factory
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(builder.build());

            // default http client
            CloseableHttpClient client = HttpClients
                .custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

            // create request method
            HttpPostHC4 post = new HttpPostHC4(this.url);

            // set default user agent
            post.setHeader("User-Agent", this.USER_AGENT);
            // set default content type
            post.setHeader("Content-Type", this.CONTENT_TYPE);

            // convert data to json string
            JSONObject data = new JSONObject(this.data);

            System.out.println(this.url);
            System.out.println(data.toString());

            try {
                // set the string entity
                StringEntityHC4 entity = new StringEntityHC4(data.toString());

                // set post data
                post.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                // throw exception
                throw new HttpRequestException(e.getMessage());
            }

            // try request
            try {
                // execute request and get the response
                CloseableHttpResponse response = client.execute(post);

                response.close();

                return response;
            } catch (IOException e) {
                // throw an exception
                throw new HttpRequestException(e.getMessage());
            }
        } catch(NoSuchAlgorithmException e) {
            // throw an exception
            throw new HttpRequestException(e.getMessage());
        } catch(KeyManagementException e) {
            // throw an exception
            throw new HttpRequestException(e.getMessage());
        } catch(KeyStoreException e) {
            // throw an exception
            throw new HttpRequestException(e.getMessage());
        }
    }

    @Override
    public CloseableHttpResponse doInBackground(String... params) {
        try {
            if(params[0] == "get") {
                return this.sendGet();
            } else {
                return this.sendPost();
            }
        } catch(HttpRequestException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onPostExecute(CloseableHttpResponse result) {
        try {
            asyncHandler.response(new HttpResponse(result));
        } catch(HttpResponseException e) {
            e.printStackTrace();
        }
    }
}
