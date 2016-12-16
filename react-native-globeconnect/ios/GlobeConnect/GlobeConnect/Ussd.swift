//
//  Ussd.swift
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(Ussd)
class Ussd: NSObject {
  var ussdAccessToken: String?
  var ussdSenderAddress: String?
  var ussdUssdMessage: String?
  var ussdAddress: String?
  var ussdFlash: Bool?
  var ussdSessionId: String?
  
  @objc func setAccessToken(_ value: String? = nil) -> Ussd {
    self.ussdAccessToken = value
    return self
  }
  
  @objc func setSenderAddress(_ value: String? = nil) -> Ussd {
    self.ussdSenderAddress = value
    return self
  }
  
  @objc func setUssdMessage(_ value: String? = nil) -> Ussd {
    self.ussdUssdMessage = value
    return self
  }
  
  @objc func setAddress(_ value: String? = nil) -> Ussd {
    self.ussdAddress = value
    return self
  }
  
  @objc func setFlash(_ value: Bool = false) -> Ussd {
    self.ussdFlash = value
    return self
  }
  
  @objc func setSessionId(_ value: String? = nil) -> Ussd {
    self.ussdSessionId = value
    return self
  }
  
  @objc(sendUssdRequest:failure:)
  func sendUssdRequest(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Ussd {
    // set the request url
    var sendUssdRequestURL = "https://devapi.globelabs.com.ph/ussd/v1/outbound/"
    sendUssdRequestURL += self.ussdSenderAddress!+"/send/requests?access_token="+self.ussdAccessToken!
    
    // prepare the payload
    let data: [String : [String: Any]] = [
      "outboundUSSDMessageRequest" : [
        "outboundUSSDMessage" : [
          "message": self.ussdUssdMessage!
        ],
        "address"       : self.ussdAddress!,
        "senderAddress" : self.ussdSenderAddress!,
        "flash"         : self.ussdFlash!
      ]
    ]
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // we need to convert first the payload to JSON
    do {
      // convert it!
      let jsonData = try JSONSerialization.data(
        withJSONObject: data,
        options: JSONSerialization.WritingOptions.prettyPrinted
      )
      
      // it is now in json so we need it to be a string so we can send it
      if let jsonPayload = String(data: jsonData, encoding: String.Encoding.utf8) {
        HTTPRequest(url: sendUssdRequestURL, headers: headers)
          .post(
            parameters: jsonPayload,
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
      }
    } catch let error as NSError {
      // oops, error in converting it to JSON
      failure?([error.localizedDescription])
    }
    
    return self
  }
  
  @objc(replyUssdRequest:failure:)
  func replyUssdRequest(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Ussd {
    // set the request url
    var replyUssdRequestURL = "https://devapi.globelabs.com.ph/ussd/v1/outbound/"
    replyUssdRequestURL += self.ussdSenderAddress!+"/reply/requests?access_token="+self.ussdAccessToken!
    
    // prepare the payload
    let data: [String : [String: Any]] = [
      "outboundUSSDMessageRequest" : [
        "outboundUSSDMessage" : [
          "message": self.ussdUssdMessage!
        ],
        "address"       : self.ussdAddress!,
        "senderAddress" : self.ussdSenderAddress!,
        "sessionID"     : self.ussdSessionId!,
        "flash"         : self.ussdFlash!
      ]
    ]
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // we need to convert first the payload to JSON
    do {
      // convert it!
      let jsonData = try JSONSerialization.data(
        withJSONObject: data,
        options: JSONSerialization.WritingOptions.prettyPrinted
      )
      
      // it is now in json so we need it to be a string so we can send it
      if let jsonPayload = String(data: jsonData, encoding: String.Encoding.utf8) {
        HTTPRequest(url: replyUssdRequestURL, headers: headers)
          .post(
            parameters: jsonPayload,
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
      }
    } catch let error as NSError {
      // oops, error in converting it to JSON
      failure?([error.localizedDescription])
    }
    
    return self
  }
}
