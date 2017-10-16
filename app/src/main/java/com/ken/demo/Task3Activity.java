package com.ken.demo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ken.demo.bean.Task;

public class Task3Activity extends BaseActivity {
    private static final int START = 0x0;
    private static final int FINISH = START + 1;

    private boolean from = false;

    private Task task = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    progressDialog.setTitle("Task3 executing");
                    progressDialog.setMessage(task.toString());
                    progressDialog.show();
                    break;
                case FINISH:
                    progressDialog.dismiss();
                    if (from) {//非电击按钮跳转过来的
                        setResult(MainActivity.RESULT_CODE);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task3);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initFrom() {
        Intent intent = getIntent();
        from = intent.getBooleanExtra("from", false);
        if (from) {
            //
            task = intent.getBundleExtra("bundle").getParcelable("task");
            //根据任务信息处理，或开启线程，或直接操作，这里使用线程模拟一个耗时操作
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(START);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(FINISH);
                }
            }).start();
        }
    }
}
