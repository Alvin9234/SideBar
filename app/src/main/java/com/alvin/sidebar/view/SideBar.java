package com.alvin.sidebar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.alvin.sidebar.R;

import java.util.Arrays;
import java.util.List;

/**
 * ListView右侧的字母索引View
 */
public class SideBar extends View {

    public static String[] INDEX_STRING = {"#","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private List<String> letterList;
    private int choose = -1;
    private Paint paint = new Paint();
    private Paint popPaint = new Paint();
    private Paint popTextPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Bitmap popBitmap;
    private int rightMargin;//字母列表距离屏幕右边的距离
    private int popRightMargin;//气泡与字母列表之间的间隔
    private int circleRadius;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        letterList = Arrays.asList(INDEX_STRING);
        Drawable popDrawable = getResources().getDrawable(R.drawable.icon_pop_sort_tip);
        popBitmap = drawableToBitmap(popDrawable);

        rightMargin = 26;
        popRightMargin = 24;
        circleRadius = 16;

        paint.setColor(Color.parseColor("#898C95"));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);

        popPaint.setAntiAlias(true);

        popTextPaint.setAntiAlias(true);
        popTextPaint.setColor(Color.WHITE);
        popTextPaint.setTextSize(38);
        popTextPaint.setTextAlign(Paint.Align.CENTER);

        circlePaint.setColor(Color.parseColor("#29C5DC"));
        circlePaint.setAntiAlias(true);
    }

    public Bitmap drawableToBitmap (Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();// 获取对应高度
        int width = getWidth();// 获取对应宽度
        int singleHeight = height / letterList.size();// 获取每一个字母的高度
        float measureSize = paint.measureText("A");//固定大小，让文字绘制在一条中心线上
        for (int i = 0; i < letterList.size(); i++) {
            String text = letterList.get(i);
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width - measureSize-rightMargin;
            float yPos = singleHeight * i + singleHeight / 2;
            // 选中的状态
            if (i == choose && i!=0) {
                float left = xPos -popRightMargin - popBitmap.getWidth();
                float top = yPos-popBitmap.getHeight()/2f;

                //绘制气泡
                canvas.drawBitmap(popBitmap,left,top, popPaint);
                float popTextCenter = popTextPaint.measureText(text)/2f;//气泡文字中心
                //绘制气泡中文字
                canvas.drawText(text,left+popBitmap.getWidth()/2f-popTextCenter,yPos+popTextCenter,popTextPaint);

                //绘制选中的字母样式
                paint.setColor(Color.WHITE);
                canvas.drawCircle(xPos, yPos-measureSize/2,circleRadius,circlePaint);
                canvas.drawText(text, xPos, yPos, paint);
            }else{
                paint.setColor(Color.parseColor("#898C95"));
                canvas.drawText(text, xPos, yPos, paint);
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        // 触摸范围等于控件宽度-字符串宽度的两倍.这样不会阻碍正常滑动ListView列表事件
        float xPos = getWidth() -  rightMargin *2;
        final float x = event.getX();// 点击x坐标
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letterList.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        if (action == MotionEvent.ACTION_UP) {
            choose = -1;
            invalidate();
        } else {
            if (oldChoose != c) {
                if (x >= xPos && c >= 0 && c < letterList.size()) {
                    if (listener != null) {
                        listener.onTouchingLetterChanged(letterList.get(c), c);
                    }
                    choose = c;
                    invalidate();
                    return true;
                }
            }
        }
        return false;
    }

    public void setIndexText(List<String> indexStrings) {
        if(indexStrings!=null && indexStrings.size()>0){
            this.letterList = indexStrings;
            invalidate();
        }
    }

    /**
     * 触摸回调事件
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 自定义触摸回调事件接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s, int index);
    }
}
