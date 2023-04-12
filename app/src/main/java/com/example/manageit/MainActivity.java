package com.example.manageit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth mAuth;
    TextView txtnote,txtpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseUser firebaseUser=mAuth.getCurrentUser();


        txtnote=findViewById(R.id.Notes);
        btnLogout=findViewById(R.id.btnLogout);
        mAuth=FirebaseAuth.getInstance();
        txtpass=findViewById(R.id.passwordbx);
        txtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PasswordActivity.class);
                startActivity(intent);
            }
        });
        txtnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,NotesActivity.class);
                startActivity(intent);
            }
        });

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              mAuth.signOut();
//                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//            }
//        });
    }
}