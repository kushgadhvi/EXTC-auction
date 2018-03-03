package com.gadhvi.extcpl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPlayer extends AppCompatActivity {
    private Button add;
    private ImageView i1;
    private EditText e1,e2,e3,e4;
    private Uri imageUri = null;
    private static int GALLERY_REQUEST =1;
    private StorageReference storeimage;
    private DatabaseReference database;
    private ProgressDialog loading;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_player);

        storeimage = FirebaseStorage.getInstance().getReference().child("Players photo");
        database = FirebaseDatabase.getInstance().getReference().child("Players");
        e1 = (EditText)findViewById(R.id.name);
        e2= (EditText)findViewById(R.id.age);
        e3 =(EditText)findViewById(R.id.skils);
        e4=(EditText)findViewById(R.id.tag);
        i1 = (ImageView)findViewById(R.id.addimage);
        add =(Button)findViewById(R.id.submit);
        loading = new ProgressDialog(this);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getimage = new Intent(Intent.ACTION_GET_CONTENT);
                getimage.setType("image/*");
                startActivityForResult(getimage,GALLERY_REQUEST);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String player_name =e1.getText().toString().trim();
                final String player_age =e2.getText().toString().trim();
                final String player_skill =e3.getText().toString();
                final String player_tag = e4.getText().toString();
                if(!TextUtils.isEmpty(player_name)  && !TextUtils.isEmpty(player_age) && !TextUtils.isEmpty(player_skill)&& !TextUtils.isEmpty(player_tag)&& imageUri!=null)
                {
                    loading.setMessage("Loading Please Wait....");
                    loading.show();
                    StorageReference imagepath = storeimage.child("Newsfeed Images").child(imageUri.getLastPathSegment());
                    imagepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri dowmloadUrl = taskSnapshot.getDownloadUrl();
                            DatabaseReference upload =database.child(player_name);
                            upload.child("Age").setValue(player_age);
                            upload.child("Skills").setValue(player_skill);
                            upload.child("Tag").setValue(player_tag);
                            upload.child("Image").setValue(dowmloadUrl.toString());

                            loading.dismiss();
                            e1.getText().clear();
                            e2.getText().clear();
                            e3.getText().clear();
                            e4.getText().clear();
                            i1.setImageResource(R.drawable.addimage);

                                                    }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            loading.dismiss();
                            Toast.makeText(AddPlayer.this,"Please Try Again Later Something Went Wrong",Toast.LENGTH_LONG).show();
                        }
                    });
                }

                else
                {
                    Toast.makeText(AddPlayer.this,"Please Fill All the field",Toast.LENGTH_LONG).show();
                }

            }
        });







    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST &&resultCode==RESULT_OK){

            imageUri =data.getData();
            i1.setImageURI(imageUri);

        }
    }

}
