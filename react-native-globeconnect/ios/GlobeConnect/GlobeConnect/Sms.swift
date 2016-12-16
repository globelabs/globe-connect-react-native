//
//  Sms.swift
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(Sms)
class Sms: NSObject {
  var smsSenderAddress: String?
  var smsAccessToken: String?
  var smsClientCorrelator: String?
  var smsReceiverAddress: String?
  var smsMessage: String?
  
  @objc func setSenderAddress(_ value: String? = nil) -> Sms {
    self.smsSenderAddress = value
    return self
  }
  
  @objc func setAccessToken(_ value: String? = nil) -> Sms {
    self.smsAccessToken = value
    return self
  }
  
  @objc func setClientCorrelator(_ value: String? = nil) -> Sms {
    self.smsClientCorrelator = value
    return self
  }

  
  @objc func setReceiverAddress(_ value: String? = nil) -> Sms {
    self.smsReceiverAddress = value
    return self
  }

  @objc func setMessage(_ value: String? = nil) -> Sms {
    self.smsMessage = value
    return self
  }

  @objc(sendMessage:failure:)
  func sendMessage(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Sms {
    // set the url
    let sendMessageUrl = "https://devapi.globelabs.com.ph/smsmessaging/v1/outbound/"+self.smsSenderAddress!+"/requests?access_token="+self.smsAccessToken!
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // fix the address
    let address = "tel:" + self.smsReceiverAddress!
    
    // fix the sender address
    let sender = "tel:" + self.smsSenderAddress!
    
    // prepare the payload
    let data: [String : [String: Any]] = [
      "outboundSMSMessageRequest" : [
        "senderAddress"           : sender,
        "address"                 : [address],
        "outboundSMSTextMessage"  : [
          "message" : self.smsMessage!
        ]
      ]
    ]
    
    // we need to convert first the payload to JSON
    do {
      // convert it!
      let jsonData = try JSONSerialization.data(
        withJSONObject: data,
        options: JSONSerialization.WritingOptions.prettyPrinted
      )
      
      // it is now in json so we need it to be a string so we can send it
      if let jsonPayload = String(data: jsonData, encoding: String.Encoding.utf8) {
        HTTPRequest(url: sendMessageUrl, headers: headers)
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
      failure?([error.localizedDescription])
    }
    
    return self
  }
}
