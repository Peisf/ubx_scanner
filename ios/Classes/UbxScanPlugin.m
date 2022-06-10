#import "UbxScanPlugin.h"
#if __has_include(<ubx_scan/ubx_scan-Swift.h>)
#import <ubx_scan/ubx_scan-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "ubx_scan-Swift.h"
#endif

@implementation UbxScanPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftUbxScanPlugin registerWithRegistrar:registrar];
}
@end
