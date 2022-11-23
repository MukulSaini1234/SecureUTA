package com.example.uta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uta.entities.EntryPass;
import com.example.uta.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GenerateEntrypassActivity extends AppCompatActivity {
    Button btnGenerate;
    TextView textViewName, textViewEmail, textViewPhone, textViewUTA, textViewPasscode;
    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference root= FirebaseDatabase.getInstance().getReference();
    private boolean passCodeCheck=false;
    private EntryPass entryPass;
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_entrypass);

        btnGenerate=findViewById(R.id.btn_generate_entrypass);
        textViewName=findViewById(R.id.tv_name);
        textViewEmail=findViewById(R.id.tv_email);
        textViewPhone=findViewById(R.id.tv_phone);
        textViewUTA=findViewById(R.id.tv_utaid);
        textViewPasscode=findViewById(R.id.tv_passcode);

        btnGenerate.setOnClickListener(e->generatePasscode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserDetails();
    }

    private void loadUserDetails(){
        String currentDate=sdf.format(new Date());
        root.child("passcodes").child(currentDate).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                entryPass=snapshot.getValue(EntryPass.class);
                if(entryPass!=null){
                    textViewName.setText("Name : "+entryPass.getName());
                    textViewEmail.setText("Email : "+entryPass.getEmail());
                    textViewPhone.setText("Phone No : "+entryPass.getPhone());
                    textViewUTA.setText("UTA ID : "+entryPass.getUtaId());
                    textViewPasscode.setText(entryPass.getPasscode());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GenerateEntrypassActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        root.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GenerateEntrypassActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void generatePasscode(){
        String currentDate=sdf.format(new Date());
        if(user!=null){
           if(entryPass==null){
               EntryPass entryPass=new EntryPass();
               entryPass.setName(user.getName());
               entryPass.setEmail(user.getEmailId());
               entryPass.setPhone(user.getPhoneNo());
               entryPass.setUtaId(user.getUtaId());
               entryPass.setType(user.getUserType());
               String code=""+System.currentTimeMillis();
               entryPass.setPasscode(code);
               root.child("passcodes").child(currentDate).child(uid).setValue(entryPass);
           }else{
               Toast.makeText(this, "Entry Pass already generated", Toast.LENGTH_SHORT).show();
           }
        }else{
            Toast.makeText(this, "User Details not loaded", Toast.LENGTH_SHORT).show();
        }
    }
}