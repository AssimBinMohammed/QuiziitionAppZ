
package com.example.quizitionapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    public static String name,password;
    Button b1, b2;
    EditText ed1, ed2;
    TextView tx2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        tx2 = findViewById(R.id.textView2);
        b2.setOnClickListener(v -> finishAffinity());
        b1.setOnClickListener(v -> {
            name=String.valueOf(ed1.getText());
            password=String.valueOf(ed2.getText());
            if ((!name.equals("")) && (!password.equals(""))){
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        String[] data = new String[2];
                        data[0] =name;
                        data[1] =password;
                        PutData putData = new PutData("http://192.168.1.2/Server/login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                char str=result.charAt(5);
                                Log.i("PutData", String.valueOf(str));
                                if ("s".equalsIgnoreCase(String.valueOf(str))){
                                    Log.i(name, String.valueOf(name));
                                    Log.i(password, String.valueOf(password));
                                    String nameAdmin="ezzo";
                                    String passAdmin="1";
                                    if ((name.equalsIgnoreCase(String.valueOf(nameAdmin)) & (password.equalsIgnoreCase(String.valueOf(passAdmin))))){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, show_subjectAdmin.class));
                                        finish();
                                    }
                                    else if(((name.equalsIgnoreCase(("1")) & (password.equalsIgnoreCase(("1"))))))
                                    {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, show_subjectAdmin.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, show_subjectStudent.class));
                                        finish();
                                    }}
                                else {
                                    tx2.setVisibility(View.VISIBLE);
                                    Toast.makeText((getApplicationContext()), result, Toast.LENGTH_SHORT).show();
                                }}}}}); }
            else
            {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            } }); }}