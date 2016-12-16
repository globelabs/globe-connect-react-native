//
//  Location-Bridge.m
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Location, NSObject)

RCT_EXTERN_METHOD(setAccessToken:(NSString *)value)
RCT_EXTERN_METHOD(setAddress:(NSString *)value)
RCT_EXTERN_METHOD(setRequestedAccuracy:(NSNumber *)value)
RCT_EXTERN_METHOD(getLocation:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock)failure)

@end
