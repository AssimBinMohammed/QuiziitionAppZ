package com.example.quizitionapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
public class show_Qus_stu extends AppCompatActivity {
    int timer1 = show_subjectStudent.timer1;
    private final long START_TIME_IN_MILLIS = timer1 * 60000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static final String GET_IMAGE_URL = "http://192.168.1.2/Server/images/getAllImages.php";
    public GetAlImages getAlImages;
    public String Q1 = "null";
    public static final String BITMAP_ID = "BITMAP_ID";
    ImageView imageView;
    public static int minutes;
    public static int seconds;
    Button b1;
    RadioGroup radio_g;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;
    TextView tv1, text_view_countdown;
    public static int marks = 0;
    public static int correctAnswer = 0;
    public static int wrongAnswer = 0;
    ArrayList<String> Subject = new ArrayList<String>();
    ArrayList<String> Qus = new ArrayList<String>();
    ArrayList<String> op1 = new ArrayList<String>();
    ArrayList<String> op2 = new ArrayList<String>();
    ArrayList<String> op3 = new ArrayList<String>();
    ArrayList<String> op4 = new ArrayList<String>();
    ArrayList<String> righ = new ArrayList<String>();
    ArrayList<String> imageArray_url = new ArrayList<String>();
    ArrayList<Bitmap> imageArray_bit = new ArrayList<android.graphics.Bitmap>();
    String SubjectName = show_subjectStudent.subjectName_actvity_show_Student;
    String name = MainActivity.name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__qus_stu);
        imageView = (ImageView) findViewById(R.id.imageView);
        b1 = findViewById(R.id.b1);
        tv1 = findViewById(R.id.tv1);
        text_view_countdown = findViewById(R.id.text_view_countdow);
        startTimer();
        getURLs();
    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }
    public void updateCountDownText() {
        minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        text_view_countdown.setText(timeLeftFormatted);
        Log.i("lylyl22", String.valueOf(seconds));
        if (minutes == 0 && seconds == 0 && (wrongAnswer > 0 || correctAnswer > 0)) {
            imageView.setImageBitmap(null);
            marks = correctAnswer;
            Log.i("tawlwllg", String.valueOf(marks));
            String[] field = new String[3];
            field[0] = "username";
            field[1] = "subjectName";
            field[2] = "marks";
            String[] data = new String[3];
            data[0] = name;
            data[1] = SubjectName;
            data[2] = String.valueOf(marks);
            PutData putData = new PutData("http://192.168.1.2/Server/marks.php", "POST", field, data);
            Log.i("t5555", String.valueOf(marks));
            if (putData.startPut()) {
                Log.i("t666", String.valueOf(marks));
                if (putData.onComplete()) {
                    Log.i("t777", String.valueOf(marks));
                    String result = putData.getResult();
                    char str = result.charAt(6);
                    Log.i("PutDatddda0", String.valueOf(str));
                    if ("p".equalsIgnoreCase(String.valueOf(str))) {
                        Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(in);
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if (Qus.size() == wrongAnswer + correctAnswer) {
                        mCountDownTimer.cancel();
                    }
                    Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(in);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    finish(); } } } }
    public void getImages() {
        radio_g = findViewById(R.id.answersgrp);
        r1 =  findViewById(R.id.radioButton);
        r2 =  findViewById(R.id.radioButton2);
        r3 =  findViewById(R.id.radioButton3);
        r4 =  findViewById(R.id.radioButton4);
        class GetImages extends AsyncTask<Void, Void, Void> {
            ProgressDialog loading;
            int secondQus =0;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(show_Qus_stu.this, "Downloading images...", "Please wait...", false, false);
            }
            @Override
            protected void onPostExecute(Void v) {
                try {
                    super.onPostExecute(v);
                    loading.dismiss();
                    Toast.makeText(show_Qus_stu.this, "Success", Toast.LENGTH_LONG).show();

                    for (int i = 0; i < GetAlImages.Question.length; i++) {

                        if (GetAlImages.subject[i].trim().equals(SubjectName.trim())) {
                            Subject.add(GetAlImages.subject[i].trim());
                            Qus.add(GetAlImages.Question[i].trim());
                            op1.add(GetAlImages.option1[i].trim());
                            op2.add(GetAlImages.option2[i].trim());
                            op3.add(GetAlImages.option3[i].trim());
                            op4.add(GetAlImages.option4[i].trim());
                            righ.add(GetAlImages.right_answer[i].trim());
                            imageArray_url.add(GetAlImages.imageURLs[i]);
                            imageArray_bit.add(GetAlImages.bitmaps[i]);
                            Log.i("Quesddtion", String.valueOf(GetAlImages.Question[i]));
                            Log.i("Questddion", String.valueOf(GetAlImages.option1[i]));
                            Log.i("Questhhion", String.valueOf(GetAlImages.option2[i]));
                            Log.i("Quesddtion", String.valueOf(GetAlImages.option3[i]));
                            Log.i("Quedddstion", String.valueOf(GetAlImages.option4[i]));
                            Log.i("Quesddtion", String.valueOf(GetAlImages.right_answer[i]));
                        }}
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(op1.get(0));
                    arrayList.add(op2.get(0));
                    arrayList.add(op3.get(0));
                    arrayList.add(op4.get(0));
                    Collections.shuffle(arrayList);
                    tv1.setText(Qus.get(0));
                    r1.setText(arrayList.get(0));
                    r2.setText(arrayList.get(1));
                    r3.setText(arrayList.get(2));
                    r4.setText(arrayList.get(3));
                    if (imageArray_url.get(0).equals(null)) {
                        imageView.setImageBitmap(null);
                        tv1.setText(Qus.get(0));
                        r1.setText(arrayList.get(0));
                        r2.setText(arrayList.get(1));
                        r3.setText(arrayList.get(2));
                        r4.setText(arrayList.get(3));
                    } else {
                        imageView.setImageBitmap(imageArray_bit.get(0));
                        tv1.setText(Qus.get(0));
                        r1.setText(arrayList.get(0));
                        r2.setText(arrayList.get(1));
                        r3.setText(arrayList.get(2));
                        r4.setText(arrayList.get(3));
                    }
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (radio_g.getCheckedRadioButtonId() == -1) {
                                Toast.makeText(getApplicationContext(), "الرجاء اختيار اجابة", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            RadioButton uans = findViewById(radio_g.getCheckedRadioButtonId());
                            String ansText = uans.getText().toString().trim();
                            Log.i("ansText", ansText);
                            if (ansText.equals(righ.get(secondQus).trim())) {
                                Log.i("correctAnswer", String.valueOf(correctAnswer));
                                correctAnswer++;
                            } else {
                                Log.i("wrongAnswer", String.valueOf(wrongAnswer));
                                wrongAnswer++;
                            }
                            secondQus++;
                            if (secondQus < Qus.size()) {
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(op1.get(secondQus));
                                arrayList.add(op2.get(secondQus));
                                arrayList.add(op3.get(secondQus));
                                arrayList.add(op4.get(secondQus));
                                Collections.shuffle(arrayList);
                                if (imageArray_url.get(secondQus).equals("null")) {
                                    imageView.setImageBitmap(null);
                                    tv1.setText(Qus.get(secondQus));
                                    r1.setText(arrayList.get(0));
                                    r2.setText(arrayList.get(1));
                                    r3.setText(arrayList.get(2));
                                    r4.setText(arrayList.get(3));
                                } else {
                                    Q1 = Qus.get(secondQus);
                                    tv1.setText(Qus.get(secondQus));
                                    imageView.setImageBitmap(imageArray_bit.get(secondQus));
                                    r1.setText(arrayList.get(0));
                                    r2.setText(arrayList.get(1));
                                    r3.setText(arrayList.get(2));
                                    r4.setText(arrayList.get(3));
                                }
                            } else {
                                marks = correctAnswer;
                                Log.i("tag", String.valueOf(marks));
                                String[] field = new String[3];
                                field[0] = "username";
                                field[1] = "subjectName";
                                field[2] = "marks";
                                String[] data = new String[3];
                                data[0] = name;
                                data[1] = SubjectName;
                                data[2] = String.valueOf(marks);
                                PutData putData = new PutData("http://192.168.1.2/Server/marks.php", "POST", field, data);
                                Log.i("t5555", String.valueOf(marks));
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        char str = result.charAt(6);
                                        Log.i("PutData0", String.valueOf(str));
                                        if ("p".equalsIgnoreCase(String.valueOf(str))) {
                                            mCountDownTimer.cancel();
                                            Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                                            startActivity(in);
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }}}
                            radio_g.clearCheck();
                        }});
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }}
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAlImages.getAllImages();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }}
        GetImages getImages = new GetImages();
        getImages.execute();
    }
    private void getURLs() {
        class GetURLs extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(show_Qus_stu.this, "Loading...", "Please Wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }}}
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }}









