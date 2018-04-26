package com.hengkai.officeautomationsystem.utils.rx;

import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2017/7/8.
 */

public interface RxActionManager<T> {
    void add(T tag, Disposable disposable);

    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}
