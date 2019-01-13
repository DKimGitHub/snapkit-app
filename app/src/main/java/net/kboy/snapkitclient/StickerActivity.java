package net.kboy.snapkitclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.exceptions.SnapMediaSizeException;
import com.snapchat.kit.sdk.creative.exceptions.SnapStickerSizeException;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapPhotoFile;
import com.snapchat.kit.sdk.creative.media.SnapSticker;

import java.io.File;

public class StickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        SnapCreativeKitApi snapCreativeKitApi = SnapCreative.getApi(this);
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(this);
        SnapSticker snapSticker = null;
        File stickerFile = new File("/Users/kevincho/SnapKitSample-Android/app/src/main/res/drawable-v24/icon5.png");
        SnapPhotoFile photoFile;
        try{
            snapSticker = snapMediaFactory.getSnapStickerFromFile(stickerFile);

        }catch (SnapStickerSizeException e){
            e.printStackTrace();
            return;
        }
        try{
            photoFile = snapMediaFactory.getSnapPhotoFromFile(stickerFile);
        }catch(SnapMediaSizeException e){
            e.printStackTrace();
            return;
        }

        snapSticker.setWidth(300);
        snapSticker.setHeight(300);

        snapSticker.setPosX(0.5f);
        snapSticker.setPosY(0.5f);

        photoFile.
    }

}
