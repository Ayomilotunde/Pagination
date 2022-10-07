package com.ayomi.pagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainData> mainDataArrayList = new ArrayList<>();
    MainAdapter mainAdapter;
    int page =1, limit = 10;
    ArrayList<MainData> batch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);

        mainAdapter = new MainAdapter(mainDataArrayList, this, this);
        getData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);


        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                limit++;
                progressBar.setVisibility(View.VISIBLE);
                if (limit < 10) {
                    // on below line we are again calling
                    // a method to load data in our array list.
                    getData();
                }
            }
        });

    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(ScalarsConverterFactory.create()).build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);
        Call<String> call = mainInterface.STRING_CALL();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseResult(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseResult(JSONArray jsonArray) throws JSONException {
        for (int i =0; i<jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MainData data = new MainData();
                data.setImage(jsonObject.getString("userId"));
                data.setName(jsonObject.getString("title"));
                data.setName1(jsonObject.getString("body"));

                mainDataArrayList.add(data);

//                Log.d("AllJsonData", data.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int ii = 0; ii < mainDataArrayList.size();ii++) {
                batch.add(mainDataArrayList.get(ii));
                if (batch.size() % limit == 0 || ii == (mainDataArrayList.size()-1)) {
                    //ToDo Process the batch;

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    MainData data = new MainData();
                    data.setImage(jsonObject.getString("userId"));
                    data.setName(jsonObject.getString("title"));
                    data.setName1(jsonObject.getString("body"));
                    batch = new ArrayList<>();
                    batch.add(data);

                }
            }
            Log.d("AllJsonData", String.valueOf(batch));
            mainAdapter = new MainAdapter(batch, MainActivity.this, MainActivity.this);
            recyclerView.setAdapter(mainAdapter);

            }

        }
    }


