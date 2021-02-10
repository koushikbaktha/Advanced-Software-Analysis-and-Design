package com.asu.bmicalculator.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.asu.bmicalculator.entity.BMIResponseEntity;
import com.asu.bmicalculator.model.BMIServiceApi;
import com.asu.bmicalculator.R;
import com.asu.bmicalculator.model.ServiceFactory;
import com.asu.bmicalculator.model.ServiceInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ServiceInterface bmiService;
    private BMIServiceApi bmiServiceApi;
    private BMIResponseEntity bmiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Service instance from factory
        bmiService = ServiceFactory.getService("bmi");
        bmiServiceApi = bmiService.createServiceAPI();
    }

    /**
     * Method to stop Heart rate motoring
     * called on click of STOP button.
     * @param view
     */
    public void educateMe(View view) {
        double height = readHeight();
        double weight = readWeight();

        if(bmiResponse == null){
            Log.d("", "BMI Response is Null, calling BMI API with default values 0, 0");
            bmiServiceApi.getBMI(height, weight).enqueue(educateMeCallback);
        } else {
            String url = bmiResponse.getMore().get(0);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);

        }
    }

    public void callAPI(View view) {
        double height = readHeight();
        double weight = readWeight();
        bmiServiceApi.getBMI(height, weight).enqueue(resultCallback);
    }

    private void setUIValues(BMIResponseEntity bmiResponse) {
        TextView bmi = (TextView) findViewById(R.id.bmi_value);
        TextView msg = (TextView) findViewById(R.id.msg_value);
        bmi.setText(bmiResponse.getBmi()+"");
        if(bmiResponse.getBmi() < 18){
            msg.setTextColor(Color.parseColor("#FF03A9F4"));
            msg.setText("You are underweight");
        } else if (bmiResponse.getBmi() >= 18 && bmiResponse.getBmi() < 25){
            msg.setTextColor(Color.parseColor("#00C853"));
            msg.setText("You are normal");
        } else if (bmiResponse.getBmi() >= 25 && bmiResponse.getBmi() <= 30){
            msg.setTextColor(Color.parseColor("#FFE91E63"));
            msg.setText("You are Pre-obese");
        } else {
            msg.setTextColor(Color.parseColor("#D84315"));
            msg.setText("You are obese");
        }

    }

    private double readHeight(){
        EditText id = (EditText) findViewById(R.id.height_value);
        String heightString = id.getText().toString();
        double height = heightString != null && !"".equals(heightString) ? Double.parseDouble(heightString) : 1;
        return height;
    }

    private double readWeight(){
        EditText id = (EditText) findViewById(R.id.weight_value);
        String weightString = id.getText().toString();
        double weight = weightString != null && !"".equals(weightString) ? Double.parseDouble(weightString) : 1;
        return weight;
    }

    Callback<BMIResponseEntity> resultCallback = new Callback<BMIResponseEntity>() {
        @Override
        public void onResponse(Call<BMIResponseEntity> call, Response<BMIResponseEntity> response) {
            Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            if (response.isSuccessful()) {
                bmiResponse = response.body();
                System.out.println("Response: "+ bmiResponse.getRisk());
                System.out.println("Response: "+ bmiResponse.getBmi());
                System.out.println("Response: "+ bmiResponse.getMore());
                setUIValues(bmiResponse);
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<BMIResponseEntity> call, Throwable t) {
            t.printStackTrace();
        }
    };

    Callback<BMIResponseEntity> educateMeCallback = new Callback<BMIResponseEntity>() {
        @Override
        public void onResponse(Call<BMIResponseEntity> call, Response<BMIResponseEntity> response) {
            Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            if (response.isSuccessful()) {
                bmiResponse = response.body();


                String url = bmiResponse.getMore().get(0);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);

            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<BMIResponseEntity> call, Throwable t) {
            t.printStackTrace();
        }
    };

}
