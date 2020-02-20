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
package com.iardelian.revives.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.iardelian.revives.manager.ReviveServiceManager;

/**
 * Entry point for the callback from the {@link android.app.job.JobScheduler}.
 * When all conditions that were set up to restart service match - application try to do this
 *
 * @author Ivan Ardelian
 *
 */
public class RestartJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        ReviveServiceManager.getInstanceForApplication(getApplicationContext()).startService();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
