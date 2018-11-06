# DcsSampleApp
DuerOS DCS Android SDK,新增匿名登录和自定义唤醒（基于dcssdk1.6.2.5版本进行的修改），仅供参考.



## 1.匿名登录

试用场景：不想用户使用App的时候登录。

```java
IOauth oauth = new SilentLoginImpl(CLIENT_ID);
```

提交节点：

https://github.com/GregGe/DcsSampleApp/commit/f1367024536c6a9412481e6fd00b46df3ba17298

## 2. 自定义唤醒

可在`app/src/main/java/com/baidu/duer/dcs/sample/sdk/wakeup/DroiWakeUp.java`中修改

```java
public static final String WAKEUP_WORD = "老板老板";
```

其他修改简略信息：

```git
#修改：
app/build.gradle
app/src/main/java/com/baidu/duer/dcs/sample/sdk/SDKBaseActivity.java

#新文件：
mvapp/CMakeLists.txt
mvapp/src/main/cpp/Interface.h
mvapp/src/main/cpp/wakeup.cpp
mvapp/src/main/java/com/baidu/duer/dcs/sample/sdk/wakeup/DroiWakeUp.java
mvapp/src/main/java/com/baidu/duer/dcs/sample/sdk/wakeup/LogUtil.java
mvapp/src/main/java/com/baidu/duer/dcs/sample/sdk/wakeup/WakeUpDecodeThread.java
mvapp/src/main/java/com/baidu/duer/dcs/sample/sdk/wakeup/WakeUpNative.java
mvapp/src/main/jniLibs/arm64-v8a/libbdEASRAndroid.so
mvapp/src/main/jniLibs/arm64-v8a/libbdEasrS1MergeNormal.so
mvapp/src/main/jniLibs/armeabi-v7a/libbdEASRAndroid.so
mvapp/src/main/jniLibs/armeabi-v7a/libbdEasrS1MergeNormal.so
mvapp/src/main/jniLibs/armeabi/libbdEASRAndroid.so
mvapp/src/main/jniLibs/armeabi/libbdEasrS1MergeNormal.so
```

提交节点：

https://github.com/GregGe/DcsSampleApp/commit/82c4bfb40a89ad2cbd69ec2ac6d78bc8ff703e63

