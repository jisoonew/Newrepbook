package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class material_push extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    List<String> title_list, item_list;
    ArrayAdapter<String> arrayAdapter;
    Model model;
    ArrayList<Model> itemlist = new ArrayList<Model>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasing_materials);

        listView = findViewById(R.id.listView);

        title_list = new ArrayList<>();
        item_list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("listview");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.textView,title_list); // 이걸로 원하는 정보 끌어오기

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    model=d.getValue(Model.class);
                    title_list.add(model.getTitle());
                    itemlist.add(model);
                }
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(material_push.this, itemFetch.class);

                        Model sublistviewList = null;
                        for (Model m:itemlist)
                        {
                            if(m.getTitle().equals(title_list.get(position)))
                            {
                                sublistviewList=m;
                                break;
                            }
                        }
                        ArrayList<String> subitemListFinal = new ArrayList<>();
                        subitemListFinal.add(sublistviewList.getItem1());
                        subitemListFinal.add(sublistviewList.getItem2());
                        subitemListFinal.add(sublistviewList.getItem3());
                        subitemListFinal.add(sublistviewList.getTitle());

                        intent.putStringArrayListExtra("item", subitemListFinal);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
