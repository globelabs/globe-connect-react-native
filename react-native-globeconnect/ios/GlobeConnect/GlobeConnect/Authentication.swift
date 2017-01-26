//
//  Authentication.swift
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Openovate Labs. All rights reserved.
//

import Foundation
import SafariServices

@objc(Authentication)
class Authentication: NSObject, SFSafariViewControllerDelegate {
    let authenticationPageURL = "https://developer.globelabs.com.ph/dialog/oauth"
    let notificationName = Notification.Name(rawValue: "kSafariViewControllerCloseNotification")
    
    var authAppId: String?
    var authAppSecret: String?
    var bridge: RCTBridge!
    var observer: AnyObject?
    
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
    
    @objc(startLogin:failure:)
    func startLogin(
        _ success: RCTResponseSenderBlock? = nil,
        _ failure: RCTResponseSenderBlock? = nil
        ) -> Authentication {
        let pageURL = URL(string: self.authenticationPageURL + "?app_id=" + self.authAppId!)
        
        let rootViewController: UIViewController? = UIApplication.shared.delegate?.window??.rootViewController
        let safariViewController = SFSafariViewController(url: pageURL!)
        let navigationController = UINavigationController(rootViewController: safariViewController)
        
        navigationController.setNavigationBarHidden(true, animated: false)
        safariViewController.delegate = self
        
        DispatchQueue.main.async(execute: {
            rootViewController?.present(safariViewController, animated: true, completion: nil)
            
            // notifier here
            self.observer = NotificationCenter.default.addObserver(
                forName: Notification.Name(rawValue: "globeLabsConnectedRedirect"),
                object: nil,
                queue: OperationQueue.main,
                using : { notification in
                    let extractedURL = notification.object as! URL
                    let responseURL = String(describing: extractedURL)
                    
                    // remove observer
                    if (self.observer != nil) {
                        NotificationCenter.default.removeObserver(self.observer!)
                    }
                    
                    // extract the code from the url
                    let code = self.extractCode(responseURL)
                    
                    if (code == nil) {
                        // close Safari
                        safariViewController.dismiss(animated: true, completion: nil)
                        
                        failure?(["Something went wrong while processing your request." as! Error])
                    }
                    
                    // request for the access token or something
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
                                    safariViewController.dismiss(animated: true, completion: nil)
                                    
                                    success?([String(data: data, encoding: .utf8)!])
                                }
                            }
                        },
                        failure: { error, data in
                            safariViewController.dismiss(animated: true, completion: nil)
                            
                            failure?([String(data: data, encoding: .utf8)!])
                        })
                }
            )
        });
        
        return self
    }
    
    func safariViewControllerDidFinish(
        _ controller: SFSafariViewController
        ) {
        controller.dismiss(animated: true, completion: nil)
    }
    
    internal func extractCode(_ urlString: String) -> String? {
        var code: String? = nil
        let url = URL(string: urlString)
        let urlQuery = (url?.query != nil) ? url?.query : urlString
        let components = urlQuery?.components(separatedBy: "&")
        for comp in components! {
            if (comp.range(of: "code=") != nil) {
                code = comp.components(separatedBy: "=").last
            }
        }
        return code
    }
}

