package com.example.addressbook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class AddPerson extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PHOTO = 1000;
    private static final int CAPTURE_PHOTO = 1001;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    Bitmap thumbnail;
    private ImageView profileImageView;
    private TextView name, address, address1, phone, phone1, email, email1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_person);
        Button btnSave = findViewById(R.id.bt_save);
        profileImageView = findViewById(R.id.profile_image_view);
        //Button pickImageView = findViewById(R.id.pick_image);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        address1 = findViewById(R.id.address1);
        phone = findViewById(R.id.phone);
        phone1 = findViewById(R.id.phone1);
        email = findViewById(R.id.email);
        email1 = findViewById(R.id.email1);
        if (ContextCompat.checkSelfPermission(AddPerson.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            profileImageView.setEnabled(false);
            ActivityCompat.requestPermissions(AddPerson.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            profileImageView.setEnabled(true);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePersonData();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pick_image) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.change_image)
                    .setItems(R.array.uploadImages, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Intent selectPhoto = new Intent(Intent.ACTION_PICK);
                                    selectPhoto.setType("image/*");
                                    startActivityForResult(selectPhoto, SELECT_PHOTO);
                                    break;
                                case 1:
                                    Intent capturePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(capturePhoto, CAPTURE_PHOTO);
                                    break;
                                case 2:
                                    profileImageView.setImageResource(R.mipmap.ic_choose_image_round);
                                    break;
                            }
                        }
                    });

        }
    }

    public void setProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus += 30;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    progressBarHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
  //get input from editor and save into database
    private void SavePersonData() {

        // get image from editor and store in a variable
          profileImageView.setDrawingCacheEnabled(true);
          profileImageView.buildDrawingCache();
          Bitmap bitmap=profileImageView.getDrawingCache();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] data=baos.toByteArray();
        String p_name=  name.getText().toString().trim();
        String addr=address.getText().toString().trim();
        String addr1=address1.getText().toString().trim();
        String ph= phone.getText().toString().trim();
        String ph1=phone1.getText().toString().trim();
        String eml=email.getText().toString().trim();
        String eml1=email1.getText().toString().trim();

        PersonDao personDao=new PersonDao(p_name,addr,addr1,ph,ph1,eml,eml1,data);
        DbDao addPerson = new DbDao(this);
        addPerson.AddPerson(personDao);
        Toast.makeText(this, "Record inserted Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                try {
                    assert imageUri != null;
                    imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeFile(String.valueOf(imageStream));
                    setProgressBar();
                    profileImageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                assert data != null;
                onCaptureImageResult(data);
            }
        }

    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
        setProgressBar();
        profileImageView.setMaxWidth(200);
        profileImageView.setImageBitmap(thumbnail);
    }

}
