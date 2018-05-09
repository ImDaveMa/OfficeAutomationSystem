package com.hengkai.officeautomationsystem.listener;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View v, T t, int position);
}
