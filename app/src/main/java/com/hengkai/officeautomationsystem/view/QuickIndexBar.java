package com.hengkai.officeautomationsystem.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hengkai.officeautomationsystem.R;

/**
 * Created by Harry on 2017/10/16.
 * 自定义快速索引Bar
 */

public class QuickIndexBar extends View {

    private String[] indexArr = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint paint;
    private int width;
    private int height;

    public QuickIndexBar(Context context) {
        super(context);
        initPaint();
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 该方法在onMeasure方法执行完之后执行, 那么可以在该方法中初始化自己和子view的宽高
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    private void initPaint() {
        //参数的意思是抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(getContext().getResources().getDimension(R.dimen.text_size));
        paint.setTextAlign(Paint.Align.CENTER); //设置文本的起点是文字边框底边的中心
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这里要注意, 文字的绘制起点是在文字的左下角开始绘制的
        for (int i = 0; i < indexArr.length; i++) {
            float x = width / 2;

            Rect bounds = new Rect();
            paint.getTextBounds(indexArr[i], 0, 1, bounds);
            int textHeight = bounds.height(); // 单个文字的高度

            /*Y的逻辑是文本都有一个格子, 而Y的点是在格子底部的中心点(Paint.Align.CENTER设置的关系),
                所以Y的高度应该是格子一半的高度 + 文字一半的高度 + 每隔格子的高度 * i*/
            float y = height / 27 / 2 + textHeight / 2 + i * height / 27;

            paint.setColor(lastIndex == i ? Color.GRAY : Color.WHITE);  //确认点击的索引值, 以便于当点击后改变当前字母的颜色

            String text = indexArr[i];
            canvas.drawText(text, x, y, paint);
        }
    }

    /**
     * 用来记录当前点击的索引值
     */
    private int lastIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   //不管是DOWN的事件还是MOVE的事件都应该是走这一段代码的
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //计算触摸点对应的字母: 根据触摸点的Y坐标 / 每一个字母所在格子的高度 = 字母对应的索引(int型的值)
                int index = (int) (y / (height / 27));
                if (lastIndex != index) {   //记录上一次打印的字母, 避免重复打印
                    if (index >= 0 && index < indexArr.length) {    //以防止数组越界异常
                        if (mListener != null) {
                            mListener.onTouchLetter(indexArr[index]);
                        }
                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                //重置, 因为当手指抬起的时候. 如果不重置则再次点击最后一个手指抬起时的字母的时候是不会打印的
                lastIndex = -1;
                if (mListener != null) {
                    mListener.onTouchLeave();
                }
                break;

            default:
                break;
        }

        invalidate();   //引起重绘, 重新调用onDraw方法

        return true;
    }

    /**
     * 触摸字母的监听器
     */
    public interface OnTouchLetterListener {
        void onTouchLetter(String letter);
        void onTouchLeave();
    }

    private OnTouchLetterListener mListener;

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        this.mListener = listener;
    }
}
