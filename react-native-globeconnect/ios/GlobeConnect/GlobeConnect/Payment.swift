//
//  Payment.swift
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(Payment)
class Payment: NSObject {
  var paymentAccessToken: String?
  var paymentAppId: String?
  var paymentAppSecret: String?
  var paymentAmount: Float?
  var paymentDescription: String?
  var paymentEndUserId: String?
  var paymentReferenceCode: String?
  var paymentTransactionOperationStatus: String?
  
  @objc func setAccessToken(_ value: String? = nil) -> Payment {
    self.paymentAccessToken = value
    return self
  }
  
  @objc func setAppId(_ value: String? = nil) -> Payment {
    self.paymentAppId = value
    return self
  }
  
  @objc func setAppSecret(_ value: String? = nil) -> Payment {
    self.paymentAppSecret = value
    return self
  }
  
  @objc func setAmount(_ value: Float = 0.00) -> Payment {
    self.paymentAmount = value
    return self
  }
  
  @objc func setDescription(_ value: String? = nil) -> Payment {
    self.paymentDescription = value
    return self
  }
  
  @objc func setEndUserId(_ value: String? = nil) -> Payment {
    self.paymentEndUserId = value
    return self
  }
  
  @objc func setReferenceCode(_ value: String? = nil) -> Payment {
    self.paymentReferenceCode = value
    return self
  }
  
  @objc func setTransactionOperationStatus(_ value: String? = nil) -> Payment {
    self.paymentTransactionOperationStatus = value
    return self
  }
  
  @objc(getLastReferenceCode:failure:)
  func getLastReferenceCode(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Payment {
    // set the url
    let getLastReferenceCodeURL = "https://devapi.globelabs.com.ph/payment/v1/transactions/getLastRefCode?app_id="+self.paymentAppId!+"&app_secret="+self.paymentAppSecret!
    
    // set the header/s
    var headers = Dictionary<String, String>()
    headers["Content-Type"] = "application/json; charset=utf-8"
    
    // perform request
    HTTPRequest(url: getLastReferenceCodeURL, headers: headers).get(
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
  
  @objc(sendPaymentRequest:failure:)
  func sendPaymentRequest(
    _ success: RCTResponseSenderBlock? = nil,
    _ failure: RCTResponseSenderBlock? = nil
  ) -> Payment {
    // set the url
    let sendPaymentURL = "https://devapi.globelabs.com.ph/payment/v1/transactions/amount?access_token="+self.paymentAccessToken!
    
    // set the payload
    let data: [String: Any] = [
      "amount"                      : String(format: "%.2f", self.paymentAmount!),
      "description"                 : self.paymentDescription!,
      "endUserId"                   : self.paymentEndUserId!,
      "referenceCode"               : self.paymentReferenceCode!,
      "transactionOperationStatus"  : self.paymentTransactionOperationStatus!
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
        HTTPRequest(url: sendPaymentURL, headers: headers)
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
