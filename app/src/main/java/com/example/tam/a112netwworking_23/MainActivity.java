package com.example.tam.a112netwworking_23;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView mTxtMonthPayment, mTxtUrl, mTxtInfoCalucation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtUrl = (TextView)findViewById(R.id.txt_url);
        mTxtMonthPayment = (TextView)findViewById(R.id.txt_monthPayment);
        mTxtInfoCalucation = (TextView)findViewById(R.id.txt_infoCalution);
        Button btnCaculation = (Button)findViewById(R.id.btn_cacutation);
        btnCaculation.setOnClickListener(new ClickCaculation());
    }

    private class ClickCaculation implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String url = mTxtUrl.getText().toString();
            new InfomationCaculation().execute(url);
        }
    }

    class InfomationCaculation extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... params) {
        JSONObject jsonObject = new JSONObject();
            String jsonString = null;
            try {
                jsonString = HttpUtils.urlContentPost(params[0], "Input", jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null){
                try {
                    mTxtMonthPayment.setText("Month Payment : "+jsonObject.getString("formattedMonthlyPayment"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mTxtInfoCalucation.setText(jsonObject.toString());


            }
        }
    }
}
