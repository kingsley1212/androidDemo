package com.aaa.lsjdemo.rxjava;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.lsjdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ActRxJava extends AppCompatActivity implements View.OnClickListener {
    private TextView start, stop;
    private ImageView mMHeaderIv;
    /**
     * 拍照选择
     */
    private Button mMGoCameraBtn;
    /**
     * 本地相册选择
     */
    private Button mMGoAlbmBtn;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //裁剪请求码
    private static final int CROP_REQUEST_CODE = 3;
    //调用照相机返回图片文件
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_rx_java);
        initView();
        start = (TextView) findViewById(R.id.start);

    }

    private void initView() {
        mMHeaderIv = (ImageView) findViewById(R.id.mHeader_iv);
        mMGoCameraBtn = (Button) findViewById(R.id.mGoCamera_btn);
        mMGoCameraBtn.setOnClickListener(this);
        mMGoAlbmBtn = (Button) findViewById(R.id.mGoAlbm_btn);
        mMGoAlbmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.mGoCamera_btn:
                getPicFromCamera();
                break;
            case R.id.mGoAlbm_btn:
                getpicFromAlbm();
                break;
        }
    }

    /**
     * 从相机获取图片
     */
    private void getPicFromCamera() {
        tempFile = new File(Environment.getExternalStorageDirectory().
                getParent(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(ActRxJava.this,
                    "com.aaa.lsjdemo", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    private void getpicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputY", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return_data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(ActRxJava.this,
                                "com.aaa.lsjdemo", tempFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));

                    }
                }
                break;
            case ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = getIntent().getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    Bitmap image = bundle.getParcelable("data");
                    mMHeaderIv.setImageBitmap(image);
                    String path = saveImagt("crop", image);
                }
        }
    }

    private String saveImagt(String crop, Bitmap image) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = crop + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;

    }
}
