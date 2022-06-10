import 'package:flutter/services.dart';

class UbxScan {
  static const MethodChannel _channel = MethodChannel('ubx_scan');

  static Future<String?> ubxScanInit() async{
    final String code = await _channel.invokeMethod('init');
    return code;
  }
}
