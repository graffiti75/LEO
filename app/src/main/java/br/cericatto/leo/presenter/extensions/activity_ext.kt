package br.cericatto.leo.presenter.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.leo.AppConfiguration
import br.cericatto.leo.MainApplication
import timber.log.Timber

/**
 * Network.
 */

@Suppress("DEPRECATION")
fun Context.checkIfHasNetwork(): Boolean {
    var result = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}

/**
 * Views.
 */

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

/**
 * Context.
 */

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(message: Int) {
    Toast.makeText(this, this.getString(message), Toast.LENGTH_LONG).show()
}

fun initViewAnimation(view: View, animation: Animation) {
    view.startAnimation(animation)
}

fun Context.anim(animationId: Int): Animation {
    return AnimationUtils.loadAnimation(this, animationId)
}

/**
 * Pagination.
 */

fun listAllLoaded() : Boolean {
    val zero = (MainApplication.itemsLoaded == 0) && (MainApplication.itemsTotal == 0)
    return if (zero) false
    else (MainApplication.itemsLoaded >= MainApplication.itemsTotal)
}

fun debugPagination(position: Int, itemsLoaded: Int, pageLoaded: Boolean, allLoaded: Boolean) {
    Timber.i("---------- position: $position")
    Timber.i("---------- itemsLoaded: $itemsLoaded")
    Timber.i("---------- pageLoaded: $pageLoaded")
    Timber.i("---------- allLoaded: $allLoaded\n")
    Timber.i("--------------------------------------------------")
}

fun RecyclerView.scrollPagination(page: Int) {
    val position = AppConfiguration.ITEMS_PER_PAGE * page
//    this.layoutManager!!.smoothScrollToPosition(this, RecyclerView.State(), position)
    this.layoutManager!!.scrollToPosition(position - 5)
}