package com.example.uta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.uta.entities.Faq;
import com.example.uta.entities.FaqAdaper;
import com.example.uta.services.IncidentMonitorActivity;

import java.util.ArrayList;
import java.util.List;

public class FaqReportActivity extends IncidentMonitorActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FaqAdaper(getList()));
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("FAQ Report");
    }

    public List<Faq> getList(){
        String[] strings=getResources().getStringArray(R.array.faq_list);
        List<Faq> list=new ArrayList<>();
        for(int i=0;i<strings.length;i+=2){
            list.add(new Faq(strings[i],strings[i+1]));
        }
        return list;
    }
}