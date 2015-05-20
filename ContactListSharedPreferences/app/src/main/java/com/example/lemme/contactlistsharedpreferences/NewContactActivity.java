package com.example.lemme.contactlistsharedpreferences;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class NewContactActivity extends Activity {

    private static final int CAMERA_REQUEST_CODE = 1;
    private ImageView imageViewPhoto;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private Bitmap imageCache;
    private String imageName, imagePath;
    private int imageNum;
    private int photo;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imageNum = sharedPreferences.getInt("NumImagen", 0);

        imageViewPhoto = (ImageView) this.findViewById(R.id.photo);
        editTextName = (EditText) this.findViewById(R.id.name);
        editTextPhone = (EditText) this.findViewById(R.id.phone);
        editTextEmail = (EditText) this.findViewById(R.id.email);
        photo = R.mipmap.ic_launcher;
    }

    public void onClickChangePhoto(View view) {
        switch (view.getId()) {
            case R.id.photoOption1:
                photo = R.drawable.contact1;
                break;
            case R.id.photoOption2:
                photo = R.drawable.contact2;
                break;
            case R.id.photoOption3:
                photo = R.drawable.contact3;
                break;
            default:
                break;
        }
        imageViewPhoto.setImageResource(photo);
        imagePath = "";
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
        if (!imageFile.exists()) {
            Toast.makeText(getApplicationContext(), getString(R.string.photo_does_not_exist), Toast.LENGTH_SHORT);
            photo = R.mipmap.ic_launcher;
            imageViewPhoto.setImageResource(photo);
        }
    }

    private void setImageToImageViewTakePhotoIfImageFileExists(File imageFile) {
        if (imageFile.exists()) {
            if(imageCache != null)
                imageCache.recycle();
            imageCache = BitmapFactory.decodeFile(imagePath);
            imageViewPhoto.setImageBitmap(imageCache);
        }
    }

    public void onClickSaveContact(View view) {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        saveContact(name, phone, email);
    }

    private void saveContact(String name, String phone, String email) {
        if (validateContactDataBeforeSaving(name, phone, email)) {

            Contact contact = new Contact(photo, name, phone, email, imagePath);
            contact.save();
            imageNum++;
            editor.putInt("NumImagen", imageNum);
            editor.commit();
            startActivity(new Intent(this, ContactListActivity.class));
            finish();
        } else {
            Toast.makeText(this, getString(R.string.fields_validation_error_message), Toast.LENGTH_SHORT);
        }
    }

    private boolean validateContactDataBeforeSaving(String name, String phone, String email) {
        if(name.length() > 0 && phone.length() > 0 && email.length() > 0)
            return  true;
        return false;
    }
}
