//
//  GlobeConnect.swift
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

public struct GlobeConnectError: Error {
  
  public enum ErrorKind: CustomStringConvertible {
    case invalidAppOnlyBearerToken
    case responseError(code: Int)
    case invalidJSONResponse
    case badOAuthResponse
    case urlResponseError(status: Int, headers: [NSObject: AnyObject], errorCode: Int)
    case jsonParseError
    
    public var description: String {
      switch self {
      case .invalidAppOnlyBearerToken:
        return "invalidAppOnlyBearerToken"
      case .invalidJSONResponse:
        return "invalidJSONResponse"
      case .responseError(let code):
        return "responseError(code: \(code))"
      case .badOAuthResponse:
        return "badOAuthResponse"
      case .urlResponseError(let code, let headers, let errorCode):
        return "urlResponseError(status: \(code), headers: \(headers), errorCode: \(errorCode)"
      case .jsonParseError:
        return "jsonParseError"
      }
    }
    
  }
  
  public var message: String
  public var kind: ErrorKind
  
  public var localizedDescription: String {
    return "[\(kind.description)] - \(message)"
  }
  
}
