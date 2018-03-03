package com.gadhvi.extcpl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
private EditText semail,sname,steam,spassword;
private Button sign_up;
private FirebaseAuth mSignup;
private DatabaseReference mCap;
private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        semail = (EditText)findViewById(R.id.email_s);
        sname=(EditText)findViewById(R.id.name_s);
        steam=(EditText)findViewById(R.id.teamname_s);
        spassword=(EditText)findViewById(R.id.password_s);
        sign_up = (Button)findViewById(R.id.submit_s);

        mSignup = FirebaseAuth.getInstance();
        mCap = FirebaseDatabase.getInstance().getReference().child("Captain");
         loading = new ProgressDialog(this);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String emails = semail.getText().toString();
               String passwords = spassword.getText().toString();
               final String names = sname.getText().toString();
               final String team = steam.getText().toString();
               loading.setMessage("Loading.......");
               loading.show();
               mSignup.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {

                         DatabaseReference upload = mCap.child(names);
                         upload.child("TeamName").setValue(team);
                         upload.child("Email").setValue(emails);
                         upload.child("credit").setValue(10000000);

                         loading.dismiss();
                           Intent i1 = new Intent(SignUp.this,MainPage.class);
                           startActivity(i1);
                           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignUp.this);
                           SharedPreferences.Editor edi = prefs.edit();
                           edi.putString("NAME",names);
                           edi.commit();

                       }

                   }
               });


            }
        });


    }
}
