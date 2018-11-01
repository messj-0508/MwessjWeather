package com.example.mwessj.mlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.mwessj.mwessjweather.R;

import java.time.Clock;
import java.util.jar.Attributes;

public class LettersView extends View {
    // TAG
    private static final String TAG = "LettersView";

    // 导航栏的索引项
    private String[] strChars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    // 画笔
    private Paint mPaint;

    //选中字母的下标
    private int checkIndex;

    //接口回调
    private OnLettersListViewListener onLettersListViewListener;

    public LettersView(Context context) {
        super(context);
        initView();
    }

    public LettersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LettersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化
    private void initView() {
        //实例化画笔
        mPaint = new Paint();
        //设置style
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置被点击索引项的初始值为-1
        checkIndex = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 获取View的宽高用以排列索引项
         */
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int singleHeight = viewHeight / strChars.length;   //索引项的高度

        //绘制字母
        for (int i = 0; i < strChars.length; i++) {
            //设置选中索引项的颜色与字体大小
            if (i == checkIndex) {
                mPaint.setColor(Color.WHITE);
                mPaint.setTextSize(50);
            } else {
                mPaint.setColor(Color.BLACK);
                mPaint.setTextSize(40);
            }
            /**
             * x:  左侧空白位置的宽度
             * y:  单个字母的高度 + 最上面的字幕空白高度
             */
            float lettersX = (viewWidth - mPaint.measureText(strChars[i])) / 2;
            float lettersY = singleHeight * i + singleHeight;
            //绘制
            canvas.drawText(strChars[i], lettersX, lettersY, mPaint);
            mPaint.reset();
        }
    }

    /**
     * 事件分发
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //判断手势
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundResource(R.color.blue);
                //获取点击的Y坐标，以此来判断选中的字母
                float y = event.getY();
                Log.i(TAG, "y:" + y);
                //第一次被选中的下标
                int oldCheckIndex = checkIndex;
                /**
                 * 计算对应位置的索引项
                 * strChars[当前Y / View的高度 * 字母个数]
                 */
                int c = (int) (y / getHeight() * strChars.length);
                Log.i(TAG, "c:" + c);
                //判断移动
                if (oldCheckIndex != c) {
                    //越界判定
                    if (c >= 0 && c < strChars.length) {
                        //效果联动
                        if (onLettersListViewListener != null) {
                            onLettersListViewListener.onLettersListener(strChars[c]);
                        }
                    }
                    checkIndex = c;
                    //更新View
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //设置透明背景
                setBackgroundResource(android.R.color.transparent);
                //恢复不选中
                checkIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    public OnLettersListViewListener getOnLettersListViewListener() {
        return onLettersListViewListener;
    }

    public void setOnLettersListViewListener(OnLettersListViewListener onLettersListViewListener) {
        this.onLettersListViewListener = onLettersListViewListener;
    }

    /**
     * 接口回调/ListView联动
     */
    public interface OnLettersListViewListener {
        public void onLettersListener(String s);
    }

}
