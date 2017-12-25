package tuuzed.sample.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tuuzed.lib.adapter.ItemConverter;
import tuuzed.lib.adapter.RecyclerViewAdapter;

public class SampleActivity extends AppCompatActivity {

    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter()
                .register(String.class, new ItemConverter<String>(R.layout.item_string) {
                    @Override
                    public void onConvert(@NonNull RecyclerViewAdapter.ViewHolder holder, String item, int position) {
                        holder.text(R.id.text, "# " + item + ": " + "STRING");
                    }
                })
                .register(Integer.class, new ItemConverter<Integer>(R.layout.item_integer) {
                    @Override
                    public void onConvert(@NonNull RecyclerViewAdapter.ViewHolder holder, Integer item, int position) {
                        holder.text(R.id.text, "# " + item + ": " + "INTEGER");
                    }
                })
                .register(Long.class, new ItemConverter<Long>(R.layout.item_long) {
                    @Override
                    public void onConvert(@NonNull RecyclerViewAdapter.ViewHolder holder, Long item, int position) {
                        holder.text(R.id.text, "# " + item + ": " + "LONG");
                    }
                })
                .attach(recyclerView);
        for (int i = 0; i < 10; i++) {
            mRecyclerViewAdapter.items().add((long) i);
            mRecyclerViewAdapter.items().add(String.valueOf(i));
            mRecyclerViewAdapter.items().add(i);
        }
        mRecyclerViewAdapter.notifyDataSetChanged();
    }
}
