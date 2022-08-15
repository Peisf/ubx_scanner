# ubx_scan
红外扫码插件.
## 支持PDA设备
    [^]: 优博讯
    [^]: 商米
    [^]: 小码哥

## 如何使用
### 方式一: 混入模式
```dart
class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}
// 第一步：混入UbxScanMixin
class _MyAppState extends State<MyApp> with UbxScanMixin<MyApp> {
  String _code = '';
  
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('扫码例子'),
        ),
        body: Center(
          child: Text('扫描到数据：$_code'),
        ),
      ),
    );
  }

  /// 第二步：实现ubxCodeHandle方法，当红外扫码头扫到数据执行的方法
  /// code: 表示扫码到的数据
  @override
  Future<void> ubxCodeHandle(String code) async {
    /// 编写你的逻辑
    print('扫描到数据：$code');
    setState(() {
      _code = code;
    });
  }
}
```

### 方式二：监听
```dart
    /// 在任何地方监听扫描数据
    UbxScanUtil.instance.listen((value) {
      print("扫描到数据：$value");
    });

    /// 不需要的时候记得关闭它
    UbxScanUtil.instance.cancel();
```