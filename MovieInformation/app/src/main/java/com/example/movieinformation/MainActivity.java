package com.example.movieinformation;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Variables
    private EditText searchEditText;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Var
        searchEditText = findViewById(R.id.search_edit_text);
        movieRecyclerView = findViewById(R.id.movie_recycler_view);

        // Movie Var
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieRecyclerView.setAdapter(movieAdapter);

        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);

        // Set click listener for search button
        findViewById(R.id.search_button).setOnClickListener(view -> {
            String searchQuery = searchEditText.getText().toString();
            if (!searchQuery.isEmpty()) {
                try {
                    searchMovies(searchQuery);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovies(String searchQuery) throws UnsupportedEncodingException {
        // Encode the search query for URL
        String encodedQuery = URLEncoder.encode(searchQuery, "UTF-8");

        // Build the URL for API request
        String url = "https://www.omdbapi.com/?apikey=6c9862c2&t=" + encodedQuery;

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response and create a Movie object
                            Movie movie = parseMovieData(response);
                            Log.d("MainActivity", movie.toString());
                            // Add the movie to the list and update the UI
                            movieList.clear();
                            movieList.add(movie);
                            movieAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);
    }

    private Movie parseMovieData(JSONObject jsonObject) throws JSONException {
        // Parse JSON response and create a Movie object
        String title = jsonObject.getString("Title");
        String year = jsonObject.getString("Year");
        String rating = jsonObject.getString("Rated");
        String runtime = jsonObject.getString("Runtime");
        String actors = jsonObject.getString("Actors");
        String plot = jsonObject.getString("Plot");
        String posterUrl = jsonObject.getString("Poster");
        final Bitmap[] posterBitmap = new Bitmap[1];

        // Create an ImageRequest to download the weather icon
        ImageRequest imageRequest = new ImageRequest(posterUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // Handle successful download
                        // Save the downloaded icon to the device
                        saveIconToStorage(response, title);

                        Log.d("MovieAda", "response : " + response);
                        // Update the ImageView with the downloaded icon
                        posterBitmap[0] = response;
//                        iconImageView.setImageBitmap(response);
//                        iconImageView.setVisibility(View.VISIBLE);
                    }
                },
                0, 0, ImageView.ScaleType.CENTER, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("ImageRequestError", "Error downloading icon: " + error.getMessage());
                    }
                });

        // Add the ImageRequest to the RequestQueue
        requestQueue.add(imageRequest);

        Log.d("MovieAda", "from main bitmap : " + posterBitmap[0]);
        return new Movie(title, year, rating, runtime, actors, plot, posterUrl, posterBitmap[0]);
    }

    private void saveIconToStorage(Bitmap iconBitmap, String iconName) {
        // Get the directory for storing the icons
        File directory = new File(getFilesDir(), "icons");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Create a file to save the icon
        File iconFile = new File(directory, iconName + ".png");

        try {
            // Write the Bitmap to the file
            FileOutputStream outputStream = new FileOutputStream(iconFile);
            iconBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

