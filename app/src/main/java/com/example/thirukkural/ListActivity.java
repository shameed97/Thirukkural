package com.example.thirukkural;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private String id, name;
    private ListView listView;
    private NameViewAdapter nameViewAdapter;
    private ArrayList<athigaram> aName = new ArrayList<athigaram>();
    private String A_url = "http://192.168.43.11/thirukkural/athigaramList.php";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationMenuView navigationMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        getList();
        setNavigation();
    }

    private void getList() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, A_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("kee", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("athigaramList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);
                        id = Object.getString("id");
                        name = Object.getString("athigaram_name");
                        athigaram a = new athigaram(id, name);
                        aName.add(a);
                    }
                    nameViewAdapter = new NameViewAdapter(ListActivity.this, aName);
                    listView.setAdapter(nameViewAdapter);


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
                return params;
            }
        };
        MySingleton.getInstance(ListActivity.this).addToRequest(stringRequest);
    }

    public void setNavigation() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navigationMenuView.addItemDecoration(new DividerItemDecoration(ListActivity.this, DividerItemDecoration.VERTICAL));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.athi:
                        drawerLayout.closeDrawers();
                        break;

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        LinearLayout layout = navigationView.findViewById(R.id.linear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }

            }
        });
    }

    public void Back(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
