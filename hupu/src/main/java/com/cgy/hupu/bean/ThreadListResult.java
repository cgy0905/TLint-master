package com.cgy.hupu.bean;

import com.cgy.hupu.db.Thread;

import java.util.ArrayList;

/**
 * Created by cgy on 2018/12/31 下午 4:30.
 */
public class ThreadListResult {

    public String stamp;
    public ArrayList<Thread> data;
    public boolean nextPage;
    public int nextDataExists;
}
