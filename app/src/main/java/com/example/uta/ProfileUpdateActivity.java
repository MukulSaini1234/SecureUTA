package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.uta.entities.GenConstants;
import com.example.uta.entities.SPManager;
import com.example.uta.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileUpdateActivity extends AppCompatActivity {
    private EditText editTextName, editTextPhone, editTextUTA;
    private RadioButton radioStudent, radioAdmin;
    private Button btnUpdateProfile;

    private FirebaseDatabase dbRef= FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        editTextName=findViewById(R.id.et_name);
        editTextPhone=findViewById(R.id.et_phone);
        editTextUTA=findViewById(R.id.et_utaid);
        radioStudent=findViewById(R.id.radio_student);
        radioAdmin=findViewById(R.id.radio_admin);
        btnUpdateProfile=findViewById(R.id.btn_update_profile);

        radioStudent.setOnClickListener(e->{
            radioStudent.setChecked(true);
            radioAdmin.setChecked(false);
        });
        radioAdmin.setOnClickListener(e->{
            radioAdmin.setChecked(true);
            radioStudent.setChecked(false);
        });

        btnUpdateProfile.setOnClickListener(e->{
            updateProfile();
        });
    }

    private void updateProfile(){
        String name=editTextName.getText().toString();
        String phone=editTextPhone.getText().toString();
        String utaID=editTextUTA.getText().toString();
        String userType="";
        if(radioStudent.isChecked()){
            userType="N";
        }
        if(radioAdmin.isChecked()){
            userType="A";
        }
        if(name.isEmpty()){
            Toast.makeText(this, "Please enter you Name", Toast.LENGTH_SHORT).show();
            return;
        }else if(utaID.isEmpty()){
            Toast.makeText(this, "Please enter your UTA ID", Toast.LENGTH_SHORT).show();
            return;
        }else if(Patterns.PHONE.matcher(phone).matches()==false){
            Toast.makeText(this, "Phone no is not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(userType.isEmpty()){
            Toast.makeText(this, "User Type is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        User user=new User();
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setEmailId(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        user.setName(name);
        user.setPhoneNo(phone);
        user.setUserType(userType);
        user.setUtaId(utaID);
        user.setStatus("P");
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef.getReference("users").child(uid).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProfileUpdateActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileUpdateActivity.this,MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileUpdateActivity.this, "Profile Setup Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}