package com.ken.demo.bean;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by work on 2017/10/16.
 */

public class Task implements Parcelable,Comparable<Task> {
    private String name;
    private int testCount;
    private int index;

    public Task() {
    }

    private Task(Parcel parcel) {
        this.index = parcel.readInt();
        this.testCount = parcel.readInt();
        this.name = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // 写入时是什么顺序,读取的时候就按什么顺序读取
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeInt(testCount);
        parcel.writeString(name);
    }


    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        @Override
        public Task createFromParcel(Parcel parcel) {
            return new Task(parcel);
        }

        @Override
        public Task[] newArray(int i) {
            return new Task[0];
        }
    };


    public static ArrayList<Task> getTasksFromSdCard() {
        File file = new File(Environment.getExternalStorageDirectory(), "task.xml");
        if (!file.exists())
            return null;
        ArrayList<Task> tasks = null;
        Task task = null;
        try {

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new FileInputStream(file), "UTF-8");

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        tasks = new ArrayList<Task>();
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        if("task".equals(tagName)){
                            task = new Task();
                        }else if("name".equals(tagName)){
                            task.setName(parser.nextText());
                        }else if("index".equals(tagName)){
                            task.setIndex(Integer.valueOf(parser.nextText().trim()));
                        }else if("testCount".equals(tagName)){
                            task.setTestCount(Integer.valueOf(parser.nextText().trim()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(tasks!= null && task !=null){
                            tasks.add(task);
                            task = null;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:

                        break;
                }
                eventType = parser.next();
            }
            if(tasks != null){
                Collections.sort(tasks);
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return tasks;
    }

    @Override
    public int compareTo(@NonNull Task task) {
        return this.index - task.index;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", testCount=" + testCount +
                ", index=" + index +
                '}';
    }
}
