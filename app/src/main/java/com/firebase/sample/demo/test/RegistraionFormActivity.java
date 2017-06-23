package com.firebase.sample.demo.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.sample.demo.test.helper.Utility;
import com.firebase.sample.demo.test.model.Perosn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by Sushant.Patekar on 6/19/2017.
 */

public class RegistraionFormActivity extends AppCompatActivity {
    private EditText edFirstName, edLastName, edWeight, edHeight, edAddress;
    private Button btnSubmit;
    private ImageView proImageView;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private String imagPath;

    private StorageReference mStorageRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaserefUsers;
    private String proUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_layout);
        initFirebase();
        initView();

    }

    private void initView() {
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edLastName = (EditText) findViewById(R.id.edLasttName);
        edHeight = (EditText) findViewById(R.id.edHeight);
        edWeight = (EditText) findViewById(R.id.edWeight);
        edAddress = (EditText) findViewById(R.id.edAddress);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        proImageView = (ImageView) findViewById(R.id.ivProfilePic);
        proImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perosn perosn = new Perosn();
                perosn.setPersonAddress(edAddress.getText().toString());
                perosn.setFirstName(edFirstName.getText().toString());
                perosn.setLastName(edLastName.getText().toString());
                perosn.setHeight(edHeight.getText().toString());
                perosn.setWeight(edWeight.getText().toString());
                perosn.setProfileUrl("" + proUrl);
                mDatabase.child("Person").push().setValue(perosn);

                setResult(RESULT_OK);
                finish();
            }
        });


    }

    private void initFirebase() {
        //  FirebaseApp.initializeApp(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // mFirebaseAuth = FirebaseAuth.getInstance();
        //  mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaserefUsers = FirebaseDatabase.getInstance().getReference("Person");

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistraionFormActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RegistraionFormActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {

            }
            //  onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {
                imagPath = onCaptureImageResult(data);
                uploadonServer(imagPath);
            }
            // onCaptureImageResult(data);
        }
    }

    private String onCaptureImageResult(Intent data) {

        Uri selectedImageUri = data.getData();
        return getRealPathFromURI(selectedImageUri);


    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    private void uploadonServer(String imgPath) {
        try {
            Uri file = Uri.fromFile(new File("" + imgPath));
            StorageReference riversRef = mStorageRef.child("images/" + imgPath);

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            proUrl = downloadUrl.toString();
                            Log.i("TRAG", "onSuccess: " + downloadUrl);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        } catch (Exception e) {

        }
    }
}
