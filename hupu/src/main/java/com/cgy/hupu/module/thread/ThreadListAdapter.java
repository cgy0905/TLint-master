package com.cgy.hupu.module.thread;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.db.ReadThreadDao;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.utils.ResourceUtil;
import com.cgy.hupu.utils.SettingPrefUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2018/12/31 下午 4:01.
 */
public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.ViewHolder> {

    private Activity mActivity;
    private ReadThreadDao mReadThreadDao;
    private List<Thread> threads;

    @Inject
    public ThreadListAdapter(Activity mActivity, ReadThreadDao mReadThreadDao) {
        this.mActivity = mActivity;
        this.mReadThreadDao = mReadThreadDao;
        threads = Collections.emptyList();
    }

    public void bind(List<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_thread, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Thread thread = threads.get(position);
        holder.mThread = thread;
        holder.mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, SettingPrefUtil.getTitleSize(mActivity));
        holder.mTvTitle.setText(Html.fromHtml(thread.getTitle()));
        if (thread.getLightReply() != null && thread.getLightReply() > 0) {
            holder.mTvLight.setVisibility(View.VISIBLE);
            holder.mTvLight.setText(String.valueOf(thread.getLightReply()));
        } else {
            holder.mTvLight.setVisibility(View.GONE);
        }
        holder.mTvReply.setText(thread.getReplies());
        holder.mTvSingleTime.setVisibility(View.VISIBLE);
        holder.mTvSummary.setVisibility(View.GONE);
        holder.mGrid.setVisibility(View.GONE);
        if (thread.getForum() != null) {
            holder.mTvSingleTime.setText(thread.getForum().getName());
        } else {
            holder.mTvSingleTime.setText(thread.getTime());
        }
        Observable.just(thread.getTid())
                .map(s -> mReadThreadDao.queryBuilder().where(ReadThreadDao.Properties.Tid.eq(s)).count() > 0).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        holder.mTvTitle.setTextColor(ResourceUtil.getThemeAttrColor(mActivity, android.R.attr.textColorSecondary));
                    } else {
                        holder.mTvTitle.setTextColor(ResourceUtil.getThemeAttrColor(mActivity, android.R.attr.textColorPrimary));
                    }
                });
        showItemAnim(holder.mCardView, position);
    }

    private int mLastPosition = -1;
    private void showItemAnim(View view, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_summary)
        TextView mTvSummary;
        @BindView(R.id.grid)
        GridLayout mGrid;
        @BindView(R.id.tv_single_time)
        TextView mTvSingleTime;
        @BindView(R.id.tv_reply)
        TextView mTvReply;
        @BindView(R.id.tv_light)
        TextView mTvLight;
        @BindView(R.id.cardView)
        CardView mCardView;

        Thread mThread;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
