package com.example.ticketmaster;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_event_details, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (events != null && position < events.size()) {
            Event event = events.get(position);
            holder.bind(event);
        }
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEventName;
        private TextView textViewStartDate;
        private TextView textViewPriceRange;
        private Button buyTicketsButton;
        private ImageView imageViewEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.event_name);
            textViewStartDate = itemView.findViewById(R.id.event_start_date);
            textViewPriceRange = itemView.findViewById(R.id.event_price_range);
            buyTicketsButton = itemView.findViewById(R.id.event_buy_tickets_btn);
            imageViewEvent = itemView.findViewById(R.id.event_image);

            // Set OnClickListener for the button
            buyTicketsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve the URL associated with the event
                    String url = (String) buyTicketsButton.getTag();
                    if (url != null && !url.isEmpty()) {
                        // Open the URL when the button is clicked
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void bind(Event event) {
            textViewEventName.setText(event.getName());
            textViewStartDate.setText("Start Date: " + event.getStartDate());
            textViewPriceRange.setText("Price Range: " + event.getPriceRange());
            // Load image using Picasso
            Picasso.get().load(event.getImageUrl()).into(imageViewEvent);

            // Set the URL as a tag for the button
            buyTicketsButton.setTag(event.getTicketmasterUrl());
        }
    }
}
