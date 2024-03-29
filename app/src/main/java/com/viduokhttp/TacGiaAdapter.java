package com.viduokhttp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TacGiaAdapter extends BaseAdapter {
    private ActivityTacgia context;
    private int layout;
    private List<TacGia> tacGias;

    public TacGiaAdapter(ActivityTacgia context, int layout, List<TacGia> tacTacGia) {
        this.context = context;
        this.layout = layout;
        this.tacGias = tacTacGia;
    }

    @Override
    public int getCount() {
        return tacGias.size();
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
        TextView tvTen, tvDiaChi, tvDienThoai, matg;
        ImageView img_Edit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.matg = (TextView) view.findViewById(R.id.tv_MaTG);
            holder.tvTen = (TextView) view.findViewById(R.id.tv_TenTG);
            holder.tvDiaChi = (TextView) view.findViewById(R.id.tv_DiaChi);
            holder.tvDienThoai = (TextView) view.findViewById(R.id.tv_DienThoai);
            holder.img_Edit = (ImageView) view.findViewById(R.id.img_editTG);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final TacGia tacGia = tacGias.get(i);
        holder.matg.setText(tacGia.MaTG);
        holder.tvTen.setText(tacGia.TenTacGia);
        holder.tvDiaChi.setText(tacGia.DiaChi);
        holder.tvDienThoai.setText(tacGia.DienThoai);
        holder.img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"Tác giả có mã là: "+tacGia.MaTG,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, ActivityUpdatetacgia.class);
                intent.putExtra("tacgia", tacGia);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
