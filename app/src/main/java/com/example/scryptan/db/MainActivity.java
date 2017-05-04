package com.example.scryptan.db;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    }

    public void sendGet(View v){
        new MyAsyncTask().execute();
    }
    public void sendPost(View v){
        new MyAsyncTask1().execute();
    }
    class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            loadAnswers();
            return null;
        }
    }

    class MyAsyncTask1 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            postAnswer();
            return null;
        }
    }

    public void loadAnswers() {
        user = new ArrayList<User>();
        getService.getList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    try {
                        user.addAll(response.body());
                        Log.d("MainAlctivity", "posts loaded from API");
                        if(user.isEmpty()){
                            Toast.makeText(getApplicationContext(),"EMPTY",Toast.LENGTH_SHORT).show();
                        }else{
                            for (int i = 0; i <user.size() ; i++) {
                                baseHs = new HashMap<String, String>();
                                baseHs.put("id",user.get(i).id.toString());
                                baseHs.put("Name",user.get(i).name.toString());
                                base.add(baseHs);
                            }
                            setAdapter();
                            base = new ArrayList<HashMap<String, String>>();
                        }
                    }catch (Exception e){
                        Log.e("ERROR", "onResponse: "+e.toString() );
                    }
                }else {
                    int statusCode  = response.code();
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
        postService.fetchUser(request).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Toast.makeText(getApplicationContext(),"SENDED",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
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
}
