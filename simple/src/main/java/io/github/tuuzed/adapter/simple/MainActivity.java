package io.github.tuuzed.adapter.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.github.tuuzed.adapter.Items;
import io.github.tuuzed.adapter.RecyclerViewAdapter;
import io.github.tuuzed.adapter.simple.provider.IntegerItemProvider;
import io.github.tuuzed.adapter.simple.provider.LongItemProvider;
import io.github.tuuzed.adapter.simple.provider.StringItemProvider;

public class MainActivity extends AppCompatActivity {

    private Items mItems;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(mItems = new Items());
        mAdapter.register(String.class, new StringItemProvider());
        mAdapter.register(Integer.class, new IntegerItemProvider());
        mAdapter.register(Long.class, new LongItemProvider());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 5; i++) {
            mItems.add((long) i);
            mItems.add(String.valueOf(i));
            mItems.add(i);
        }
        mAdapter.notifyDataSetChanged();
    }
}
