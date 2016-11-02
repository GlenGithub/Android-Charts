/*
 * SlipGestureDetector.java
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

package cn.limc.androidcharts.event;

import android.graphics.PointF;
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
 * @version v1.0 2014/06/23 16:48:07
 */
public class LongPressSlipGestureDetector<T extends ISlipable> extends ZoomGestureDetector<IZoomable> {
    protected PointF startPointA;
    protected PointF startPointB;

    protected PointF lastPoint;
    protected boolean isLongPressed = false;
    protected boolean isMoved = false;
    protected long pressStartTime;

    private OnSlipGestureListener onSlipGestureListener;
    
    /**
     * <p>Constructor of TouchGestureDetector</p>
     * <p>TouchGestureDetector类对象的构造函数</p>
     * <p>TouchGestureDetectorのコンストラクター</p>
     */
    public LongPressSlipGestureDetector(T slipable) {
        super(slipable);
        if (slipable != null) {
            this.onSlipGestureListener = slipable.getOnSlipGestureListener();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @param event
     *
     * @return
     *
     * @see
     * cn.limc.androidcharts.event.IGestureDetector#onTouchEvent(android.view
     * .MotionEvent)
     */
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 设置拖拉模式
            case MotionEvent.ACTION_DOWN:
                lastPoint = null;
                if (event.getPointerCount() == 1) {
                    lastPoint = new PointF(event.getX(0), event.getY(0));
                    pressStartTime = System.currentTimeMillis();
                }
                break;
            case MotionEvent.ACTION_UP:

                if (isLongPressed) {
                    // call back to listener
                    if (instance.getOnTouchGestureListener() != null) {
                        instance.getOnTouchGestureListener().onLongPressUp(instance, event);
                    }
                }
                lastPoint = null;
                pressStartTime = 0;
                isLongPressed = false;
                isMoved = false;


                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (isLongPressed) {
                    // call back to listener
                    if (instance.getOnTouchGestureListener() != null) {
                        instance.getOnTouchGestureListener().onLongPressUp(instance, event);
                    }
                }

                lastPoint = null;
                pressStartTime = 0;
                isLongPressed = false;
                isMoved = false;
                break;
//			startPointA = null;
//			startPointB = null;
            case MotionEvent.ACTION_POINTER_DOWN:
//			olddistance = calcDistance(event);
//			if (olddistance > MIN_DISTANCE) {
//				touchMode = TOUCH_MODE_MULTI;
//				startPointA = new PointF(event.getX(0), event.getY(0));
//				startPointB = new PointF(event.getX(1), event.getY(1));
//			}
//			return true;
                //break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1 && lastPoint != null) {
                    PointF currentPoint = new PointF(event.getX(0), event.getY(0));
                    if (!isLongPressed && !isMoved) {
                        float offsetX = Math.abs(currentPoint.x - lastPoint.x);
                        float offsetY = Math.abs(currentPoint.y - lastPoint.y);
                        if (offsetX <= 80 && offsetY <= 80) {
                            if (pressStartTime - System.currentTimeMillis() < -500) {
                                isLongPressed = true;
                                if (instance.getOnTouchGestureListener() != null) {
                                    instance.getOnTouchGestureListener().onLongPressDown(instance, event);
                                }
                            }
                        } else {
                            isMoved = true;
                        }
                    }
                    if (isLongPressed) {
                        touchPoint = new PointF(event.getX(), event.getY());
                        // call back to listener
                        if (instance.getOnTouchGestureListener() != null) {
                            instance.getOnTouchGestureListener().onLongPressMoved(instance, event);
                        }
                    } else {
                        if (currentPoint.x > lastPoint.x + 80) {
                            if (((T) instance).getOnSlipGestureListener() != null) {
                                ((T) instance).getOnSlipGestureListener().onMoveLeft((ISlipable) instance, event);
                            }
                        } else if (currentPoint.x < lastPoint.x - 80) {
                            if (((T) instance).getOnSlipGestureListener() != null) {
                                ((T) instance).getOnSlipGestureListener().onMoveRight((ISlipable) instance, event);
                            }
                        }
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

}
