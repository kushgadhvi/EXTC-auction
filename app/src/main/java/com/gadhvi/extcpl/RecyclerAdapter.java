package com.gadhvi.extcpl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gadhvi on 13/2/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder>{

        List<Players> list;
        Context context;

public RecyclerAdapter(List<Players> list, Context context) {
        this.list = list;
        this.context = context;
        }

@Override
public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.player_card,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
        }

@Override
public void onBindViewHolder(MyHoder holder, int position) {
        Players mylist = list.get(position);
        holder.name.setText(mylist.getPlayer());
        holder.email.setText(Long.toString(mylist.getAmount()));

        }

    @Override
    public int getItemCount() {
        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;
    }


    class MyHoder extends RecyclerView.ViewHolder{
    TextView name,email,address;


    public MyHoder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.play_name);
        email= (TextView) itemView.findViewById(R.id.play_prize);


    }
}
}
