package com.release.fast.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
class TrampolineMainScheduler<T> : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
