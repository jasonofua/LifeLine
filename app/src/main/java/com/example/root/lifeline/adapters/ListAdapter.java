package com.example.root.lifeline.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.example.root.lifeline.Activities.ListDetail;
import com.example.root.lifeline.Constant.IntentExtras;
import com.example.root.lifeline.Helpers.DatabaseHelper;
import com.example.root.lifeline.R;
import com.example.root.lifeline.models.MyList;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    ArrayList<MyList>myListArrayList;
    Context context;

    public ListAdapter(ArrayList<MyList> myListArrayList, Context context) {
        this.myListArrayList = myListArrayList;
        this.context = context;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_mylist_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.MyViewHolder holder, int position) {
        MyList list = myListArrayList.get(position);
        holder.listItemName.setText(list.getListName());

    }

    @Override
    public int getItemCount() {
        return myListArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listItemName;
        private ImageButton delete;
        public MyViewHolder(View itemView) {
            super(itemView);

            listItemName = (TextView)itemView.findViewById(R.id.tvListName);
            delete = (ImageButton) itemView.findViewById(R.id.btnDelete);

            final String key = myListArrayList.get(getAdapterPosition()+1).getId();

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (delete.getTag() == null){
                        //confirm imgActionDelete before deleting
                        final Toast noticeToast = Toast.makeText(context, "Tap again to permanently Delete", Toast.LENGTH_SHORT);
                        noticeToast.show();
                        delete.setImageTintList(ColorStateList.valueOf(Color.RED));
                        delete.setTag("imgActionDelete");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                delete.setImageTintList(ColorStateList.valueOf(Color.BLACK));
                                noticeToast.cancel();
                                delete.setTag(null);
                            }
                        }, 2000);
                    }
                    else{
                        Database database = DatabaseHelper.getDatabase(context, DatabaseHelper.OFFLINE_LIST_DATABASE);
                        if (database != null) {
                            Document document = database.getDocument(key);
                            if (document != null)
                            {
                                try {
                                    document.delete();
                                    ListAdapter.this.myListArrayList.remove(getAdapterPosition());
                                    ListAdapter.this.notifyDataSetChanged();
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                } catch (CouchbaseLiteException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListDetail.class);
                    intent.putExtra(IntentExtras.ITEM_ID,myListArrayList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });


        }


        }
    }
