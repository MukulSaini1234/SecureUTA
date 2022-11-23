package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.uta.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStateAndGotoNextPage();
    }

    private void checkUserStateAndGotoNextPage(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
            return;
        }
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference uuidRef= FirebaseDatabase.getInstance().getReference("users").child(uid).getRef();
        uuidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user==null){
                    startActivity(new Intent(SplashScreenActivity.this, ProfileUpdateActivity.class));
                    finish();
                }else if(user.getUserType().equalsIgnoreCase("A")|| user.getUserType().equalsIgnoreCase("N")){
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashScreenActivity.this, "Error while loading user details", Toast.LENGTH_SHORT).show();
                System.out.println("FirebaseError : "+error.getMessage());
            }
        });
    }
}