import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class UbxScanUtil{
  UbxScanUtil._();

  factory UbxScanUtil() => UbxScanUtil._();

  static UbxScanUtil get instance => UbxScanUtil._();

  EventChannel eventChannel = const EventChannel('hm_flutter');

  StreamSubscription? streamSubscription;
  Stream? stream;

  Stream start(){
    stream ??= eventChannel.receiveBroadcastStream();
    return stream!;
  }

  void listen(ValueChanged<String> changed){
    streamSubscription = start().listen((event) {
      if(event != null){
        changed.call(event.toString());
      }
    });
  }

  void cancel(){
    streamSubscription?.cancel();
  }

}
