package com.viduokhttp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterSach extends BaseAdapter {
    private List<Sach> saches;
    private Context context;
    private int layout;
    public AdapterSach(Activitysach context, int layout, List<Sach> listSach) {
        this.context = context;
        this.layout = layout;
        this.saches = listSach;
    }
    @Override
    public int getCount() {
        return saches.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        TextView tenSach, MaSach, TenNXB, TenTacGia;
        int namxb;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.MaSach = (TextView) view.findViewById(R.id.tv_masach);
            holder.tenSach = (TextView) view.findViewById(R.id.tv_Tensach);
            holder.TenTacGia = (TextView) view.findViewById(R.id.tv_tacgia);
            holder.TenNXB = (TextView) view.findViewById(R.id.tv_Nxb);
//            holder.namxb = (ImageView) view.findViewById(R.id.im_edit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Sach sach = saches.get(i);
        holder.MaSach.setText(sach.MaSach);
        holder.tenSach.setText(sach.TenSach);
        holder.TenTacGia.setText(sach.TenTacGia);
        holder.TenNXB.setText(sach.TenNXB);
//        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UpdateLoaihang.class);
//                intent.putExtra("dataLoaiHang", loaihang);
//                context.startActivity(intent);
//            }
//        });
//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XacNhanXoa(loaihang.getTenLH(), loaihang.getId());
//            }
//        });
        return view;
    }
}
