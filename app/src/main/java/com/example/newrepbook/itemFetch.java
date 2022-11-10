package com.example.newrepbook;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class itemFetch extends AppCompatActivity {
    ListView listView2;
    List<String> itemFetchList;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasing_materials2);

        listView2 = findViewById(R.id.itemListView);
        itemFetchList = getIntent().getStringArrayListExtra("item");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.textView,itemFetchList);
        listView2.setAdapter(arrayAdapter);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "success",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
