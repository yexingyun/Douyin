package com.yxe.ktarmor.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxe.ktarmor.R;

/**
 * 标题栏
 */
public class CommonTitle extends FrameLayout implements View.OnClickListener, Runnable {

    private OnTitleBarListener mListener;

    public CommonTitle(Context context) {
        this(context, null, 0);
    }

    public CommonTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initStyle(attrs);
    }

    private LinearLayout mMainLayout;
    private TextView mLeftView;
    private TextView mTitleView;
    private TextView mRightView;

    private View mLineView;

    private void initView(Context context) {
        Builder builder = new Builder(context);
        mMainLayout = builder.getMainLayout();
        mLineView = builder.getLineView();
        mTitleView = builder.getTitleView();
        mLeftView = builder.getLeftView();
        mRightView = builder.getRightView();

        mMainLayout.addView(mLeftView);
        mMainLayout.addView(mTitleView);
        mMainLayout.addView(mRightView);

        addView(mMainLayout, 0);
        addView(mLineView, 1);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //设置监听
        mTitleView.setOnClickListener(this);
        mLeftView.setOnClickListener(this);
        mRightView.setOnClickListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        //移除监听
        mTitleView.setOnClickListener(null);
        mLeftView.setOnClickListener(null);
        mRightView.setOnClickListener(null);
        super.onDetachedFromWindow();
    }

    public void setOnTitleBarListener(OnTitleBarListener l) {
        mListener = l;
    }

    public interface OnTitleBarListener {

        void onLeftClick(View v);

        void onTitleClick(View v);

        void onRightClick(View v);
    }

// View.OnClickListener

    @Override
    public void onClick(View v) {
        if (mListener == null) return;

        if (v == mLeftView) {
            mListener.onLeftClick(v);
        }else if (v == mTitleView) {
            mListener.onTitleClick(v);
        }else if (v == mRightView) {
            mListener.onRightClick(v);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置TitleBar默认的高度
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST || MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Builder.getActionBarHeight(getContext()), MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    private void initStyle(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);

        //标题设置

        if (ta.hasValue(R.styleable.TitleBar_title_left)) {
            setLeftTitle(ta.getString(R.styleable.TitleBar_title_left));
        }

        if (ta.hasValue(R.styleable.TitleBar_title)) {
            setTitle(ta.getString(R.styleable.TitleBar_title));
        } else {
            //如果当前上下文对象是Activity，就获取Activity的标题
            if (getContext() instanceof Activity) {
                //获取清单文件中的label属性值
                CharSequence label = ((Activity) getContext()).getTitle();
                //如果Activity没有设置label属性，则默认会返回APP名称，需要过滤掉
                if (label != null && !label.toString().equals("")) {

                    try {
                        PackageManager packageManager = getContext().getPackageManager();
                        PackageInfo packageInfo = packageManager.getPackageInfo(
                                getContext().getPackageName(), 0);

                        if (!label.toString().equals(packageInfo.applicationInfo.loadLabel(packageManager).toString())) {
                            setTitle(label);
                        }
                    } catch (PackageManager.NameNotFoundException ignored) {}
                }
            }
        }

        if (ta.hasValue(R.styleable.TitleBar_title_right)) {
            setRightTitle(ta.getString(R.styleable.TitleBar_title_right));
        }

        // 图标设置

        if (ta.hasValue(R.styleable.TitleBar_icon_left)) {
            setLeftIcon(getContext().getResources().getDrawable(ta.getResourceId(R.styleable.TitleBar_icon_left, 0)));
        } else {
            // 显示返回图标
            if (ta.getBoolean(R.styleable.TitleBar_icon_back, true)) {
                setLeftIcon(getContext().getResources().getDrawable(R.mipmap.back));
            }
        }

        if (ta.hasValue(R.styleable.TitleBar_icon_right)) {
            setRightIcon(getContext().getResources().getDrawable(ta.getResourceId(R.styleable.TitleBar_icon_right, 0)));
        }

        //文字颜色设置
        mLeftView.setTextColor(ta.getColor(R.styleable.TitleBar_color_left, 0xFF666666));
        mTitleView.setTextColor(ta.getColor(R.styleable.TitleBar_color_title, 0xFF222222));
        mRightView.setTextColor(ta.getColor(R.styleable.TitleBar_color_right, 0xFFA4A4A4));

        //文字大小设置
        mLeftView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.TitleBar_size_left, Builder.sp2px(getContext(), 14)));
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.TitleBar_size_title, Builder.sp2px(getContext(), 16)));
        mRightView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.TitleBar_size_right, Builder.sp2px(getContext(), 14)));

        //背景设置
        mLeftView.setBackgroundResource(ta.getResourceId(R.styleable.TitleBar_background_left, R.drawable.selector_selectable_transparent));
        mRightView.setBackgroundResource(ta.getResourceId(R.styleable.TitleBar_background_right, R.drawable.selector_selectable_transparent));

        //分割线设置
        mLineView.setVisibility(ta.getBoolean(R.styleable.TitleBar_line, true) ? View.VISIBLE : View.GONE);
        mLineView.setBackgroundColor(ta.getColor(R.styleable.TitleBar_color_line, 0xFFECECEC));

        //回收TypedArray
        ta.recycle();

        //设置默认背景
        if (getBackground() == null) {
            setBackgroundColor(0xFFFFFFFF);
        }
    }

    public void setTitle(CharSequence text) {
        mTitleView.setText(text);
        postDelayed(this, 100);
    }

    public void setLeftTitle(CharSequence text) {
        mLeftView.setText(text);
        postDelayed(this, 100);
    }

    public void setRightTitle(CharSequence text) {
        mRightView.setText(text);
        postDelayed(this, 100);
    }

    public void setLeftIcon(Drawable drawable) {
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        postDelayed(this, 100);
    }

    public void setRightIcon(Drawable drawable) {
        mRightView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        postDelayed(this, 100);
    }

