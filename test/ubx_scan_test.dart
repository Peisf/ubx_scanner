import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:ubx_scan/ubx_scan.dart';

void main() {
  const MethodChannel channel = MethodChannel('ubx_scan');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

}
