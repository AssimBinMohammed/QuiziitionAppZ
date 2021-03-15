package com.example.quizitionapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class upload_image extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "http://192.168.1.2/Server/images/upload.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    EditText editText;
    private Bitmap bitmap;
    private Uri filePath;
    public String question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        editText = findViewById(R.id.editText);
        question = editText.getText().toString().trim();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.i("enc", encodedImage);
        return encodedImage;
    }

    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(upload_image.this, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                HashMap<String, String> data = new HashMap<>();
                Log.i("question", question);
                data.put(UPLOAD_KEY, uploadImage);
                Log.i("question", question);
                data.put("question", question);
                Log.i("u", UPLOAD_KEY);
                String result = rh.sendPostRequest(UPLOAD_URL, data);
                Log.i("res", result);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        Log.i("ui", String.valueOf(ui));
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }

        if (v == buttonUpload) {
            uploadImage();
        }
//
//        if(v == buttonView){
//            viewImage();
//        }

    }

//    private void viewImage() {
//        startActivity(new Intent(this, test2.class));
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_write_qus, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {

            case R.id.back:
                startActivity(new Intent(upload_image.this, show_subjectAdmin.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}