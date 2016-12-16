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
 * Location Based Services class.
 * 
 * @author Charles Zamora czamora@openovate.com
 */
public class Location extends Context {
    /* Location API Url */
    private final String LOCATION_URL = "https://devapi.globelabs.com.ph/location/v1/queries/location";
    
    /* API Access token */
    protected String accessToken = null;
    
    /* Subscribers address */
    protected String address = null;
    
    /* Prefered accuracy of the result */
    protected int requestedAccuracy = 0;
    
    /**
     * Create Location class without parameters.
     *
     * @param reactContext
     */
    public Location(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    
    /**
     * Sets access token.
     * 
     * @param  accessToken access token
     * @return this
     */
    @ReactMethod
    public Location setAccessToken(String accessToken) {
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
    public Location setAddress(String address) {
        // set address
        this.address = address;
        
        return this;
    }
    
    /**
     * Set accuracy.
     * 
     * @param  requestedAccuracy requested accuracy
     * @return this
     */
    @ReactMethod
    public Location setRequestedAccuracy(int requestedAccuracy) {
        // set requested accuracy
        this.requestedAccuracy = requestedAccuracy;
        
        return this;
    }
    
    /**
     * Get location request.
     * 
     * @param  address subscriber address
     * @param  requestedAccuracy request accuracy
     * @param  success
     * @param  error
     * @return HttpResponse
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void getLocation(
        String address,
        int requestedAccuracy,
        final Callback success,
        final Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // try parsing url
        try {
            // initialize url builder
            URIBuilder builder = new URIBuilder(this.LOCATION_URL);
            
            // set access token parameter
            builder.setParameter("access_token", this.accessToken);
            // set address parameter
            builder.setParameter("address", address);
            // set requested accuracy parameter
            builder.setParameter("requestedAccuracy", Integer.toString(requestedAccuracy));
            
            
            // build the url
            String url = builder.build().toString();
            
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

        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }
    }
    
    /**
     * Get location request.
     *
     * @param  success
     * @param  error
     * @return void
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    @ReactMethod
    public void getLocation(Callback success, Callback error)
        throws ApiException, HttpRequestException, HttpResponseException {
        
        // call get location
        this.getLocation(this.address, this.requestedAccuracy, success, error);
    }
}
