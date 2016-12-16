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
 * BinarySms Class.
 * 
 * @author charleszamora
 */
public class BinarySms extends Context {
    /* Sms-Binary API url */
    private final String SMS_BIN_URL = "https://devapi.globelabs.com.ph/binarymessaging/v1/outbound/%s/requests";
    
    /* Sender address (Short Code) */
    protected String senderAddress = null;
    
    /* API Access token */
    protected String accessToken = null;
    
    /* Receiver address */
    protected String receiverAddress = null;

    /* Message UDH */
    protected String userDataHeader = null;
    
    /* Data coding of the message */
    protected int dataCodingScheme = 0;
    
    /* Binary message */
    protected String binaryMessage = null;
    
    /**
     * Create BinarySms class without parameters.
     *
     * @param  reactContext
     */
    public BinarySms(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    
    /**
     * Sets sender address (Short Code)
     * 
     * @param  senderAddress app short code
     * @return this 
     */
    @ReactMethod
    public BinarySms setSenderAddress(String senderAddress) {
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
    public BinarySms setAccessToken(String accessToken) {
        // set access token
        this.accessToken = accessToken;
        
        return this;
    }
    
    /**
     * Sets receiver address.
     * 
     * @param  receiverAddress receiver address
     * @return this
     */
    @ReactMethod
    public BinarySms setReceiverAddress(String receiverAddress) {
        // set receiver address
        this.receiverAddress = receiverAddress;
        
        return this;
    }
    
    /**
     * Sets user data header.
     * 
     * @param  userDataHeader user data header
     * @return this
     */
    @ReactMethod
    public BinarySms setUserDataHeader(String userDataHeader) {
        // set user data header
        this.userDataHeader = userDataHeader;
        
        return this;
    }
    
    /**
     * Sets data coding scheme.
     * 
     * @param  dataCodingScheme data coding scheme
     * @return this
     */
    @ReactMethod
    public BinarySms setDataCodingScheme(int dataCodingScheme) {
        // set data coding scheme
        this.dataCodingScheme = dataCodingScheme;
        
        return this;
    }
    
    /**
     * Sets binary message.
     * 
     * @param  binaryMessage binary message
     * @return this
     */
    @ReactMethod
    public BinarySms setBinaryMessage(String binaryMessage) {
        // set binary message
        this.binaryMessage = binaryMessage;
        
        return this;
    }
    
    /**
     * Sends binary message request.
     * 
     * @param  userDataHeader user data header
     * @param  dataCodingScheme data coding scheme
     * @param  receiverAddress receiver address
     * @param  binaryMessage binary message
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendBinaryMessage(
        String userDataHeader,
        int dataCodingScheme,
        String receiverAddress,
        String binaryMessage,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // get the url
        String url = this.buildUrl(this.SMS_BIN_URL);
        
        // set base data
        Map<String, Object> data = new HashMap<>();
    
        // set outbound binary messsage request data
        Map<String, Object> obmr = new HashMap<>();
        
        // set message
        Map<String, String> msg = new HashMap<>();
        
        // set sender address
        obmr.put("senderAddress", this.senderAddress);
        
        // set binary message
        msg.put("message", binaryMessage);
        
        // set user data header
        obmr.put("userDataHeader", userDataHeader);
        
        // set data coding scheme
        obmr.put("dataCodingScheme", Integer.toString(dataCodingScheme));
        
        // set address
        obmr.put("address", receiverAddress);
        
        // set outbound binary message
        obmr.put("outboundBinaryMessage", new org.json.JSONObject(msg));
        
        // set to base data
        data.put("outboundBinaryMessageRequest", new org.json.JSONObject(obmr));

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
     * Sends binary message request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void sendBinaryMessage(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call send binary message
        this.sendBinaryMessage(
            this.userDataHeader, 
            this.dataCodingScheme, 
            this.receiverAddress, 
            this.binaryMessage,
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
