package com.viduokhttp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachItemViewHolder> {
    private List<Sach> saches;
    private Context context;

    public SachAdapter(List<Sach> saches, Context c) {
        this.saches = saches;
        this.context = c;
    }

    @Override
    public int getItemCount() {
        return saches.size();
    }

    @Override
    public SachItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sach, parent, false);

        return new SachAdapter.SachItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SachItemViewHolder holder, int position) {
        Sach u = saches.get(position);
        Picasso.with(context)
                .load(u.MaSach);
        holder.MaSach.setText(u.MaSach);
        holder.tenSach.setText(u.TenSach);
        holder.TenNXB.setText(u.TenNXB);
        holder.TacGia.setText(u.TenTacGia);
    }

    public static class SachItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tenSach;
        public TextView MaSach;
        public TextView TenNXB;
        public TextView TacGia;
        public ImageView im_edit;
        public ImageView im_delete;

        public SachItemViewHolder(View itemView) {
            super(itemView);
            tenSach = (TextView) itemView.findViewById(R.id.tv_Tensach);
            MaSach = (TextView) itemView.findViewById(R.id.tv_masach);
            TenNXB = (TextView) itemView.findViewById(R.id.tv_Nxb);
            TacGia=(TextView)itemView.findViewById(R.id.tv_tacgia);
            im_edit = (ImageView) itemView.findViewById(R.id.im_edit);
            im_delete = (ImageView) itemView.findViewById(R.id.im_delete);
        }
    }
}
