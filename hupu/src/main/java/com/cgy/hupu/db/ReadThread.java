package com.cgy.hupu.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table READ_THREAD.
 */
public class ReadThread {

    private Long id;
    private String tid;

    public ReadThread() {
    }

    public ReadThread(Long id) {
        this.id = id;
    }

    public ReadThread(Long id, String tid) {
        this.id = id;
        this.tid = tid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
