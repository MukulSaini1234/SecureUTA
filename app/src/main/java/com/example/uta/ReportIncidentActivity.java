package com.example.uta;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uta.entities.GenConstants;
import com.example.uta.entities.Incident;
import com.example.uta.entities.User;
import com.example.uta.services.IncidentMonitorActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportIncidentActivity extends IncidentMonitorActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    EditText Name,Email,Phone,UtaId, Location, Datetime, Remarks;
    Button btnSubmit;
    User user;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final String TAG="ReportIncidentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        user=(User)getIntent().getSerializableExtra("user");

        Name=findViewById(R.id.et_name);
        Email=findViewById(R.id.et_email);
        Phone=findViewById(R.id.et_phone);
        UtaId=findViewById(R.id.et_utaid);
        Datetime=findViewById(R.id.et_datetime);
        Location=findViewById(R.id.et_location);
        Remarks=findViewById(R.id.et_remarks);
        btnSubmit=findViewById(R.id.btn_report_fire_incident);
        btnSubmit.setOnClickListener(e->reportFireIncident());

        Name.setText(user.getName());
        Email.setText(user.getEmailId());
        Phone.setText(user.getPhoneNo());
        UtaId.setText(user.getUtaId());
        Datetime.setText(sdf2.format(new Date()));
//        Location.setText(user.getName());
//        Remarks.setText(user.getName());
    }

    private void reportFireIncident(){
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String phone=Phone.getText().toString();
        String utaId=UtaId.getText().toString();
        String datetime=Datetime.getText().toString();
        String location=Location.getText().toString();
        String remarks=Remarks.getText().toString();

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || utaId.isEmpty() || datetime.isEmpty() || location.isEmpty()){
            Toast.makeText(this, "Please fill the Details", Toast.LENGTH_SHORT).show();
            return;
        }else{
            try{
                sdf2.parse(datetime);
            }catch (ParseException ex){
                Toast.makeText(this, "Date format is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Incident incident=new Incident();
        incident.setSenderName(name);
        incident.setSenderEmail(email);
        incident.setSendertype(user.getUserType());
        incident.setSenderPhone(phone);
        incident.setSenderUtaId(utaId);
        incident.setDatetime(datetime);
        incident.setLocation(location);
        incident.setRemarks(remarks);
        incident.setStatus(GenConstants.STATUS_PENDING);
        incident.setIncidentId(sdf.format(new Date()));

        FirebaseDatabase.getInstance().getReference("incidents")
                .setValue(incident)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ReportIncidentActivity.this, "Incident reported Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReportIncidentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}