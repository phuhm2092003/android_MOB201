package fpt.edu.schoolproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpt.edu.schoolproject.R;
import fpt.edu.schoolproject.dao.KhoaHocDao;
import fpt.edu.schoolproject.model.KhoaHoc;
import fpt.edu.schoolproject.service.DangKyKhoaHocService;

public class KhoaHocAdapter extends BaseAdapter {
    Context context;
    ArrayList<KhoaHoc> list;
    KhoaHocDao khoaHocDao;

    public KhoaHocAdapter(Context context, ArrayList<KhoaHoc> list, KhoaHocDao khoaHocDao) {
        this.context = context;
        this.list = list;
        this.khoaHocDao = khoaHocDao;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewOfItem viewOfItem = null;
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        if(view == null){
            view = layoutInflater.inflate(R.layout.layout_item_khoahoc, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvTen = view.findViewById(R.id.tvTenKhoaHoc);
            viewOfItem.tvGia = view.findViewById(R.id.tvGiaKhoaHoc);
            viewOfItem.btnTrangThai = view.findViewById(R.id.btnTrangThai);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        
        viewOfItem.tvTen.setText(list.get(i).getTenKhoaHoc());
        viewOfItem.tvGia.setText(String.valueOf(list.get(i).getGiaKhoaHoc()));
        // 1: chưa đăng ký, 0 đã đăng ký
        if(list.get(i).getTrangThai() == 0){
            viewOfItem.btnTrangThai.setText("Huỷ đăng ký khoá học");
            viewOfItem.btnTrangThai.setBackground(context.getResources().getDrawable(R.drawable.bgr_btn_huydang_ky));
        }else {
            viewOfItem.btnTrangThai.setText("Đăng ký khoá học");
            viewOfItem.btnTrangThai.setBackground(context.getResources().getDrawable(R.drawable.bgr_btn_dang_ky));
        }
        viewOfItem.btnTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DangKyKhoaHocService.class);
                Bundle bundle = new Bundle();
                bundle.putString("maKhoaHoc", String.valueOf(list.get(i).getMaKhoaHoc()));
                intent.putExtras(bundle);
                context.startService(intent);
            }
        });

        return view;
    }

    private void loadData() {
        list.clear();
        list = khoaHocDao.getAll();
        notifyDataSetChanged();
    }

    public static class ViewOfItem{
        TextView tvTen, tvGia;
        Button btnTrangThai;
    }
}
