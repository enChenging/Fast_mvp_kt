package com.release.fast.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.release.fast.R
import com.release.fast.widget.CustomToast
import com.google.android.material.snackbar.Snackbar
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.release.easybasex.widget.CoolIndicatorLayout
import com.release.easybasex.widget.WebLayout
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */

fun androidx.fragment.app.Fragment.showToast(content: String) {
    CustomToast(this.activity, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

fun androidx.fragment.app.Fragment.showSnackMsg(msg: String) {
    val snackbar =
        Snackbar.make(this.requireActivity().window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text)
        .setTextColor(ContextCompat.getColor(this.requireActivity(), R.color.white))
    snackbar.show()
}

fun Activity.showSnackMsg(msg: String) {
    val snackbar = Snackbar.make(this.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text)
        .setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}

fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    webChromeClient: WebChromeClient?,
    webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件
    .setCustomIndicator(CoolIndicatorLayout(activity))
    .setWebChromeClient(webChromeClient)
    .setWebViewClient(webViewClient)
    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
    .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
    .setWebLayout(WebLayout(activity))
    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
    .interceptUnkownUrl() //拦截找不到相关页面的Scheme
    .createAgentWeb()
    .ready()
    .go(this)

/**
 * 格式化当前日期
 */
@SuppressLint("SimpleDateFormat")
fun formatCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}

/**
 * String 转 Calendar
 */
@SuppressLint("SimpleDateFormat")
fun String.stringToCalendar(): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}