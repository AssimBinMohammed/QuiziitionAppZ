
package com.example.quizitionapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.quizitionapp.R ;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class add_subjectAdmin extends AppCompatActivity implements View.OnClickListener   {
    Button b, b1, b2;
    EditText ed1, ed2;
    TextView tx1, tx2;
    Button btnDatePicker, btnTimePicker, btnDatePicker2, btnTimePicker2;
    EditText txtDate, txtTime, txtDate2, txtTime2;
    int mYear, mMonth, mDay, mHour, mMinute;
    public String first_date = "";
    public String second_date = "";
    public String first_time = "";
    public String second_time = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_add_subject_admin);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        ///////

//        First Interval
        btnDatePicker2 = (Button) findViewById(R.id.btn_date2);
        btnTimePicker2 = (Button) findViewById(R.id.btn_time2);
        txtDate2 = (EditText) findViewById(R.id.in_date2);
        txtTime2 = (EditText) findViewById(R.id.in_time2);


        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        b = (Button) findViewById(R.id.b);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        tx2 = findViewById(R.id.textView2);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        ///

//        Second Interval
        btnDatePicker2.setOnClickListener(this);
        btnTimePicker2.setOnClickListener(this);
//        b.setOnClickListener(this);

b.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(add_subjectAdmin.this, show_subjectAdmin.class));

        finish();
    }
} );
        b1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String subject, timer;

                subject = String.valueOf(ed1.getText());
                timer = String.valueOf(ed2.getText());

                Log.i("poip",subject);


                if ((!subject.equals(""))) {


                    Log.i("equil",subject);


                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "Subject";
                            field[1] = "timer";
                            field[2]="first_date";
                            field[3] = "second_date";
                            field[4] = "first_time";
                            field[5]="second_time";


                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = subject;
                            data[1] = timer;

                            data[2]=first_date;
                            data[3] =second_date;
                            data[4] =first_time;
                            data[5]=second_time;


                            PutData putData = new PutData("http://192.168.1.2/Server/add_subject.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();


                                    char str = result.charAt(1);
                                    Log.i("PutData", String.valueOf(str));


                                    if ("o".equalsIgnoreCase(String.valueOf(str))) {

                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(add_subjectAdmin.this, show_subjectAdmin.class));

                                        finish();
                                    } else {


                                        Toast.makeText((getApplicationContext()), result, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                    ////

                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }


    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {


            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            Log.i("year", String.valueOf(mYear));
            Log.i("MONTH", String.valueOf(mMonth+1));
            Log.i("day", String.valueOf(mDay));


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    if(monthOfYear<10 && dayOfMonth<10){

                        first_date = "0"+dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year;
                        txtDate.setText("0"+dayOfMonth + "-" +"0"+ (monthOfYear + 1) + "-" + year);

                    }
                    else if (monthOfYear<10){
                    first_date = dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year;
                    txtDate.setText(dayOfMonth + "-" +"0"+ (monthOfYear + 1) + "-" + year);

                }
                    else if (dayOfMonth<10){
                        first_date = "0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate.setText("0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    } else {

                        first_date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }


                }

            }, mYear, mMonth, mDay);
            Log.i("dating", first_date);
            datePickerDialog.show();


        }


        if (v == btnDatePicker2) {


            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            Log.i("year", String.valueOf(mYear));


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    if(monthOfYear<10 && dayOfMonth<10){

                        second_date = "0"+dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year;
                        txtDate2.setText("0"+dayOfMonth + "-" +"0"+ (monthOfYear + 1) + "-" + year);

                    }
                    else if (monthOfYear<10){
                        second_date = dayOfMonth + "-" + "0"+(monthOfYear + 1) + "-" + year;
                        txtDate2.setText(dayOfMonth + "-" +"0"+ (monthOfYear + 1) + "-" + year);

                    }
                    else if (dayOfMonth<10){
                        second_date = "0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate2.setText("0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    } else {

                        second_date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }

                }
            }, mYear, mMonth, mDay);
            Log.i("date", second_date);
            datePickerDialog.show();


        }


        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);





            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                    if(hourOfDay<10 && minute<10){

                        first_time= "0"+hourOfDay + ":" +"0"+ minute;
                        txtTime.setText("0"+hourOfDay + ":" + "0"+minute);
                        Log.i("fewefqeff",first_time);

                    }
                   else if(hourOfDay<10){

                        first_time= "0"+hourOfDay + ":" + minute;
                        txtTime.setText("0"+hourOfDay + ":" + minute);

                        Log.i("ffgtgtf",first_time);

                    }   else if(minute<10){

                        first_time= hourOfDay + ":" + "0"+minute;
                        txtTime.setText(hourOfDay + ":" + "0"+minute);
                        Log.i("f111f",first_time);

                    } else{
                        Log.i("f222f",first_time);
                        first_time= hourOfDay + ":" + minute;
                        txtTime.setText(hourOfDay + ":" + minute);


                    }
                    Log.i("h", String.valueOf(hourOfDay));



                }
            }, mHour, mMinute, false);
            Log.i("first_time", first_time);
            timePickerDialog.show();
        }


        if (v == btnTimePicker2) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);






            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                    if(hourOfDay<10 && minute<10){

                        second_time= "0"+hourOfDay + ":" +"0"+ minute;
                        txtTime2.setText("0"+hourOfDay + ":" + "0"+minute);

                    }
                   else if(hourOfDay<10){

                        second_time= "0"+hourOfDay + ":" + minute;
                        txtTime2.setText("0"+hourOfDay + ":" + minute);

                    }   else if(minute<10){

                        second_time= hourOfDay + ":" + "0"+minute;
                        txtTime2.setText(hourOfDay + ":" + "0"+minute);

                    } else{
                        second_time= hourOfDay + ":" + minute;
                        txtTime2.setText(hourOfDay + ":" + minute);


                    }

                }
            }, mHour, mMinute, false);
            Log.i("first_time", first_time);
            timePickerDialog.show();
        }





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.back_write_qus, menu );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {

            case R.id.back:
                startActivity( new Intent( add_subjectAdmin.this, show_subjectAdmin.class ) );

                return true;
            default:
                return super.onOptionsItemSelected( item );


        }
    }
}