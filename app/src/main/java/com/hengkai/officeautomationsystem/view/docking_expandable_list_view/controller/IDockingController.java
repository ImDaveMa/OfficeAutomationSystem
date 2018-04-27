package com.hengkai.officeautomationsystem.view.docking_expandable_list_view.controller;

/**
 * Created by Harry on 2018/4/27.
 */
public interface IDockingController {
    /**
     * 当分组没有展开，或者组里没有子项的时候，是不需要绘制悬停标题的
     */
    int DOCKING_HEADER_HIDDEN = 1;
    /**
     * 当滚动到上一个分组的最后一个子项时，需要把旧的标题“推”出去，“停靠”新的标题，所以这个状态命名为“docking”
     */
    int DOCKING_HEADER_DOCKING = 2;
    /**
     * 新标题“停靠”完毕，在该分组内部滚动，称为“docked”状态
     */
    int DOCKING_HEADER_DOCKED = 3;

    int getDockingState(int firstVisibleGroup, int firstVisibleChild);
}
