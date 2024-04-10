package com.example.ticketmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = 	"YOUR_API_KEY";
    private final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";

    private EditText editTextCity;
    private EditText editTextRadius;
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editTextCity);
        editTextRadius = findViewById(R.id.editTextRadius);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);

        // Set up RecyclerView
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter();
        recyclerViewEvents.setAdapter(eventAdapter);

        // Set button click listener
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvents(v);
            }
        });
    }

    public void searchEvents(View view) {
        String city = editTextCity.getText().toString();
        String radius = editTextRadius.getText().toString();

        if (city.isEmpty() || radius.isEmpty()) {
            Toast.makeText(this, "Please enter city and radius", Toast.LENGTH_SHORT).show();
            return;
        }

        fetchEvents(city, Integer.parseInt(radius));
    }

    private void fetchEvents(String city, int radius) {
        // Build URL with query parameters
        String url = BASE_URL + "?apikey=" + API_KEY + "&city=" + city + "&radius=" + radius;

        Log.d("Test" , "Url : " + url);

        // Create JsonObjectRequest to fetch events
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse JSON response
                            List<Event> events = parseEvents(response);
                            Log.d("Test" , "Response : " + response.toString());
                            // Update RecyclerView with events
                            eventAdapter.setEvents(events);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(MainActivity.this, "Error fetching events", Toast.LENGTH_SHORT).show();
            }
        });

        // Add JsonObjectRequest to Volley RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private List<Event> parseEvents(JSONObject response) throws JSONException {
        List<Event> events = new ArrayList<>();

        // Parse JSON response and extract event details
        JSONObject embedded = response.getJSONObject("_embedded");
        JSONArray eventsArray = embedded.getJSONArray("events");

        for (int i = 0; i < eventsArray.length(); i++) {
            JSONObject eventObject = eventsArray.getJSONObject(i);

            // Extract event details
            String eventName = eventObject.getString("name");
            Log.d("Test", "Event Name : " + eventName);
            String startDate = eventObject.getJSONObject("dates").getJSONObject("start").getString("localDate");

            String priceRange = "";
            JSONArray priceRanges = eventObject.optJSONArray("priceRanges");
            if (priceRanges != null && priceRanges.length() > 0) {
                JSONObject priceObject = priceRanges.getJSONObject(0);
                double minPrice = priceObject.optDouble("min", 0.0);
                double maxPrice = priceObject.optDouble("max", 0.0);
                priceRange = minPrice + " - " + maxPrice;
            }

            String url = eventObject.getString("url");

            String imageUrl = "";
            JSONArray images = eventObject.optJSONArray("images");
            if (images != null && images.length() > 0) {
                JSONObject imageObject = images.getJSONObject(0);
                imageUrl = imageObject.getString("url");
            }

            // Create Event object and add to list
            Event event = new Event(eventName, startDate, priceRange, url, imageUrl);
            events.add(event);
        }

        return events;
    }


}
