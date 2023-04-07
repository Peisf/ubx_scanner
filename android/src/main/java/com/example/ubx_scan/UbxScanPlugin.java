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

  /// 商米
  private static final String SOURCE = "source_byte";
  private static final String ACTION_DATA_CODE_RECEIVED =
          "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";
  /// 优博讯
  private static final String UBX_SCAN_ACTION = "android.intent.ACTION_DECODE_DATA";
  /// 小码哥
  private static final String SEUIC_SCAN_ACTION = "com.android.scanner.service_settings";
  /// 成为
  private static final String CHENGWEI_SCAN_ACTION = "com.scanner.broadcast";
  /// 其他
  private static final String OTHER_SCAN_ACTION = "other";

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
        IntentFilter ubxFilter = new IntentFilter();
        ubxFilter.addAction(UBX_SCAN_ACTION);
        context.registerReceiver(receiver, ubxFilter);

        IntentFilter smFilter = new IntentFilter();
        smFilter.addAction(ACTION_DATA_CODE_RECEIVED);
        context.registerReceiver(receiver, smFilter);

        IntentFilter xmgFilter = new IntentFilter();
        xmgFilter.addAction(SEUIC_SCAN_ACTION);
        context.registerReceiver(receiver,xmgFilter);

        IntentFilter cwFilter = new IntentFilter();
        cwFilter.addAction(CHENGWEI_SCAN_ACTION);
        context.registerReceiver(receiver, cwFilter);
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
        String actionName = intent.getAction();
        if (UBX_SCAN_ACTION.equals(actionName)){
          String code = intent.getStringExtra("barcode_string");
          if (code != null && !code.isEmpty()){
            System.out.printf("---优博讯-----扫描结果：" + code);
            eventSink.success(code);
          }
        }
        if (ACTION_DATA_CODE_RECEIVED.equals(actionName)){
          String code = intent.getStringExtra("data");
          byte[] arr = intent.getByteArrayExtra(SOURCE);
          if (code != null && !code.isEmpty()){
            System.out.printf("---商米-----扫描结果：" + code);
            eventSink.success(code);
          }
        }
        if (SEUIC_SCAN_ACTION.equals(actionName)){
          String code = intent.getStringExtra("scannerdata");
          if (code != null && !code.isEmpty()){
            System.out.printf("---小码哥-----扫描结果：" + code);
            eventSink.success(code);
          }
        }
        if (CHENGWEI_SCAN_ACTION.equals(actionName)){
          String code = intent.getStringExtra("data");
          if (code != null && !code.isEmpty()){
            System.out.printf("---成为-----扫描结果：" + code);
            eventSink.success(code);
          }
        }
      }
    };
  }

  // 此静态方法仅用于与不使用 v2 Android 嵌入的应用保持兼容。
  @Deprecated
  @SuppressLint("Registrar")
  public static void registerWith(PluginRegistry.Registrar registrar){
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "ubx_scan");
    channel.setMethodCallHandler(new UbxScanPlugin());
  }
}







