package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.entities.GenConstants;
import com.example.uta.entities.SPManager;
import com.example.uta.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvRegisterForm, tvForgotPassword;

    EditText editTextEmail,editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvRegisterForm=findViewById(R.id.btn_register_form);
        tvForgotPassword=findViewById(R.id.btn_forgot_password);
        btnLogin=findViewById(R.id.btn_login);
        editTextEmail=findViewById(R.id.et_email);
        editTextPassword=findViewById(R.id.et_password);

        tvRegisterForm.setOnClickListener(e->{
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        });
        tvForgotPassword.setOnClickListener(e->{
            startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(e->{
            login();
        });

    }

    private void login(){
        String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email or Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValidEmail=Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(!isValidEmail){
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnSuccessListener(new OnSuccessListener(){
                @Override
                public void onSuccess(Object o) {
                    checkUserStateAndGotoNextPage();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void checkUserStateAndGotoNextPage(){
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference uuidRef= FirebaseDatabase.getInstance().getReference("users").child(uid).getRef();
        uuidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user==null){
                    startActivity(new Intent(LoginActivity.this, ProfileUpdateActivity.class));
                    finish();
                }else if(user.getUserType().equalsIgnoreCase("A")|| user.getUserType().equalsIgnoreCase("N")){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error while loading user details", Toast.LENGTH_SHORT).show();
                System.out.println("FirebaseError : "+error.getMessage());
            }
        });
    }


}