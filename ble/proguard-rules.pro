# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/yangcai/Android/Sdk/tools/proguard/proguard-android.txt
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


#指定外部模糊字典
-obfuscationdictionary dictionary.txt
#指定class模糊字典
-classobfuscationdictionary dictionary.txt
#指定package模糊字典
-packageobfuscationdictionary dictionary.txt


-keep  class **.R$**{*;}
-keep  class com.coolqi.lib.ble.BleLockManager{
    public <methods>;
}
-keep  class com.coolqi.lib.ble.L{
    public <methods>;
}
-keep  class com.coolqi.lib.ble.model.LockInfo{*;}

-keep interface com.coolqi.lib.ble.impl.CoolqiListener{*;}

-keep interface com.coolqi.lib.ble.impl.LiftOrderCallback{*;}

-keep interface com.coolqi.lib.ble.impl.OrderCallback{*;}

-keep class com.coolqi.lib.ble.Config{
    public static void init(...);
    public static void setDebugMode(...);
    public static void saveLog(...);
}