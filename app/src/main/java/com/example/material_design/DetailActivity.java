package com.example.material_design;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleDetail = findViewById(R.id.titleDetail);
        TextView subTitleDetail = findViewById(R.id.subTitleDetail);
        ImageView sportsImageDetail = findViewById(R.id.sportsImageDetail);

        String title = getIntent().getStringExtra("title");
        String info = getIntent().getStringExtra("info");
        int imageRes = getIntent().getIntExtra("image_resource", 0);

        titleDetail.setText(title);
        subTitleDetail.setText(info);

        Glide.with(this)
                .load(imageRes)
                .into(sportsImageDetail);
    }
}
