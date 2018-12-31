package com.cgy.hupu.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cgy on 2018/12/31 下午 5:33.
 */
@Entity
public class ReadThread {

    @Id(autoincrement =  true)
    private Long id;
    
    private String tid;

    @Generated(hash = 1252754)
    public ReadThread(Long id, String tid) {
        this.id = id;
        this.tid = tid;
    }

    @Generated(hash = 13230651)
    public ReadThread() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
