package com.cgy.hupu.module.gallery;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgy.hupu.R;
import com.cgy.hupu.bean.Folder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 17:25
 */
public class FolderAdapter extends BaseAdapter {

    private List<Folder> folders = new ArrayList<>();
    private int lastSelected = 0;

    @Inject
    public FolderAdapter() {

    }

    public void bind(List<Folder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    public void setSelectIndex(int i) {
        if (lastSelected == i)
            return;
        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    private int getTotalImageSize(List<Folder> folders) {
        int result = 0;
        if (folders != null && folders.size() > 0) {
            for (Folder folder : folders) {
                result += folder.images.size();
            }
        }
        return result;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Object getItem(int position) {
        return folders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_folder, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Folder folder = folders.get(position);
        if (position == 0) {
            holder.tvName.setText("所有图片");
            holder.tvSize.setText(getTotalImageSize(folders) + "张");
            if (!folders.isEmpty()) {
                holder.ivCover.setImageURI(Uri.fromFile(new File(folder.cover.path)));
            }
        } else {
            holder.tvName.setText(folder.name);
            holder.tvSize.setText(folder.images.size() + "张");
            holder.ivCover.setImageURI(Uri.fromFile(new File(folder.cover.path)));
        }
        holder.ivIndicator.setVisibility(lastSelected == position ? View.VISIBLE : View.GONE);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.iv_indicator)
        ImageView ivIndicator;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_size)
        TextView tvSize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
