package com.example.manageit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
TextView btn;
EditText inputEmail,inputPass;
Button btnLogin;
TextView btnForgotpass;
private FirebaseAuth mAuth;
ProgressDialog mLoadingbar;
CheckBox chbx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        inputEmail=findViewById(R.id.inputEmail);
        inputPass=findViewById(R.id.inputPass);
        btnLogin=findViewById(R.id.btnlogin);

        chbx=findViewById(R.id.checkBox);
        chbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    inputPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else
                {
                    inputPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //anima
        mAuth=FirebaseAuth.getInstance();
        mLoadingbar=new ProgressDialog(LoginActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });
        btnForgotpass=findViewById(R.id.forgotpassword);
        btnForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

         btn=findViewById(R.id.Signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


    }

    private void checkCredentials() {
        String email=inputEmail.getText().toString();
        String pass=inputPass.getText().toString();

        if(email.isEmpty()||!email.contains("@"))
        {
            showError(inputEmail,"Invalid Email!!");
        }
        else if(pass.isEmpty()||pass.length()<7)
        {
            showError(inputPass,"Password must be more than 7 Characters");
        }
        else
        {
           mLoadingbar.setTitle("Login");
           mLoadingbar.setMessage("Please Wait  While check your Credentials");
           mLoadingbar.setCanceledOnTouchOutside(false);
           mLoadingbar.show();

           mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful())
                   {
                       mLoadingbar.dismiss();
                       Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent);

                   }
                   else
                   {
                       Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                       mLoadingbar.dismiss();
                   }
               }
           });

        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}