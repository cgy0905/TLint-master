package com.cgy.hupu.bean;

/**
 * @author cgy
 * @desctiption 图片实体
 * @date 2019/5/22 16:40
 */
public class Image {

    public String path;
    public String name;
    public long time;
    public int type;
    public boolean checked = false;

    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
