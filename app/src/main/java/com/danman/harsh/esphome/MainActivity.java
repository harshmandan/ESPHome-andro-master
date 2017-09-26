package com.danman.harsh.esphome;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import Util.TextFilter;
import data.Settings;
import func.HTTPClient;
import func.JSONParser;
import func.SendHTTPDataPOST;

public class MainActivity extends AppCompatActivity {

    private TextView powerStatus;
    private TextView bgText;
    private TextView timerStatus;
    private TextView timercount;
    private TextView schdStatus;
    private TextView schdOn;
    private TextView schdOff;
    private ImageView background;
    private TextView schd1;
    private TextView schd2;
    private Switch power;
    private Switch timer;
    private Switch scheduler;
    private Button apply;
    private EditText timer_text;
    private EditText onh;
    private EditText onm;
    private EditText offh;
    private EditText offm;
    private TextView timer_static;
    private TextView sleft;
    private TextView mleft;
    private TextView hleft;
    private TextView sep1;
    private TextView sep2;
    private CheckBox schddays;
    private CheckBox monday;
    private CheckBox tueday;
    private CheckBox wedday;
    private CheckBox thuday;
    private CheckBox friday;
    private CheckBox satday;
    private CheckBox sunday;

    Settings settings = new Settings();
    String temp;
    public static final String MyPREFERENCES = "ESPPref";
    long sec = 0;
    long lastd;
    long lefttime =0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bgText = (TextView) findViewById(R.id.bgText);
        powerStatus = (TextView) findViewById(R.id.status);
        timerStatus = (TextView) findViewById(R.id.timer_status);
        schdStatus = (TextView) findViewById(R.id.scheduler_status);
        schdOn = (TextView) findViewById(R.id.scheduler_st_ontime);
        schdOff = (TextView) findViewById(R.id.scheduler_st_offtime);
        background = (ImageView) findViewById(R.id.backstatus);
        schd1 = (TextView) findViewById(R.id.schedulerstatus1_static);
        schd2 = (TextView) findViewById(R.id.schedulerstatus2_static);
        power = (Switch) findViewById(R.id.power_sw);
        timer = (Switch) findViewById(R.id.timer_sw);
        scheduler = (Switch) findViewById(R.id.scheduler_sw);
        apply = (Button) findViewById(R.id.button);
        timer_text = (EditText) findViewById(R.id.timer_min);
        onh = (EditText) findViewById(R.id.scheduler_onh);
        onm = (EditText) findViewById(R.id.scheduler_onm);
        offh = (EditText) findViewById(R.id.scheduler_offh);
        offm = (EditText) findViewById(R.id.scheduler_offm);
        timer_static = (TextView) findViewById(R.id.timer_static);
        onh.setFilters(new InputFilter[]{new TextFilter("1", "12")});
        offh.setFilters(new InputFilter[]{new TextFilter("1", "12")});
        offm.setFilters(new InputFilter[]{new TextFilter("1", "60")});
        onm.setFilters(new InputFilter[]{new TextFilter("1", "60")});
        sleft = (TextView) findViewById(R.id.secleft);
        mleft = (TextView) findViewById(R.id.minleft);
        hleft = (TextView) findViewById(R.id.hrleft);
        sep1 = (TextView) findViewById(R.id.seprator1);
        sep2 = (TextView) findViewById(R.id.seprator2);
        schddays = (CheckBox) findViewById(R.id.checkBox);
        monday = (CheckBox) findViewById(R.id.checkBoxmon);
        tueday = (CheckBox) findViewById(R.id.checkBoxtue);
        wedday = (CheckBox) findViewById(R.id.checkBoxwed);
        thuday = (CheckBox) findViewById(R.id.checkBoxthu);
        friday = (CheckBox) findViewById(R.id.checkBoxfri);
        satday = (CheckBox) findViewById(R.id.checkBoxsat);
        sunday = (CheckBox) findViewById(R.id.checkBoxsun);
        onh.setFocusable(false);
        onm.setFocusable(false);
        offh.setFocusable(false);
        offm.setFocusable(false);
        timer_text.setFocusable(false);
        schddays.setVisibility(View.INVISIBLE);
        monday.setVisibility(View.INVISIBLE);
        tueday.setVisibility(View.INVISIBLE);
        wedday.setVisibility(View.INVISIBLE);
        thuday.setVisibility(View.INVISIBLE);
        friday.setVisibility(View.INVISIBLE);
        satday.setVisibility(View.INVISIBLE);
        sunday.setVisibility(View.INVISIBLE);

        SharedPreferences sharedpref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        lastd = sharedpref.getLong("LASTD", 0);
        Log.e("ACCQUIRED LASTD ", Long.toString(lastd));

        renderData("jsontest.php");

