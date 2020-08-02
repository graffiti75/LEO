package br.cericatto.leo.presenter.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import br.cericatto.leo.presenter.NavigationUtils

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToast(message: Int) {
    Toast.makeText(this, this.getString(message), Toast.LENGTH_LONG).show()
}

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