import 'dart:async';

import 'package:flutter/material.dart';
import 'package:ubx_scan/ubx_scan_util.dart';

mixin UbxScanMixin<T extends StatefulWidget> on State<T> {
  late StreamSubscription streamSubscription;

  @override
  void initState() {
    super.initState();

    /// 监听流
    streamSubscription = UbxScanUtil.instance.start().listen((event) {
      if (event != null) {
        ubxCodeHandle(event.toString());
      }
    });
  }

  /// 红外扫描头获取到数据的回调
  Future<void> ubxCodeHandle(String code);

  @override
  void dispose() {
    super.dispose();
    streamSubscription.cancel();
  }
}
