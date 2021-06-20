package com.finalproject.ncovitrackerproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalproject.ncovitrackerproject.Models.HealthDeclaration;
import com.finalproject.ncovitrackerproject.R;

import java.util.List;

public class HealthDeclarationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HealthDeclaration> lichsuList;

    public HealthDeclarationAdapter(Context context, int layout, List<HealthDeclaration> lichsuList) {
        this.context = context;
        this.layout = layout;
        this.lichsuList = lichsuList;
    }

    @Override
    public int getCount() {
        return lichsuList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);
        TextView txtNgay = (TextView) view.findViewById(R.id.txtNgay);
        TextView txtMota = (TextView) view.findViewById(R.id.txtMota);
        TextView txtThoiGian = (TextView) view.findViewById(R.id.txtThoiGian);
        ImageView imgButton = (ImageView) view.findViewById(R.id.imgViewLichsu);

        HealthDeclaration khaiBaoHangNgay = lichsuList.get(i);
        txtNgay.setText(khaiBaoHangNgay.getNgay());
        txtMota.setText(khaiBaoHangNgay.getMota());
        txtThoiGian.setText(khaiBaoHangNgay.getThoiGian());
        imgButton.setImageResource(khaiBaoHangNgay.getHinh());
        return view;
    }
}
