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
package com.iardelian.revives.model;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.iardelian.revives.service.RestartJobService;

import static android.app.job.JobInfo.NETWORK_TYPE_ANY;
import static android.app.job.JobInfo.NETWORK_TYPE_NONE;


/**
 * This class provides container of data passed to the {@link android.app.job.JobScheduler} to
 * set conditions in in which service need to be restarted after it was stopped
 *
 * @author Ivan Ardelian
 *
 */
public class ReviveServiceController {

    private final static String LOG_TAG = ReviveServiceController.class.getSimpleName();

    /*
     * Default settings. Used if other were not sets up
     */

    private boolean restartWhenDeviceInUse = true;
    private boolean needInternet = false;
    private boolean needCharging = false;
    private long restartDeadline = -1;
    private long restartDelay = -1;
    private int jobId = 270114;

    private ReviveServiceController() {}

    /**
     * Get job info.
     *
     * @param context
     * @return JobInfo
     */
    public JobInfo getJobInfo(Context context){

        JobInfo.Builder restartJobInfoBuilder = new JobInfo.Builder(jobId
                ,new ComponentName(context, RestartJobService.class))
                .setPersisted(true) //restart after reboot
                .setRequiresDeviceIdle(!restartWhenDeviceInUse) //true - ensure that this job will not run if the device is in active use
                .setRequiresCharging(needCharging) //device must be charging
                .setMinimumLatency(((restartDelay == -1) ? 0 : restartDelay)) //set delay
                .setRequiredNetworkType(((needInternet) ? NETWORK_TYPE_ANY : NETWORK_TYPE_NONE)); //need internet

        if(restartDeadline == -1){
            if(restartDelay == -1 && !restartWhenDeviceInUse && !needInternet && !needCharging){
                restartJobInfoBuilder.setOverrideDeadline(0);
            }
        }else{
            restartJobInfoBuilder.setOverrideDeadline(restartDeadline); //the job will be executed anyway after
        }


        return restartJobInfoBuilder.build();
    }

    /**
     * Builder
     * @return ReviveServiceController.Builder
     */
    public static ReviveServiceController.Builder Builder() {
        return new ReviveServiceController().new Builder();
    }

    /**
     * Builder class for ReviveServiceController objects.
     * Provides a convenient way to set the various fields
     */
    public class Builder {

        private Builder() {
        }

        /**
         * Restart when device in use.
         *
         * @param restartWhenDeviceInUse the restart when device in use
         */
        public Builder restartWhenDeviceInUse(boolean restartWhenDeviceInUse) {
            ReviveServiceController.this.restartWhenDeviceInUse = restartWhenDeviceInUse;
            return this;
        }

        /**
         * Restart only when internet exist.
         *
         * @param needInternet the need internet
         */
        public Builder needInternet(boolean needInternet) {
            ReviveServiceController.this.needInternet = needInternet;
            return this;
        }

        /**
         * Restart only when device is charging.
         *
         * @param needCharging need charging
         */
        public Builder needCharging(boolean needCharging) {
            ReviveServiceController.this.needCharging = needCharging;
            return this;
        }

        /**
         *  Restart service after this time even if other requirements are not met.
         *
         * @param restartDeadline restart deadline
         */
        public Builder restartDeadline(long restartDeadline) {
            if(restartDeadline >= 0) {
                ReviveServiceController.this.restartDeadline = restartDeadline;
            }else{
                Log.w(LOG_TAG, "Restart deadline value not set. Default will be used");
            }
            return this;
        }

        /**
         * Restart service delay.
         *
         * @param restartDelay restart delay
         */
        public Builder restartDelay(long restartDelay) {
            if(restartDelay >= 0) {
                ReviveServiceController.this.restartDelay = restartDelay;
            }else{
                Log.w(LOG_TAG, "Restart delay value not set. Default will be used");
            }
            return this;
        }

        /**
         * Sets job id.
         *
         * @param jobId job id
         */
        public Builder setJobId(int jobId) {
            if(jobId > 0) {
                ReviveServiceController.this.jobId = jobId;
            }else{
                Log.w(LOG_TAG, "Restart ID value not set. Default will be used");
            }
            return this;
        }

        /**
         * Build revive service controller.
         *
         * @return ReviveServiceController object
         */
        public ReviveServiceController build() {
            return ReviveServiceController.this;
        }

    }

}
