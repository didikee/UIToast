package com.didikee.uitoast;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
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

public final class UIToastBuild {
    public static final int NONE = 0;

    private UIToastBuild() {
    }

    private UIToastBuild(Builder builder) {
        @ColorInt int toastTextColor = builder.toastTextColor;
        @ColorInt int toastBackgroundColor = builder.toastBackgroundColor;
        @StyleRes int animationStyleId = builder.animationStyleId;
        View anchor = builder.anchor;
        int gravity = builder.gravity;
        int xOffset = builder.xOffset;
        int yOffset = builder.yOffset;
        boolean longTime = builder.longTime;
        CharSequence text = builder.text;
        final Context context = builder.context;

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
            int realGravity = gravity == 0 ? toast.getGravity() : (Gravity.START |
                    Gravity.TOP);
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

    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    public static class Builder {
        private
        @ColorInt
        int toastTextColor;
        private
        @ColorInt
        int toastBackgroundColor;
        private
        @StyleRes
        int animationStyleId;
        private View anchor;
        private int gravity;
        private int xOffset;
        private int yOffset;
        private boolean longTime;
        private CharSequence text;

        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTextColor(@ColorInt int toastTextColor) {
            this.toastTextColor = toastTextColor;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int toastBackgroundColor) {
            this.toastBackgroundColor = toastBackgroundColor;
            return this;
        }

        public Builder setAnimations(@StyleRes int animationStyleId) {
            this.animationStyleId = animationStyleId;
            return this;
        }

        public Builder anchor(View anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder offset(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        public Builder duration(boolean longTime) {
            this.longTime = longTime;
            return this;
        }

        public Builder setText(CharSequence text) {
            this.text = text;
            return this;
        }

        public UIToastBuild show() {
            return new UIToastBuild(this);
        }
    }

}
