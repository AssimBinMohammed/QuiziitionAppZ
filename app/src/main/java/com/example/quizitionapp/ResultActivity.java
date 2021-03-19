package com.example.quizitionapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class ResultActivity extends AppCompatActivity {
    TextView tv, tv2, tv3;
    Button btnRestart;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_result);
        Log.i("minnnn", String.valueOf(show_Qus_stu.minutes));
        Log.i("minnnn", String.valueOf(show_Qus_stu.seconds));
        show_Qus_stu.seconds=0;
        tv = (TextView) findViewById(R.id.tvres);
        tv2 = (TextView) findViewById(R.id.tvres2);
        tv3 = (TextView) findViewById(R.id.tvres3);
        btnRestart = (Button) findViewById(R.id.btnRestart);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        sb.append("الإجابات الصحيحة : " + show_Qus_stu.correctAnswer+ "\n");
        sb2.append("الإجابات الخاطئة : " + show_Qus_stu.wrongAnswer+ "\n");
        sb3.append("الدرجة النهائية : " + show_Qus_stu.marks + "\n");
        tv.setText(sb);
        tv2.setText(sb2);
        tv3.setText(sb3);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Qus_stu.correctAnswer=0;
                show_Qus_stu.marks=0;
                show_Qus_stu.wrongAnswer=0;
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                finish(); }}); }}