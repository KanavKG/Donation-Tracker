package team58.cs2340.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogoutPressed(View view) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Good Bye",Toast.LENGTH_SHORT).show();
    }
}
