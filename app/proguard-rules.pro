# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/guxiuzhong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html
# Add any project specific keep options here:
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################
#忽略警告
-ignorewarnings
#webview
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
#保持Javascript接口
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
   public void openFileChooser(...);
}
#-----自定义控件-----#
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#-----自定义控件-----#
#序列化
-keep class * implements android.os.Parcelable {
    public *;
}
-keep class * implements java.io.Serializable {
    public *;
}
#保留枚举类不被混淆
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
#避免混淆Annotation，内部类，范型，匿名类
-keepattributes Exceptions, InnerClasses, Signature, Deprecated,SourceFile, LineNumberTable, *Annotation*, EnclosingMethod
#重命名抛出异常时的文件名称
-renamesourcefileattribute SourceFile
#程序Crash时，会在stacktrace中包含行号信息，便于调试
-keepattributes SourceFile,LineNumberTable
#保持本地native方法不被混淆
-keepclassmembernames class *{
        native <methods>;
}
#处理support包
-dontnote android.support.**
-dontwarn android.support.**
-dontwarn org.apache.commons.**
-keep class org.apache.commons.** { *;}
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.widget.RecyclerView
-keep public class * extends android.app.Fragment
-keep public class * extends android.os.IInterface
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class com.android.vending.licensing.ILicensingService
#保留Keep注解的类名和方法
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}
#############################################
#
# dcs-及用到的第三方jar包
#
#############################################

# okhttp-3.8.1.jar  okio-1.14.0.jar
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-keep class okio.** {*;}
-keep class com.baidu.dcs.okhttp3.** {*;}
-keep class javax.annotation.** {*;}

#common-fileupload-1.3.2
-dontwarn javax.servlet.**
-dontwarn javax.portlet.**
-keep class javax.servlet.** {*;}
-keep class javax.portlet.** {*;}
#jlayer-1.0.1.jar
-dontwarn javazoom.jl.**
-dontwarn javax.sound.**
-keep class javax.sound.** {*;}
-keep class javazoom.jl.** {*;}
#jackson
-dontwarn org.codehaus.jackson.**
-keep class org.codehaus.jackson.** {*;}
#fasterxml
-dontwarn com.fasterxml.jackson.databind.**
-keep class com.fasterxml.jackson.databind.** {*;}
#commons
-keep class org.apache.commons.**{*;}


#turbonet.jar
-dontwarn com.baidu.turbonet.**
-keep class com.baidu.turbonet.** {*;}
#crab
-keep public class com.baidu.crabsdk.**
-keepclassmembers public class com.baidu.crabsdk.*{
    *;
}
#localtts
-keep class com.baidu.tts.**{*;}
-keep class com.baidu.speechsynthesizer.**{*;}
#speechv3
-keep class com.baidu.speech.**{*;}
#oauth
-keep class com.baidu.cloudsdk.common.http.**{*;}
-keep class com.baidu.android.common.**{*;}
-keep class com.baidu.sapi2.**{*;}
-keep class com.baidu.pass.**{*;}
-keep class com.baidu.sofire.**{*;}
-keep class com.baidu.passport.sapi2.**{*;}

#dcs-sdk
-keep class ai.kitt.snowboy.** {*;}
-keep class com.baidu.duer.** {*;}
-dontwarn com.baidu.duer.**
-keep class com.baidu.dcs.**{*;}

