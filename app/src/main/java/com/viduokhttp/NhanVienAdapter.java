package com.viduokhttp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NhanVienAdapter extends BaseAdapter {
    private ActivityNhanvien context;
    private int layout;
    private List<NhanVien> nhanViens;

    public NhanVienAdapter(ActivityNhanvien context, int layout, List<NhanVien> listNhanvien) {
        this.context = context;
        this.layout = layout;
        this.nhanViens = listNhanvien;
    }
    @Override
    public int getCount() {
        return nhanViens.size();
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
        TextView txtTenNV, txtDiaChi, txtSoDT;
        ImageView imgEdit, imgDelete;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTenNV = (TextView) view.findViewById(R.id.tv_TenNV);
            holder.txtDiaChi = (TextView) view.findViewById(R.id.tv_DiaChi);
            holder.txtSoDT = (TextView) view.findViewById(R.id.tv_SoDT);
            holder.imgDelete = (ImageView) view.findViewById(R.id.im_delete);
            holder.imgEdit = (ImageView) view.findViewById(R.id.im_edit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final NhanVien nhanVien = nhanViens.get(i);
        holder.txtTenNV.setText(nhanVien.TenNV);
        holder.txtDiaChi.setText(nhanVien.DiaChi);
        holder.txtSoDT.setText(nhanVien.SoDienThoai);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, UpdateLoaihang.class);
//                intent.putExtra("dataLoaiHang", loaihang);
//                context.startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                XacNhanXoa(loaihang.getTenLH(), loaihang.getId());
            }
        });
        return view;
    }
}
