package com.example.uta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uta.entities.HelplineAdapter;
import com.example.uta.services.IncidentMonitorActivity;

import java.util.ArrayList;
import java.util.List;

public class HelplineActivity extends IncidentMonitorActivity {
    RecyclerView recyclerView;
    HelplineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        recyclerView=findViewById(R.id.recyclerview);
        HelplineAdapter adapter=new HelplineAdapter(this,getHelplineNo());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Helpline Numbers");
    }

    public List<String> getHelplineNo(){
        List<String> list=new ArrayList<>();
        String[] names=getResources().getStringArray(R.array.help_line_names);
        String[] values=getResources().getStringArray(R.array.help_line_values);
        for(int i=0;i<names.length && i<values.length;i++){
            list.add(names[i]+ " : "+values[i]);
        }
        return list;
    }
}