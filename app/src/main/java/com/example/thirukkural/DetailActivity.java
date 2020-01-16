package com.example.thirukkural;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private String id, name, getI, getN;
    private ListView listView;
    private TextView textViewname;
    private DetailViewAdapter detailViewAdapter;
    private ArrayList<athigaram> aName = new ArrayList<athigaram>();
    private String url = "http://192.168.43.11/thirukkural/detailList.php";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Getting value from List Activity
        String[] value = getIntent().getStringArrayExtra("value");
        assert value != null;
        getI = value[0];
        getN = value[1];
        //Getting value from List Activity

        listView = findViewById(R.id.DlistView);
        textViewname = findViewById(R.id.aName);
        textViewname.setText(getI + "." + getN);
        getDetail();
    }

    //Method for Getting Kural from Mysql
    private void getDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("kee", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("kuralList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);
                        id = Object.getString("id");
                        name = Object.getString("kural_name");
                        athigaram a = new athigaram(id, name);
                        aName.add(a);
                    }
                    detailViewAdapter = new DetailViewAdapter(DetailActivity.this, aName);
                    listView.setAdapter(detailViewAdapter);


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
                params.put("athigaram", getN);
                return params;
            }
        };
        MySingleton.getInstance(DetailActivity.this).addToRequest(stringRequest);
    }
    //End Method for Getting Kural Details from Mysql

    //Method for Back Button
    public void Back(View view) {
        startActivity(new Intent(DetailActivity.this, ListActivity.class));
    }
    //End Method for Back Button
}
