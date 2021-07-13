package com.raja.sammy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.raja.sammy.databinding.ActivityPhonenumberBinding;

public class Phonenumber extends AppCompatActivity {

    ActivityPhonenumberBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPhonenumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth =FirebaseAuth.getInstance();

        if (auth.getCurrentUser() !=null){
            Intent intent=new Intent(Phonenumber.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding.phoneBox.requestFocus();
        binding.continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Phonenumber.this, "done", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Phonenumber.this,OTPActivity.class);
                intent.putExtra("phoneNumber",binding.phoneBox.getText().toString());
                startActivity(intent);
            }
        });
    }
}