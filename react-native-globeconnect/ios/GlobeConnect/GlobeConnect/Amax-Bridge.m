//
//  Amax-Bridge.m
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Amax, NSObject)

RCT_EXTERN_METHOD(setAppId:(NSString *)value)
RCT_EXTERN_METHOD(setAppSecret:(NSString *)value)
RCT_EXTERN_METHOD(setAddress:(NSString *)value)
RCT_EXTERN_METHOD(setRewardsToken:(NSString *)value)
RCT_EXTERN_METHOD(setPromo:(NSString *)value)
RCT_EXTERN_METHOD(sendRewardRequest:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)

@end
