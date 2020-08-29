package com.release.fast.ext

import com.release.fast.App
import com.release.fast.R
import com.release.fast.http.exception.ErrorStatus
import com.release.fast.http.exception.ExceptionHandle
import com.release.fast.http.function.RetryWithDelay
import com.release.fast.mvp.model.BaseBean
import com.release.fast.rx.SchedulerUtils
import com.orhanobut.logger.Logger
import com.release.easybasex.base.IView
import com.release.easybasex.utils.NetWorkUtil
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
fun <T> Observable<T>.ext(
    view: IView?,
    scopeProvider: AndroidLifecycleScopeProvider,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit  //T.()->Unit里的this代表的是自身实例，而()->Unit里，this代表的是外部类的实例
) {
    this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .autoDisposable(scopeProvider)
        .subscribe(object : Observer<T> {

            override fun onSubscribe(d: Disposable) {

                if (isShowLoading) view?.showLoading()

                if (!NetWorkUtil.isNetworkConnected(App.instance)) {
                    view?.showMsg(App.instance.resources.getString(R.string.network_unavailable_tip))
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                onSuccess.invoke(t)
            }

            override fun onError(e: Throwable) {
                Logger.i("onError: ${e.message}")
                if (isShowLoading)
                    view?.showError()
                else
                    view?.showError(App.instance.getString(R.string.data_error))
            }

            override fun onComplete() {
                view?.hideLoading()
            }

        })
}

fun <T : BaseBean> Observable<T>.ext2(
    view: IView?,
    scopeProvider: AndroidLifecycleScopeProvider,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit  //T.()->Unit里的this代表的是自身实例，而()->Unit里，this代表的是外部类的实例
) {
    this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .autoDisposable(scopeProvider)
        .subscribe(object : Observer<T> {

            override fun onSubscribe(d: Disposable) {

                if (isShowLoading) view?.showLoading()

                if (!NetWorkUtil.isNetworkConnected(App.instance)) {
                    view?.showMsg(App.instance.resources.getString(R.string.network_unavailable_tip))
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                when {
                    t.code == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                    t.code == ErrorStatus.TOKEN_INVALID -> {
                        view?.showMsg("Token 过期，重新登录")
                    }
                    else -> view?.showMsg(t.msg)
                }
            }

            override fun onError(e: Throwable) {
                Logger.i("onError: ${e.message}")
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(e))
            }

            override fun onComplete() {
                view?.hideLoading()
            }
        })
}

fun <T : BaseBean> Observable<T>.ext3(
    view: IView?,
    isShowLoading: Boolean = true,
    scopeProvider: AndroidLifecycleScopeProvider,
    onSuccess: (T) -> Unit
): Disposable {
    if (isShowLoading) view?.showLoading()
    return this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .autoDisposable(scopeProvider)
        .subscribe({
            when {
                it.code == ErrorStatus.SUCCESS -> onSuccess.invoke(it)
                it.code == ErrorStatus.TOKEN_INVALID -> {
                    // Token 过期，重新登录
                }
                else -> view?.showMsg(it.msg)
            }
            view?.hideLoading()
        }, {
            view?.hideLoading()
            view?.showError(ExceptionHandle.handleException(it))
        })
}




