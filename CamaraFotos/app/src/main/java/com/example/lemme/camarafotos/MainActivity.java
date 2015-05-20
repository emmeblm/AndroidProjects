package com.example.lemme.camarafotos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity {
    private final int CAMERA_REQUEST_CODE = 1;
    private ImageView imgTakePhoto;
    private Bitmap imageCache;
    private String imageName, imagePath;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int imageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgTakePhoto = (ImageView) this.findViewById(R.id.takePhoto);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imageNum = sharedPreferences.getInt("NumImagen", 0);
    }

    public void onClickTakePhoto(View view) {
        imageName = "imagen" + imageNum + ".jpg";
        imagePath = Environment.getExternalStorageDirectory() + "/" + imageName;

        Intent cameraStartIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri outputDirectory = Uri.fromFile(new File(imagePath));
        cameraStartIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputDirectory);
        this.startActivityForResult(cameraStartIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            File imageFile = new File(imagePath);
            showToastIfImageFileDoesNotExist(imageFile);
            setImageToImageViewTakePhotoIfImageFileExists(imageFile);
        }
    }

    private void showToastIfImageFileDoesNotExist(File imageFile) {
        if (!imageFile.exists())
            Toast.makeText(getApplicationContext(), getString(R.string.photo_does_not_exist), Toast.LENGTH_SHORT);
    }

    private void setImageToImageViewTakePhotoIfImageFileExists(File imageFile) {
        if (imageFile.exists()) {
            if(imageCache != null)
                imageCache.recycle();
            imageCache = BitmapFactory.decodeFile(imagePath);
            imgTakePhoto.setImageBitmap(imageCache);
        }
    }

    public void onClickSavePhoto(View view) {
        imageNum++;
        editor.putInt("NumImagen", imageNum);
        editor.commit();

        int obtenerNumero = sharedPreferences.getInt("NumImagen", 0);
        Log.d("", obtenerNumero + "");
    }


}
