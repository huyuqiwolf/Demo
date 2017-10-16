package com.ken.demo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by work on 2017/10/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initData(savedInstanceState);
        initDialog();
        initFrom();
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                progressDialog.setMessage("");
                progressDialog.setTitle("");
            }
        });
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void initData(Bundle savedInstanceState);
    protected abstract void initFrom();

    protected void showToast(String message){
        if(toast == null){
            toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

}
