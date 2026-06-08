package com.example.material_design;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        // 🔥 1) Preberemo število stolpcev iz integers.xml (portrait/landscape/tablet)
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // 🔥 2) Nastavimo GridLayoutManager namesto LinearLayoutManager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        mSportsData = new ArrayList<>();
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();

        // 🔥 3) Swipe omogočen samo, če je 1 stolpec (portrait)
        int swipeDirs;
        if (gridColumnCount > 1) {
            swipeDirs = 0; // disable swipe
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        // 🔥 4) ItemTouchHelper (drag & drop vedno, swipe samo v portrait)
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                        swipeDirs) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        int from = viewHolder.getAdapterPosition();
                        int to = target.getAdapterPosition();
                        Collections.swap(mSportsData, from, to);
                        mAdapter.notifyItemMoved(from, to);
                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        mSportsData.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                });

        helper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::resetSports);
    }

    private void initializeData() {
        String[] sportsTitles = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportsImages = getResources().obtainTypedArray(R.array.sports_images);

        mSportsData.clear();

        for (int i = 0; i < sportsTitles.length; i++) {
            int imageResId = sportsImages.getResourceId(i, 0);
            mSportsData.add(new Sport(sportsTitles[i], sportsInfo[i], imageResId));
        }

        sportsImages.recycle();
        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
    }
}
