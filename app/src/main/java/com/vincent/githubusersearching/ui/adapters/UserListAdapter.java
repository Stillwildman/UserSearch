package com.vincent.githubusersearching.ui.adapters;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.vincent.githubusersearching.R;
import com.vincent.githubusersearching.bases.BaseBindingRecycler;
import com.vincent.githubusersearching.databinding.InflateUserRowBinding;
import com.vincent.githubusersearching.model.ItemSearchResult;

public class UserListAdapter extends BaseBindingRecycler<ItemSearchResult.ItemUser, InflateUserRowBinding> {

    private final RequestOptions options;

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<ItemSearchResult.ItemUser> diffCallback) {
        super(diffCallback);
        options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter();
    }

    public void updateList(PagedList<ItemSearchResult.ItemUser> userPagedList) {
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
        ItemSearchResult.ItemUser userItem = getItem(position);

        if (userItem != null) {
            userItem.setPosition(position);
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
