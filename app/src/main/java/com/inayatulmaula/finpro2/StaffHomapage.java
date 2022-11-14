package com.inayatulmaula.finpro2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inayatulmaula.finpro2.databinding.ActivityStaffHomapageBinding;

import java.util.ArrayList;
import java.util.List;

public class StaffHomapage extends AppCompatActivity {

    private ActivityStaffHomapageBinding binding;
    private FirebaseAuth auth;

    FirebaseDatabase database;
    FirebaseUser user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffHomapageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        DatabaseReference reference = database.getReference();

        auth = FirebaseAuth.getInstance();
        checkUser();

        reference.child("Staff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child(uid).child("name").getValue(String.class);
                binding.shName.setText(username);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnLogoutStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null){
            startActivity(new Intent(this, StaffLogin.class));
            finish();
        }
    }

}