package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.R;
import com.example.uta.entities.User;
import com.example.uta.services.IncidentMonitorActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewModifyStudentActivity extends IncidentMonitorActivity {
    String uid;
    TextView textViewName, textViewEMail, textViewPhone,textViewUTAID;
    EditText editTextName,editTextPhone;
    Button btnUpdateForm, btnUpdate;
    ViewGroup UpdateForm;
    User user;

    DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_modify_student);

        textViewName=findViewById(R.id.tv_name);
        textViewEMail=findViewById(R.id.tv_email);
        textViewPhone=findViewById(R.id.tv_phone);
        textViewUTAID=findViewById(R.id.tv_utaid);

        editTextName=findViewById(R.id.et_name);
        editTextPhone=findViewById(R.id.et_phone);

        btnUpdate=findViewById(R.id.btn_update_profile);
        btnUpdateForm=findViewById(R.id.btn_update_form);
        UpdateForm=findViewById(R.id.update_form);

        btnUpdateForm.setOnClickListener(e->{
            UpdateForm.setVisibility(View.VISIBLE);
        });
        btnUpdate.setOnClickListener(e->{
            updateProfile();
        });
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("title");
        toolbar.setTitle(title);

        uid=getIntent().getStringExtra("uid");
        userRef=FirebaseDatabase.getInstance().getReference("users").child(uid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchUserDetails();
    }

    private void fetchUserDetails(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
                if(user!=null){
                    textViewEMail.setText(user.getEmailId());
                    textViewName.setText(user.getName());
                    textViewPhone.setText(user.getPhoneNo());
                    textViewUTAID.setText(user.getUtaId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewModifyStudentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile(){
        if(user!=null){
            String name=editTextName.getText().toString();
            String phone=editTextPhone.getText().toString();
            if(!name.isEmpty() && !phone.isEmpty()){
                user.setName(name);
                user.setPhoneNo(phone);
                userRef.setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                UpdateForm.setVisibility(View.GONE);
                                Toast.makeText(ViewModifyStudentActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewModifyStudentActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(this, "Please Enter the Details", Toast.LENGTH_SHORT).show();
            }
        }
    }
}