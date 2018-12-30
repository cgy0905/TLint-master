package com.cgy.hupu.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

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

    private Long forumId;
    @ToOne(joinProperty = "forumId")
    private Forum forum;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 473811190)
    private transient ThreadDao myDao;

    @Generated(hash = 405191179)
    public Thread(long id, String tid, String title, String puid, String fid,
                  String replies, String userName, String time, String forumName,
                  Integer lightReply, Integer type, Long forumId) {
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
        this.forumId = forumId;
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

    public Long getForumId() {
        return this.forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    @Generated(hash = 142144214)
    private transient Long forum__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1371006896)
    public Forum getForum() {
        Long __key = this.forumId;
        if (forum__resolvedKey == null || !forum__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ForumDao targetDao = daoSession.getForumDao();
            Forum forumNew = targetDao.load(__key);
            synchronized (this) {
                forum = forumNew;
                forum__resolvedKey = __key;
            }
        }
        return forum;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 884584905)
    public void setForum(Forum forum) {
        synchronized (this) {
            this.forum = forum;
            forumId = forum == null ? null : forum.getId();
            forum__resolvedKey = forumId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 5320433)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getThreadDao() : null;
    }
}
