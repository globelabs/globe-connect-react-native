//
//  BinarySms-Bridge.m
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(BinarySms, NSObject)

RCT_EXTERN_METHOD(setSenderAddress:(NSString *)value)
RCT_EXTERN_METHOD(setAccessToken:(NSString *)value)
RCT_EXTERN_METHOD(setUserDataHeader:(NSString *)value)
RCT_EXTERN_METHOD(setDataCodingScheme:(NSNumber *)value)
RCT_EXTERN_METHOD(setReceiverAddress:(NSString *)value)
RCT_EXTERN_METHOD(setBinaryMessage:(NSString *)value)
RCT_EXTERN_METHOD(sendBinaryMessage:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)

@end
