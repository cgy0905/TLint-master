package com.cgy.hupu.otto;

/**
 * Created by cgy on 2019/4/21 21:27 .
 */
public class UpdateContentPageEvent {

    private int page;
    private int totalPage;

    public UpdateContentPageEvent(int page, int totalPage) {
        this.page = page;
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
