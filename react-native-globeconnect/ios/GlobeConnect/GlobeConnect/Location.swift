//
//  Location.swift
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation

@objc(Location)
class Location: NSObject {
    var locationAccessToken: String?
    var locationAddress: String?
    var locationAccuracy: Int?
  
    @objc func setAccessToken(_ value: String? = nil) -> Location {
        self.locationAccessToken = value
        return self
    }
  
    @objc func setAddress(_ value: String? = nil) -> Location {
        self.locationAddress = value
        return self
    }
  
    @objc func setRequestedAccuracy(_ value: Int = 0) -> Location {
        self.locationAccuracy = value
        return self
    }
    
    @objc(getLocation:failure:)
    func getLocation(
        _ success: RCTResponseSenderBlock? = nil,
        _ failure: RCTResponseSenderBlock? = nil
    ) -> Location {
        // set the url
        var locationUrl = "https://devapi.globelabs.com.ph/location/v1/queries/location?"
        locationUrl += "access_token="+self.locationAccessToken!
        locationUrl += "&address="+self.locationAddress!
        locationUrl += "&requestedAccuracy="+String(self.locationAccuracy!)
        
        // set the header/s
        var headers = Dictionary<String, String>()
        headers["Content-Type"] = "application/json; charset=utf-8"
        
        // perform request
        HTTPRequest(url: locationUrl, headers: headers).get(
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
