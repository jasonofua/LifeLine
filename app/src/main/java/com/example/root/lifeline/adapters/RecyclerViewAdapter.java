package com.example.root.lifeline.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.lifeline.Constant.IntentExtras;
import com.example.root.lifeline.POJO.PlacesPOJO;
import com.example.root.lifeline.R;
import com.example.root.lifeline.maps.Hospitals;
import com.example.root.lifeline.models.StoreModel;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private List<PlacesPOJO.CustomA> stLstStores;
    private List<StoreModel> models;
    String lng;
    String lat;
    Context context;
    String hospitalname;


    public RecyclerViewAdapter(List<PlacesPOJO.CustomA> stores, List<StoreModel> storeModels,Context context) {

        stLstStores = stores;
        models = storeModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.setData(stLstStores.get(holder.getAdapterPosition()), holder, models.get(holder.getAdapterPosition()));
    }


    @Override
    public int getItemCount() {
        return Math.min(5, stLstStores.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txtStoreName;
        TextView txtStoreAddr;
        TextView txtStoreDist;
        TextView txtIsOpen;
        StoreModel model;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.txtStoreDist = (TextView) itemView.findViewById(R.id.txtStoreDist);
            this.txtStoreName = (TextView) itemView.findViewById(R.id.txtStoreName);
            this.txtStoreAddr = (TextView) itemView.findViewById(R.id.txtStoreAddr);
            txtIsOpen = (TextView) itemView.findViewById(R.id.opened);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context,"Lat "+lat+" long "+lng,Toast.LENGTH_SHORT).show();
                    Intent openMap = new Intent(context, Hospitals.class);
                    openMap.putExtra(IntentExtras.LATITUDE,Double.parseDouble(stLstStores.get(getAdapterPosition()).geometry.locationA.lat));
                    openMap.putExtra(IntentExtras.LONGITUDE,Double.parseDouble(stLstStores.get(getAdapterPosition()).geometry.locationA.lng));
                    openMap.putExtra(IntentExtras.HOSPITAL,stLstStores.get(getAdapterPosition()).name);
                    context.startActivity(openMap);
                }
            });



        }


        public void setData(PlacesPOJO.CustomA info, MyViewHolder holder, StoreModel storeModel) {


            this.model = storeModel;

            holder.txtStoreDist.setText(model.distance);
            holder.txtStoreName.setText(info.name);
            holder.txtStoreAddr.setText(info.vicinity);
            holder.txtIsOpen.setText(model.duration);



        }

    }
}
