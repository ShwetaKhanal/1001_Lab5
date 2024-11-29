package com.example.giphyapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView recyclerView;
    private GifAdapter adapter;
    private static final String API_KEY = "0wpy0JbePesJmvexpguajOgIGP68k47s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new GifAdapter();
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                searchGIFs(query);
            } else {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchGIFs(String query) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.searchGIFs(API_KEY, query, 25).enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GiphyResponse.Data> gifs = response.body().getData();
                    if (gifs.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No GIFs found", Toast.LENGTH_SHORT).show();
                    }
                    adapter.setGifs(gifs);
                } else {
                    Log.e("MainActivity", "Error fetching data: " + response.message());
                    Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GiphyResponse> call, Throwable t) {
                Log.e("MainActivity", "Failed to fetch data: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
