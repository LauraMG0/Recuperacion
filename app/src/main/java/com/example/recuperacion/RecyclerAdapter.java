package com.example.recuperacion;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private Cursor _c;
    private Context _con;
    private ArrayList<Product> list;
    private OnNoteListener mOnNoteListener;

    public RecyclerAdapter(ArrayList<Product> list,OnNoteListener mOnNoteListener, Context con, Cursor cur){
        this.list = list;
        this.mOnNoteListener = mOnNoteListener;
        this._con = con;
        this._c = cur;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_con);
        View mItemView = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.set(list.get(position));
    }

    @Override
    public int getItemCount() {
        return _c.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv;
        private ImageView img, img2;
        private OnNoteListener onNoteListener;

        public ViewHolder(View itemView){
            super(itemView);
            tv = itemView.findViewById(R.id.textView);
            img = itemView.findViewById(R.id.imageView2);
            img2 = itemView.findViewById(R.id.imageView3);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public void set(Product p){
            this.tv.setText(p.getName());
            this.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) _con).edit(getAdapterPosition());
                }
            });
            this.img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) _con).delete(getAdapterPosition());
                }
            });
        }
        @Override
        public void onClick(View v) {
        }
    }
    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
