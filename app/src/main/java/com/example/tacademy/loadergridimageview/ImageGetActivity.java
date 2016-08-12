package com.example.tacademy.loadergridimageview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;




public class ImageGetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_get);

        Button btn = (Button)findViewById(R.id.btn_get_image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageGetActivity.this, ImageViewActivity.class);
                startActivityForResult(intent, RC_GET_IMAGE);
            }
        });

        btn = (Button)findViewById(R.id.btn_single_image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RC_SINGLE_IMAGE);
            }
        });
    }

    private static final int RC_SINGLE_IMAGE = 2;
    private static final int RC_GET_IMAGE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (resultCode == RESULT_OK) {
                String[] files = data.getStringArrayExtra("files");
                for (String s : files) {
                    Log.i("ImageFiles", "files : " + s);
                }
            }
        }

        if (requestCode == RC_SINGLE_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                Cursor c = getContentResolver().query(fileUri, new String[] {MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.i("Single", "path : " + path);
                }
            }
        }
    }
}