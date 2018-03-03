package com.gadhvi.extcpl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email,password;
    private  Button login;
    private TextView signup;
    FirebaseAuth mAuth;
    ProgressDialog p1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          email = (EditText)findViewById(R.id.email_p);
          password = (EditText)findViewById(R.id.password_p);
          login = (Button)findViewById(R.id.sub_p);
          signup = (TextView)findViewById(R.id.signup);
          p1 = new ProgressDialog(this);
          mAuth = FirebaseAuth.getInstance();
          login.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
              String cemail = email.getText().toString();
              String cpass = password.getText().toString();
              p1.setMessage("Loading......");
              p1.show();

              if (!TextUtils.isEmpty(cemail) && !TextUtils.isEmpty(cpass))
                  {
                  mAuth.signInWithEmailAndPassword(cemail,cpass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                          if (task.isSuccessful())
                          {
                              startActivity(new Intent(MainActivity.this,MainPage.class));
                              MainActivity.this.finish();
                              p1.dismiss();


                          }
                          else {
                              Toast.makeText(MainActivity.this,"LOGIN failed please try again",Toast.LENGTH_LONG);

                          }

                      }
                  });
                  }




              }

          });

          signup.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
              Intent i1 = new Intent(MainActivity.this,SignUp.class);
              startActivity(i1);


              }
          });
    }
}