// Runnable

    @Override
    public void run() {
        //更新中间标题的内边距，避免向左或者向右偏移
        int leftSize = mLeftView.getWidth();
        int rightSize = mRightView.getWidth();
        if (leftSize != rightSize) {
            if (leftSize > rightSize) {
                mTitleView.setPadding(0, 0, leftSize - rightSize, 0);
            } else {
                mTitleView.setPadding(rightSize - leftSize, 0, 0, 0);
            }
        }

        //更新View状态
        if (!"".equals(mLeftView.getText().toString()) || mLeftView.getCompoundDrawables()[0] != null) {
            mLeftView.setEnabled(true);
        }
        if (!"".equals(mTitleView.getText().toString())) {
            mTitleView.setEnabled(true);
        }
        if (!"".equals(mRightView.getText().toString()) || mRightView.getCompoundDrawables()[2] != null) {
            mRightView.setEnabled(true);
        }
    }
}
class Builder {

    private LinearLayout mMainLayout;
    private TextView mLeftView;
    private TextView mTitleView;
    private TextView mRightView;

    private View mLineView;

    Builder(Context context) {
        mMainLayout = new LinearLayout(context);
        mMainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mMainLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mLeftView = new TextView(context);
        mLeftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLeftView.setPadding(Builder.dp2px(context, 15), 0, Builder.dp2px(context, 15), 0);
        mLeftView.setCompoundDrawablePadding(Builder.dp2px(context, 5));
        mLeftView.setGravity(Gravity.CENTER_VERTICAL);
        mLeftView.setSingleLine();
        mLeftView.setEllipsize(TextUtils.TruncateAt.END);
        mLeftView.setEnabled(false);

        mTitleView = new TextView(context);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.weight = 1;
        titleParams.leftMargin = Builder.dp2px(context, 10);
        titleParams.rightMargin = Builder.dp2px(context, 10);
        mTitleView.setLayoutParams(titleParams);
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setSingleLine();
        mTitleView.setEllipsize(TextUtils.TruncateAt.END);
        mTitleView.setEnabled(false);

        mRightView = new TextView(context);
        mRightView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRightView.setPadding(Builder.dp2px(context, 15), 0, Builder.dp2px(context, 15), 0);
        mRightView.setCompoundDrawablePadding(Builder.dp2px(context, 5));
        mRightView.setGravity(Gravity.CENTER_VERTICAL);
        mRightView.setSingleLine();
        mRightView.setEllipsize(TextUtils.TruncateAt.END);
        mRightView.setEnabled(false);

        mLineView = new View(context);
        FrameLayout.LayoutParams lineParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        lineParams.gravity = Gravity.BOTTOM;
        mLineView.setLayoutParams(lineParams);
    }

    LinearLayout getMainLayout() {
        return mMainLayout;
    }

    View getLineView() {
        return mLineView;
    }

    TextView getLeftView() {
        return mLeftView;
    }

    TextView getTitleView() {
        return mTitleView;
    }

    TextView getRightView() {
        return mRightView;
    }

    /**
     * 获取ActionBar的高度
     */
    static int getActionBarHeight(Context context) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) ta.getDimension(0, 0);
        ta.recycle();
        return actionBarSize;
    }

    /**
     * dp转px
     */
    static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     */
    static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}