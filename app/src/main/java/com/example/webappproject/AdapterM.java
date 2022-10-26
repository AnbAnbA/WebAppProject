package com.example.webappproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterM extends BaseAdapter{

    private Context mContext;
    List<Med> medList;

    public AdapterM(Context mContext, List<Med> listMed) {
        this.mContext = mContext;
        this.medList = listMed;
    }

    @Override
    public int getCount() {
        return medList.size();
    }

    @Override
    public Object getItem(int i) {
        return medList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return medList.get(i).getID();
    }
    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return null;

        }
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
