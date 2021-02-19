package com.example.myhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class loginActivity2 extends AppCompatActivity {
    EditText email;
    EditText pass;
    EditText em;
    Button login;
    public static final String filename="login";
    public static final String user="email";
    public static final String password="pass";
   private FirebaseAuth firebaseAuth;
   SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        em=(EditText)findViewById(R.id.emp);
        login=(Button)findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();
        sharedPreferences =getSharedPreferences(filename, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(user)){
            Intent i=new Intent(loginActivity2.this,MainActivity.class);
            startActivity(i);
        }
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String emai =email.getText().toString().trim();
                String pa=pass.getText().toString().trim();
                String ei=em.getText().toString();

                if(email.equals("Admin") && pass.equals("1234")){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(user,emai);
                    editor.putString(password,pa);
                    editor.commit();
                }


                if(TextUtils.isEmpty(emai)){
                    Toast.makeText(loginActivity2.this,"please enter email",Toast.LENGTH_LONG);
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(emai,pa).addOnCompleteListener(loginActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Intent intent =new Intent(loginActivity2.this,MainActivity.class);

                            intent.putExtra("keyname",ei);
                            startActivity(intent);



                        }
                        else{

                        }
                    }
                });
            }
        });



//
    }
    
}
