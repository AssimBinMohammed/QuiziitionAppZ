package com.example.quizitionapp;
import android.app.ProgressDialog;
import android.content.Intent;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import static com.example.quizitionapp.GetAlImages.question;
public class show_Qus_stu extends AppCompatActivity {
    int timer1 = show_subjectStudent.timer1;
    private final long START_TIME_IN_MILLIS = timer1 * 60000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public static final String GET_IMAGE_URL = "http://192.168.1.2/Server/images/getAllImages.php";
    public GetAlImages getAlImages;
    public String Q1 = "null";
    public String Q0;
    public String ex;
    public static final String BITMAP_ID = "BITMAP_ID";
    ImageView imageView;
    private static final String url = "http://192.168.1.2/Server/show_sub_Q.php";
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
    private static String right_answer[];
    private static String Question[];
    private static String option1[];
    private static String option2[];
    private static String option3[];
    private static String option4[];
    private static String subject[];
    private static String pic[];
    ArrayList<String> Subject = new ArrayList<String>();
    ArrayList<String> Subject2 = new ArrayList<String>();
    ArrayList<String> Qus = new ArrayList<String>();
    ArrayList<String> op1 = new ArrayList<String>();
    ArrayList<String> op2 = new ArrayList<String>();
    ArrayList<String> op3 = new ArrayList<String>();
    ArrayList<String> op4 = new ArrayList<String>();
    ArrayList<String> righ = new ArrayList<String>();
    ArrayList<String> picture = new ArrayList<String>();
    String pla = show_subjectStudent.gg;
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
        Fdata();
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
            data[1] = pla;
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
    public void Fdata() {
        radio_g = findViewById(R.id.answersgrp);
        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);
        r4 = (RadioButton) findViewById(R.id.radioButton4);
        class dbManager extends AsyncTask<String, Void, String> {
            int flag = 0;
            int S = Subject.size();
            String nameEquility = "";
            int secondQus = 0;
            int qoo = 0;
            protected void onPostExecute(String data) {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    subject = new String[ja.length()];
                    Question = new String[ja.length()];
                    option1 = new String[ja.length()];
                    option2 = new String[ja.length()];
                    option3 = new String[ja.length()];
                    option4 = new String[ja.length()];
                    right_answer = new String[ja.length()];
                    pic = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        subject[i] = jo.getString("Subject");
                        Question[i] = jo.getString("Question");
                        option1[i] = jo.getString("option1");
                        option2[i] = jo.getString("option2");
                        option3[i] = jo.getString("option3");
                        option4[i] = jo.getString("option4");
                        right_answer[i] = jo.getString("right_answer");
                        pic[i] = jo.getString("pic");
                        if (subject[i].equals(pla)) {
                            nameEquility = pla;
                            Subject.add(subject[i]);
                            Qus.add(Question[i]);
                            op1.add(option1[i]);
                            op2.add(option2[i]);
                            op3.add(option3[i]);
                            op4.add(option4[i]);
                            righ.add(right_answer[i]);
                            if (pic[i] != null) {
                                picture.add(pic[i]);
                            }
                            S = Subject.size();
                            Log.i("Question", String.valueOf(Question[i]));
                            Log.i("Question", String.valueOf(option1[i]));
                            Log.i("Question", String.valueOf(option2[i]));
                            Log.i("Question", String.valueOf(option3[i]));
                            Log.i("Question", String.valueOf(option4[i]));
                            Log.i("Question", String.valueOf(right_answer[i]));
                        }
                    }
                    Log.i("picture", String.valueOf(picture.get(0)));
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(op1.get(0));
                    arrayList.add(op2.get(0));
                    arrayList.add(op3.get(0));
                    arrayList.add(op4.get(0));
                    Collections.shuffle(arrayList);
                    if (picture.get(0).equalsIgnoreCase("yes")) {
                        Log.i("img 0", picture.get(0));
                        Q0 = Qus.get(0);
                        getURLs();
                        tv1.setText(Qus.get(0));
                        r1.setText(arrayList.get(0));
                        r2.setText(arrayList.get(1));
                        r3.setText(arrayList.get(2));
                        r4.setText(arrayList.get(3));
                    }
                    else {
                        imageView.setImageBitmap(null);
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
                            String ansText = uans.getText().toString();
                            if (ansText.equals(righ.get(secondQus))) {
                                correctAnswer++;
                            } else {
                                wrongAnswer++;
                            }
                            secondQus++;
                            if (secondQus < Qus.size()) {
                                Log.i("inside", String.valueOf(nameEquility));
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(op1.get(secondQus));
                                arrayList.add(op2.get(secondQus));
                                arrayList.add(op3.get(secondQus));
                                arrayList.add(op4.get(secondQus));
                                Collections.shuffle(arrayList);
                                if (picture.get(secondQus).equals("no")) {
                                    imageView.setImageBitmap(null);
                                    tv1.setText(Qus.get(secondQus));
                                    r1.setText(arrayList.get(0));
                                    r2.setText(arrayList.get(1));
                                    r3.setText(arrayList.get(2));
                                    r4.setText(arrayList.get(3));
                                }
                                else {
                                    Log.i("img 1", picture.get(secondQus));
                                    Q1 = Qus.get(secondQus);
                                    getURLs();
                                    tv1.setText(Qus.get(secondQus));
                                    r1.setText(arrayList.get(0));
                                    r2.setText(arrayList.get(1));
                                    r3.setText(arrayList.get(2));
                                    r4.setText(arrayList.get(3));
                                }
                                Subject2.add(nameEquility);
                                qoo = Subject2.size();
                                Log.i("qoo", String.valueOf(qoo));
                                Log.i("s", String.valueOf(S - 1));
                            }
                            else {
                                marks = correctAnswer;
                                Log.i("tag", String.valueOf(marks));
                                String[] field = new String[3];
                                field[0] = "username";
                                field[1] = "subjectName";
                                field[2] = "marks";
                                String[] data = new String[3];
                                data[0] = name;
                                data[1] = pla;
                                data[2] = String.valueOf(marks);
                                PutData putData = new PutData("http://192.168.1.2/Server/marks.php", "POST", field, data);
                                Log.i("t5555", String.valueOf(marks));
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        char str = result.charAt(6);
                                        Log.i("PutData0", String.valueOf(str));
                                        if ("p".equalsIgnoreCase(String.valueOf(str))) {
                                            Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                                            startActivity(in);
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    } } }
                            radio_g.clearCheck();
                        }}); }
                catch (Exception e) {
    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                } }
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer data = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        data.append(line + "\n");
                    }
                    br.close();
                    return data.toString(); }
                catch (Exception ex) {
                    return ex.getMessage();
                }}}
        dbManager obj = new dbManager();
        obj.execute(url);
    }
    public void getImages() {
        class GetImages extends AsyncTask<Void, Void, Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(show_Qus_stu.this, "Downloading images...", "Please wait...", false, false);
            }
            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                Toast.makeText(show_Qus_stu.this, "Success", Toast.LENGTH_LONG).show();
                Log.i("GetAlImages.imageURLs", String.valueOf(GetAlImages.imageURLs[0]));
                Log.i("GetAlImages.imageURLs", String.valueOf(GetAlImages.bitmaps[0]));
                for (int x = 0; x < question.length; x++) {
                    ex = question[x].toString();
                    Log.i("cvcvc", question[x]);
                    Log.i("**", Q0);
                    if (Q1.equals("null")) {
                    }
                    else {
                        Log.i("&&", Q1);
                    }
                    int length_Q0 = Q0.length();
                    int length_Q1 = Q1.length();
                    int length_question = question.length;
                    String sub_qus = question[x].substring(0, length_question - 1);
                    String sub_Q0 = Q0.substring(0, length_Q0);
                    String sub_Q1 = Q1.substring(0, length_Q1);
                    Log.i("subS", sub_qus);
                    Log.i("subS", sub_Q0);
                    String qus = question[x].trim();
                    String inputQ0 = Q0.trim();
                    String inputQ1 = Q1.trim();
                    if (qus.equalsIgnoreCase(inputQ0)) {
                        Log.i("zzz", "Q0000");
                        imageView.setImageBitmap(GetAlImages.bitmaps[x]);
                    }
                    else if (inputQ1.equals(qus)) {
                        Log.i("zzz", "Q000-1100");
                        imageView.setImageBitmap(GetAlImages.bitmaps[x]);
                    } } }
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
    public void getURLs() {
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
                if (s != null) {
                    loading.dismiss();
                    getAlImages = new GetAlImages(s);
                    getImages();
                }
                else {
                    Log.i("non", "non");
                } }
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
                }
                catch (Exception e) {
                    return null;
                }}}
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }}