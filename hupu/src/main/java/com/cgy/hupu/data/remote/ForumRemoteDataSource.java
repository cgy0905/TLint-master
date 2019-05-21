package com.cgy.hupu.data.remote;

import com.cgy.hupu.data.ForumDataSource;
import com.cgy.hupu.bean.Forums;
import com.cgy.hupu.bean.ForumsResult;
import com.cgy.hupu.bean.MyForumsResult;
import com.cgy.hupu.db.Forum;
import com.cgy.hupu.net.forum.ForumApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 13:47
 */
public class ForumRemoteDataSource implements ForumDataSource {

    private final ForumApi mForumApi;

    @Inject
    public ForumRemoteDataSource(ForumApi forumApi) {
        this.mForumApi = forumApi;
    }

    @Override
    public Observable<List<Forum>> getForumList(String forumId) {
        if (forumId.equals("0")) {
            return mForumApi.getMyForums().map(myForumsData -> {
                if (myForumsData != null && myForumsData.data != null) {
                   MyForumsResult data = myForumsData.data;
                    for (Forum forum: data.sub) {
                        forum.setForumId(data.fid);
                        forum.setCategoryName(data.name);
                        forum.setWeight(1);
                    }
                    return data.sub;
                }
                return null;
            }).onErrorReturn(throwable -> null);
        } else {
            return mForumApi.getForums().map(forumsData -> {
                if (forumsData != null) {
                    List<Forum> forumList = new ArrayList<>();
                    for (ForumsResult data : forumsData.data) {
                        if (data.fid.equals(forumId)) {
                            for (Forums forums:data.sub) {
                                for (Forum forum : forums.data) {
                                    forum.setForumId(data.fid);
                                    forum.setCategoryName(forums.name);
                                    forum.setWeight(forums.weight);
                                    forumList.add(forum);
                                }
                            }
                        }
                    }
                    return forumList;
                }
                return null;
            }).onErrorReturn(throwable -> null);
        }
    }

    @Override
    public Observable<Boolean> removeForum(String fid) {
        return null;
    }
}
