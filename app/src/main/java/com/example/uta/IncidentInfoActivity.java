package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.entities.Incident;
import com.example.uta.entities.SPManager;
import com.example.uta.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IncidentInfoActivity extends AppCompatActivity {
    private TextView textViewName, textViewEmail, textViewUtaId, textViewPhone,textViewType, textViewDatetime, textViewLocation, textViewRemarks;
    private Incident incident;
    private Button playSound,stopSound;
    private MediaPlayer mediaPlayer;
    private Button btnClose;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_info);

        mediaPlayer = MediaPlayer.create(this, R.raw.fire_siren);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fire Incident Information");
        textViewName=findViewById(R.id.tv_name);
        textViewEmail=findViewById(R.id.tv_email);
        textViewPhone=findViewById(R.id.tv_phone);
        textViewUtaId=findViewById(R.id.tv_utaid);
        textViewType=findViewById(R.id.tv_usertype);
        textViewDatetime=findViewById(R.id.tv_datetime);
        textViewLocation=findViewById(R.id.tv_location);
        textViewRemarks=findViewById(R.id.tv_remarks);
        btnClose=findViewById(R.id.btn_close);

        playSound=findViewById(R.id.btn_play_sound);
        stopSound=findViewById(R.id.btn_stop_sound);

        playSound.setOnClickListener(e->playSound());
        stopSound.setOnClickListener(e->stopSound());

        incident =(Incident) getIntent().getSerializableExtra("incident");
        textViewName.setText(incident.getSenderName());
        textViewEmail.setText(incident.getSenderEmail());
        textViewPhone.setText(incident.getSenderPhone());
        textViewUtaId.setText(incident.getSenderUtaId());
        textViewType.setText(incident.getSendertype());
        textViewDatetime.setText(incident.getDatetime());
        textViewLocation.setText(incident.getLocation());
        textViewRemarks.setText(incident.getRemarks());
        String status=SPManager.getInstance(this).getString(incident.getIncidentId(),null);
        if(status==null){
            playSound();
        }

        btnClose.setOnClickListener(e->closeIncident());
    }

    private void playSound(){
        mediaPlayer.start();
    }

    private void stopSound(){
        SPManager.getInstance(this).edit().putString(incident.getIncidentId(),incident.getIncidentId()).commit();
        String status=SPManager.getInstance(this).getString(incident.getIncidentId(),null);
        if(status!=null){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        if(user!=null && user.getUserType().equalsIgnoreCase("A")){
                            btnClose.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(IncidentInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void closeIncident(){
        FirebaseDatabase.getInstance().getReference("incidents").setValue(null);
    }
}