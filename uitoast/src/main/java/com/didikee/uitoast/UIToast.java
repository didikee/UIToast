package com.didikee.uitoast;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


/**
 * Created by admin 
 * Created time 2017/2/24
 * Description: 
 */

public class UIToast {

    private UIToast() {
    }

    public static final int NONE = 0;

    public static void showBaseToast(@NonNull Context context,
                                     CharSequence text,
                                     boolean longTime,
                                     @ColorInt int toastTextColor,
                                     @ColorInt int toastBackgroundColor,
                                     View anchor,
                                     int gravity,
                                     int xOffset,
                                     int yOffset,
                                     @StyleRes int animationStyleId) {
        int duration = longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        View toastView = toast.getView();

        // toastTextColor
        if (toastTextColor != 0) {
            ((TextView) toastView.findViewById(android.R.id.message)).setTextColor(toastTextColor);
        }
        // toastBackgroundColor
        if (toastBackgroundColor != 0) {
            Drawable toastBackgroundDrawable = TintDrawableUtil.tintDrawable(toastView
                    .getBackground().mutate(), ColorStateList.valueOf(toastBackgroundColor));
            toastView.setBackgroundDrawable(toastBackgroundDrawable);
        }

        // location
        if (anchor == null) {
            int realGravity = gravity == 0 ? toast.getGravity() : (Gravity.START | Gravity.TOP);
            if (xOffset != 0 || yOffset != 0) {
                // do
                toast.setGravity(realGravity, xOffset, yOffset);
            }

        } else {
            // there is no use for gravity
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int locationX = location[0];
            int locationY = location[1];
            int width = anchor.getWidth();
            int height = anchor.getHeight();

            int realX = locationX + width / 2;
            int realY = locationY + height / 2;
            Log.e("test", "realX: " + realX + " realY: " + realY);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, realY + yOffset);
        }

        // animation
        if (animationStyleId != 0) {
            try {
                Object mTN;
                mTN = getField(toast, "mTN");
                if (mTN != null) {
                    Object mParams = getField(mTN, "mParams");
                    if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                        WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                        params.windowAnimations = animationStyleId;
                    }
                }
            } catch (Exception e) {
                Log.d("UIToast", "Toast windowAnimations setting failed");
            }
        }

        // show
        toast.setView(toastView);
        toast.show();
    }

    public static void showToast(@NonNull Context context,
                                 CharSequence text,
                                 boolean longTime) {
        showBaseToast(context, text, longTime, NONE, NONE, null, NONE, NONE, NONE, NONE);
    }

    public static void showToast(@NonNull Context context,
                                 CharSequence text) {
        showToast(context, text, false);
    }

    /**
     * 展示样式
     * @param context
     * @param text
     * @param longTime
     * @param toastTextColor
     * @param toastBackgroundColor
     * @param animationStyleId
     */
    public static void showStyleToast(@NonNull Context context,
                                      CharSequence text,
                                      boolean longTime,
                                      @ColorInt int toastTextColor,
                                      @ColorInt int toastBackgroundColor,
                                      @StyleRes int animationStyleId) {
        showBaseToast(context, text, longTime, toastTextColor, toastBackgroundColor, null, NONE,
                NONE, NONE, animationStyleId);

    }

    public static void showStyleToast(@NonNull Context context,
                                      CharSequence text,
                                      boolean longTime) {
        showStyleToast(context, text, longTime, getResColor(context, false), getResColor(context,
                true), getResStyle(context));
    }

    public static void showStyleToast(@NonNull Context context,
                                      CharSequence text) {
        showStyleToast(context, text, false);
    }

    /**
     * 展示位置
     * @param context
     * @param text
     * @param longTime
     * @param anchor
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showLocationToast(@NonNull Context context,
                                         CharSequence text,
                                         boolean longTime,
                                         View anchor,
                                         int gravity,
                                         int xOffset,
                                         int yOffset) {
        showBaseToast(context, text, longTime, NONE, NONE, anchor, gravity, xOffset, yOffset, NONE);
    }

    public static void showLocationToast(@NonNull Context context,
                                         CharSequence text,
                                         boolean longTime,
                                         View anchor) {
        showBaseToast(context, text, longTime, NONE, NONE, anchor, NONE, NONE, NONE, NONE);
    }

    public static void showLocationWithStyleToast(@NonNull Context context,
                                                  CharSequence text,
                                                  boolean longTime,
                                                  View anchor,
                                                  int gravity,
                                                  int xOffset,
                                                  int yOffset) {
        showBaseToast(context, text, longTime, getResColor(context, false), getResColor(context,
                true), anchor, gravity, xOffset, yOffset, getResStyle(context));
    }

    public static void showLocationWithStyleToast(@NonNull Context context,
                                                  CharSequence text,
                                                  boolean longTime,
                                                  View anchor) {
        showBaseToast(context, text, longTime, getResColor(context, false), getResColor(context,
                true), anchor, NONE, NONE, NONE, getResStyle(context));
    }

    /**
     * 反射字段
     * @param object 要反射的对象
     * @param fieldName 要反射的字段名称
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @return obj
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    private static int getResColor(@NonNull Context context, boolean isBg) {
        String name = isBg ? "colorToastBackgroundColor" : "colorToastTextColor";
        int color = NONE;
        try {
            String packageName = context.getPackageName();
            int colorId = context.getResources().getIdentifier(name, "color",
                    packageName);
            if (colorId != 0) {
                color = context.getResources().getColor(colorId);
            } else {
                Log.d("UIToast", "not found....");
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return color;
    }

    private static int getResStyle(@NonNull Context context) {
        String name = "AnimationToast";
        int styleId = NONE;
        try {
            String packageName = context.getPackageName();
            styleId = context.getResources().getIdentifier(name, "style",
                    packageName);

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return styleId;
    }
}
