package com.example.scryptan.db;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Get getService;
    private Post postService;
    String request;
    EditText requestED;
    List<User> user;
    ListView listView;
    HashMap<String,String> baseHs;
    ArrayList<HashMap<String,String>> base;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getService = ApiUtils.getService();
        postService = ApiUtils.postService();
        requestED = (EditText) findViewById(R.id.requestED);
        user = new ArrayList<>();
        base = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        setListener();
    }

    public void sendGet(View v){
        loadAnswers();
    }
    public void sendPost(View v){
        postAnswer();
    }

    public void loadAnswers() {
        user = new ArrayList<User>();
        getService.getList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    try {
                        base = new ArrayList<HashMap<String, String>>();
                        user.addAll(response.body());
                        Log.d("MainAlctivity", "posts loaded from API");
                        if (user.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "EMPTY", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < user.size(); i++) {
                                baseHs = new HashMap<String, String>();
                                baseHs.put("id", user.get(i).id.toString());
                                baseHs.put("Name", user.get(i).name.toString());
                                base.add(baseHs);
                            }
                            setAdapter();
                        }
                    } catch (Exception e) {
                        Log.e("ERROR", "onResponse: " + e.toString());
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
    public void postAnswer() {

        request = requestED.getText().toString();
        postService.fetchUser(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("MainActivity", "post loading from API");
                    Toast.makeText(getApplicationContext(),"SENDED",Toast.LENGTH_SHORT).show();
                    loadAnswers();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });

    }
    public void setAdapter(){
        String[] from = {"id","Name"};
        int[] to = {R.id.idTV,R.id.textTV};
        SimpleAdapter adapter = new SimpleAdapter(this,base,R.layout.adapter_item,from,to);
        listView.setAdapter(adapter);
    }

    public void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID = base.get(position).get("id");
                String name = base.get(position).get("Name");
                Intent it = new Intent(MainActivity.this,EditActivity.class);
                it.putExtra("id",ID);
                it.putExtra("name",name);
                startActivity(it);
            }
        });
    }
}
