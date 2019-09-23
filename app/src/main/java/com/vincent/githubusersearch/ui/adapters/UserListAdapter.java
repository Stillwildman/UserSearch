package com.vincent.githubusersearch.ui.adapters;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.vincent.githubusersearch.R;
import com.vincent.githubusersearch.bases.BaseBindingRecycler;
import com.vincent.githubusersearch.databinding.InflateUserRowBinding;
import com.vincent.githubusersearch.model.User;

public class UserListAdapter extends BaseBindingRecycler<User, InflateUserRowBinding> {

    private final RequestOptions options;

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
        options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter();
    }

    public void updateList(PagedList<User> userPagedList) {
        if (userPagedList != null) {
            this.submitList(userPagedList);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.inflate_user_row;
    }

    @Override
    protected void onBindingViewHolder(RecyclerView.ViewHolder holder, InflateUserRowBinding bindingView, int position) {
        User userItem = getItem(position);

        if (userItem != null) {
            userItem.setNumber(position);
            bindingView.setItem(userItem);

            Glide.with(holder.itemView)
                    .load(userItem.getAvatarUrl())
                    .apply(options)
                    .into(bindingView.imageAvatar);
        }
    }

    @Override
    protected void onBindingViewHolder(RecyclerView.ViewHolder holder, InflateUserRowBinding bindingView, int position, Object payload) {

    }
}
