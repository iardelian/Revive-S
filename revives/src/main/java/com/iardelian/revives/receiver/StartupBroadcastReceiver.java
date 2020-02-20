/**
 * Copyright 2020 Ivan Ardelian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.iardelian.revives.receiver;

import android.annotation.SuppressLint;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.iardelian.revives.manager.RestartJobHelper;
import com.iardelian.revives.manager.ReviveServiceManager;
import com.iardelian.revives.model.ReviveServiceController;

/**
 * A class used to get system and applications broadcast messages.
 * In case it receives a message from a system (that is configured in the Android manifest file) it
 * will run {@link RestartJobHelper#setCheckingAlarm} and in case when it triggered by system - it will
 * schedule new job, with configuration from {@link ReviveServiceController#getJobInfo(Context)}
 *
 * @author Ivan Ardelian
 */
public class StartupBroadcastReceiver extends BroadcastReceiver {

    private final static String LOG_TAG = StartupBroadcastReceiver.class.getSimpleName();

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra("sAlarmIntent", false)) {
            schedule(context);
        } else {
            RestartJobHelper.setCheckingAlarm(context);
        }
    }

    private void schedule(Context context) {

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        ReviveServiceController restartServiceController = ReviveServiceManager.getInstanceForApplication(context).getRestartServiceController();

        if (restartServiceController == null) {
            Log.w(LOG_TAG, "Settings error. Have you already initialized ReviveServiceController? Default will be used");
            restartServiceController = ReviveServiceController.Builder().build();
        }

        jobScheduler.schedule(restartServiceController.getJobInfo(context));
    }
}
