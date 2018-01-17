package com.huxley.wiisample.page.timeRhythm;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.huxley.wiisample.R;
import com.huxley.wiitools.utils.ResUtils;
import com.huxley.wiitools.utils.StringUtils;
import com.huxley.wiitools.utils.logger.Logger;
import com.huxley.wiitools.view.WiiToast;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.TimerTask;

//////////////////////////////////////////////////////////
//
//      我们的征途是星辰大海
//
//		...．．∵ ∴★．∴∵∴ ╭ ╯╭ ╯╭ ╯╭ ╯∴∵∴∵∴ 
//		．☆．∵∴∵．∴∵∴▍▍ ▍▍ ▍▍ ▍▍☆ ★∵∴ 
//		▍．∴∵∴∵．∴▅███████████☆ ★∵ 
//		◥█▅▅▅▅███▅█▅█▅█▅█▅█▅███◤ 
//		． ◥███████████████████◤
//		.．.．◥████████████████■◤
//
//      Created by huxley on 2017/11/10.
//
//////////////////////////////////////////////////////////
public class TimeRhythmView extends View {

    private int mCenterColor, mCenterTouchColor, mEnableColor;
    private float mOutRadius, mInRadius;
    private int mWidth, mHeight;
    private static final float FILL_ANGLE = 360f;

    private int   mCycleTimes = 1;
    private Paint paint       = new Paint();
    private int[] mRingColor  = new int[3], mRingTouchColor = new int[3];
    private ArrayList<Integer>  mTimeList      = new ArrayList<>();
    private ArrayList<Double[]> angleRangeList = new ArrayList<>();


    public enum State {
        Start, Pause, Stop
    }


    public State timeState = State.Stop;


    public TimeRhythmView(Context context) {
        this(context, null);
    }


