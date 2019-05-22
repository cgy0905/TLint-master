package com.cgy.hupu.bean;

import java.util.List;

/**
 * @author cgy
 * @desctiption 文件夹
 * @date 2019/5/22 16:40
 */
public class Folder {
    public String name;
    public String path;
    public Image cover;
    public List<Image> images;

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
