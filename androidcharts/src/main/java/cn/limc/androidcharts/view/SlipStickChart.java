/*
 * SlipStickChart.java
 * Android-Charts
 *
 * Created by limc on 2014.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.limc.androidcharts.view;

import cn.limc.androidcharts.event.IGestureDetector;
import cn.limc.androidcharts.event.ISlipable;
import cn.limc.androidcharts.event.OnSlipGestureListener;
import cn.limc.androidcharts.event.LongPressSlipGestureDetector;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>
 * en
 * </p>
 * <p>
 * jp
 * </p>
 * <p>
 * cn
 * </p>
 *
 * @author limc
 * @version v1.0 2014-1-20 下午2:03:08
 */
public class SlipStickChart extends StickChart implements ISlipable {

//	public static final int DEFAULT_ZOOM_BASE_LINE = ZOOM_BASE_LINE_CENTER;
//
//	protected int zoomBaseLine = DEFAULT_ZOOM_BASE_LINE;

    protected OnSlipGestureListener onSlipGestureListener = new OnSlipGestureListener();
    protected IGestureDetector slipGestureDetector = new LongPressSlipGestureDetector<ISlipable>(this);

    protected boolean detectSlipEvent = true;

    /**
     * <p>
     * Constructor of SlipStickChart
     * </p>
     * <p>
     * SlipStickChart类对象的构造函数
     * </p>
     * <p>
     * SlipStickChartのコンストラクター
     * </p>
     *
     * @param context
     */
    public SlipStickChart(Context context) {
        super(context);
    }

    /**
     * <p>
     * Constructor of SlipStickChart
     * </p>
     * <p>
     * SlipStickChart类对象的构造函数
     * </p>
     * <p>
     * SlipStickChartのコンストラクター
     * </p>
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SlipStickChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>
     * Constructor of SlipStickChart
     * </p>
     * <p>
     * SlipStickChart类对象的构造函数
     * </p>
     * <p>
     * SlipStickChartのコンストラクター
     * </p>
     *
     * @param context
     * @param attrs
     */
    public SlipStickChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * <p>Called when is going to draw this chart<p> <p>チャートを書く前、メソッドを呼ぶ<p>
     * <p>绘制图表时调用<p>
     *
     * @param canvas
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    /*
     * (non-Javadoc)
     *
     * @param event
     * @return
     * @see cn.limc.androidcharts.view.StickChart#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // valid
        if (!isValidTouchPoint(event.getX(), event.getY())) {
            return false;
        }
        if (null == stickData || stickData.size() == 0) {
            return false;
        }

        if (detectSlipEvent) {
            return slipGestureDetector.onTouchEvent(event);
        } else {
            return true;
        }
    }

    /* (non-Javadoc)
     *
     * @see cn.limc.androidcharts.common.ISlipable#moveRight()
     */
    public void moveRight() {
        if (this.forward(SLIP_STEP)) {
            this.postInvalidate();
        }
//
        //Listener
        if (onDisplayCursorListener != null) {
            onDisplayCursorListener.onCursorChanged(this, getDisplayFrom(), getDisplayNumber());
        }
    }

    /* (non-Javadoc)
     *
     * @see cn.limc.androidcharts.common.ISlipable#moveLeft()
     */
    public void moveLeft() {
        if (this.backward(SLIP_STEP)) {
            this.postInvalidate();
        }
//
//
        //Listener
        if (onDisplayCursorListener != null) {
            onDisplayCursorListener.onCursorChanged(this, getDisplayFrom(), getDisplayNumber());
        }
    }

    //	/**
//	 * @return the zoomBaseLine
//	 */
//	public int getZoomBaseLine() {
//		return zoomBaseLine;
//	}
//
//	/**
//	 * @param zoomBaseLine
//	 *            the zoomBaseLine to set
//	 */
//	public void setZoomBaseLine(int zoomBaseLine) {
//		this.zoomBaseLine = zoomBaseLine;
//	}
    public boolean isDetectSlipEvent() {
        return detectSlipEvent;
    }

    public void setDetectSlipEvent(boolean detectSlipEvent) {
        this.detectSlipEvent = detectSlipEvent;
    }


    /* (non-Javadoc)
     *
     * @param listener
     * @see cn.limc.androidcharts.event.ISlipable#setOnSlipGestureListener(cn.limc.androidcharts.event.OnSlipGestureListener)
     */
    public void setOnSlipGestureListener(OnSlipGestureListener listener) {
        this.onSlipGestureListener = listener;

    }

    /* (non-Javadoc)
     *
     * @return
     * @see cn.limc.androidcharts.event.ISlipable#getOnSlipGestureListener()
     */
    public OnSlipGestureListener getOnSlipGestureListener() {
        return onSlipGestureListener;
    }
}
