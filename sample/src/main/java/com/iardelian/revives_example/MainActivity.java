package com.iardelian.revives_example;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.iardelian.revives.utils.AutoStartPermission;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_et) EditText title_et;
    @BindView(R.id.text_et) EditText text_et;
    @BindView(R.id.channel_name_et) EditText channel_name_et;
    @BindView(R.id.channel_id_et) EditText channel_id_et;
    @BindView(R.id.notification_id_et) EditText notification_id_et;
    @BindView(R.id.request_code_et) EditText request_code_et;
    @BindView(R.id.restartInUseCheck) CheckBox restartInUseCheck;
    @BindView(R.id.needInternetCheck) CheckBox needInternetCheck;
    @BindView(R.id.needChargingCheck) CheckBox needChargingCheck;
    @BindView(R.id.restart_deadline) EditText restart_deadline;
    @BindView(R.id.restart_delay) EditText restart_delay;
    @BindView(R.id.job_id) EditText job_id;
    @BindView(R.id.alarm_restart_time) EditText alarm_restart_time;
    @BindView(R.id.request_autostart_perm) CheckBox request_autostart_perm;

    private AlertDialog restartAlert;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewData();
    }

    private void initViewData() {
        sharedPrefs = new SharedPrefs(getApplication());
        title_et.setText(sharedPrefs.getTitle());
        text_et.setText(sharedPrefs.getText());
        channel_name_et.setText(sharedPrefs.getChannelName());
        channel_id_et.setText(sharedPrefs.getChannelID());

        if (sharedPrefs.getNotifID() != -1) {
            notification_id_et.setText(String.valueOf(sharedPrefs.getNotifID()));
        }
        if (sharedPrefs.getRequestCode() != -1) {
            request_code_et.setText(String.valueOf(sharedPrefs.getRequestCode()));
        }

        restartInUseCheck.setChecked(sharedPrefs.getRestartWhenInUse());
        needInternetCheck.setChecked(sharedPrefs.getRestartNeedInternet());
        needChargingCheck.setChecked(sharedPrefs.getRestartNeedCharging());

        if(sharedPrefs.getRestartDeadline() != -1){
            restart_deadline.setText(String.valueOf(sharedPrefs.getRestartDeadline()/1000));
        }

        if(sharedPrefs.getRestartDelay() != -1){
            restart_delay.setText(String.valueOf(sharedPrefs.getRestartDelay()/1000));
        }

        if(sharedPrefs.getJobID() != -1){
            job_id.setText(String.valueOf(sharedPrefs.getJobID()));
        }

        if(sharedPrefs.getServiceRestart() != -1){
            alarm_restart_time.setText(String.valueOf(sharedPrefs.getServiceRestart()/1000));
        }

        request_autostart_perm.setChecked(sharedPrefs.getRequestPerm());
    }

    @OnClick(R.id.save_btn)
    public void saveData() {
        try {
            sharedPrefs.saveTitle( title_et.getText().toString());
            sharedPrefs.saveText( text_et.getText().toString());
            sharedPrefs.saveChannelName( channel_name_et.getText().toString().replaceAll("\\s+",""));
            sharedPrefs.saveChannelID( channel_id_et.getText().toString().replaceAll("\\s+",""));
            if(!TextUtils.isEmpty(notification_id_et.getText().toString())) {
                sharedPrefs.saveNotifID( Integer.valueOf(notification_id_et.getText().toString().replaceAll("\\s+", "")));
            }else{
                sharedPrefs.saveNotifID(-1);
            }
            if(!TextUtils.isEmpty(request_code_et.getText().toString())) {
                sharedPrefs.saveRequestCode( Integer.valueOf(request_code_et.getText().toString().replaceAll("\\s+", "")));
            }else{
                sharedPrefs.saveRequestCode(-1);
            }
            sharedPrefs.saveRestartWhenInUse( restartInUseCheck.isChecked());
            sharedPrefs.saveRestartNeedInternet( needInternetCheck.isChecked());
            sharedPrefs.saveRestartNeedCharging( needChargingCheck.isChecked());

            if(!TextUtils.isEmpty(restart_deadline.getText().toString())) {
                sharedPrefs.saveRestartDeadline( Long.parseLong(restart_deadline.getText().toString().replaceAll("\\s+", "")) * 1000);
            }else{
                sharedPrefs.saveRestartDeadline(-1);
            }
            if(!TextUtils.isEmpty(restart_delay.getText().toString())) {
                sharedPrefs.saveRestartDelay( Long.parseLong(restart_delay.getText().toString().replaceAll("\\s+", "")) * 1000);
            }else{
                sharedPrefs.saveRestartDelay( -1);
            }
            if(!TextUtils.isEmpty(job_id.getText().toString())) {
                sharedPrefs.saveJobID( Integer.valueOf(job_id.getText().toString().replaceAll("\\s+", "")));
            }
            if(!TextUtils.isEmpty(alarm_restart_time.getText().toString())) {
                sharedPrefs.saveServiceRestart( Long.parseLong(alarm_restart_time.getText().toString().replaceAll("\\s+", "")) * 1000);
            }else{
                sharedPrefs.saveServiceRestart(-1);
            }
            sharedPrefs.saveRequestPerm( request_autostart_perm.isChecked());
            showExitDialog();
        } catch (Exception e) {
            Toast.makeText(getApplication(), getString(R.string.input_error), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void showExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.need_to_restart))
                .setCancelable(false)
                .setMessage(getString(R.string.remove_from_recent));
        restartAlert = builder.create();
        restartAlert.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(sharedPrefs.getRequestPerm() && !sharedPrefs.getPermWasRequested()) {
            AutoStartPermission.launchAutostartActivity(getApplication());
            sharedPrefs.savePermWasRequested(true);
            sharedPrefs.saveRequestPerm(false);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(restartAlert != null && restartAlert.isShowing()) {
            restartAlert.dismiss();
        }
    }
}
