package io.github.tuuzed.adapter.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.github.tuuzed.adapter.Items;
import io.github.tuuzed.adapter.RecyclerViewAdapter;
import io.github.tuuzed.adapter.simple.itemcomponent.IntegerItemComponent;
import io.github.tuuzed.adapter.simple.itemcomponent.LongItemComponent;
import io.github.tuuzed.adapter.simple.itemcomponent.StringItemComponent;

public class MainActivity extends AppCompatActivity {

    private Items mItems;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItems = new Items();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mItems);
        adapter.register(String.class, new StringItemComponent());
        adapter.register(Integer.class, new IntegerItemComponent());
        adapter.register(Long.class, new LongItemComponent());

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(adapter);
        for (int i = 0; i < 5; i++) {
            mItems.add((long) i);
            mItems.add(String.valueOf(i));
            mItems.add(i);
        }
        adapter.notifyDataSetChanged();

    }
}
