package com.ken.demo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ken.demo.bean.Task;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0;
    public static final int RESULT_CODE = REQUEST_CODE+1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler handler = new Handler();
    private boolean from = false;
    private ArrayList<Task> tasks = null;
    private int currentTaskIndex = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initFrom() {
        from = getIntent().getBooleanExtra("from", true);
        if (from) {
            tasks = Task.getTasksFromSdCard();
            executeTask();
        }
    }

    private void executeTask() {
        if(tasks == null || tasks.isEmpty()){
            showToast("task.xml文件缺失，或者文件已损坏");
            return;
        }
        if(currentTaskIndex == tasks.size()){
            showToast("已完成task.xml文件中的所有任务");
            return;
        }
        Log.d(TAG, "executeTask: "+tasks);
        Task task = tasks.get(currentTaskIndex);
        Log.d(TAG, "executeTask: "+task);
        currentTaskIndex ++;
        String taskName = task.getName();
        //此处应该和task.xml文件中的name标签值一致
        Class<? extends BaseActivity> clazz = null;
        if("Task1".equals(taskName)){
            clazz = Task1Activity.class;
        }else if("Task2".equals(taskName)){
            clazz = Task2Activity.class;
        }else if("Task3".equals(taskName)){
            clazz = Task3Activity.class;
        }
        if(clazz != null){
            Intent intent =new Intent(this,clazz);
            Bundle bundle = new Bundle();
            bundle.putParcelable("task",task);
            intent.putExtra("bundle",bundle);
            intent.putExtra("from",true);
            startActivityForResult(intent,REQUEST_CODE);
        }

    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.btn_task1:
                startActivity(new Intent(this,Task1Activity.class));
                break;
            case R.id.btn_task2:
                startActivity(new Intent(this,Task2Activity.class));
                break;
            case R.id.btn_task3:
                startActivity(new Intent(this,Task3Activity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if (resultCode == RESULT_CODE){
                //延迟500ms方便观察
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        executeTask();
                    }
                },500);
            }
        }
    }
}
