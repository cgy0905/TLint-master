package com.cgy.hupu.module.messagelist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.bean.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cgy on 2019/4/17.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void bind(List<Message> messages) {

    }

    public void remove(Message message) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
