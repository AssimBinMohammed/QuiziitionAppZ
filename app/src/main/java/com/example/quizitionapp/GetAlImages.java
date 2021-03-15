package com.example.quizitionapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAlImages {

    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static String[] question;


    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    private String json;
    private JSONArray urls;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
            Log.i("url", String.valueOf(urls));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            Log.i("lolololp", String.valueOf(url));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];

        imageURLs = new String[urls.length()];

        question = new String[urls.length()];


        for (int i = 0; i < urls.length(); i++) {
            JSONObject jo=null;
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i] = getImage(jsonObject);

            Log.i("len", String.valueOf(urls.getJSONObject(i)));


            Log.i("question", String.valueOf(question));
            question[i]=urls.getJSONObject(i).getString("question");




        }
    }
}