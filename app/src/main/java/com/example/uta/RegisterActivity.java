package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.entities.GenConstants;
import com.example.uta.entities.SPManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    TextView tvLoginForm;

    EditText etName,etEmail,etPhone,etUTAID,etPassword,etConfirm;
    RadioButton radioStudent,radioAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister=findViewById(R.id.btn_register);
        tvLoginForm=findViewById(R.id.btn_login_form);
        etName=findViewById(R.id.et_name);
        etEmail=findViewById(R.id.et_email);
        etPhone=findViewById(R.id.et_phone);
        etUTAID=findViewById(R.id.et_utaid);
        etPassword=findViewById(R.id.et_password);
        etConfirm=findViewById(R.id.et_confirm);

        tvLoginForm.setOnClickListener(e->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });

        btnRegister.setOnClickListener(e->{
            if(etPassword.getText().toString().isEmpty() || etConfirm.getText().toString().isEmpty()) {
                Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
            }else if(etPassword.getText().toString().equals(etConfirm.getText().toString())){
                register();
            }else {
                Toast.makeText(this, "Password And Confirm Password not matched", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
            .addOnSuccessListener(ob->{
                Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, ProfileUpdateActivity.class));
            })
            .addOnFailureListener(exception->{
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            });
    }

}