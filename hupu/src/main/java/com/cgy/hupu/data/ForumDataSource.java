package com.cgy.hupu.data;

import com.cgy.hupu.db.Forum;

import java.util.List;

import rx.Observable;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 13:41
 */
public interface ForumDataSource {

    Observable<List<Forum>> getForumList(String forumId);

    Observable<Boolean> removeForum(String fid);
}
