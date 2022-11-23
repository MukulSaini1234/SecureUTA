package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.services.IncidentMonitorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MedicalSlotBookingActivity extends IncidentMonitorActivity {
    Spinner spinner;
    EditText editTextDatetime;
    Button btnSearch,btnBookSlot;
    Toolbar toolbar;
    TextView textViewAvailableSlots, textViewBookings;
    int countCovid,countTB,countFlu;
    String[] testTypes;
    String freeKeyCovid, freeKeyTB, freeKeyFlu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_slot_booking);
        editTextDatetime=findViewById(R.id.et_datetime);
        spinner=findViewById(R.id.spinner);
        btnSearch=findViewById(R.id.btn_search);
        btnBookSlot=findViewById(R.id.btn_book_slot);
        textViewAvailableSlots=findViewById(R.id.tv_slots_count);
        textViewBookings=findViewById(R.id.tv_my_bookings);
        testTypes=getResources().getStringArray(R.array.medical_tests);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.medical_tests, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        btnSearch.setOnClickListener(e->searchClicked());
        btnBookSlot.setOnClickListener(e->bookSlot());
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Medical Slot");
    }

    private void bookSlot(){
        String dateTime=editTextDatetime.getText().toString();
        String testType=testTypes[spinner.getSelectedItemPosition()];
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try{
            sdf.parse(dateTime);
        }catch (ParseException ex){
            Toast.makeText(this, "DateFormat is Invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] str=dateTime.split("-");
        if(!(Integer.parseInt(str[2])>=1 && Integer.parseInt(str[2])<=31 &&
                Integer.parseInt(str[1])>=1 && Integer.parseInt(str[1])<=12)){
            Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(!testType.isEmpty() && !dateTime.isEmpty()){
            if((testType.equalsIgnoreCase("COVID") && countCovid>0)){
                FirebaseDatabase.getInstance().getReference("medical_slots").child("COVID").child(freeKeyCovid).setValue(uid);
            }else if(testType.equalsIgnoreCase("FLU") && countFlu>0){
                FirebaseDatabase.getInstance().getReference("medical_slots").child("FLU").child(freeKeyFlu).setValue(uid);
            }else if(testType.equalsIgnoreCase("TB") && countTB>0){
                FirebaseDatabase.getInstance().getReference("medical_slots").child("TB").child(freeKeyTB).setValue(uid);
            }
            Toast.makeText(this, "Booked Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("medical_slots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countCovid=0;
                countFlu=0;
                countTB=0;
                StringBuilder sb=new StringBuilder();
                for(DataSnapshot s:snapshot.child("COVID").getChildren()){
                    if(s.getValue(String.class).isEmpty()){
                        countCovid++;
                        freeKeyCovid=s.getKey();
                    }else if(s.getValue(String.class).indexOf(uid)>=0){
//                        sb.append("\nCOVID: "+s.getValue(String.class).split(" ")[1]);
                    }
                }
                for(DataSnapshot s:snapshot.child("TB").getChildren()){
                    if(s.getValue(String.class).isEmpty()){
                        countTB++;
                        freeKeyTB=s.getKey();
                    }else if(s.getValue(String.class).indexOf(uid)>=0){
//                        sb.append("\nTB: "+s.getValue(String.class).split(" ")[1]);
                    }
                }
                for(DataSnapshot s:snapshot.child("FLU").getChildren()){
                    if(s.getValue(String.class).isEmpty()){
                        countFlu++;
                        freeKeyFlu=s.getKey();
                    }else if(s.getValue(String.class).indexOf(uid)>=0){
//                        sb.append("\nFLU: "+s.getValue(String.class).split(" ")[1]);
                    }
                }
                textViewAvailableSlots.setText("COVID: "+countCovid+", TB: "+countTB+", FLU: "+countFlu);
                textViewBookings.setText(sb.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MedicalSlotBookingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}