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
import java.util.Arrays;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import org.apache.http.client.utils.URIBuilder;

/**
 * SMS Class.
 * 
 * @author Charles Zamora czamora@openovate.com
 */
public class Sms extends Context {
    /* SMS-MT API url */
    private final String SMS_MT_URL = "https://devapi.globelabs.com.ph/smsmessaging/v1/outbound/%s/requests";
            
    /* Sender address (Short Code) */
    protected String senderAddress = null;
    
    /* API Access token */
    protected String accessToken = null;
    
    /* Client correlator */
    protected String clientCorrelator = null;
    
    /* Receiver address */
    protected String receiverAddress = null;
    
    /* Outbound SMS Text message */
    protected String message = null;
    
    /**
     * Create Sms class without parameters.
     *
     * @param reactContext
     */
    public Sms(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    
    /**
     * Sets sender address (Short Code)
     * 
     * @param  senderAddress sender address
     * @return this 
     */
    @ReactMethod
    public Sms setSenderAddress(String senderAddress) {
        // set sender address
        this.senderAddress = senderAddress;
        
        return this;
    }
    
    /**
     * Sets access token.
     * 
     * @param  accessToken access token
     * @return this
     */
    @ReactMethod
    public Sms setAccessToken(String accessToken) {
        // set access token
        this.accessToken = accessToken;
        
        return this;
    }
    
    /**
     * Sets client correlator.
     * 
     * @param  clientCorrelator client correlator
     * @return this
     */
    @ReactMethod
    public Sms setClientCorrelator(String clientCorrelator) {
        // set client correlator
        this.clientCorrelator = clientCorrelator;
        
        return this;
    }
    
    /**
     * Sets receiver address.
     * 
     * @param  receiverAddress receiver address
     * @return this
     */
    @ReactMethod
    public Sms setReceiverAddress(String receiverAddress) {
        // set receiver address
        this.receiverAddress = receiverAddress;
        
        return this;
    }
    
    /**
     * Sets outbound sms text message.
     * 
     * @param  message outbound message
     * @return this
     */
    @ReactMethod
    public Sms setMessage(String message) {
        // set message
        this.message = message;
        
        return this;
    }
    
    /**
     * Returns the current sender address.
     * 
     * @return String 
     */
    public String getSenderAddress() {
        return this.senderAddress;
    }
    
    /**
     * Returns the current access token.
     * 
     * @return String 
     */
    public String getAccessToken() {
        return this.accessToken;
    }
    
    /**
     * Sends an outbound sms request.
     * 
     * @param  clientCorrelator client correlator
     * @param  message outbound message
     * @param  receiverAddress receiver address
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendMessage(
        String clientCorrelator,
        String message,
        String receiverAddress,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // get the url
        String url = this.buildUrl(this.SMS_MT_URL);
        
        // set base data
        Map<String, Object> data = new HashMap<>();
        
        // set OutboundSMSMessageRequest data
        Map<String, Object> osmr = new HashMap<>();
        
        // set message data
        Map<String, String> msg  = new HashMap<>();
        
        
        // set client correlator
        if(clientCorrelator != null) {
            osmr.put("client_correlator", clientCorrelator);
        }
            
        // set sender address
        osmr.put("senderAddress", "tel:" + this.senderAddress);
        
        // set message data
        msg.put("message", message);
        
        // set message
        osmr.put("outboundSMSTextMessage", new org.json.JSONObject(msg));
        
        // set address
        osmr.put("address", 
            new org.json.JSONArray(Arrays.asList(new String[] {"tel:" + receiverAddress })));
        
        // set the outbound sms request data
        data.put("outboundSMSMessageRequest", new org.json.JSONObject(osmr));

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
     * Sends an outbound sms request.
     * 
     * @param  message outbound message
     * @param  receiverAddress receiver address
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendMessage(
        String message,
        String receiverAddress,
        Callback success,
        Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call send message
        this.sendMessage(this.clientCorrelator, this.message, this.receiverAddress, success, error);
    }
    
    /**
     * Sends an outbound sms request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void sendMessage(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call send message
        this.sendMessage(this.clientCorrelator, this.message, this.receiverAddress, success, error);
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
            // build url
            url = String.format(url, this.senderAddress);
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
}