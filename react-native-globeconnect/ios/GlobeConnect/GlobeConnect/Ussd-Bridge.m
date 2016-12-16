//
//  Ussd-Bridge.m
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Ussd, NSObject)

RCT_EXTERN_METHOD(setAccessToken:(NSString *)value)
RCT_EXTERN_METHOD(setSenderAddress:(NSString *)value)
RCT_EXTERN_METHOD(setUssdMessage:(NSString *)value)
RCT_EXTERN_METHOD(setAddress:(NSString *)value)
RCT_EXTERN_METHOD(setFlash:(BOOL *)value)
RCT_EXTERN_METHOD(setSessionId:(NSString *)value)
RCT_EXTERN_METHOD(sendUssdRequest:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)
RCT_EXTERN_METHOD(replyUssdRequest:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)

@end
