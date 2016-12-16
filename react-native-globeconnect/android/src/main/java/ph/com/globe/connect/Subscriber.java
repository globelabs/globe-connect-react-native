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

import java.net.URISyntaxException;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import org.apache.http.client.utils.URIBuilder;

/**
 * Subscriber Data Query.
 * 
 * @author Charles Zamora czamora@openovate.com
 */
public class Subscriber extends Context {
    /* Subscriber url */
    private final String SUBSCRIBER_URL = "https://devapi.globelabs.com.ph/location/v1/queries/balance";
    
    /* Subscriber reload amount url */
    private final String SUBSCRIBER_RA_URL = "https://devapi.globelabs.com.ph/location/v1/queries/reload_amount";
    
    /* API Access token */
    protected String accessToken = null;
    
    /* Subscribers address */
    protected String address = null;
    
    /**
     * Create Subscriber class without parameters.
     *
     * @param reactContext
     */
    public Subscriber(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    
    /**
     * Sets access token.
     * 
     * @param  accessToken access token
     * @return this
     */
    @ReactMethod
    public Subscriber setAccessToken(String accessToken) {
        // set access token
        this.accessToken = accessToken;
        
        return this;
    }
    
    /**
     * Set subscriber address.
     * 
     * @param  address subscriber address
     * @return this
     */
    @ReactMethod
    public Subscriber setAddress(String address) {
        // set address
        this.address = address;
        
        return this;
    }
    
    /**
     * Get subscriber balance request.
     * 
     * @param  address subscriber address
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void getSubscriberBalance(
        String address,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // set request url
        String url = this.SUBSCRIBER_URL;
        
        // build url
        try {
            // initialize url builder
            URIBuilder builder = new URIBuilder(url);
            
            // set access token parameter
            builder.setParameter("access_token", this.accessToken);
            // set the address
            builder.setParameter("address", address);
            
            // build the url
            url = builder.build().toString();
        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }

        // send request
        new HttpRequest()
        // set url
        .setUrl(url)
        // set async handler
        .setAsyncHandler(new AsyncHandler() {
            @Override
            public void response(HttpResponse response) throws HttpResponseException {
                // try parsing
                try {
                    // send response
                    success.invoke(response.convertJsonToMap(response.getJsonResponse()));
                } catch(HttpResponseException e) {
                    error.invoke(e.getMessage());
                }
            }
        })
        // send get request
        .execute("get");
    }
    
    /**
     * Get subscriber balance request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException  http response exception
     */
    @ReactMethod
    public void getSubscriberBalance(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call get subscriber balance
        this.getSubscriberBalance(this.address, success, error);
    }
    
    /**
     * Get subscriber reload amount.
     * 
     * @param  address subscriber address
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void getSubscriberReloadAmount(
        String address, 
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // set request url
        String url = this.SUBSCRIBER_RA_URL;
        
        // build url
        try {
            // initialize url builder
            URIBuilder builder = new URIBuilder(url);
            
            // set access token parameter
            builder.setParameter("access_token", this.accessToken);
            // set the address
            builder.setParameter("address", address);
            
            // build the url
            url = builder.build().toString();
        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }

        // send request
        new HttpRequest()
        // set url
        .setUrl(url)
        // set async handler
        .setAsyncHandler(new AsyncHandler() {
            @Override
            public void response(HttpResponse response) throws HttpResponseException {
                // try parsing
                try {
                    // send response
                    success.invoke(response.convertJsonToMap(response.getJsonResponse()));
                } catch(HttpResponseException e) {
                    error.invoke(e.getMessage());
                }
            }
        })
        // send get request
        .execute("get");
    }
    
    /**
     * Get subscriber reload amount.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void getSubscriberReloadAmount(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call get subscriber reload amount
        this.getSubscriberReloadAmount(this.address, success, error);
    }
}
