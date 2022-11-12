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

    private final Context mContext;
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
            return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.def);

        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v=View.inflate(mContext,R.layout.med_layout,null);
        TextView NameMed= v.findViewById(R.id.NameMed);
        TextView Manufacturers=v.findViewById(R.id.Manufactures);
        TextView Manufacturer_country=v.findViewById(R.id.Manufacturer_country);
        TextView Price= v.findViewById(R.id.Price);
        ImageView Image = v.findViewById(R.id.Image);

        Med med=medList.get(i);
        NameMed.setText(med.getNameMed());
        Manufacturers.setText(med.getManufacturers());
        Manufacturer_country.setText(med.getManufacturer_country());
        Price.setText(Double.toString(med.getPriceMed()));
        if(med.getImage() != null){
            Image.setImageBitmap(getUserImage(med.getImage()));
        }
        else {
            Image.setImageResource(R.drawable.def);
        }
        return v;
    }
}
