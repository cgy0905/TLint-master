package com.cgy.hupu.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by cgy on 2018/12/30 下午 5:14.
 */
@Entity
public class Forum implements Serializable {

    private static final long serialVersionUID = 9134632047039486840L;

    @Id(autoincrement = true)
    private Long id;
    private String fid;
    private String name;
    private String logo;
    private String description;
    private String backImg;
    private String forumId;
    private String categoryName;
    private Integer weight;
    @Generated(hash = 729047331)
    public Forum(Long id, String fid, String name, String logo, String description,
            String backImg, String forumId, String categoryName, Integer weight) {
        this.id = id;
        this.fid = fid;
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.backImg = backImg;
        this.forumId = forumId;
        this.categoryName = categoryName;
        this.weight = weight;
    }
    @Generated(hash = 2136154180)
    public Forum() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFid() {
        return this.fid;
    }
    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBackImg() {
        return this.backImg;
    }
    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }
    public String getForumId() {
        return this.forumId;
    }
    public void setForumId(String forumId) {
        this.forumId = forumId;
    }
    public String getCategoryName() {
        return this.categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Integer getWeight() {
        return this.weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