    public TimeRhythmView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TimeRhythmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTimeList.add(20);
        mTimeList.add(10);
        mTimeList.add(5);
        obtainStyledAttributes(context, attrs);
        initPath();
    }


    private void initPath() {

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }


    private void obtainStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.time_rhythm_view);

        mCenterColor = ta.getColor(R.styleable.time_rhythm_view_color_center,
            ResUtils.getColor(R.color.color_brown_500));

        mCenterTouchColor = ta.getColor(R.styleable.time_rhythm_view_color_center,
            ResUtils.getColor(R.color.color_brown_400));

        mRingColor[0] = ta.getColor(R.styleable.time_rhythm_view_color_high,
            ResUtils.getColor(R.color.color_red_500));
        mRingColor[1] = ta.getColor(R.styleable.time_rhythm_view_color_medium,
            ResUtils.getColor(R.color.color_orange_500));
        mRingColor[2] = ta.getColor(R.styleable.time_rhythm_view_color_low,
            ResUtils.getColor(R.color.color_green_500));

        mRingTouchColor[0] = ta.getColor(R.styleable.time_rhythm_view_color_high,
            ResUtils.getColor(R.color.color_red_200));
        mRingTouchColor[1] = ta.getColor(R.styleable.time_rhythm_view_color_medium,
            ResUtils.getColor(R.color.color_orange_200));
        mRingTouchColor[2] = ta.getColor(R.styleable.time_rhythm_view_color_low,
            ResUtils.getColor(R.color.color_green_200));

        mEnableColor = ta.getColor(R.styleable.time_rhythm_view_color_empty,
            ResUtils.getColor(R.color.color_grey_500));

        ta.recycle();
    }


    RectF mOutRectF = new RectF();


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w - getPaddingLeft() - getPaddingRight();
        mHeight = h - getPaddingTop() - getPaddingBottom();

        mOutRadius = Math.min(mWidth, mHeight) / 2.0f;
        mInRadius = (mOutRadius * 3) / 5;

        mOutRectF.left = -mOutRadius;
        mOutRectF.top = -mOutRadius;
        mOutRectF.right = mOutRadius;
        mOutRectF.bottom = mOutRadius;
        mOutRectF.offset(mOutRadius, mOutRadius);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        angleRangeList.clear();

        if (mTimeList.size() == 0) {
            paint.setColor(mEnableColor);
            canvas.drawCircle(mOutRadius, mOutRadius, mOutRadius, paint);
        } else {
            float startAngle = 0f;
            for (int i = 0; i < mTimeList.size(); i++) {
                int time = mTimeList.get(i);
                if (time > 0) {
                    float sweepAngle = (FILL_ANGLE * time) / (getTotalTime());
                    paint.setColor(currentPosition == (i + 1) ? mRingTouchColor[i] : mRingColor[i]);
                    canvas.drawArc(mOutRectF, startAngle, sweepAngle, true, paint);
                    angleRangeList.add(new Double[] { Double.valueOf(startAngle),
                        Double.valueOf(startAngle + sweepAngle) });
                    float textAngle = startAngle + sweepAngle / 2;
                    float x = (float) ((mOutRadius + mInRadius) / 2 *
                        Math.cos(textAngle * Math.PI / 180));    //计算文字位置坐标
                    float y = (float) ((mOutRadius + mInRadius) / 2 *
                        Math.sin(textAngle * Math.PI / 180));
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(20);
                    canvas.drawText(mTimeList.get(i) + "S", x + mOutRadius, y + mOutRadius, paint);
                    startAngle += sweepAngle;
                }
            }
        }

        if (runTime > 0) {
            paint.setColor(mEnableColor);
            float runAngle = (runTime % getTotalTime()) * FILL_ANGLE / getTotalTime();
            for (int i = 0; i < angleRangeList.size(); i++) {
                if (angleRangeList.get(i)[1] == runAngle) {
                    Logger.i("i = " + i);
                }
            }
            canvas.drawArc(mOutRectF, 0, runAngle, true, paint);
        }

        paint.setColor(currentPosition == 0 ? mCenterTouchColor : mCenterColor);
        canvas.drawCircle(mOutRadius, mOutRadius, mInRadius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(300);
        canvas.drawText("+", mOutRadius - 85, mOutRadius + 85, paint);

        canvas.restore();
    }


    public int getTotalTime() {
        int timeTotal = 0;
        for (int i = 0; i < mTimeList.size(); i++) {
            timeTotal += mTimeList.get(i);
        }
        return timeTotal;
    }


    int startX, startY, startPosition = -1, currentPosition = -1;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (timeState != State.Stop) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPosition = -1;
                currentPosition = -1;
                startX = (int) event.getX();
                startY = (int) event.getY();
                Point centerPoint = new Point((int) mOutRadius, (int) mOutRadius);
                Point startPoint = new Point((int) mOutRadius * 2, (int) mOutRadius);
                Point endPoint = new Point(startX, startY);
                if (distance(endPoint, centerPoint) <= mInRadius) {
                    startPosition = 0;
                    currentPosition = startPosition;
                    invalidate();
                    return true;
                } else if (distance(endPoint, centerPoint) <= mOutRadius) {
                    double angle = angle(centerPoint, startPoint, endPoint);
                    for (int i = 0; i < angleRangeList.size(); i++) {
                        Double[] angleRange = angleRangeList.get(i);
                        if (angle >= angleRange[0] && angle < angleRange[1]) {
                            startPosition = i + 1;
                            currentPosition = startPosition;
                            invalidate();
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();
                Point moveCenterPoint = new Point((int) mOutRadius, (int) mOutRadius);
                Point moveStartPoint = new Point((int) mOutRadius * 2, (int) mOutRadius);
                Point moveEndPoint = new Point(moveX, moveY);
                if (distance(moveEndPoint, moveCenterPoint) <= mInRadius) {
                    currentPosition = 0;
                    invalidate();
                    return true;
                } else if (distance(moveEndPoint, moveCenterPoint) <= mOutRadius) {
                    double angle = angle(moveCenterPoint, moveStartPoint, moveEndPoint);
                    for (int i = 0; i < angleRangeList.size(); i++) {
                        Double[] angleRange = angleRangeList.get(i);
                        if (angle >= angleRange[0] && angle < angleRange[1]) {
                            if (currentPosition != (i + 1)) {
                                currentPosition = i + 1;
                                invalidate();
                            }
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                Point upCenterPoint = new Point((int) mOutRadius, (int) mOutRadius);
                Point upStartPoint = new Point((int) mOutRadius * 2, (int) mOutRadius);
                Point upEndPoint = new Point(upX, upY);
                currentPosition = -1;
                startX = 0;
                startY = 0;
                if (distance(upEndPoint, upCenterPoint) <= mInRadius) {
                    if (startPosition == 0) {
                        addTime();
                    }
                    startPosition = -1;
                    invalidate();
                    return true;
                } else if (distance(upEndPoint, upCenterPoint) <= mOutRadius) {
                    double angle = angle(upCenterPoint, upStartPoint, upEndPoint);
                    for (int i = 0; i < angleRangeList.size(); i++) {
                        Double[] angleRange = angleRangeList.get(i);
                        if (angle >= angleRange[0] && angle < angleRange[1]) {
                            if (startPosition == i + 1) {
                                editTime(i);
                            }
                            startPosition = -1;
                            invalidate();
                            return true;
                        }
                    }
                }
                startPosition = -1;
                break;
        }
        return super.onTouchEvent(event);
    }


    public static double angle(Point o, Point s, Point e) {
        double cosfi, fi, norm;
        double dsx = s.x - o.x;
        double dsy = s.y - o.y;
        double dex = e.x - o.x;
        double dey = e.y - o.y;
        cosfi = dsx * dex + dsy * dey;
        norm = (dsx * dsx + dsy * dsy) * (dex * dex + dey * dey);
        cosfi /= Math.sqrt(norm);
        if (cosfi >= 1.0) return 0;
        if (cosfi <= -1.0) return Math.PI;
        fi = Math.acos(cosfi);
        if (180 * fi / Math.PI < 180) {
            if (e.y >= o.y) {
                return 180 * fi / Math.PI;
            } else {
                return 360 - 180 * fi / Math.PI;
            }
        } else {
            return 360 - 180 * fi / Math.PI;
        }
    }


    public static double distance(Point s, Point e) {
        return Math.sqrt(Math.abs((s.x - e.x) * (s.x - e.x) + (s.y - e.y) * (s.y - e.y)));
    }


    private void editTime(int position) {
        showDialog(position);
    }


    private void addTime() {
        if (mTimeList.size() >= mRingTouchColor.length) {
            WiiToast.show(MessageFormat.format("最多只能添加 {0} 个时间段", mTimeList.size()));
            return;
        }
        showDialog(-1);
    }


    private void showDialog(int position) {
        View inflate = LayoutInflater.from(getContext())
            .inflate(R.layout.time_rhythm_dialog_add_time, null);
        EditText etTime = (EditText) inflate.findViewById(R.id.et_time);
        if (position >= 0) {
            etTime.setText(String.valueOf(mTimeList.get(position)));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
            .setTitle(position < 0 ?
                      MessageFormat.format("添加第 {0} 个时间段", mTimeList.size() + 1) :
                      MessageFormat.format("编辑第 {0} 个时间段", position + 1))
            .setView(inflate)
            .setNegativeButton("取消", null)
            .setPositiveButton(position < 0 ? "添加" : "编辑", (dialog, which) -> {
                String timeString = etTime.getText().toString();
                if (StringUtils.isEmpty(timeString)) {
                    return;
                }
                int time = Integer.valueOf(timeString);
                if (position >= 0) { // 编辑
                    mTimeList.remove(position);
                    mTimeList.add(position, time);
                } else { // 添加
                    mTimeList.add(time);
                }
                invalidate();
            });
        if (position >= 0) {
            builder.setNeutralButton("删除", (dialog, which) -> {
                mTimeList.remove(position);
                invalidate();
            });
        }
        builder.create().show();
    }


    public void setData(ArrayList<Integer> timeList) {
        mTimeList.clear();
        mTimeList.addAll(timeList);
        invalidate();
    }



    public void showCycleTimesDialog() {
        View inflate = LayoutInflater.from(getContext())
            .inflate(R.layout.time_rhythm_dialog_add_time, null);
        EditText etTime = (EditText) inflate.findViewById(R.id.et_time);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
            .setTitle("循环次数")
            .setView(inflate)
            .setNegativeButton("取消", null)
            .setPositiveButton("修改", (dialog, which) -> {
                String timeString = etTime.getText().toString();
                if (StringUtils.isEmpty(timeString)) {
                    return;
                }
                int time = Integer.valueOf(timeString);
                if (time > 0) { // 编辑
                    setCycleTimes(time);
                }
                invalidate();
            });
        builder.create().show();
    }

    public int getCycleTimes() {
        return mCycleTimes;
    }


    public void setCycleTimes(int CycleTimes) {
        this.mCycleTimes = CycleTimes;
    }


    private int totalTime;
    private int runTime;
    Runnable runnable;


    public void start() {
        Logger.i("-1");
        if (mTimeList.size() <= 0) {
            WiiToast.warn("没有可以开始的时间段");
            return;
        }
        Logger.i("0");
        if (timeState == State.Start) { // 暂停
            Logger.i("timeState = Start");
            return;
        }
        Logger.i("1");
        if (timeState == State.Stop || (timeState == State.Pause && totalTime == 0)) {
            Logger.i("2");
            totalTime = getTotalTime() * getCycleTimes();
            runTime = 0;
        }
        Logger.i("3");
        runnable = () -> {
            if (totalTime > runTime) {
                runTime++;
                invalidate();
                Logger.i("runTime = " + runTime);
                getHandler().postDelayed(runnable, 1000);
            }
        };
        getHandler().postDelayed(runnable, 1000);
    }


    public void pause() {

    }
}