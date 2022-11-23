package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.entities.FaqAdaper;
import com.example.uta.entities.GenConstants;
import com.example.uta.entities.Incident;
import com.example.uta.entities.SPManager;
import com.example.uta.entities.User;
import com.example.uta.services.IncidentMonitorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.properties.ReadOnlyProperty;

public class MainActivity extends IncidentMonitorActivity {
    private String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference userRef=FirebaseDatabase.getInstance().getReference("users").child(uid);
    private User user;
    private Button StudentList,FAQ,Profile,ReportFire,Imergency,Logout, GeneratePass, BookSlot;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudentList=findViewById(R.id.btn_student_list);
        BookSlot=findViewById(R.id.btn_book_slot);
        FAQ=findViewById(R.id.btn_faq);
        Profile=findViewById(R.id.btn_profile);
        ReportFire=findViewById(R.id.btn_report_fire_incident);
        Imergency=findViewById(R.id.btn_imergency_help);
        Logout=findViewById(R.id.btn_signout);
        GeneratePass=findViewById(R.id.btn_generate_entrypass);

        StudentList.setVisibility(View.GONE);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StudentList.setOnClickListener(e->{
            startActivity(new Intent(this,StudentListActivity.class));
        });
        Profile.setOnClickListener(e->{
            Intent intent=new Intent(MainActivity.this, ViewModifyStudentActivity.class);
            intent.putExtra("uid",user.getUid());
            intent.putExtra("title","My Profile");
            startActivity(intent);
        });
        Imergency.setOnClickListener(e->{
            startActivity(new Intent(MainActivity.this,HelplineActivity.class));
        });
        Logout.setOnClickListener(e->logout());
        GeneratePass.setOnClickListener(e->{
            startActivity(new Intent(MainActivity.this, GenerateEntrypassActivity.class));
        });
        ReportFire.setOnClickListener(e->{
            Intent intent=new Intent(this,ReportIncidentActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        });
        FAQ.setOnClickListener(e->{
            startActivity(new Intent(MainActivity.this, FaqReportActivity.class));
        });

        BookSlot.setOnClickListener(e->{
            startActivity(new Intent(MainActivity.this,MedicalSlotBookingActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForUserSetup();
    }

    private void checkForUserSetup(){
       userRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
                if(user!=null){
                    if(user.getUserType().equalsIgnoreCase("A")){
                        toolbar.setTitle("Admin Dashboard");
                        StudentList.setVisibility(View.VISIBLE);
                    }else{
                        toolbar.setTitle("User Dashboard");
                    }
                }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(MainActivity.this, "Error while loading user details", Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout: logout(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}