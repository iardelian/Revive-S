# Revive-S

[ ![Download](https://api.bintray.com/packages/iardelian/revives/revives/images/download.svg?version=1.0.3) ](https://bintray.com/iardelian/revives/revives/1.0.3/link)


Simple in use android library to provide unstoppable background service.

# Download
Add this line to your module-level build.gradle file
```
dependencies {
    ...
    implementation 'com.iardelian.revives:revives:1.0.3'
}
```

# How to use?

Create class that extends Application class and implement ReviveServiceNotifier interface:
```
public class App extends Application implements ReviveServiceNotifier 
```
and implement two methods that will be called when service starts and stops (killed by system):
```
  @Override
    public void reviveServiceStarted() {
        Toast.makeText(getApplicationContext(), getString(R.string.service_started), Toast.LENGTH_LONG).show();
    }

    @Override
    public void reviveServiceStopped() {
        Toast.makeText(getApplicationContext(), getString(R.string.service_stopped), Toast.LENGTH_LONG).show();
    }
 ```
 
 Next you need to init ReviveServiceManager, ServiceNotification and ReviveServiceController:
 
 ```
 ...
@Override
    public void onCreate() {
        super.onCreate();
        
        ReviveServiceManager reviveServiceManager = ReviveServiceManager.getInstanceForApplication(this);

        reviveServiceManager.initCallbacks(this);

        ServiceNotification serviceNotification = ServiceNotification.Builder() //init notification settings
                .setTitle()
                .setText()
                .setChannelName()
                .setChannelID()
                .setNotificationID()
                .setRequestCode() 
                .setLaunchActivity() // activity for pending intent if needed
                .build();

        ReviveServiceController restartServiceController = ReviveServiceController.Builder() // service settings
                .setJobId()
                .restartDeadline() // restart service anyway after specific time. (If all needed conditions are not met)
                .restartDelay() // if filled by system or user, restart with delay
                .restartWhenDeviceInUse() // restart service only when device in use 
                .needInternet() // restart service only when device connected to the internet
                .needCharging() // restart service only when device is charging
                .build();

        reviveServiceManager.setServiceNotification(serviceNotification);
        reviveServiceManager.setRestartServiceController(restartServiceController);
        reviveServiceManager.setStatusCheckTime(sp.getServiceRestart());

        reviveServiceManager.startService();
    }
    ...
 ```
# Example 

You can find example of use in "sample" forder.
Also you can try the compiled apk file [here](https://drive.google.com/open?id=1QUi7DEituCJ08bIpkWCmSrM6QUBUwaQI)


# Licence

```
Copyright 2020 Ivan Ardelian

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.

You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
