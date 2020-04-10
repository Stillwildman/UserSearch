package com.vincent.githubusersearch.ui

import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.githubusersearch.AppController
import com.vincent.githubusersearch.R
import com.vincent.githubusersearch.bases.BaseActivity
import com.vincent.githubusersearch.callbacks.PagingStatusCallback
import com.vincent.githubusersearch.databinding.ActivityMainBinding
import com.vincent.githubusersearch.model.User
import com.vincent.githubusersearch.ui.adapters.UserListAdapter
import com.vincent.githubusersearch.utilities.Utility
import com.vincent.githubusersearch.viewmodel.UserViewModel

/**
 * <p>MainActivity�A�]�O�o��demo app���ߤ@��Activity</p>
 *
 * �o�̥u�|��UI�e�{�������ơA�]�tViewModel�C
 */
class UiMainActivity : BaseActivity<ActivityMainBinding>(), PagingStatusCallback {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getToolbar(): Toolbar = mBinding.toolbar

    private val viewModel: UserViewModel by lazy { ViewModelProvider(this).get(UserViewModel::class.java) }

    // Init RecyclerView and ViewModel.
    override fun init() {
        initRecycler()
        initViewModel()
    }

    private fun initRecycler() {
        mBinding.layoutContent.recycler.layoutManager = LinearLayoutManager(this)
        mBinding.layoutContent.recycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        // �]Adapter�ϥ�PagedListAdapter�A�ҥH�npass�@��DiffUtil.ItemCallback<DataType>�i�h
        mBinding.layoutContent.recycler.adapter = UserListAdapter(object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.getName() == newItem.getName()
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.getName() == newItem.getName() && oldItem.getAvatarUrl() == newItem.getAvatarUrl()
            }
        })
    }

    private fun initViewModel() {
        viewModel.setStatusCallback(this)
        mBinding.userViewModel = viewModel
        observeStatus()
    }

    // �}�lobserve�AViewModel�f�tLiveData�ϥΡA���Ʀ��ܰʮɥD�ʳq��
    private fun observeStatus() {
        viewModel.liveLoadingStatus.observe(this, Observer { isLoading: Boolean -> setLoading(isLoading) })
        viewModel.liveErrorMessage.observe(this, Observer { errorMessage: String -> Utility.toastShort(errorMessage) })
    }

    override fun requireData() {
        AppController.instance.hideKeyboard(mBinding.layoutContent.editQuery)

        viewModel.getUserList(getQueryString()).observe(this, Observer { userPagedList: PagedList<User>? ->
            getUserListAdapter().updateList(userPagedList)
        })
    }

    // ��Paging�b���o��Ʈ�
    override fun onLoading(isLoading: Boolean) {
        viewModel.liveLoadingStatus.postValue(isLoading)
    }

    // ��Paging����Ʈɵo�Ϳ��~
    override fun onFailed(errorMessage: String?) {
        errorMessage?.let {
            Log.e(tag, it)
            viewModel.liveErrorMessage.value = it
        }
    }

    // �bToolbar��ܩ�����ProgressBar
    private fun setLoading(isLoading: Boolean) {
        mBinding.isLoading = isLoading
    }

    private fun getQueryString(): String = mBinding.layoutContent.editQuery.text.toString()

    private fun getUserListAdapter(): UserListAdapter = mBinding.layoutContent.recycler.adapter as UserListAdapter
}