package asu.edu.televisionandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;
import android.content.pm.PackageManager;
import java.io.File;
import android.os.Environment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.Log;

public class ImagePickerActivity2 extends Activity {

    private static ImageView showLeftImg;
    private static ImageView showRightImg;
    private int whichEye = 0;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "MayoClinicStorage";
    private Uri fileUri; // file url to store image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker_activity2);

        showLeftImg = (ImageView) findViewById(R.id.showLeftImg);
        showRightImg = (ImageView) findViewById(R.id.showRightImg);


        Button photo = (Button) findViewById(R.id.captureleft);
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                whichEye = 1;
                dispatchTakePictureIntent();
            }
        });

        Button photo2 = (Button) findViewById(R.id.captureright);
        photo2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                whichEye = 2;
                dispatchTakePictureIntent();
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }


        Button clickButton = (Button) findViewById(R.id.buttonImagePicker);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(ImagePickerActivity2.this, SubmitActivity.class);
                ImagePickerActivity2.this.startActivity(myIntent);
            }
        });

        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LogoutHandler lh = new LogoutHandler();
                lh.deleteStoredFiles();
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(ImagePickerActivity2.this, LoginActivity.class);
                ImagePickerActivity2.this.startActivity(myIntent);
            }
        });
    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    // capture image
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // image captured successfully
                try{
                    System.out.println(fileUri);
                    Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
                    if(whichEye == 1)
                        showLeftImg.setImageBitmap(bmp);
                    else if(whichEye == 2)
                        showRightImg.setImageBitmap(bmp);
                } catch(IOException e){
                    Toast.makeText(getApplicationContext(),
                            "File not found", Toast.LENGTH_SHORT)
                            .show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    // create file uri
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    // return image
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
