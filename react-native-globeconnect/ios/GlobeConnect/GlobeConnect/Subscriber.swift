//
//  Subscriber.swift
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(Subscriber)
class Subscriber: NSObject {
  var subscriberAccessToken: String?
  var subscriberAddress: String?
  
  @objc func setAccessToken(_ value: String? = nil) -> Subscriber {
    self.subscriberAccessToken = value
    return self
  }
  
  @objc func setAddress(_ value: String? = nil) -> Subscriber {
    self.subscriberAddress = value
    return self
  }
  
  @objc(getSubscriberBalance:failure:)
  func getSubscriberBalance(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Subscriber {
    // set the url
    var getSubscriberBalanceURL = "https://devapi.globelabs.com.ph/location/v1/queries/balance"
    getSubscriberBalanceURL += "?access_token="+self.subscriberAccessToken!+"&address="+self.subscriberAddress!
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // perform request
    HTTPRequest(url: getSubscriberBalanceURL, headers: headers).get(
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
  
  @objc(getSubscriberReloadAmount:failure:)
  func getSubscriberReloadAmount(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Subscriber {
    // set the url
    var getSubscriberReloadAmountURL = "https://devapi.globelabs.com.ph/location/v1/queries/reload_amount"
    getSubscriberReloadAmountURL += "?access_token="+self.subscriberAccessToken!+"&address="+self.subscriberAddress!
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // perform request
    HTTPRequest(url: getSubscriberReloadAmountURL, headers: headers).get(
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
