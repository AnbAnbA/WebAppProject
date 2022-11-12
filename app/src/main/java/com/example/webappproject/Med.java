package com.example.webappproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Med implements Parcelable{

    private int ID;
    private String NameMed;
    private String Manufacturers;
    private String Manufacturer_country;
    private double PriceMed;
    public String Image;


    public Med(int ID, String nameMed, String manufacturers, String manufacturer_country, double priceMed, String image)
    {
        this.ID = ID;
        NameMed=nameMed;
        Manufacturers=manufacturers;
        Manufacturer_country=manufacturer_country;
        PriceMed=priceMed;
        Image=image;
    }


    protected Med(Parcel in) {
        ID = in.readInt();
        NameMed=in.readString();
        Manufacturers=in.readString();
        Manufacturer_country=in.readString();
        PriceMed= in.readDouble();
        Image=in.readString();
    }

    public static final Creator<Med> CREATOR = new Creator<Med>() {
        @Override
        public Med createFromParcel(Parcel in) {
            return new Med(in);
        }

        @Override
        public Med[] newArray(int size) {
            return new Med[size];
        }
    };
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setNameMed(String nameMed)
    {
        NameMed=NameMed;
    }
    public void setManufacturers(String manufacturers)
    {
        Manufacturers=manufacturers;
    }
    public void setManufacturer_country(String manufacturer_country) {Manufacturer_country=manufacturer_country;}
    public void setPriceMed(double priceMed)
    {
        PriceMed=priceMed;
    }
    public void setImage(String image){Image=image;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(ID);
        parcel.writeString(NameMed);
        parcel.writeString(Manufacturers);
        parcel.writeString(Manufacturer_country);
        parcel.writeDouble(PriceMed);
    }
    public int getID(){return ID;};

    public String getNameMed(){return NameMed;}

    public String getManufacturers(){return Manufacturers;}

    public String getManufacturer_country(){return Manufacturer_country;}

    public double getPriceMed(){return PriceMed;}

    public String getImage(){return Image;}



}
