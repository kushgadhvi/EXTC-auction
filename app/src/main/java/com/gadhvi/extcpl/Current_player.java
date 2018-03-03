package com.gadhvi.extcpl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Current_player extends Activity {
private TextView name,age,skills,tag,base,credit;
private Button r20,r50;
private ImageView image;
private Object betValue=null;
private DatabaseReference getdata,getplayer,getbet,getcaptain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_player);
        getdata = FirebaseDatabase.getInstance().getReference().child("current");
        getbet = FirebaseDatabase.getInstance().getReference();

        name = (TextView)findViewById(R.id.player_name);
        skills =(TextView)findViewById(R.id.player_skill);
        tag =(TextView)findViewById(R.id.player_tag);
        base =(TextView)findViewById(R.id.base_price);
        image=(ImageView)findViewById(R.id.player_image);
        r20 = (Button)findViewById(R.id.raise20);
        r50= (Button)findViewById(R.id.raise50);
        credit = (TextView)findViewById(R.id.credit);
        getcaptain = getbet.child("Captain");
         getcaptain.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Current_player.this);
                 String cap_name = prefs.getString("NAME", "defaulfValue");
                 DataSnapshot cr = dataSnapshot.child(cap_name).child("credit");
                 credit.setText("Credit left:"+cr.getValue().toString());

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

        getdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot player =dataSnapshot.child("player");
                DataSnapshot bet  =dataSnapshot.child("bet");
                String name1 = player.child("Name").getValue().toString();
                name.setText("Name:" +name1);
                base.setText("Base Price:" +player.child("Age").getValue().toString());
                skills.setText("Skills:" +player.child("Skills").getValue().toString());
                tag.setText("Tag: "+player.child("Tag").getValue().toString());

                Picasso.with(getApplicationContext())
                        .load(player.child("Image").getValue().toString())
                        .error(R.drawable.addimage)
                        .resize(200, 200)
                        .transform(new ImageTrans_CircleTransform())
                        .into(image);



                if(dataSnapshot.hasChild("bet")){
                   if(betValue==null){
                       Toast.makeText(getApplicationContext(),"Yes you can bet now",Toast.LENGTH_LONG).show();
                   }
                   betValue = bet.child("amount").getValue();

                }else{
                    betValue=null;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        r20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(betValue==null){
                    Toast.makeText(getApplicationContext(),"Please wait for the auction to begin",Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Current_player.this);
                    String cap_name = prefs.getString("NAME","defaulfValue");
                    Bet newBet = new Bet();
                    newBet.setAmount(Long.parseLong(betValue.toString())+200000);
                    newBet.setCaptain(cap_name);
                    getdata.child("bet").setValue(newBet).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Bet Placed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        r50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(betValue==null){
                    Toast.makeText(getApplicationContext(),"Please wait for the auction to begin",Toast.LENGTH_LONG).show();
                }else{
                    Bet newBet = new Bet();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Current_player.this);
                    newBet.setAmount(Long.parseLong(betValue.toString())+500000);
                    String cap_name = prefs.getString("NAME","defaulfValue");

                    newBet.setCaptain(cap_name);
                    getdata.child("bet").setValue(newBet).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Bet Placed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });





    }
}
