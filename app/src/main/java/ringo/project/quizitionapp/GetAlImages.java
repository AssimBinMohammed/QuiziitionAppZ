package ringo.project.quizitionapp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class GetAlImages {
    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static String[] Question;
    public static String right_answer[];
    public static String option1[];
    public static String option2[];
    public static String option3[];
    public static String option4[];
    public static String subject[];
    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    private JSONArray urls;
    public GetAlImages(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        } }
    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }
    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];
        imageURLs = new String[urls.length()];
        Question = new String[urls.length()];
        right_answer = new String[urls.length()];
        option1 = new String[urls.length()];
        option2 = new String[urls.length()];
        option3 = new String[urls.length()];
        option4 = new String[urls.length()];
        subject = new String[urls.length()];
        for (int i = 0; i < urls.length(); i++) {
            JSONObject jo = null;
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i] = getImage(jsonObject);
            Question[i] = urls.getJSONObject(i).getString("Question");
            option1[i] = urls.getJSONObject(i).getString("option1");
            option2[i] = urls.getJSONObject(i).getString("option2");
            option3[i] = urls.getJSONObject(i).getString("option3");
            option4[i] = urls.getJSONObject(i).getString("option4");
            right_answer[i] = urls.getJSONObject(i).getString("right_answer");
            subject[i] = urls.getJSONObject(i).getString("Subject");
        }}}