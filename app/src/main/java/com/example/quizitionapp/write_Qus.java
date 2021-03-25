package com.example.quizitionapp;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class write_Qus extends AppCompatActivity implements View.OnClickListener {
    public static final String UPLOAD_URL = "http://192.168.1.2/Server/images/upload.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    Button b1, b2,buttonChoose;
    EditText ed1, ed2, ed3, ed4, ed5, ed;
    String Subject = show_subjectAdmin.subjectName_actvity_show_Admin;
    public String Question, option1, option2, option3, option4, right_answer;
    private Bitmap bitmap;
    private Uri filePath;
    private ImageView imageView;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_write__qus );
        b1 = findViewById( R.id.b1 );
        b2 = findViewById( R.id.button );
        buttonChoose = findViewById(R.id.buttonChoose);
        b1 = findViewById(R.id.b1);
        imageView =findViewById(R.id.imageView);
        Log.i("s", String.valueOf(imageView));

        buttonChoose.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(v -> {
            startActivity(new Intent(write_Qus.this, show_subjectAdmin.class));
            finish();
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.back_write_qus, menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                startActivity(new Intent(write_Qus.this, show_subjectAdmin.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ed1 = findViewById(R.id.ed1);
        ed5 = findViewById( R.id.ed5 );
        ed = findViewById( R.id.ed );
        ed2 = findViewById( R.id.ed2 );
        ed3 = findViewById( R.id.ed3 );
        ed4 = findViewById( R.id.ed4 );
        Question=ed1.getText().toString().trim();
        option1=ed2.getText().toString().trim();
        option2=ed3.getText().toString().trim();
        option3=ed5.getText().toString().trim();
        option4=ed.getText().toString().trim();
        right_answer=ed4.getText().toString().trim();
        Log.i("data", String.valueOf(data));
        Log.i("resultCode", String.valueOf(resultCode));
        Log.i("requestCode", String.valueOf(requestCode));
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.i("do", String.valueOf(filePath));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                Log.i("duuuu", String.valueOf(bitmap));
            }
            catch (IOException e) {
                e.printStackTrace();
            } } }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.i("enc",encodedImage);
        return encodedImage;
    }
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> data = new HashMap<>();
                Log.i("question",Question);

                if ((!Question.equals("")) && (!option1.equals("")) && (!option2.equals("")) &&
                        (!right_answer.equals("")) && (!option3.equals("")) && (!option4.equals(""))) {
                    data.put(UPLOAD_KEY, uploadImage);
                    data.put("Question",Question);
                    data.put("option1",option1.trim());
                    data.put("option2",option2.trim());
                    data.put("option3",option3);
                    data.put("option4",option4);
                    data.put("right_answer",right_answer);
                    data.put("Subject",Subject);
                }
                Log.i("u",UPLOAD_KEY);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(write_Qus.this, "Uploading...", null,true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        }
        UploadImage ui = new UploadImage();
        Log.i("ui", String.valueOf(ui));
        ui.execute(bitmap);
    }

    private void uploadWithoutIamge() {
        ed1 = findViewById(R.id.ed1);
        ed1 = findViewById(R.id.ed1);
        ed5 = findViewById( R.id.ed5 );
        ed = findViewById( R.id.ed );
        ed2 = findViewById( R.id.ed2 );
        ed3 = findViewById( R.id.ed3 );
        ed4 = findViewById( R.id.ed4 );
        String question2 = String.valueOf(ed1.getText()).trim();
        option1 = String.valueOf(ed2.getText());
        option2 = String.valueOf(ed3.getText());
        option3 = String.valueOf(ed5.getText());
        option4 = String.valueOf(ed.getText());
        right_answer = String.valueOf(ed4.getText());
        if ( (!question2.equals(""))&& (!option1.equals("")) && (!option2.equals("")) &&
                (!right_answer.equals("")) && (!option3.equals("")) && (!option4.equals(""))) {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[8];
                    field[0] = "Question";
                    field[1] = "option1";
                    field[2] = "option2";
                    field[3] = "option3";
                    field[4] = "option4";
                    field[5] = "right_answer";
                    field[6] = "Subject";
                    field[7] = UPLOAD_KEY;
                    String[] data = new String[8];
                    data[0] = question2;
                    data[1] = option1;
                    data[2] = option2;
                    data[3] = option3;
                    data[4] = option4;
                    data[5] = right_answer;
                    data[6] = Subject;
                    data[7] = "null";
                    PutData putData = new PutData("http://192.168.1.2/Server/images/Qus_sub.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            char str = result.charAt(7);
                            Log.i("PutData", String.valueOf(str));
                            if ("n".equalsIgnoreCase(String.valueOf(str))) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(write_Qus.this, write_Qus.class));
                                finish();
                            } else {
                                Toast.makeText((getApplicationContext()), result, Toast.LENGTH_SHORT).show();
                            }}}}});
        }else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }}
    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == b1) {
            Log.i("question", String.valueOf(Question));
            if (Question==null){
                uploadWithoutIamge();
            }else {
                uploadImage();
                ed1.getText().clear();
                ed5.getText().clear();
                ed.getText().clear();
                ed2.getText().clear();
                ed3.getText().clear();
                ed4.getText().clear();
                imageView.setImageBitmap(null);
            }}}}