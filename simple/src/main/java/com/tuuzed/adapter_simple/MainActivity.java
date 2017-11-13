package com.tuuzed.adapter_simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tuuzed.adapter.ItemProvider;
import com.tuuzed.adapter.RSimpleAdapter;
import com.tuuzed.adapter.ViewHolder;

public class MainActivity extends AppCompatActivity {
    private RSimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = RSimpleAdapter.create()
                .register(String.class, R.layout.item_string, new ItemProvider<String>() {
                    @Override
                    public void onBindViewHolder(@NonNull ViewHolder holder, String item, final int position) {
                        holder.text(R.id.text, "# " + item + ": " + "STRING");
                    }
                })
                .register(Integer.class, R.layout.item_integer, new ItemProvider<Integer>() {
                    @Override
                    public void onBindViewHolder(@NonNull ViewHolder holder, Integer item, int position) {
                        holder.text(R.id.text, "# " + item + ": " + "INTEGER");
                    }
                })
                .register(Long.class, R.layout.item_long, new ItemProvider<Long>() {
                    @Override
                    public void onBindViewHolder(@NonNull ViewHolder holder, Long item, int position) {
                        holder.text(R.id.text, "# " + item + ": " + "LONG");
                    }
                })
                .attach(recyclerView);
        for (int i = 0; i < 50; i++) {
            mAdapter.items().add((long) i);
            mAdapter.items().add(String.valueOf(i));
            mAdapter.items().add(i);
        }
        mAdapter.notifyDataSetChanged();
    }
}
