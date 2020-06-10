package com.example.addressbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    private Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;

    private int mIndexCount;
    private int mIndex = 0;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Details");
        //initialize Database
        new DbDao(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPerson.class);
                startActivity(intent);
            }
        });
        getImages();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Exit) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");


        mImageUrls.add(R.drawable.ic_edit_foreground);
        mNames.add("Trondheim");

        mImageUrls.add(R.mipmap.ic_choose_image);
        mNames.add("Portugal");

        mImageUrls.add(R.mipmap.ic_camera);
        mNames.add("Rocky Mountain National Park");

    }

    public void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");

        recyclerView = findViewById(R.id.recycler_view_holder);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter view = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(view);




    }


}
