package com.example.webappproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdMed extends AppCompatActivity {

    Bundle arg;
    Med med;
    EditText NameMed, Manufacturers, Manufacturer_country, PriceMed;
    public Context mContext;
    ImageView Image;
    Bitmap b=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updamed);
        arg = getIntent().getExtras();
        med = arg.getParcelable(Med.class.getSimpleName());
        NameMed = findViewById(R.id.NameMed);
        Manufacturers = findViewById(R.id.Manufacturers);
        Manufacturer_country = findViewById(R.id.Manufacturer_country);
        PriceMed = findViewById(R.id.PriceMed);
        Image = findViewById(R.id.Image);
        NameMed.setText(med.getNameMed());
        Manufacturers.setText(med.getManufacturers());
        Manufacturer_country.setText(med.getManufacturer_country());
        PriceMed.setText(Double.toString(med.getPriceMed()));

        Bitmap userImage = getUserImage(med.getImage());
        Image.setImageBitmap(userImage);
        if(!med.getImage().equals("null")){
            b = userImage;
        }
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


    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return java.util.Base64.getEncoder().encodeToString(bytes);
        }
        else{
            return "";
        }
    }

    public String Image(Bitmap bitmap){
        if(bitmap==null){
            return null;
        }
        else{
            String Image = encodeImage(bitmap);
            return Image;
        }
    }


    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    b = BitmapFactory.decodeStream(is);
                    Image.setImageBitmap(b);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void SelectPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public void Update(View v){
        NameMed.setText(med.getNameMed());
        Manufacturers.setText(med.getManufacturers());
        Manufacturer_country.setText(med.getManufacturer_country());
        PriceMed.setText(Double.toString(med.getPriceMed()));

        med.setImage(Image(b));
            DataPutDelete(med, 0, "Данные изменены", v);

    }

    public void Delete(View v){
        DataPutDelete(med, 1, "Запись удалена", v);
    }

    public void Back(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void DataPutDelete(Med med, int number, String str, View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/БыковаАА/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Med> call = null;
        switch (number) {
            case 0:
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                call = retrofitAPI.createPut(med, med.getID());
                break;
            case 1:
                RetrofitAPI retrofitAPIs = retrofit.create(RetrofitAPI.class);
                call = retrofitAPIs.createDelete(med.getID());
                break;
            default:
                break;
        }
        call.enqueue(new Callback<Med>() {
            @Override
            public void onResponse(Call<Med> call, Response<Med> response) {
                Toast.makeText(UpdMed.this, str, Toast.LENGTH_SHORT).show();
                Back(v);
            }
            @Override
            public void onFailure(Call<Med> call, Throwable t) {
                Toast.makeText(UpdMed.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
