package com.example.webappproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMed extends AppCompatActivity{

    ImageView Image;
    Bitmap bitmap=null, b;
    EditText NameMed, Manufacturers, Manufacturer_country, PriceMed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addm);
        Image = findViewById(R.id.Image);
        b = BitmapFactory.decodeResource(AddMed.this.getResources(), R.drawable.def);
        NameMed = findViewById(R.id.NameMed);
        Manufacturers = findViewById(R.id.Manufacturers);
        Manufacturer_country = findViewById(R.id.Manufacturer_country);
        PriceMed = findViewById(R.id.PriceMed);
        Image.setImageBitmap(b);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    Image.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void Back(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void SelectPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
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

    public void DataPost(View v){

            postData(NameMed.getText().toString(), Manufacturers.getText().toString(), Manufacturer_country.getText().toString(),Double.parseDouble(PriceMed.getText().toString()), Image(bitmap), v);

    }


    private void postData(String NameMed, String Manufacturers,String Manufacturer_country,  double PriceMed, String Image, View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/NGKNN/БыковаАА/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Med modal = new Med( null, NameMed, Manufacturers,Manufacturer_country, PriceMed, Image);
        Call<Med> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Med>() {
            @Override
            public void onResponse(Call<Med> call, Response<Med> response) {
                Toast.makeText(AddMed.this, "Данные добавлены!", Toast.LENGTH_SHORT).show();
                Back(v);
            }

            @Override
            public void onFailure(Call<Med> call, Throwable t) {
                Toast.makeText(AddMed.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
