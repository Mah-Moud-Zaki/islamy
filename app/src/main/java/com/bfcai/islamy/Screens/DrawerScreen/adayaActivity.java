package com.bfcai.islamy.Screens.DrawerScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bfcai.islamy.Adapter.MuslimDoaaAdapter;
import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.Model.MuslimDoaa;
import com.bfcai.islamy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class adayaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<MuslimDoaa> doaas;
    private static String JSON_URL = "https://muslim-api.herokuapp.com/adayaApi";
    MuslimDoaaAdapter adapter;
    Toolbar toolbar;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaya);
        toolbar = findViewById(R.id.adayaToolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.adayaProgress);

        recyclerView = findViewById(R.id.adayaList);
        doaas = new ArrayList<>();

        extractSongs();
    }

    private void extractSongs() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject songObject = response.getJSONObject(i);

                        MuslimDoaa doaa = new MuslimDoaa();
                        doaa.setTitle(songObject.getString("title").toString());
                        doaa.setSubtitle(songObject.getString("subtitle".toString()));
                        //doaa.setNum(songObject.getString("num".toString()));
                        progressBar.setVisibility(View.INVISIBLE);

                        doaas.add(doaa);

                    } catch (JSONException e) {
                        Toast toast=Toast.makeText(getApplicationContext(),"JSON EXCEPTION e ERROR",Toast.LENGTH_SHORT);
                        toast.show();
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new MuslimDoaaAdapter(getApplicationContext(),doaas,getBaseContext());
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast=Toast.makeText(getApplicationContext(),"Fetch data failed",Toast.LENGTH_LONG);
                toast.show();
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }
   @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}