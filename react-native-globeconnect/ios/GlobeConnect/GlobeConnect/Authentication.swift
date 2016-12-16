//
//  Authentication.swift
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Openovate Labs. All rights reserved.
//

import Foundation

@objc(Authentication)
class Authentication: NSObject {
    var authAppId: String?
    var authAppSecret: String?
    
    @objc func setAppId(_ value: String? = nil) -> Authentication {
        self.authAppId = value
        return self
    }
    
    @objc func setAppSecret(_ value: String? = nil) -> Authentication {
        self.authAppSecret = value
        return self
    }
  
    @objc func getDialogUrl(
        _ success: RCTResponseSenderBlock? = nil,
        _ failure: RCTResponseSenderBlock? = nil
    ) -> Authentication {
        let dialogUrl = "https://developer.globelabs.com.ph/dialog/oauth?app_id=" + self.authAppId!
      
        success?([dialogUrl])
      
        return self
    }
  
    @objc(getAccessToken:success:failure:)
    func getAccessToken(
        _ code: String? = nil,
        _ success: RCTResponseSenderBlock? = nil,
        _ failure: RCTResponseSenderBlock? = nil
    ) -> Authentication {
        // set the url
        let accessTokenURL = "https://developer.globelabs.com.ph/oauth/access_token"
      
        // set the header/s
        var headers = Dictionary<String, String>()
        headers["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8"
        
        // set the payload
        let payload: [String : String] = [
            "app_id": self.authAppId!,
            "app_secret": self.authAppSecret!,
            "code": (code)!
        ]
        
        // build the payload
        var body: String = ""
        for (key, value) in payload {
            body = body.appending(key)
            body = body.appending("=")
            body = body.appending(value.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)!)
            body = body.appending("&")
        }
        
        // perform http request
        HTTPRequest(url: accessTokenURL, headers: headers).post(
            parameters: body,
            success: { data, _ in
                DispatchQueue.global(qos: .utility).async {
                    DispatchQueue.main.async {
                        success?([String(data: data, encoding: .utf8)!])
                    }
                }
            },
            failure: { error, data in
                failure?([String(data: data, encoding: .utf8)!])
            })
      
        return self
    }
}

