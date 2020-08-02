package br.cericatto.leo.presenter.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.cericatto.leo.AppConfiguration
import br.cericatto.leo.MainApplication
import br.cericatto.leo.presenter.NavigationUtils
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

fun View.setInvisible() {
    visibility = View.INVISIBLE
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

fun Context.openActivity(activity: Activity, clazz: Class<*>) {
    val intent = Intent(activity, clazz)
    activity.startActivity(intent)
    NavigationUtils.animate(activity, NavigationUtils.Animation.GO)
}

fun Context.openActivityExtra(activity: Activity, clazz: Class<*>, key: String, value: Any) {
    val intent = Intent(activity, clazz)
    val extras = getExtra(Bundle(), key, value)
    intent.putExtras(extras)

    activity.startActivity(intent)
    NavigationUtils.animate(activity, NavigationUtils.Animation.GO)
}

fun Context.openActivityExtras(activity: Activity, clazz: Class<*>, keys: Array<String>, values: Array<Any>) {
    val intent = Intent(activity, clazz)
    var extras = Bundle()
    val size = keys.size
    for (i in 0 until size) {
        val key = keys[i]
        val value = values[i]
        extras = getExtra(extras, key, value)
    }
    intent.putExtras(extras)

    activity.startActivity(intent)
    NavigationUtils.animate(activity, NavigationUtils.Animation.GO)
}

fun Context.getExtra(extras: Bundle, key: String, value: Any): Bundle {
    when (value) {
        is String -> extras.putString(key, value)
        is Int -> extras.putInt(key, value)
        is Long -> extras.putLong(key, value)
        is Boolean -> extras.putBoolean(key, value)
    }
    return extras
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
    val tag = "leo"
    tag.apply {
        Log.i(this,"---------- position: $position")
        Log.i(this,"---------- itemsLoaded: $itemsLoaded")
        Log.i(this,"---------- pageLoaded: $pageLoaded")
        Log.i(this,"---------- allLoaded: $allLoaded\n")
        Log.i(this,"--------------------------------------------------")
    }
//    Timber.i("---------- position: $position")
//    Timber.i("---------- itemsLoaded: $itemsLoaded")
//    Timber.i("---------- pageLoaded: $pageLoaded")
//    Timber.i("---------- allLoaded: $allLoaded\n")
//    Timber.i("--------------------------------------------------")
}

fun RecyclerView.scrollPagination(page: Int) {
    val position = AppConfiguration.ITEMS_PER_PAGE * page
//    this.layoutManager!!.smoothScrollToPosition(this, RecyclerView.State(), position)
    this.layoutManager!!.scrollToPosition(position - 5)
}