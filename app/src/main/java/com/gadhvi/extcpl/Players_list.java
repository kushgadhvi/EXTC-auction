package com.gadhvi.extcpl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Players_list extends AppCompatActivity {
    private RecyclerView playlist;
    private DatabaseReference getdatabase, getcaptain, getplayer;
    List<Players> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);
        getdatabase = FirebaseDatabase.getInstance().getReference().child("Captain");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String cap_name = prefs.getString("NAME", "defaulfValue");
        Toast.makeText(this,cap_name,Toast.LENGTH_LONG);
        getcaptain = getdatabase.child(cap_name);
        getplayer = getcaptain.child("Players");
        playlist = (RecyclerView) findViewById(R.id.play_list);
        playlist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        playlist.setLayoutManager(llm);
        Toast.makeText(this, "Loading Please Wait.......", Toast.LENGTH_LONG).show();
        getplayer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Players value = dataSnapshot1.getValue(Players.class);
                    Players play = new Players();
                    String name = value.getPlayer();
                    Long amount = value.getAmount();
                    play.setPlayer(name);
                    play.setAmount(amount);
                    list.add(play);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,Players_list.this);
        playlist.setAdapter(recyclerAdapter);
    }
     /*   @Override
        protected void onStart () {
            super.onStart();
            FirebaseRecyclerAdapter<Players,PlayerHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Players,PlayerHolder>(
                    Players.class,
                    R.layout.player_card,
                    PlayerHolder.class,
                    getplayer
            ) {

                @Override
                protected void populateViewHolder(PlayerHolder viewHolder,Players model, int position) {

                    viewHolder.SetName(model.getPlayer());
                   viewHolder.SetPrize(model.getAmount());


                }
            };
            newslist.setAdapter(firebaseRecyclerAdapter);
        }*/


   /*public static class PlayerHolder extends RecyclerView.ViewHolder {
            View view;

            public PlayerHolder(View itemView) {
                super(itemView);
                view = itemView;
            }

            public void SetName(String Player) {
                TextView post_name = (TextView)view.findViewById(R.id.play_name);
                post_name.setText(Player);
            }

           public void SetPrize(Long Amount) {
               TextView post_prize = (TextView) view.findViewById(R.id.play_prize);
               post_prize.setText(Long.toString(Amount));
           }

           }*/
        }


