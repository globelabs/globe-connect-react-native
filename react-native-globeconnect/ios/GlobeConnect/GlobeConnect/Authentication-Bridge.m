//
//  Authentication-Bridge.m
//  GlobeConnect
//
//  Created by Rico Maglayon on 13/12/2016.
//  Copyright © 2016 Openovate Labs. All rights reserved.
//

#import "RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(Authentication, NSObject)

RCT_EXTERN_METHOD(setAppId:(NSString *)value)
RCT_EXTERN_METHOD(setAppSecret:(NSString *)value)
RCT_EXTERN_METHOD(startAuthActivity:(RCTResponseSenderBlock)success failure:(RCTResponseSenderBlock)failure)

@end
