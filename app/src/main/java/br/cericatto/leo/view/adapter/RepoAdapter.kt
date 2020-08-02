package br.cericatto.leo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.leo.MainApplication
import br.cericatto.leo.R
import br.cericatto.leo.model.Repo
import br.cericatto.leo.view.activity.MainActivity
import timber.log.Timber

class RepoAdapter(activity: MainActivity) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private val mActivity = activity
    private var mRepoList: MutableList<Repo> = ArrayList()

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(inflater.inflate(R.layout.item_repo, parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        var repo = mRepoList[position]
        var view = holder.itemView
        checkPagination(position)
    }

    override fun getItemCount(): Int = mRepoList.size

    //--------------------------------------------------
    // Callback
    //--------------------------------------------------

    fun updateAdapter(list: MutableList<Repo>) {
        val newList = mRepoList
        newList.addAll(list)
        mRepoList = newList
        notifyDataSetChanged()
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private fun checkPagination(position: Int) {
        val shouldPaginate : Boolean = position == (mRepoList.size - 1)
        Timber.d("position: $position, itemCount - 1: ${mRepoList.size - 1}, shouldPaginate: $shouldPaginate")

        val app: MainApplication = mActivity.application as MainApplication
        val loadedAllData = app.loadedAllData
        val page = app.page
        if (!loadedAllData && shouldPaginate) {
            app.page = page + 1
            Timber.d("page: $page")
        }
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}