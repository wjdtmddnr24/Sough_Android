package moe.chocola.sough.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import lzma.streams.LzmaOutputStream;
import moe.chocola.sough.R;
import moe.chocola.sough.data.Result;
import moe.chocola.sough.util.SoughService;
import moe.chocola.sough.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQRActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private TextView qrContent;
    private TextView cloudContent;
    private TextView cloudTitle;
    private SoughService soughService;
    private String content;
    private TextView cloudWorkspaceName;
    private int mode;
    private TextView qrContentDecompressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        soughService = Util.getSoughService();

        qrContent = findViewById(R.id.qr_content);
        qrContentDecompressed = findViewById(R.id.qr_content_decompressed);
        cloudContent = findViewById(R.id.cloud_content);
        cloudTitle = findViewById(R.id.cloud_title);
        cloudWorkspaceName = findViewById(R.id.cloud_workspace_name);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.rgb(0, 105, 92), PorterDuff.Mode.MULTIPLY);
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", MainActivity.QR_SCAN);
        setTitle(mode == MainActivity.QR_SCAN ? "QR 코드 읽기" : "작업공간에 추가하기");

        content = intent.getStringExtra("content");
        if (content == null) {
            Toast.makeText(this, "스캔 오류", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        qrContent.setText(content);

        if (content.startsWith("sough:")) {
            content = content.substring("sough:".length());
            try {
                content = Util.getDecompressString(content);
            } catch (Exception e) {
                Toast.makeText(this, "압축형식이 잘못된 내용입니다.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        if (mode == MainActivity.QR_WORK_ADD)
            sendWork();
        else {
            progressBar.setVisibility(View.GONE);
            if (!qrContent.getText().equals(content)) {
                findViewById(R.id.card_decompressed).setVisibility(View.VISIBLE);
                qrContentDecompressed.setText(content);
            }
        }

    }

    public void sendWork() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String title = sdf.format(new Date()) + "QR";

        soughService.postWork(Util.getUserWorkspaceName(this), title, content).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult().equals("success") && response.body().getWork() != null) {
                        cloudTitle.setText(response.body().getWork().getTitle());
                        cloudContent.setText(response.body().getWork().getContent());
                        Toast.makeText(ScanQRActivity.this, "성공!", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.sough_cloud_status).setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(ScanQRActivity.this, "Error : " + response.code(), Toast.LENGTH_SHORT).show();
                }

                cloudWorkspaceName.setText(String.format("작업공간 이름 : %s", Util.getUserWorkspaceName(ScanQRActivity.this)));
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(ScanQRActivity.this, "전송에 오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
