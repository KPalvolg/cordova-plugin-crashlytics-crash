#if (__has_include(<Fabric/Fabric.h>) || __has_include("Fabric.h")) && (__has_include(<Crashlytics/Crashlytics.h>) || __has_include("Crashlytics.h"))
#define CrashlyticsIncludeExists
#import <Fabric/Fabric.h>
#import <Crashlytics/Crashlytics.h>
#endif

#import "CrashlyticsCrash.h"
#import <Cordova/CDV.h>



@implementation CrashlyticsCrash

- (void)crash:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
#ifdef CrashlyticsIncludeExists
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"App failed successfully."];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    [CrashlyticsKit crash];
#else
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Missing core Crashlytics plugin."];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
#endif
}

@end
