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
 * USSD Class.
 * 
 * @author Charles Zamora czamora@openovate.com
 */
public class Ussd extends Context {
    /* USSD Send url */
    private final String USSD_SEND_NI_URL = "https://devapi.globelabs.com.ph/ussd/v1/outbound/%s/send/requests";
    
    /* USSD Reply url */
    private final String USSD_REPLY_MT_URL = "https://devapi.globelabs.com.ph/ussd/v1/outbound/%s/reply/requests";
    
    /* Sender address (Short Code) */
    protected String senderAddress = null;
    
    /* API Access token */
    protected String accessToken = null;
    
    /* USSD Message */
    protected String ussdMessage = null;
    
    /* Subscriber address */
    protected String address = null;
    
    /* Final message flag */
    protected boolean flash = false;
    
    /* USSD Session id */
    protected String sessionId = null;
    
    /**
     * Create Ussd class without parameters.
     *
     * @param reactContext
     */
    public Ussd(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    
    /**
     * Sets sender address (Short Code)
     * 
     * @param  senderAddress subscriber address
     * @return this 
     */
    @ReactMethod
    public Ussd setSenderAddress(String senderAddress) {
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
    public Ussd setAccessToken(String accessToken) {
        // set access token
        this.accessToken = accessToken;
        
        return this;
    }
    
    /**
     * Set ussd message.
     * 
     * @param  ussdMessage ussd message
     * @return this
     */
    @ReactMethod
    public Ussd setUssdMessage(String ussdMessage) {
        // set ussd message
        this.ussdMessage = ussdMessage;
        
        return this;
    }
    
    /**
     * Set subscriber address.
     * 
     * @param  address subscriber address
     * @return this
     */
    @ReactMethod
    public Ussd setAddress(String address) {
        // set subscriber address
        this.address = address;
        
        return this;
    }
    
    /**
     * Set flash.
     * 
     * @param  flash final message flag
     * @return this
     */
    @ReactMethod
    public Ussd setFlash(boolean flash) {
        // set flash
        this.flash = flash;
        
        return this;
    }
    
    /**
     * Set session id.
     * 
     * @param  sessionId session id
     * @return this
     */
    @ReactMethod
    public Ussd setSessionId(String sessionId) {
        // set session id
        this.sessionId = sessionId;
        
        return this;
    }
    
    /**
     * Send USSD Request.
     * 
     * @param  senderAddress sender address
     * @param  ussdMessage ussd message
     * @param  address subscriber address
     * @param  flash final message flag
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendUssdRequest(
        String senderAddress,
        String ussdMessage,
        String address,
        boolean flash,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // build url
        String url = this.buildUrl(this.USSD_SEND_NI_URL);
        
        // set base data
        Map<String, Object> data = new HashMap<>();
        
        // set outbound ussd message request
        Map<String, Object> oumr = new HashMap<>();
        
        // set message data
        Map<String, String> msg = new HashMap<>();
        
        // set sender address
        oumr.put("senderAddress", senderAddress);
        // set address
        oumr.put("address", address);
        // set flash
        oumr.put("flash", flash);
        
        // set message
        msg.put("message", ussdMessage);
        
        // set message
        oumr.put("outboundUSSDMessage", new org.json.JSONObject(msg));
        
        // set on base data
        data.put("outboundUSSDMessageRequest", new org.json.JSONObject(oumr));

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
     * Send USSD Request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void sendUssdRequest(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
         // call send ussd request
         this.sendUssdRequest(
            this.senderAddress, 
            this.ussdMessage, 
            this.address, 
            this.flash,
            success,
            error);
    }
    
    /**
     * Reply USSD Request.
     * 
     * @param  sessionId session id
     * @param  senderAddress sender address
     * @param  address subscriber address
     * @param  flash final message flag
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void replyUssdRequest(
        String sessionId,
        String senderAddress,
        String address,
        boolean flash,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // build url
        String url = this.buildUrl(this.USSD_REPLY_MT_URL);
        
        // set base data
        Map<String, Object> data = new HashMap<>();
        
        // set outbound ussd message request
        Map<String, Object> oumr = new HashMap<>();
        
        // set message data
        Map<String, String> msg = new HashMap<>();
        
        // set sender address
        oumr.put("senderAddress", senderAddress);
        // set address
        oumr.put("address", address);
        // set session id
        oumr.put("sessionID", sessionId);
        // set flash
        oumr.put("flash", flash);
        
        // set message
        msg.put("message", ussdMessage);
        
        // set message
        oumr.put("outboundUSSDMessage", new org.json.JSONObject(msg));
        
        // set on base data
        data.put("outboundUSSDMessageRequest", new org.json.JSONObject(oumr));

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
     * Reply USSD Request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void replyUssdRequest(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call reply ussd request
        this.replyUssdRequest(
            this.sessionId, 
            this.senderAddress, 
            this.address, 
            this.flash,
            success,
            error);
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