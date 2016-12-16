//
//  Sms-Bridge.m
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Sms, NSObject)

RCT_EXTERN_METHOD(setSenderAddress:(NSString *)value)
RCT_EXTERN_METHOD(setAccessToken:(NSString *)value)
RCT_EXTERN_METHOD(setClientCorrelator:(NSString *)value)
RCT_EXTERN_METHOD(setReceiverAddress:(NSString *)value)
RCT_EXTERN_METHOD(setMessage:(NSString *)value)
RCT_EXTERN_METHOD(sendMessage:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)

@end
