package com.parkho.camerasample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * <p>
 *     Main activity
 * </p>
 *
 * @author parkho79
 * @date 2019-01-17
 */
public class PhMainActivity extends AppCompatActivity
{
    /**
     * StartActivityForResult 파라미터
     */
    public interface PhActivityRequest {
        int IMAGE_CAPTURE = 1001;
    }

    // 카메라로 사진을 찍어 image 추출
    private PhImageCapture mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Listener 설정
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View a_view) {
                final int imageWidth = (int) getResources().getDimension(R.dimen.image_width);
                final int imageHeight = (int) getResources().getDimension(R.dimen.image_height);
                mCamera = new PhImageCapture(imageWidth, imageHeight);
                mCamera.onStart(PhMainActivity.this);
            }
        };
        findViewById(R.id.btn_camera).setOnClickListener(listener);
    }

    @Override
    protected void onActivityResult(int a_requestCode, int a_resultCode, Intent a_data) {
        super.onActivityResult(a_requestCode, a_resultCode, a_data);
        if (a_resultCode != Activity.RESULT_OK) {
            return;
        }

        // Camera action pick 결과 전달
        ImageView ivImage = findViewById(R.id.iv_image);
        mCamera.onResult(ivImage);
    }
}
