package com.vincent.githubusersearch.bases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * <h1>BaseBindingRecycler</h1>
 * <p>
 * �����ϥ�DataBinding��RecyclerView.Adapter�~�Ӧ����A�i�H��²���a�ϥ�DataBinding
 * </p>
 *
 * �������NAdapter�ɯŬ�PagedListAdapter�A��pass�@��DiffUtil.ItemCallback�i��
 *
 * @param Item Data type of PagedList
 * @param BindingView �j�w��ViewDataBinding
 */
abstract class BaseBindingRecycler<Item, BindingView : ViewDataBinding> protected constructor(diffCallback: DiffUtil.ItemCallback<Item>)
    : PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback) {

    protected abstract fun getLayoutId(): Int

    // ���N�쥻��onBindViewHolder�A�ηN����bindingView�a�X�ӵ��l���O�ϥ�
    protected abstract fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: BindingView, position: Int)

    // �P�W�A�P�ɤ]���K��payload�qList�����X
    protected abstract fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: BindingView, position: Int, payload: Any?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: BindingView = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(), parent, false)
        return BindingViewHolder(binding)
    }

    inner class BindingViewHolder(val bindingView: BindingView) : RecyclerView.ViewHolder(bindingView.root)

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindingViewHolder(holder, (holder as BaseBindingRecycler<Item, BindingView>.BindingViewHolder).bindingView, position)
    }

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
        else {
            onBindingViewHolder(holder, (holder as BaseBindingRecycler<Item, BindingView>.BindingViewHolder).bindingView, position, payloads[0])
        }
    }
}