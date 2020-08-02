package br.cericatto.leo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.leo.MainApplication
import br.cericatto.leo.R
import br.cericatto.leo.model.Repo
import br.cericatto.leo.presenter.extensions.*
import br.cericatto.leo.view.activity.MainActivity
import kotlinx.android.synthetic.main.item_repo.view.*

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
        val itemsLoaded = MainApplication.itemsLoaded
        val pageLoaded = position == itemsLoaded - 1
        val allLoaded = listAllLoaded()
        debugPagination(position, itemsLoaded, pageLoaded, allLoaded)
        activity.apply {
            if (!allLoaded && pageLoaded) {
                MainApplication.page++
                presenter.showPaginationLoading()
                presenter.getRepos()
            }
        }
    }

    private fun setTitle(view: View, repo: Repo) {
        view.apply {
            val textView = id_item_repo__title_text_view
            textView.text = repo.full_name
            textView.setOnClickListener {
                val context = it.context
                activity.showToast(repo.toString())
                initViewAnimation(view, context.anim(R.anim.zoom_in))
            }
        }
    }

    //--------------------------------------------------
    // View Holder
    //--------------------------------------------------

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}