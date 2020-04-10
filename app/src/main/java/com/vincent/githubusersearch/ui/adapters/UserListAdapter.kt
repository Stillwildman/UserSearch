package com.vincent.githubusersearch.ui.adapters

import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vincent.githubusersearch.R
import com.vincent.githubusersearch.bases.BaseBindingRecycler
import com.vincent.githubusersearch.databinding.InflateUserRowBinding
import com.vincent.githubusersearch.model.User

/**
 * 繼承[BaseBindingRecycler]，用DataBinding呈現資料。
 */
class UserListAdapter(diffCallback: DiffUtil.ItemCallback<User>) : BaseBindingRecycler<User, InflateUserRowBinding>(diffCallback) {

    private val options: RequestOptions by lazy {
        RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_place_holder_circle).fitCenter()
    }

    override fun getLayoutId(): Int = R.layout.inflate_user_row

    /**
     * 更新PagedList，必須使用submitList
     *
     * @see submitList
     */
    fun updateList(userPagedList: PagedList<User>?) {
        userPagedList?.run {
            submitList(userPagedList)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateUserRowBinding, position: Int) {
        val userItem = getItem(position)

        userItem?.let {
            it.indexNumber = position

            bindingView.item = it

            Glide.with(holder.itemView)
                    .load(it.getAvatarUrl())
                    .apply(options)
                    .into(bindingView.imageAvatar)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateUserRowBinding, position: Int, payload: Any?) {
        // 如果有帶payload更新特定位置的話
    }
}