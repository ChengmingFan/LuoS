package com.fan.soulkiller.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fan.soulkiller.R;

import java.net.URLEncoder;

/**
 * Created by Chengming Fan on 2023/3/10 13:16
 */
public class DonateActivity extends AppCompatActivity implements View.OnClickListener {
    private Button alipayBtn;
    private Button wechatPayBtn;

    private final static String ALIPAY_LINK = "https://qr.alipay.com";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate);
        initViews();
        initEvent();
    }

    private void initViews() {
        alipayBtn = findViewById(R.id.alipayBtn);
        wechatPayBtn = findViewById(R.id.wechatPayBtn);
    }

    private void initEvent() {
        alipayBtn.setOnClickListener(this);
        wechatPayBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alipayBtn:
                openAlipay();
                break;
            case R.id.wechatPayBtn:
                openWechatPay();
                break;
            default:
                break;
        }
    }

    private void openAlipay() {
        if (openAlipayPayPage(this, ALIPAY_LINK)) {
            Toast.makeText(this, "跳转成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_SHORT).show();
        }
    }
    private void openWechatPay() {
        Dialog dialog = new Dialog(DonateActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCanceledOnTouchOutside(true);
        ImageView imageView = dialog.findViewById(R.id.wechatPayImage);
        imageView.setOnClickListener(it-> dialog.cancel());
        dialog.show();
    }

    public static boolean openAlipayPayPage(Context context, String qrcode) {
        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            openUri(context, alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    private static void openUri(Context context, String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        context.startActivity(intent);
    }
}
