package com.tuuzed.adapter.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tuuzed.adapter.RecyclerViewAdapter;
import com.tuuzed.adapter.SimpleItemProvider;
import com.tuuzed.adapter.ViewHolder;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(mRecyclerView);
        mAdapter.register(String.class, new SimpleItemProvider<String>(R.layout.item_string) {
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item, int position) {
                holder.$(R.id.text, TextView.class).setText(item + ": " + "STRING");
            }
        });
        mAdapter.register(Integer.class, new SimpleItemProvider<Integer>(R.layout.item_integer) {
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Integer item, int position) {
                holder.$(R.id.text, TextView.class).setText(item + ": " + "INTEGER");
            }
        });
        mAdapter.register(Long.class, new SimpleItemProvider<Long>(R.layout.item_long) {
            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Long item, int position) {
                holder.$(R.id.text, TextView.class).setText(item + ": " + "LONG");
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 50; i++) {
            mAdapter.items().add((long) i);
            mAdapter.items().add(String.valueOf(i));
            mAdapter.items().add(i);
        }
        mAdapter.notifyDataSetChanged();
    }
}