        power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (power.isChecked())
                    timer_static.setText("Turn off after");
                else
                    timer_static.setText("Turn on after");
            }
        });

        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (timer.isChecked()) {
                    timer_text.setFocusable(true);
                    timer_text.setFocusableInTouchMode(true);
                } else {
                    timer_text.setText("");
                    timer_text.setFocusable(false);
                }
            }
        });

        schddays.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    monday.setVisibility(View.VISIBLE);
                    tueday.setVisibility(View.VISIBLE);
                    wedday.setVisibility(View.VISIBLE);
                    thuday.setVisibility(View.VISIBLE);
                    friday.setVisibility(View.VISIBLE);
                    satday.setVisibility(View.VISIBLE);
                    sunday.setVisibility(View.VISIBLE);
                }
                else
                {
                    monday.setVisibility(View.INVISIBLE);
                    tueday.setVisibility(View.INVISIBLE);
                    wedday.setVisibility(View.INVISIBLE);
                    thuday.setVisibility(View.INVISIBLE);
                    friday.setVisibility(View.INVISIBLE);
                    satday.setVisibility(View.INVISIBLE);
                    sunday.setVisibility(View.INVISIBLE);
                }
            }
        });

        scheduler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (scheduler.isChecked()) {
                    onh.setFocusable(true);
                    onm.setFocusable(true);
                    offh.setFocusable(true);
                    offm.setFocusable(true);
                    onh.setFocusableInTouchMode(true);
                    onm.setFocusableInTouchMode(true);
                    offh.setFocusableInTouchMode(true);
                    offm.setFocusableInTouchMode(true);
                    schddays.setVisibility(View.VISIBLE);
                } else {
                    onh.setText("");
                    onm.setText("");
                    offh.setText("");
                    offm.setText("");
                    onh.setFocusable(false);
                    onm.setFocusable(false);
                    offh.setFocusable(false);
                    offm.setFocusable(false);
                }
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String some[] = new String[8];
                some[0] = "newupdate.php";
                if (power.isChecked())
                    some[1] = "on";
                else
                    some[1] = "off";
                if (timer.isChecked()) {
                    some[2] = timer_text.getText().toString();
                } else {
                    some[2] = "0";
                }
                if (scheduler.isChecked()) {
                    some[3] = "on";
                    some[4] = onh.getText().toString();
                    some[5] = onm.getText().toString();
                    some[6] = offh.getText().toString();
                    some[7] = offm.getText().toString();
                } else {
                    some[3] = "off";
                    some[4] = "06";
                    some[5] = "30";
                    some[6] = "06";
                    some[7] = "31";
                }
                PostData postData = new PostData();
                postData.execute(some);
            }
        });
    }

    public void renderData(String settpath) {

        SettingsTask settingsTask = new SettingsTask();
        settingsTask.execute(new String[]{settpath});
    }

    private class SettingsTask extends AsyncTask<String, Void, Settings> {

        @Override
        protected Settings doInBackground(String... params) {
try{
            String data = ((new HTTPClient()).getSettings(params[0]));
            settings = JSONParser.getSettings(data);
            Log.e("gotpower", settings.getPower());
            Log.e("gottimer", settings.getTimer());
            Log.e("gotschd", settings.getScheduler());
            Log.e("gotonh", settings.getSchd_onh());
            Log.e("gotonm", settings.getSchd_onm());
            Log.e("gotoffh", settings.getSchd_offh());
            Log.e("gotofffm", settings.getSchd_offm());
            temp = settings.getScheduler();
            return settings;
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "There's some problem reaching the server. Please try again later.", Toast.LENGTH_LONG).show();
            return null;
        }
        }

        @Override
        protected void onPostExecute(Settings settings) {

            super.onPostExecute(settings);
            try {
                powerStatus.setText(settings.getPower().toUpperCase());
                schdStatus.setText(settings.getScheduler().toUpperCase());
                Log.e("onstring", "decide visiblities");
                Log.e("Scheduler stat", settings.getScheduler());
                Log.e("current uts", Long.toString(System.currentTimeMillis()));

                if (settings.getScheduler().equals("on")) {
                    temp = (String.format(((Integer.parseInt(settings.getSchd_onh())<10)?"0":"")+"%s:"+((Integer.parseInt(settings.getSchd_onm())<10)?"0":"")+"%s", settings.getSchd_onh(), settings.getSchd_onm()));
                    Log.e("onstring", temp);
                    schdOn.setText(temp);
                    Log.e("offstring", temp);
                    temp = (String.format(((Integer.parseInt(settings.getSchd_offh())<10)?"0":"")+"%s:"+((Integer.parseInt(settings.getSchd_offm())<10)?"0":"")+"%s", settings.getSchd_offh(), settings.getSchd_offm()));
                    schdOff.setText(temp);
                    schdOn.setVisibility(View.VISIBLE);
                    schdOff.setVisibility(View.VISIBLE);
                    schd1.setVisibility(View.VISIBLE);
                    schd2.setVisibility(View.VISIBLE);
                } else {
                    Log.e("onstring", "scheduler invisible");
                    schd1.setVisibility(View.INVISIBLE);
                    schd2.setVisibility(View.INVISIBLE);
                    schdOn.setVisibility(View.INVISIBLE);
                    schdOff.setVisibility(View.INVISIBLE);
                }
                if (Integer.parseInt(settings.getTimer()) > 0) {
                    Log.e("timer", "INSIDE GETTIMER");
                    Log.e("Calculation:", Long.toString(System.currentTimeMillis() - (lastd + (Long.parseLong(settings.getTimer()) * 60 * 1000))));
                    if ((System.currentTimeMillis() - (lastd + (Long.parseLong(settings.getTimer())* 60*1000))) < 0) {
                        Log.e("min left timer : ", Long.toString(sec / 60));
                        Log.v("timer", "set timer visible call timer and hide off");
                        timerStatus.setVisibility(View.INVISIBLE);
                        hleft.setVisibility(View.VISIBLE);
                        sleft.setVisibility(View.VISIBLE);
                        mleft.setVisibility(View.VISIBLE);
                        sep1.setVisibility(View.VISIBLE);
                        sep2.setVisibility(View.VISIBLE);
                        sec = Math.abs(System.currentTimeMillis() - (lastd + (Long.parseLong(settings.getTimer())* 60*1000)));
                        startTimer();
                    } else {
                        Log.e("timer", "TIMER OFF");
                        hleft.setVisibility(View.INVISIBLE);
                        sleft.setVisibility(View.INVISIBLE);
                        mleft.setVisibility(View.INVISIBLE);
                        sep1.setVisibility(View.INVISIBLE);
                        sep2.setVisibility(View.INVISIBLE);
                        timerStatus.setVisibility(View.VISIBLE);
                        timerStatus.setText("OFF");
                    }
                } else {
                    Log.e("timer", "set timer invisble show off");
                    timerStatus.setVisibility(View.VISIBLE);
                    hleft.setVisibility(View.INVISIBLE);
                    sleft.setVisibility(View.INVISIBLE);
                    mleft.setVisibility(View.INVISIBLE);
                    sep1.setVisibility(View.INVISIBLE);
                    sep2.setVisibility(View.INVISIBLE);
                    timerStatus.setText("OFF");
                }
                if(Integer.parseInt(settings.getTimer())>0) {
                    if ((System.currentTimeMillis() - lastd > (Long.parseLong(settings.getTimer()) * 60 * 1000))) {
                        if (powerStatus.getText() == "ON")
                            powerStatus.setText("OFF");
                        else
                            powerStatus.setText("ON");
                    }
                }
                background.setImageResource(R.color.colorPrimary);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private class PostData extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            apply.setText("APPLYING...");
        }

        @Override
        protected String doInBackground(String... params) {
            String response;
            Log.e("someTag", "ENTRING DOINBACKGROUND");
            try {
                SendHTTPDataPOST sendHTTPData = new SendHTTPDataPOST();
                response = sendHTTPData.sendData(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
            } catch (Exception e) {
                Log.e("someTag", "Caught exception after doinbackground");
                response = "ERROR!";
            }
            return response;
        }

        protected void onPostExecute(String response) {
            Log.e("someTag", "ENTERTED ONPOSTEXECUTE");
            apply.setText(response);
            apply.postDelayed(new Runnable() {
                @Override
                public void run() {
                    apply.setText("APPLY");
                }
            }, 2000);
                if(lefttime>0) {
                    countDownTimer.cancel();
                    lefttime = 0;
                }
            renderData("jsontest.php");
            lastd = System.currentTimeMillis();
            SharedPreferences sharedpref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putLong("LASTD", lastd).apply();
            Log.e("COMMITED LASTD ", Long.toString(lastd));
        }
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(sec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lefttime = millisUntilFinished/1000;
                long seconds = millisUntilFinished / 1000;
                Log.e("total sec:", Long.toString(seconds));
                hleft.setText(String.format("%02d", seconds / 3600));
                seconds=seconds-((seconds/3600)*3600);
                Log.e("min and sec:", Long.toString(seconds));
                mleft.setText(String.format("%02d", seconds / 60));
                seconds=seconds-((seconds/60)*60);
                Log.e("only sec:", Long.toString(seconds));
                sleft.setText(String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                hleft.setVisibility(View.INVISIBLE);
                sleft.setVisibility(View.INVISIBLE);
                mleft.setVisibility(View.INVISIBLE);
                sep1.setVisibility(View.INVISIBLE);
                sep2.setVisibility(View.INVISIBLE);
                timerStatus.setVisibility(View.VISIBLE);
                timerStatus.setText("OFF");
                if(powerStatus.getText().equals("ON"))
                {
                    powerStatus.setText("OFF");}
                else{
                    powerStatus.setText("ON");}
            }
        }.start();
    }
}
