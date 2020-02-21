package com.iardelian.revives_example;

import android.content.Context;
import android.content.SharedPreferences;

class SharedPrefs {

    private SharedPreferences preferences;
    
    SharedPrefs(Context context){
        preferences = context.getSharedPreferences("account-data", Context.MODE_PRIVATE);
    }

    void saveTitle(String title) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("title", title);
        editor.apply();
    }

    String getTitle() {
        return preferences.getString("title", "");
    }

    void saveText(String text) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("text", text);
        editor.apply();
    }

    String getText() {
        return preferences.getString("text", "");
    }

    void saveChannelName(String channelName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("channelName", channelName);
        editor.apply();
    }

    String getChannelName() {
        return preferences.getString("channelName", "");
    }

    void saveChannelID(String channelID) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("channelID", channelID);
        editor.apply();
    }

    String getChannelID() {
        return preferences.getString("channelID", "");
    }

    void saveNotifID(int notifID) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("notifID", notifID);
        editor.apply();
    }

    int getNotifID() {
        return preferences.getInt("notifID", -1);
    }

    void saveRequestCode(int requestCode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("requestCode", requestCode);
        editor.apply();
    }

    int getRequestCode() {
        return preferences.getInt("requestCode", -1);
    }

    void saveRestartWhenInUse(boolean restartWhenInUse) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("restartWhenInUse", restartWhenInUse);
        editor.apply();
    }

    boolean getRestartWhenInUse() {
        return preferences.getBoolean("restartWhenInUse", false);
    }

    void saveRestartNeedInternet(boolean restartNeedInternet) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("restartNeedInternet", restartNeedInternet);
        editor.apply();
    }

    boolean getRestartNeedInternet() {
        return preferences.getBoolean("restartNeedInternet", false);
    }

    void saveRestartNeedCharging(boolean restartNeedCharging) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("restartNeedCharging", restartNeedCharging);
        editor.apply();
    }

    boolean getRestartNeedCharging() {
        return preferences.getBoolean("restartNeedCharging", false);
    }

    void saveRestartDeadline(long restartDeadline) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("restartDeadline", restartDeadline);
        editor.apply();
    }

    long getRestartDeadline() {
        return preferences.getLong("restartDeadline", -1);
    }

    void saveRestartDelay(long restartDelay) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("restartDelay", restartDelay);
        editor.apply();
    }

    long getRestartDelay() {
        return preferences.getLong("restartDelay", -1);
    }

    void saveJobID(int jobID) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("jobID", jobID);
        editor.apply();
    }

    int getJobID() {
        return preferences.getInt("jobID", -1);
    }

    void saveServiceRestart(long serviceRestart) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("serviceRestart", serviceRestart);
        editor.apply();
    }

    long getServiceRestart() {
        return preferences.getLong("serviceRestart", -1);
    }

    void saveRequestPerm(boolean requestPerm) {
        if(requestPerm){
            savePermWasRequested(false);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("requestPerm", requestPerm);
        editor.apply();
    }

    boolean getRequestPerm() {
        return preferences.getBoolean("requestPerm", false);
    }

    void savePermWasRequested(boolean requestPerm) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("permWasRequested", requestPerm);
        editor.apply();
    }

    boolean getPermWasRequested() {
        return preferences.getBoolean("permWasRequested", false);
    }

}
