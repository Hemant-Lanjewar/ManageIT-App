package com.example.manageit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {
TextView btn;
    CheckBox checkBox;
private EditText inputUsername , inputPassword,inputEmail ,inputConfirmPassword;
Button btnRegister;
private FirebaseAuth mAuth;
private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);


         btn=findViewById(R.id.alreadyHaveAccount);
         inputUsername=findViewById(R.id.inputEmail);
         inputEmail=findViewById(R.id.inputPass);
         inputPassword=findViewById(R.id.inputPassword);
         inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
         mAuth=FirebaseAuth.getInstance();
         mLoadingBar=new ProgressDialog(RegisterActivity.this);
         btnRegister=findViewById(R.id.btnRegister);

         checkBox=findViewById(R.id.checkBox2);
         checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if(b)
                 {
                     inputConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                 }
                 else
                 {
                     inputConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                 }
             }
         });


         btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 checkCredentials();
             }
         });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void checkCredentials() {

        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();

        if(username.isEmpty()||username.length()<7)
        {
            showError(inputUsername,"Username Must Contain more than seven characters!");
        }
        else if(email.isEmpty()||!email.contains("@"))
        {
            showError(inputEmail,"Invalid Email!");
        }
        else if(password.isEmpty()||password.length()<7)
        {
            showError(inputPassword,"Password must be more than seven characters");
        }
        else if(confirmPassword.isEmpty()||!confirmPassword.equals(password))
        {
            showError(inputConfirmPassword,"Password not matched");
        }
        else
        {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please Wait While checking your Credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        mLoadingBar.dismiss();



                    }

                    else
                    {
                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();

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