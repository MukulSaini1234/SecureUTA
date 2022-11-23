package com.example.uta.services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uta.IncidentInfoActivity;
import com.example.uta.entities.GenConstants;
import com.example.uta.entities.Incident;
import com.example.uta.entities.SPManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IncidentMonitorActivity extends AppCompatActivity{
    ValueEventListener eventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Incident incident=snapshot.getValue(Incident.class);
                if(incident!=null && incident.getStatus().equalsIgnoreCase(GenConstants.STATUS_PENDING)){
                    String status=SPManager.getInstance(getApplicationContext()).getString(incident.getIncidentId(),null);
                    if(status==null){
                        Intent intent=new Intent(IncidentMonitorActivity.this, IncidentInfoActivity.class);
                        intent.putExtra("incident",incident);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(IncidentMonitorActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("incidents").addValueEventListener(eventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference("incidents").removeEventListener(eventListener);
    }
}
