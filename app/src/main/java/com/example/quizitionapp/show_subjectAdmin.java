package com.example.quizitionapp;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class show_subjectAdmin extends AppCompatActivity {
    private static final String apiurl = "http://192.168.1.2/Server/Subject.php";
    ListView lv;

    Button b_subj,b_add;


    private static String subject[];
    private static String timer[];
    private static String first_date[];
    private static String second_date[];
    private static String first_time[];
    private static String second_time[];


    public static String gg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subject_admin);

        lv = (ListView) findViewById( R.id.lv);

        b_add=findViewById(R.id.b_add);

        fetch_data_into_array(lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lv.getItemAtPosition(position).toString();

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(show_subjectAdmin.this, add_subjectAdmin.class));
                return true;
            case R.id.back:
                startActivity(new Intent(show_subjectAdmin.this, MainActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);





        }
    }

    public void fetch_data_into_array(View view) {

        class dbManager extends AsyncTask<String, Void, String> {
            protected void onPostExecute(String data) {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;

                    subject = new String[ja.length()];
                    timer =new String[ja.length()];

                    first_date = new String[ja.length()];
                    second_date =new String[ja.length()];
                    first_time = new String[ja.length()];
                    second_time =new String[ja.length()];




                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        subject[i] = jo.getString("Subject");
                        timer[i] = jo.getString("timer");
                        first_date[i]=jo.getString("first_date");
                        second_date[i]=jo.getString("second_date");
                        first_time[i]=jo.getString("first_time");
                        second_time[i]=jo.getString("second_time");





                    }

                    myadapter adptr = new myadapter(getApplicationContext(), subject,timer,first_date,second_date,first_time,second_time);
                    lv.setAdapter(adptr);

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

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

                    return data.toString();

                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }

        }
        dbManager obj = new dbManager();
        obj.execute(apiurl);

    }

    class myadapter extends ArrayAdapter<String> {
        Context context;
        String subject[];
        String timer[];
        String first_date[];
        String second_date[];
        String first_time[];
        String second_time[];



        myadapter(Context c, String subject[],
                  String timer[],
                  String first_date[],
                  String second_date[],
                  String first_time[],
                  String second_time[]) {
            super(c, R.layout.subject_list_admin, R.id.tv1, subject);
            context = c;
            this.subject = subject;
            this.timer=timer;


            this.first_date = first_date;
            this.second_date=second_date;
            this.first_time=first_time;
            this.second_time=second_time;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View subject_list = inflater.inflate(R.layout.subject_list_admin, parent, false);


            TextView tv1 = subject_list.findViewById(R.id.tv1);
            TextView tv2 = subject_list.findViewById(R.id.tv2);
            Button b_add=subject_list.findViewById(R.id.b_add);
            Button b_add_Pic=subject_list.findViewById(R.id.b_add_Pic);


            TextView f_date = subject_list.findViewById(R.id.f_date);
            TextView s_date = subject_list.findViewById(R.id.s_date);

            TextView f_time = subject_list.findViewById(R.id.f_time);
            TextView s_time = subject_list.findViewById(R.id.s_time);







            tv1.setText(subject[position]);
            tv2.setText(timer[position]);


            f_date.setText(first_date[position]);
            s_date.setText(second_date[position]);

            f_time.setText(first_time[position]);
            s_time.setText(second_time[position]);

            b_add.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    startActivity(new Intent(show_subjectAdmin.this, write_Qus.class));

                    Log.i("vcvcv", subject[position]);

                    gg=subject[position];

                }


            });

            b_add_Pic.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    startActivity(new Intent(show_subjectAdmin.this, upload_image.class));

                    Log.i("vcvcv", subject[position]);

                    gg=subject[position];

                }


            });











            return subject_list;

        }
    }

}