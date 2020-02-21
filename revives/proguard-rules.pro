# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-optimizationpasses 5
-overloadaggressively
-repackageclasses ''
-allowaccessmodification
-keepattributes SourceFile, LineNumberTable

-keep public interface com.iardelian.revives.ReviveServiceNotifier {*;}
-keep public class com.iardelian.revives.utils.AutoStartPermission {*;}
-keep public class com.iardelian.revives.manager.ReviveServiceManager {*;}

-keep, allowobfuscation, allowoptimization class com.iardelian.revives.utils.AutoStartPermission {
    public static void launchAutostartActivity(android.content.Context);
}

-keepnames class com.iardelian.revives.manager.ReviveServiceManager {*;}
-keepnames class com.iardelian.revives.manager.ReviveServiceManager$* {*;}
-keepnames class com.iardelian.revives.model.ReviveServiceController {*;}
-keepnames class com.iardelian.revives.model.ReviveServiceController$* {*;}
-keepnames class com.iardelian.revives.model.ServiceNotification {*;}
-keepnames class com.iardelian.revives.model.ServiceNotification$* {*;}

-keepclassmembers class com.iardelian.revives.manager.ReviveServiceManager$* {
    public <methods>;
    !private <fields>;
}

-keepclassmembers class com.iardelian.revives.model.ReviveServiceController$* {
    public <methods>;
    !private <fields>;
}

-keepclassmembers class com.iardelian.revives.model.ServiceNotification$* {
    public <methods>;
    !private <fields>;
    !public Notification getNotification(android.content.Context);
}
