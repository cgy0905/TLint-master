package com.cgy.hupu.module.pmlist;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.bean.Pm;
import com.cgy.hupu.widget.LabelCardView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 17:09
 */
public class PmListAdapter extends RecyclerView.Adapter<PmListAdapter.ViewHolder> {

    private List<Pm> pms = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onPmListClick(Pm pm);
    }

    @Inject
    public PmListAdapter() {

    }

    public void bind(List<Pm> pms) {
        this.pms = pms;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pm, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pm pm = pms.get(position);
        holder.mPm = pm;
        if (!TextUtils.isEmpty(pm.header)) {
            holder.ivIcon.setImageURI(Uri.parse(pm.header));
        } else {
            holder.ivIcon.setVisibility(View.GONE);
        }
        holder.tvContent.setText(pm.content);
        holder.tvName.setText(pm.nickname);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(Long.valueOf(pm.last_time) * 1000);
        holder.tvTime.setText(format.format(date));
        if (!TextUtils.isEmpty(pm.unread) && pm.unread.equals("1")) {
            holder.cardView.setLabelText("NEW");
            holder.cardView.setLabelVisual(true);
        } else {
            holder.cardView.setLabelVisual(false);
        }
    }

    @Override
    public int getItemCount() {
        return pms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        Pm mPm;

        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.list_item)
        LabelCardView cardView;

        @OnClick(R.id.list_item)
        void listItemClick() {
            if (onItemClickListener != null) {
                onItemClickListener.onPmListClick(mPm);
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
