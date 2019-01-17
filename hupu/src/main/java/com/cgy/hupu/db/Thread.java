package com.cgy.hupu.db;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cgy on 2018/12/30 下午 5:09.
 */
@Entity
public class Thread {

    @Id(autoincrement = true)
    private long id;
    private String tid;
    private String title;
    private String puid;
    private String fid;
    private String replies;
    private String userName;
    private String time;
    private String forumName;
    private Integer lightReply;
    private Integer type;
    @Convert(converter = ForumConvert.class, columnType = String.class)
    private Forum forum;
    @Generated(hash = 298534356)
    public Thread(long id, String tid, String title, String puid, String fid,
            String replies, String userName, String time, String forumName,
            Integer lightReply, Integer type, Forum forum) {
        this.id = id;
        this.tid = tid;
        this.title = title;
        this.puid = puid;
        this.fid = fid;
        this.replies = replies;
        this.userName = userName;
        this.time = time;
        this.forumName = forumName;
        this.lightReply = lightReply;
        this.type = type;
        this.forum = forum;
    }
    @Generated(hash = 218849146)
    public Thread() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTid() {
        return this.tid;
    }
    public void setTid(String tid) {
        this.tid = tid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPuid() {
        return this.puid;
    }
    public void setPuid(String puid) {
        this.puid = puid;
    }
    public String getFid() {
        return this.fid;
    }
    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getReplies() {
        return this.replies;
    }
    public void setReplies(String replies) {
        this.replies = replies;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getForumName() {
        return this.forumName;
    }
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    public Integer getLightReply() {
        return this.lightReply;
    }
    public void setLightReply(Integer lightReply) {
        this.lightReply = lightReply;
    }
    public Integer getType() {
        return this.type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Forum getForum() {
        return this.forum;
    }
    public void setForum(Forum forum) {
        this.forum = forum;
    }
    

}
