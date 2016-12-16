//
//  BinarySms.swift
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(BinarySms)
class BinarySms: NSObject {
  var binarySmsSenderAddress: String?
  var binarySmsAccessToken: String?
  var binarySmsUserDataHeader: String?
  var binarySmsDataCodingScheme : Int?
  var binarySmsReceiverAddress : String?
  var binarySmsBinaryMessage : String?
  
  @objc func setSenderAddress(_ value: String? = nil) -> BinarySms {
    self.binarySmsSenderAddress = value
    return self
  }
  
  @objc func setAccessToken(_ value: String? = nil) -> BinarySms {
    self.binarySmsAccessToken = value
    return self
  }
  
  @objc func setUserDataHeader(_ value: String? = nil) -> BinarySms {
    self.binarySmsUserDataHeader = value
    return self
  }
  
  @objc func setDataCodingScheme(_ value: Int = 1) -> BinarySms {
    self.binarySmsDataCodingScheme = value
    return self
  }
  
  @objc func setReceiverAddress(_ value: String? = nil) -> BinarySms {
    self.binarySmsReceiverAddress = value
    return self
  }
  
  @objc func setBinaryMessage(_ value: String? = nil) -> BinarySms {
    self.binarySmsBinaryMessage = value
    return self
  }
  
  @objc(sendBinaryMessage:failure:)
  func sendBinaryMessage(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
    ) -> BinarySms {
    // set the url
    let binarySmsURL = "https://devapi.globelabs.com.ph/binarymessaging/v1/outbound/"+self.binarySmsSenderAddress!+"/requests?access_token="+self.binarySmsAccessToken!
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // prepare the payload
    let data: [String : [String: Any]] = [
      "outboundBinaryMessageRequest" : [
        "userDataHeader"        : self.binarySmsUserDataHeader!,
        "dataCodingScheme"      : self.binarySmsDataCodingScheme!,
        "address"               : self.binarySmsReceiverAddress!,
        "senderAddress"         : self.binarySmsSenderAddress!,
        "access_token"          : self.binarySmsAccessToken!,
        "outboundBinaryMessage" : [
          "message" : self.binarySmsBinaryMessage!
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
        HTTPRequest(url: binarySmsURL, headers: headers)
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
