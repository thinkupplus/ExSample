package kr.co.thinkup.exsample.camera;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.co.thinkup.exsample.R;

/**
 * 2019-09-04 by yh.Choi
 */
public class CameraActivity extends AppCompatActivity {

    private static String TAG = "CameraActivity";

    private static int PICK_FROM_ALBUM = 1;
    private static int PICK_FROM_CAMERA = 2;

    private boolean isPermission = false;

    private File tempFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acvity_camera);
        ImageButton camera = findViewById(R.id.t_camera);
        ImageButton gallery = findViewById(R.id.t_galley);

        tedPermission();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermission)
                    takePhoto();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermission)
                    goToAlbum();
            }
        });
    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(CameraActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(CameraActivity.this, "PermisstionDenied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void goToAlbum() {
        // https://black-jin0427.tistory.com/120
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

//        LG 폰에서 여기 값이 들어올때 이상하게 들어온다.
//        if(requestCode != Activity.RESULT_OK) {
//            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
//
//            if(tempFile != null) {
//                if(tempFile.exists()) {
//                    if(tempFile.delete()) {
//                        Log.e(TAG, "onActivityResult: ");
//                        tempFile = null;
//                    }
//                }
//            }
//            return ;
//        }


        if(requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {
                String [] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
            }finally {
                if(cursor != null) {
                    cursor.close();
                }
            }
            setImage();
        }else if( requestCode == PICK_FROM_CAMERA) {
            setImage();
        }
    }

    private void setImage() {

        ImageView imageView = findViewById(R.id.iv_person);

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap orifinalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(orifinalBm);
    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImagreFile();
        }catch (IOException e) {
            Toast.makeText(this, "Image Error", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }

        if(tempFile != null) {
            Uri photoUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImagreFile() throws IOException {

        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "thinkup_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/thiinkup/");

        if(!storageDir.exists())
            storageDir.mkdirs();

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }
}
