package com.hengkai.officeautomationsystem.view.docking_expandable_list_view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.controller.IDockingController;

/**
 * Created by Harry on 2018/4/27.
 */
public class DockingExpandableListViewAdapter extends BaseExpandableListAdapter implements IDockingController {

    private Context mContext;
    private ExpandableListView mListView;
    private IDockingAdapterDataSource mData;

    public DockingExpandableListViewAdapter(Context context, ExpandableListView expandableListView, IDockingAdapterDataSource data) {
        this.mContext = context;
        this.mListView = expandableListView;
        this.mData = data;
    }

    @Override
    public int getGroupCount() {
        return mData.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.getChildCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.getChild(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return mData.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return mData.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getDockingState(int firstVisibleGroup, int firstVisibleChild) {
        // No need to draw header view if this group does not contain any child & also not expanded.
        if (firstVisibleChild == -1 && !mListView.isGroupExpanded(firstVisibleGroup)) {
            return DOCKING_HEADER_HIDDEN;
        }

        // Reaching current group's last child, preparing for docking next group header.
        if (firstVisibleChild == getChildrenCount(firstVisibleGroup) - 1) {
            return IDockingController.DOCKING_HEADER_DOCKING;
        }

        // Scrolling inside current group, header view is docked.
        return IDockingController.DOCKING_HEADER_DOCKED;
    }
}
