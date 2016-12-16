//
//  Amax.swift
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(Amax)
class Amax: NSObject {
  var amaxAppId: String?
  var amaxAppSecret: String?
  var amaxAddress: String?
  var amaxRewardsToken: String?
  var amaxPromo: String?
  
  @objc func setAppId(_ value: String? = nil) -> Amax {
    self.amaxAppId = value
    return self
  }
  
  @objc func setAppSecret(_ value: String? = nil) -> Amax {
    self.amaxAppSecret = value
    return self
  }
  
  @objc func setAddress(_ value: String? = nil) -> Amax {
    self.amaxAddress = value
    return self
  }
  
  @objc func setRewardsToken(_ value: String? = nil) -> Amax {
    self.amaxRewardsToken = value
    return self
  }
  
  @objc func setPromo(_ value: String? = nil) -> Amax {
    self.amaxPromo = value
    return self
  }
  
  @objc(sendRewardRequest:failure:)
  func sendRewardRequest(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Amax {
    // set the url
    let sendRewardRequestURL = "https://devapi.globelabs.com.ph/rewards/v1/transactions/send"
    
    // prepare the payload
    let data: [String : [String: Any]] = [
      "outboundRewardRequest" : [
        "app_id"        : self.amaxAppId!,
        "app_secret"    : self.amaxAppSecret!,
        "rewards_token" : self.amaxRewardsToken!,
        "address"       : self.amaxAddress!,
        "promo"         : self.amaxPromo!
      ]
    ]
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    do {
      // convert it!
      let jsonData = try JSONSerialization.data(
        withJSONObject: data,
        options: JSONSerialization.WritingOptions.prettyPrinted
      )
      
      // it is now in json so we need it to be a string so we can send it
      if let jsonPayload = String(data: jsonData, encoding: String.Encoding.utf8) {
        HTTPRequest(url: sendRewardRequestURL, headers: headers)
          .post(
            parameters: jsonPayload,
            success: { data, _ in
              DispatchQueue.global(qos: .utility).async {
                DispatchQueue.main.async {
                  success?([String(data: data, encoding: .utf8)!])
                }
              }
            }, failure: { error, data in
              failure?([String(data: data, encoding: .utf8)!])
            })
      }
    } catch let error as NSError {
      // oops, error in converting it to JSON
      failure?([error.localizedDescription])
    }
    
    return self
  }
}
