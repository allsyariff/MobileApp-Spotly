package com.example.project1892.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project1892.Domain.ItemDomain;
import com.example.project1892.databinding.ActivityTicketBinding;

public class TicketActivity extends BaseActivity {
    ActivityTicketBinding binding;
    private ItemDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();

        // Menambahkan listener untuk tombol "Start Location"
        binding.button.setOnClickListener(v -> {
            // Ambil latitude dan longitude dari objek ItemDomain
            double latitude = object.getLatitude();
            double longitude = object.getLongitude();

            // Membuat URI untuk Google Maps Navigation
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=d");

            // Membuat intent untuk membuka Google Maps
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            // Memeriksa apakah Google Maps terinstal
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Jika Google Maps tidak terinstal, tampilkan pesan ke pengguna
                Toast.makeText(this, "Google Maps tidak terinstal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVariable() {
        // Load gambar menggunakan Glide
        Glide.with(TicketActivity.this)
                .load(object.getPic())
                .into(binding.pic);

        Glide.with(TicketActivity.this)
                .load(object.getTourGuidePic())
                .into(binding.profile);

        // Set teks dan listener lainnya
        binding.backBtn.setOnClickListener(v -> finish());
        binding.titleTxt.setText(object.getTitle());
        binding.durationTxt.setText(object.getDuration());
        binding.tourGuideTxt.setText(object.getDateTour());
        binding.timeTxt.setText(object.getTimeTour());
        binding.tourGuideNameTxt.setText(object.getTourGuideName());

        // Listener untuk tombol call
        binding.callBtn.setOnClickListener(v -> {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:" + object.getTourGuidePhone()));
            sendIntent.putExtra("sms_body", "type your message");
            startActivity(sendIntent);
        });

        // Listener untuk tombol message
        binding.messageBtn.setOnClickListener(v -> {
            String phone = object.getTourGuidePhone();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        });
    }

    private void getIntentExtra() {
        // Ambil objek ItemDomain dari Intent
        object = (ItemDomain) getIntent().getSerializableExtra("object");
    }
}