package com.cgy.hupu.module.messagelist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.bean.Message;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cgy on 2019/4/17.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    @Inject
    public MessageListAdapter() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onMessageClick(Message message);
    }

    private List<Message> messages = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void bind(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.message = message;
        holder.tvTime.setText(message.time);
        holder.tvCategory.setText(message.catergory);
        holder.tvInfo.setText(message.info);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void remove(Message message) {
        messages.remove(message);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.tv_info)
        TextView tvInfo;
        @BindView(R.id.tv_time)
        TextView tvTime;

        Message message;

        @OnClick(R.id.list_item)
        void listItemClick() {
            if (onItemClickListener != null) {
                onItemClickListener.onMessageClick(message);
            }
        }

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
