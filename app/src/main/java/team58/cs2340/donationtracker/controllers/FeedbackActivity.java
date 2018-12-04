package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import team58.cs2340.donationtracker.R;

public class FeedbackActivity extends AppCompatActivity {

    final static String url = "http://where2trip.herokuapp.com/feedback";
    private static final int SUCCESS_CODE = 200;
    EditText feedbackTxt;
    Button submitBtn;
    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        feedbackTxt = findViewById(R.id.feedbackText);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitFeedback();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void submitFeedback() throws InterruptedException {
        final String feedback = feedbackTxt.getText().toString();
        if (feedback.length() < 5) {
            feedbackTxt.setError("Feedback has to be more than 5 characters!");
            feedbackTxt.requestFocus();
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try  {
                    sendPost(feedback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread postThread = new Thread(runnable);
        postThread.start();
        Thread.sleep(3000);
        if (success) {
            Toast.makeText(FeedbackActivity.this,
                    "Feedback received! Thank you for your inputs!",
                    Toast.LENGTH_LONG).show();
            Intent backIntent = new Intent(FeedbackActivity.this, WelcomeScreenActivity.class);
            startActivity(backIntent);
        } else {
            Toast.makeText(FeedbackActivity.this,
                    "Feedback wasn't received! Please try again later!",
                    Toast.LENGTH_LONG).show();
            Intent backIntent = new Intent(FeedbackActivity.this, WelcomeScreenActivity.class);
            startActivity(backIntent);
        }
    }

    public void sendPost(String feedback) throws IOException {
        String postParam = "{\n" + "\"feedback\": \"" + feedback + "\"" + "\n}";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        OutputStream outputStream =  connection.getOutputStream();
        outputStream.write(postParam.getBytes());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == SUCCESS_CODE) {
            success = true;
        } else {
            success = false;
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        }
    }
}
