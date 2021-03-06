package com.example.ubx_scan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** UbxScanPlugin */
public class UbxScanPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private EventChannel eventChannel;
  private Context context;
  private static final String CHARGING_CHANNEL = "hm_flutter";

  private static final String DATA = "barcode_string";
  private static final String UBX_SCAN_ACTION = "android.intent.ACTION_DECODE_DATA";

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "ubx_scan");
    channel.setMethodCallHandler(this);

    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),CHARGING_CHANNEL);
    eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
      private BroadcastReceiver receiver;

      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        receiver = broadcastReceiver(events);
        IntentFilter filter = new IntentFilter();
        filter.addAction(UBX_SCAN_ACTION);
        context.registerReceiver(receiver, filter);
      }

      @Override
      public void onCancel(Object arguments) {
        context.unregisterReceiver(receiver);
        receiver = null;

      }
    });

    context = flutterPluginBinding.getApplicationContext();

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
  private BroadcastReceiver broadcastReceiver(final EventChannel.EventSink eventSink){
    return new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String code = intent.getStringExtra(DATA);
        if (code != null && !code.isEmpty()){
          System.out.printf("???????????????????????????" + code);
          eventSink.success(code);
        }
      }
    };
  }

  // ???????????????????????????????????? v2 Android ??????????????????????????????
  @Deprecated
  @SuppressLint("Registrar")
  public static void registerWith(PluginRegistry.Registrar registrar){
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "ubx_scan");
    channel.setMethodCallHandler(new UbxScanPlugin());
  }
}







