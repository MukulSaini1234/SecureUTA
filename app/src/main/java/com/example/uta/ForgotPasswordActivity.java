package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private TextView tvLoginForm;
    private Button btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail=findViewById(R.id.et_email);
        tvLoginForm=findViewById(R.id.btn_login_form);
        btnForgotPassword=findViewById(R.id.btn_forgot_password);

        tvLoginForm.setOnClickListener(e->{
            startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
            finish();
        });

        btnForgotPassword.setOnClickListener(e->{
            forgotPassword();
        });
    }

    private void forgotPassword(){
        String email=editTextEmail.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
        }
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ForgotPasswordActivity.this, "Password Reset Link is Send to your Email. \nPlease Check Your Email", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Could not connect to Server. Please Check Your Internet", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}