package com.hengkai.officeautomationsystem.view.docking_expandable_list_view.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Harry on 2018/4/27.
 */
public interface IDockingAdapterDataSource {

    int getGroupCount();
    int getChildCount(int groupPosition);
    Object getGroup(int groupPosition);
    Object getChild(int groupPosition, int childPosition);
    View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);
    View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);
}
