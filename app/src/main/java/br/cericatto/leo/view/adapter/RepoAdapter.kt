package br.cericatto.leo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.leo.MainApplication
import br.cericatto.leo.R
import br.cericatto.leo.model.Repo
import br.cericatto.leo.view.activity.MainActivity
import kotlinx.android.synthetic.main.item_repo.view.*
import timber.log.Timber

class RepoAdapter(
    private val activity: MainActivity
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private var mRepoList: MutableList<Repo> = ArrayList()

    //--------------------------------------------------
    // Adapter Methods
    //--------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(inflater.inflate(R.layout.item_repo, parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = mRepoList[position]
        val view = holder.itemView
        setTitle(view, repo)
        checkPagination(position)
    }

    override fun getItemCount(): Int = mRepoList.size

    //--------------------------------------------------
    // Callback
    //--------------------------------------------------

    fun updateAdapter(list: List<Repo>) {
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

        val app: MainApplication = activity.application as MainApplication
        val loadedAllData = app.loadedAllData
        val page = app.page
        if (!loadedAllData && shouldPaginate) {
            app.page = page + 1
            Timber.d("page: $page")
            activity.presenter.initDataSet()
        }
    }

    private fun setTitle(view: View, repo: Repo) {
        view.apply {
            id_item_repo__title_text_view.text = repo.name
            id_item_repo__title_text_view.setOnClickListener {}
        }
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}