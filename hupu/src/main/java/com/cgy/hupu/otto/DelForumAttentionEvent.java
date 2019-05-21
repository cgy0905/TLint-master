package com.cgy.hupu.otto;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 14:14
 */
public class DelForumAttentionEvent {
    private String fid;

    public DelForumAttentionEvent(String fid) {
        this.fid = fid;
    }

    public String getFid() {
        return fid;
    }
}
