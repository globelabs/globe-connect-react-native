//
//  Subscriber-Bridge.m
//  GlobeConnectSample
//
//  Created by Rico Maglayon on 14/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Subscriber, NSObject)

RCT_EXTERN_METHOD(setAccessToken:(NSString *)value)
RCT_EXTERN_METHOD(setAddress:(NSString *)value)
RCT_EXTERN_METHOD(getSubscriberBalance:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)
RCT_EXTERN_METHOD(getSubscriberReloadAmount:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock *)failure)

@end
