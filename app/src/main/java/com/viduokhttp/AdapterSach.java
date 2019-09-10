package com.viduokhttp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class AdapterSach extends BaseAdapter {
    private List<Sach> saches;
    //    private Context context;
    private int layout;
    private Activitysach context;

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
        TextView tenSach, MaSach, TenNXB, TenTacGia, NamXB;
        ImageView imgEdit, imgDelete;
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
            holder.NamXB = (TextView) view.findViewById(R.id.tv_NamXB);
            holder.imgEdit = (ImageView) view.findViewById(R.id.im_edit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.im_delete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Sach sach = saches.get(i);
        holder.MaSach.setText(sach.MaSach);
        holder.tenSach.setText(sach.TenSach);
        holder.TenTacGia.setText(sach.TenTacGia);
        holder.TenNXB.setText(sach.TenNXB);
        holder.NamXB.setText(sach.NamXB + "");
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityUpdateSach.class);
                intent.putExtra("dataSach", sach);
                context.startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(sach.TenSach, sach.id);
            }
        });
        return view;
    }

    private void XacNhanXoa(String ten, final String id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xoá bản ghi " + ten + " và có mã id " + id + " Không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                context.DeleteLoaiHang(id);
                new DeleteSach(id).execute("http://192.168.26.111:1337/Saches/"+id);
                context.getData();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
}
