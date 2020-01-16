package com.example.thirukkural;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MeaningActivity extends AppCompatActivity {

    private String id, kural, name;
    private String[] value;
    private TextView textViewkural, textViewdetail;
    private String k_url = "http://192.168.43.11/thirukkural/kuralList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);

        value = getIntent().getStringArrayExtra("value");
        assert value != null;
        id = value[0];
        kural = value[1];
        textViewkural = findViewById(R.id.kural);
        textViewdetail = findViewById(R.id.aDetail);
        textViewkural.setText(kural);
        getDetail();

    }

    //Method for Getting Kural Details from Mysql
    private void getDetail() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, k_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("kee", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("DetailList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);
                        name = Object.getString("detail_name");
                        textViewdetail.setText(name);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("kee", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kee", error.toString());
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Log.d("kee", "working");
                params.put("id", id);
                return params;
            }
        };
        MySingleton.getInstance(MeaningActivity.this).addToRequest(stringRequest);
    }
    //End Method for Getting Kural Details from Mysql


}



