####################################
#                                  #
#          常用的默认配置命令         #
#                                  #
####################################

#-include {fileame}                                                 从给定的文件中读取配置参数
#-libraryjars libs/xxxx.jar                                         指定库jar包
#-keep public class * extends android.app.Activity                  保留类不被删除
#-keep class className$InnerName{                                   保留内部类的属性和方法
#    public <fields>;
#    public <methods>;
#}
#-dontshrink                                                        不压缩输入的类文件
#-dontoptimize                                                      不优化输入的类文件
#-keepattributes *Annotation*                                       保留Annotation
#-dontwarn xxx.xxx.**                                               不检查引用


####################################
#                                  #
#          Android 默认混淆         #
#                                  #
####################################

-dontusemixedcaseclassnames                                                         # 是否使用大小写混合
-dontskipnonpubliclibraryclass                                                      # 是否混淆第三方jar，不跳过非公共的库的类成员
-verbose                                                                            # 混淆时是否记录日志
#-dontoptimize
-dontpreverify                                                                      # 混淆时是否做预校验

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

## 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
## 保留自定义View,如"属性动画"中的set/get方法
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
## 保留Activity中参数是View的方法，如XML中配置android:onClick=”buttonClick”属性，Activity中调用的buttonClick(View view)方法
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
## 保持枚举 Enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
## 保持 Parcelable 不被混淆
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
## 不混淆R文件
-keepclassmembers class **.R$* {
    public static <fields>;
}
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}


####################################
#                                  #
#              常用混淆             #
#                                  #
####################################

-optimizationpasses 5                                                               # 指定代码的压缩级别
-ignorewarnings                                                                     # 忽略警告，避免打包时某些警告出现
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*            # 混淆时所采用的算法
-useuniqueclassmembernames                                                          # 把混淆类中的方法名也混淆了
-allowaccessmodification                                                            # 优化时允许访问并修改有修饰符的类和类的成员
-renamesourcefileattribute SourceFile                                               # 将文件来源重命名为“SourceFile”字符串
-keepattributes SourceFile,LineNumberTable                                          # 保留行号

# 保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# support v4
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.widget
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# appcompat v7
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 实体类不被混淆
-keepclassmembernames class com.vma.speechrobot.bean.** { *; }
-keepclassmembernames class com.example.common.net.response.** { *; }
-keepclassmembernames class com.example.common.net.request.** { *; }

# WebView 控件 Js 交互混淆配置
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# 移除 Release 模式下不需要的代码
-assumenosideeffects class com.example.common.utils.L{
    public static *** d(...);
}
#-assumenosideeffects class com.example.common.net.HttpClientWrapper{
#    private static *** addHttpLoggingInterceptor(...);
#    private static *** addStethoInterceptor(...);
#}
#-assumenosideeffects class com.example.common.CommonBaseApplicationtion{
#    private *** initStetho(...);
#}

####################################
#                                  #
#             第三方引入            #
#                                  #
####################################


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}


# ButterKnife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }


# Gson
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


# Retrofit2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


# OkHttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**


# RxJava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}


# ButterKnife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
#-keep class **$$ViewInjector { *; }  #butterknife6.x-生成的类
#-keep class **$$ViewBinder { *; }  #butterknife7.x生成的类
-keep class **_ViewBinding { *; } #butterknife8.x生成的类


# EventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


# 七牛
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings


# Stetho
-keep class com.facebook.stetho.** { *; }
-dontwarn com.facebook.stetho.**


# 支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}


# 微信
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}


# ShareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*


# JPush
#-dontoptimize
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}


# 腾讯云通讯
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**
-keep class tencent.**{*;}
-dontwarn tencent.**
-keep class qalsdk.**{*;}
-dontwarn qalsdk.**


# 腾讯Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# Echarts
-keepclasseswithmembernames class com.github.abel533.echarts.**{*;}

-dontwarn com.lsh.packagelibrary.**
-keepclasseswithmembernames class com.lsh.packagelibrary.**{*;}
