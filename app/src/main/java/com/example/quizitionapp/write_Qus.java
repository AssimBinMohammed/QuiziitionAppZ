
package com.example.quizitionapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class write_Qus extends AppCompatActivity {



    Button b1, b2;
    EditText ed1, ed2, ed3, ed4, ed5, ed,picture;

    String Subject = show_subjectAdmin.gg;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_write__qus );



        ed5 = findViewById( R.id.ed5 );
        ed = findViewById( R.id.ed );
        ed1 = findViewById( R.id.ed1 );
        ed2 = findViewById( R.id.ed2 );
        ed3 = findViewById( R.id.ed3 );
        ed4 = findViewById( R.id.ed4 );
        picture = findViewById( R.id.pic );
        b1 = findViewById( R.id.b1 );
        b2 = findViewById( R.id.button );

        b2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(write_Qus.this, show_subjectAdmin.class));
                finish();
            }
        } );
        b1.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                final String Question, option1, option2, option3, option4, right_answer,pic;


                Question = String.valueOf( ed1.getText() );
                option1 = String.valueOf( ed2.getText() );
                option2 = String.valueOf( ed3.getText() );
                option3 = String.valueOf( ed5.getText() );
                option4 = String.valueOf( ed.getText() );
                right_answer = String.valueOf( ed4.getText() );
                pic = String.valueOf( picture.getText());



                if ((!Question.equals( "" )) && (!option1.equals( "" )) && (!option2.equals( "" )) &&
                        (!right_answer.equals( "" )) && (!option3.equals( "" )) && (!option4.equals( "" ))) {
                    Handler handler = new Handler();
                    handler.post( new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[8];
                            field[0] = "Question";
                            field[1] = "option1";
                            field[2] = "option2";
                            field[3] = "option3";
                            field[4] = "option4";
                            field[5] = "right_answer";
                            field[6] = "Subject";
                            field[7]="pic";

//                            Log.i("bit", getStringImage(bitmap));







                            //Creating array for data


                            String[] data = new String[8];
                            data[0] = Question;
                            data[1] = option1;
                            data[2] = option2;
                            data[3] = option3;
                            data[4] = option4;
                            data[5] = right_answer;
                            data[6] = Subject;
                            data[7] = pic;



                            PutData putData = new PutData( "http://192.168.1.2/Server/Qus_sub.php", "POST", field, data );
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();


                                    char str = result.charAt( 7 );
                                    Log.i( "PutData", String.valueOf( str ) );


                                    if ("n".equalsIgnoreCase( String.valueOf( str ) )) {

                                        Toast.makeText( getApplicationContext(), result, Toast.LENGTH_SHORT ).show();

                                        startActivity( new Intent( write_Qus.this, write_Qus.class ) );
                                        finish();
                                    } else {

                                        Toast.makeText( (getApplicationContext()), result, Toast.LENGTH_SHORT ).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    } );


                } else {
                    Toast.makeText( getApplicationContext(), "error", Toast.LENGTH_SHORT ).show();
                }


            }


        } );

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
                startActivity( new Intent( write_Qus.this, show_subjectAdmin.class ) );

                return true;
            default:
                return super.onOptionsItemSelected( item );


        }
    }


}