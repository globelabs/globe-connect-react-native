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
import java.util.Map;
import java.util.HashMap;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import org.apache.http.client.utils.URIBuilder;

/**
 * Payment Services Class.
 * 
 * @author Charles Zamora czamora@openovate.com
 */
public class Payment extends Context {
    /* Payment API Url */
    private final String PAYMENT_URL = "https://devapi.globelabs.com.ph/payment/v1/transactions/amount";
    
    /* Payment last reference url */
    private final String LAST_REF_URL = "https://devapi.globelabs.com.ph/payment/v1/transactions/getLastRefCode";
    
    /* API app id */
    protected String appId     = null;
    
    /* API app secret */
    protected String appSecret = null;
    
    /* API Access token */
    protected String accessToken = null;
    
    /* Payment amount */
    protected double amount = 0.00;
    
    /* Payment description */
    protected String description = null;
    
    /* User id / Subscribers number */
    protected String endUserId = null;
    
    /* Custom reference code */
    protected String referenceCode = null;

    /* Resource state */
    protected String transactionOperationStatus = null;
    
    /**
     * Create Payment class without parameters.
     *
     * @param reactContext
     */
    public Payment(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    
    /**
     * Set API app id.
     * 
     * @param appId app id
     * @return this
     */
    @ReactMethod
    public Payment setAppId(String appId) {
        // set app id
        this.appId = appId;
        
        return this;
    }
    
    /**
     * Set API app secret.
     * 
     * @param appSecret app secret
     * @return this 
     */
    @ReactMethod
    public Payment setAppSecret(String appSecret) {
        // set app secret
        this.appSecret = appSecret;
        
        return this;
    }
    
    /**
     * Sets access token.
     * 
     * @param  accessToken access token
     * @return this
     */
    @ReactMethod
    public Payment setAccessToken(String accessToken) {
        // set access token
        this.accessToken = accessToken;
        
        return this;
    }
    
    
    /**
     * Sets payment amount.
     * 
     * @param  amount payment amount
     * @return this
     */
    @ReactMethod
    public Payment setAmount(double amount) {
        // set amount
        this.amount = amount;
        
        return this;
    }
    
    /**
     * Set payment description.
     * 
     * @param  description payment description
     * @return this
     */
    @ReactMethod
    public Payment setDescription(String description) {
        // set description
        this.description = description;
        
        return this;
    }
    
    /**
     * Set end user id.
     * 
     * @param  endUserId end user id
     * @return this
     */
    @ReactMethod
    public Payment setEndUserId(String endUserId) {
        // set end user id
        this.endUserId = endUserId;
        
        return this;
    }
    
    /**
     * Set reference code.
     * 
     * @param  referenceCode reference code
     * @return this
     */
    @ReactMethod
    public Payment setReferenceCode(String referenceCode) {
        // set reference code
        this.referenceCode = referenceCode;
        
        return this;
    }
    
    /**
     * Set transaction operation status.
     * 
     * @param  transactionOperationStatus resource state
     * @return this
     */
    @ReactMethod
    public Payment setTransactionOperationStatus(String transactionOperationStatus) {
        // set transaction operation status
        this.transactionOperationStatus = transactionOperationStatus;
        
        return this;
    }
    
    /**
     * Sends payment request.
     * 
     * @param  amount payment amount
     * @param  description payment description
     * @param  endUserId end user id
     * @param  referenceCode custom reference code
     * @param  transactionOperationStatus resource state
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendPaymentRequest(
        double amount,
        String description,
        String endUserId,
        String referenceCode,
        String transactionOperationStatus,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // build url
        String url = this.buildUrl(this.PAYMENT_URL);
        
        // set base data
        Map<String, Object> data = new HashMap<>();
        
        // set amount
        data.put("amount", String.format("%.2f", amount));
        // set description
        data.put("description", description);
        // set end user id
        data.put("endUserId", endUserId);
        // set reference code
        data.put("referenceCode", referenceCode);
        // set transaction operation status
        data.put("transactionOperationStatus", transactionOperationStatus);

        // send request
        new HttpRequest()
        // set url
        .setUrl(url)
        // set data
        .setData(data)
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
        // execute async post
        .execute("post");
    }
    
    /**
     * Sends payment request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void sendPaymentRequest(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call send payment request
        this.sendPaymentRequest(
            this.amount, 
            this.description, 
            this.endUserId, 
            this.referenceCode, 
            this.transactionOperationStatus,
            success,
            error);
    }
    
    /**
     * Get last reference code request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void getLastReferenceCode(final Callback success, final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // build url
        String url = this.buildUrl(this.LAST_REF_URL, this.appId, this.appSecret);

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
     * Build request url.
     * 
     * @param  url target url
     * @return String
     * @throws ApiException api exception
     */
    protected String buildUrl(String url) throws ApiException {
        // try parsing url
        try {
            // initialize url builder
            URIBuilder builder = new URIBuilder(url);
            
            // set access token parameter
            builder.setParameter("access_token", this.accessToken);
            
            // build the url
            url = builder.build().toString();
            
            return url;
        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }
    }
    
    /**
     * Build request url.
     * 
     * @param  url target url
     * @param  appId app id
     * @param  appSecret app secret
     * @return String
     * @throws ApiException api exception
     */
    protected String buildUrl(String url, String appId, String appSecret) throws ApiException {
        // try parsing url
        try {
            // initialize url builder
            URIBuilder builder = new URIBuilder(url);
            
            // set app id parameter
            builder.setParameter("app_id", appId);
            // set app secret parameter
            builder.setParameter("app_secret", appSecret);
            
            // build the url
            url = builder.build().toString();
            
            return url;
        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }
    }
}
