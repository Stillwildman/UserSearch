package com.vincent.githubusersearching.bases;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseBindingRecycler<Item, BindingView extends ViewDataBinding> extends PagedListAdapter<Item, RecyclerView.ViewHolder> {

    protected final String TAG = getClass().getSimpleName();

    protected BaseBindingRecycler(@NonNull DiffUtil.ItemCallback<Item> diffCallback) {
        super(diffCallback);
    }

    protected abstract int getLayoutId();

    protected abstract void onBindingViewHolder(RecyclerView.ViewHolder holder, BindingView bindingView, int position);

    protected abstract void onBindingViewHolder(RecyclerView.ViewHolder holder, BindingView bindingView, int position, Object payload);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BindingView binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new BindingViewHolder(binding);
    }

    protected class BindingViewHolder extends RecyclerView.ViewHolder {

        final BindingView bindingView;

        BindingViewHolder(BindingView bindingView) {
            super(bindingView.getRoot());
            this.bindingView = bindingView;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindingViewHolder(holder, ((BindingViewHolder) holder).bindingView, position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        }
        else {
            onBindingViewHolder(holder, ((BindingViewHolder) holder).bindingView, position, payloads.get(0));
        }
    }
}
