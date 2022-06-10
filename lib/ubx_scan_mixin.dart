import 'dart:async';

import 'package:flutter/material.dart';
import 'package:ubx_scan/ubx_scan_util.dart';

mixin UbxScanMixin<T extends StatefulWidget> on State<T> {
  late StreamSubscription streamSubscription;

  @override
  void initState() {
    super.initState();

    streamSubscription = UbxScanUtil.instance.start().listen((event) {
      if (event != null) {
        ubxCodeHandle(event.toString());
      }
    });
  }

  Future<void> ubxCodeHandle(String code);

  @override
  void dispose() {
    super.dispose();
    streamSubscription.cancel();
  }
}
