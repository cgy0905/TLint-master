package com.cgy.hupu.module.account;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.db.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onAccountItemDelClicked(User user);

        void onAccountItemClicked(User user);
    }

    private List<User> users = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @Inject
    public AccountAdapter() {}


    public void bind(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_account, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.user = user;
        if (!TextUtils.isEmpty(user.getIcon())) {
            holder.ivIcon.setImageURI(Uri.parse(user.getIcon()));
        }
        holder.tvName.setText(user.getUserName());
        holder.tvDesc.setText(user.getRegisterTime());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        User user;

        @OnClick(R.id.rl_del)
        void rlDelClick() {
            if (onItemClickListener != null) {
                onItemClickListener.onAccountItemDelClicked(user);
            }
        }

        @OnClick(R.id.ll_account)
        void llAccountClick() {
            if (onItemClickListener != null) {
                onItemClickListener.onAccountItemClicked(user);
            }
        }

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
