package com.cgy.hupu.components.notifier;

import android.app.Notification;
import android.app.NotificationManager;

import javax.inject.Inject;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 14:25
 */
public class Notifier {
    public static final int OfflineThreads = 9001;//离线帖子

    public static final int OfflineReplies = 9002;//离线回复

    public static final int OfflinePicture = 9003;//离线图片

    @Inject
    NotificationManager notificationManager;

    protected void notify(int id, Notification notification) {
        notificationManager.notify(id, notification);
    }

    public void cancalNotification(int request) {
        notificationManager.cancel(request);
    }

    public void cancelAll() {
        notificationManager.cancel(OfflineThreads);
        notificationManager.cancel(OfflineReplies);
        notificationManager.cancel(OfflinePicture);
    }
}
