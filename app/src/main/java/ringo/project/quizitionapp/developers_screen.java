package ringo.project.quizitionapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class developers_screen extends AppCompatActivity {
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_screen);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            startActivity(new Intent(developers_screen.this, MainActivity.class));
        }); }}