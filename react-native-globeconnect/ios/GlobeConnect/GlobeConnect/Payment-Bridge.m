//
//  Payment-Bridge.m
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Payment, NSObject)

RCT_EXTERN_METHOD(setAccessToken:(NSString *)value)
RCT_EXTERN_METHOD(setAppId:(NSString *)value)
RCT_EXTERN_METHOD(setAppSecret:(NSString *)value)
RCT_EXTERN_METHOD(setAmount:(NSNumber *)value)
RCT_EXTERN_METHOD(setDescription:(NSString *)value)
RCT_EXTERN_METHOD(setEndUserId:(NSString *)value)
RCT_EXTERN_METHOD(setReferenceCode:(NSString *)value)
RCT_EXTERN_METHOD(setTransactionOperationStatus:(NSString *)value)
RCT_EXTERN_METHOD(getLastReferenceCode:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)
RCT_EXTERN_METHOD(sendPaymentRequest:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)

@end
