package com.example.manageit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button forgotbtn;
    EditText reEmail;
    String email;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth=FirebaseAuth.getInstance();

        reEmail=findViewById(R.id.RePass);
        forgotbtn=findViewById(R.id.button);

        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentails();
            }

            private void checkCredentails() {
               email= reEmail.getText().toString();
             if(email.isEmpty())
             {
                 reEmail.setError("Required ");
             }
             else
             {
                 forgetpass();
             }
            }

            private void forgetpass() {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ResetPasswordActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(ResetPasswordActivity.this, "Error:" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        });
    }
}