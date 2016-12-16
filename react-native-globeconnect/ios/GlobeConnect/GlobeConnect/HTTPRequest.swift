//
//  HTTPRequest.swift
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Openovate Labs. All rights reserved.
//

import Foundation

public enum HTTPMethodType: String {
    case POST
    case GET
}

public class HTTPRequest  {
    // Types
    public typealias SuccessHandler = (Data, URLResponse) -> Void
    public typealias ErrorHandler = (Error, Data) -> Void
    
    let url: String
    let headers: Dictionary<String, String>
    
    public init(url: String, headers: Dictionary<String, String>? = [:]) {
        self.url = url
        self.headers = headers!
    }
    
    public func get(
        parameters: String? = nil,
        success: @escaping SuccessHandler,
        failure: @escaping ErrorHandler
        ) -> Void {
        // build out the request
        var request = URLRequest(url: URL(string: self.url.addingPercentEncoding( withAllowedCharacters: .urlQueryAllowed)!)!)
        
        // set the request method
        request.httpMethod = "GET"
        
        // set for the headers
        for (key, value) in self.headers {
            request.setValue(value, forHTTPHeaderField: key)
        }
        
        // set payload
        if parameters != nil {
            request.httpBody = parameters?.data(using: .utf8)
        }
        
        // send the request
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if error != nil {
                failure(error!, data!)
                return
            }
            
            success(data!, response!)
        }
        
        task.resume();
    }
    
    public func post(
        parameters: String,
        success: @escaping SuccessHandler,
        failure: @escaping ErrorHandler
        ) -> Void {
        // build out the request
        var request = URLRequest(url: URL(string: self.url.addingPercentEncoding( withAllowedCharacters: .urlQueryAllowed)!)!)
        
        // set the request method
        request.httpMethod = "POST"
        
        // set for the headers
        for (key, value) in self.headers {
            request.setValue(value, forHTTPHeaderField: key)
        }
        
        // set payload
        request.httpBody = parameters.data(using: .utf8)
        
        // send the request
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if error != nil {
                failure(error!, data!)
                return
            }
            
            success(data!, response!)
        }
        
        task.resume();
    }
}

