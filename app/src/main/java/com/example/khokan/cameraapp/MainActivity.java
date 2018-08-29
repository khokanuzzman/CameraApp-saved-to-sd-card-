package com.example.khokan.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView myImage;
    Button takephoto;
    int flag=0;
    Bitmap b;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImage = (ImageView) findViewById(R.id.myimage);
        takephoto = (Button) findViewById(R.id.takephoto);

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag == 0) {

                    // -- code for taking photo --

                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 99);

                } else if(flag == 1) {

                    //--- code for saving photo ---

                    savePhotoToMySdCard(b);

                    Toast.makeText(getApplicationContext(), "Photo saved to sd card!", Toast.LENGTH_SHORT).show();

                    flag = 0;
                    takephoto.setText("Take Photo");

                }

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 99 && resultCode == RESULT_OK && data != null){

            b = (Bitmap) data.getExtras().get("data");

            myImage.setImageBitmap(b);

            flag = 1;
            takephoto.setText("Save Photo");

        }

    }


    private void savePhotoToMySdCard(Bitmap bit){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String pname = sdf.format(new Date());


        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root+"/MYCamera_Photos");
        folder.mkdirs();

        File my_file = new File(folder, pname+".png");

        try {

            FileOutputStream stream = new FileOutputStream(my_file);
            bit.compress(Bitmap.CompressFormat.PNG, 80, stream);
            stream.flush();
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
