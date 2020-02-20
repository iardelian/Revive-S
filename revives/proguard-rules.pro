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

-keep public interface com.ardelian.revives.ReviveServiceNotifier {*;}
-keep, public class com.ardelian.revives.utils.AutoStartPermission {*;}

-keep, allowobfuscation, allowoptimization class com.ardelian.revives.utils.AutoStartPermission {
    public static void launchAutostartActivity(android.content.Context);
}

-keepnames class com.ardelian.revives.manager.ReviveServiceManager {*;}
-keepnames class com.ardelian.revives.model.ReviveServiceController {*;}
-keepnames class com.ardelian.revives.model.ReviveServiceController$* {*;}
-keepnames class com.ardelian.revives.model.ServiceNotification {*;}
-keepnames class com.ardelian.revives.model.ServiceNotification$* {*;}

-keepclassmembers class com.ardelian.revives.manager.ReviveServiceManager {
    public static ReviveServiceManager getInstanceForApplication(android.content.Context);
    public void initCallbacks(com.ardelian.revives.ReviveServiceNotifier);
    public void setServiceNotification(com.ardelian.revives.model.ServiceNotification);
    public void setRestartServiceController(com.ardelian.revives.model.ReviveServiceController);
    public void setStatusCheckTime(long);
    public void startService();
}

-keepclassmembers class com.ardelian.revives.model.ReviveServiceController$* {
    public <methods>;
    !private <fields>;
}
-keepclassmembers class com.ardelian.revives.model.ServiceNotification$* {
    public <methods>;
    !private <fields>;
    !public Notification getNotification(android.content.Context);
}
