package com.bob.android.clgl.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bob.android.clgl.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EasyDialog extends Dialog {

    public static final int THEME_HOLO_LIGHT = R.style.Widget_Theme_Dialog_Light;

    public static final int LIST_STYLE_LISTVIEW = 0x01;
    public static final int LIST_STYLE_GRIDVIEW = 0x02;
    public static final int LIST_STYLE_SINGLE_CHOICE = 0x03;
    public static final int LIST_STYLE_MULTI_CHOICE = 0x04;

    /**
     * @Fields mBuilder : 对话框内容构建器
     */
    private Builder mBuilder;

    /**
     * @Fields mHideButtonDividers : 是否显示button之间分割线
     */
    private boolean mHideButtonDividers;

    /**
     * @Fields mAdapter : 对话框内容列表适配器
     */
    private EasyDialogListAdapter mAdapter;

    // Title View
    /**
     * @Fields mTitleLayout : 对话框头部布局
     */
    private RelativeLayout mTitleLayout;
    /**
     * @Fields mTitleText : 对话框标题
     */
    private TextView mTitleText;
    /**
     * @Fields mSubtitleText : 对话框副标题
     */
    private TextView mSubtitleText;
    /**
     * @Fields mTitleIcon : 对话框标题Icon
     */
    private ImageView mTitleIcon;
    /**
     * @Fields mTitleProgress : 对话框上面的进度条
     */
    private ProgressBar mTitleProgress;
    /**
     * @Fields mTitleDivider : 对话框标题间分割线
     */
    private View mTitleDivider;
    /**
     * @Fields mTitleCheckBox : 对话框标题选择框
     */
    private CheckBox mTitleCheckBox;

    // Main Content Views
    /**
     * @Fields mDialogLayout : 对话框内容
     */
    private RelativeLayout mDialogLayout;
    /**
     * @Fields mMessageLayout : 对话框内容滚动容器
     */
    private ScrollView mMessageLayout;
    /**
     * @Fields mMessageText : 对话框消息内容
     */
    private TextView mMessageText;
    /**
     * @Fields mIndeterminateProgressLayout : 不确定进度条容器
     */
    private LinearLayout mIndeterminateProgressLayout;
    /**
     * @Fields mIndeterminateProgress : 不确定进度条
     */
    private ProgressBar mIndeterminateProgress;
    /**
     * @Fields mIndeterminateProgressText : 不确定进度标题
     */
    private TextView mIndeterminateProgressText;
    /**
     * @Fields mHorizontalProgressLayout : 进度条容器
     */
    private RelativeLayout mHorizontalProgressLayout;
    /**
     * @Fields mHorizontalProgress : 进度条
     */
    private ProgressBar mHorizontalProgress;
    private TextView mHorizontalProgressPercentText;
    /**
     * @Fields mHorizontalProgressMessageText : 进度条标题
     */
    private TextView mHorizontalProgressMessageText;
    /**
     * @Fields mHorizontalProgressCountText : 进度条进度文字
     */
    private TextView mHorizontalProgressCountText;
    /**
     * @Fields mWebView : 对话框WebView容器
     */
    private WebView mWebView;
    /**
     * @Fields mListView : 对话框内容列表List
     */
    private ListView mListView;
    /**
     * @Fields mGridView : 对话网格内容GridView
     */
    private GridView mGridView;
    /**
     * @Fields mEditText : 对话框输入框
     */
    private EditText mEditText;
    /**
     * @Fields mCheckBox : 对话框选择框
     */
    private CheckBox mCheckBox;

    // Dialog Buttons
    /**
     * @Fields mDialogButtonDivider : 对话框按钮间分隔符
     */
    private View mDialogButtonDivider;
    /**
     * @Fields mDialogButtonLayout : 对话框按钮容器
     */
    private LinearLayout mDialogButtonLayout;
    /**
     * @Fields mNegativeButton : cancel按钮
     */
    private Button mNegativeButton;
    /**
     * @Fields mNeutralButton : confirm按钮
     */
    private Button mNeutralButton;
    /**
     * @Fields mPositiveButton : ok按钮
     */
    private Button mPositiveButton;
    /**
     * @Fields mNegativeButtonDivider : cancel按钮分割线
     */
    private View mNegativeButtonDivider;
    /**
     * @Fields mPositiveButtonDivider : ok按钮分割线
     */
    private View mPositiveButtonDivider;

    public EasyDialog(Builder builder) {
        super(builder.mContext, builder.mThemeId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        build(builder);
    }

    /**
     * @param builder 对话框构建器
     * @Description 通过构建器生成对话框内容
     */
    public void build(Builder builder) {
        // Set the builder
        mBuilder = builder;
        // Set the content view of the dialog
        setContentView(R.layout.widget_dialog_main);
        // Find the various views
        findViews();
        // Set if the dialog is cancelable
        setCancelable(builder.mCancelable);
        setCanceledOnTouchOutside(builder.mCancelableOutsideTouch);
        // Set various dialog listeners
        setListeners();
        // Set any custom fonts
        setCustomFonts();
        // Sets the dialog background
        setBackgroundFromBuilder();
        // Set the title view from the builder
        setTitleView();

        setTitleDividerView();
        // Set the main dialog view from the builder
        setDialogView();
        // Set the EditText view from the builder
        setEditText();
        // Set the CheckBox from the builder
        setCheckBoxView();
        // Set the positive, negative, and neutral buttons
        setButtonView();
    }

    /**
     * @Description 重新生成对话框内容
     */
    public void rebuild() {
        build(mBuilder);
    }

    /**
     * @param builder 对话框构建器
     * @param handler handler消息分发器
     * @Description 重新生成对话框，异步生产
     */
    public void build(final Builder builder, Handler handler) {
        handler.post(new Runnable() {
            public void run() {
                build(builder);
            }
        });
    }

    /**
     * @param handler handler消息分发器
     * @Description 重新生成对话框，异步生产
     */
    public void rebuild(Handler handler) {
        handler.post(new Runnable() {
            public void run() {
                rebuild();
            }
        });
    }

    /**
     * 查找对话框布局中所有控件
     */
    private void findViews() {
        // Dialog Title
        mTitleLayout = (RelativeLayout) findViewById(R.id.dialog_title_rl);
        mTitleText = (TextView) findViewById(R.id.dialog_title_tv);
        mSubtitleText = (TextView) findViewById(R.id.dialog_titile_subtitle_tv);
        mTitleIcon = (ImageView) findViewById(R.id.dialog_title_ic);
        mTitleProgress = (ProgressBar) findViewById(R.id.dialog_title_pro);
        mTitleCheckBox = (CheckBox) findViewById(R.id.dialog_title_cbx);
        mTitleDivider = (View) findViewById(R.id.dialog_title_di);
        // Dialog Content
        mDialogLayout = (RelativeLayout) findViewById(R.id.dialog_content_rl);
        mMessageLayout = (ScrollView) findViewById(R.id.dialog_content_sv);
        mMessageText = (TextView) findViewById(R.id.dialog_content_sv_msg_tv);
        mIndeterminateProgressLayout = (LinearLayout) findViewById(R.id.dialog_content_indeterminate_pro);
        mIndeterminateProgress = (ProgressBar) findViewById(R.id.dialog_content_indeterminate_pro_bar);
        mIndeterminateProgressText = (TextView) findViewById(R.id.dialog_content_indeterminate_pro_msg);
        mHorizontalProgressLayout = (RelativeLayout) findViewById(R.id.dialog_content_hor_pro);
        mHorizontalProgress = (ProgressBar) findViewById(R.id.dialog_content_hor_pro_bar);
        mHorizontalProgressPercentText = (TextView) findViewById(R.id.dialog_content_hor_pro_percent);
        mHorizontalProgressMessageText = (TextView) findViewById(R.id.dialog_content_hor_pro_msg);
        mHorizontalProgressCountText = (TextView) findViewById(R.id.dialog_content_hor_pro_count);
        mWebView = (WebView) findViewById(R.id.dialog_content_webview);
        mListView = (ListView) findViewById(R.id.dialog_content_lv);
        mGridView = (GridView) findViewById(R.id.dialog_content_gv);
        mEditText = (EditText) findViewById(R.id.dialog_content_edttxt);
        mCheckBox = (CheckBox) findViewById(R.id.dialog_content_cbx);
        // Dialog Buttons
        mDialogButtonDivider = (View) findViewById(R.id.dialog_content_btn_di);
        mDialogButtonLayout = (LinearLayout) findViewById(R.id.dialog_btns);
        mNegativeButton = (Button) findViewById(R.id.dialog_btns_negative_btn);
        mNeutralButton = (Button) findViewById(R.id.dialog_btns_neutral_btn);
        mPositiveButton = (Button) findViewById(R.id.dialog_btns_positive_btn);
        mNegativeButtonDivider = (View) findViewById(R.id.dialog_btns_negative_btn_di);
        mPositiveButtonDivider = (View) findViewById(R.id.dialog_btns_positive_btn_di);
    }

    /**
     * @Description 设置对话框背景
     */
    private void setBackgroundFromBuilder() {
        if (mBuilder.mDialogBackground != null) {
            setBackground(mBuilder.mDialogBackground);
        }

        if (mBuilder.mDialogBackgroundResId != -1) {
            setBackground(mBuilder.mDialogBackgroundResId);
        }
        if (mBuilder.mDialogContentBackgroundResId != -1) {
            setContentBackground(mBuilder.mDialogContentBackgroundResId);
        }
    }

    /**
     * @Description 设置对话框事件监听接收者，可以监听ok按钮、cancel按钮 、onShow、onDismiss事件监听
     */
    private void setListeners() {
        if (mBuilder.mOnKeyListener != null) {
            setOnKeyListener(mBuilder.mOnKeyListener);
        }

        if (mBuilder.mOnCancelListener != null) {
            setOnCancelListener(mBuilder.mOnCancelListener);
        }

        if (mBuilder.mOnShowListener != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            setOnShowListener(mBuilder.mOnShowListener);
        }

        if (mBuilder.mOnDismissListener != null) {
            setOnDismissListener(mBuilder.mOnDismissListener);
        }
    }

    /**
     * @Description 设置对话框标题
     */
    private void setTitleView() {
        if (mBuilder.mCustomTitleView != null) {
            // 可以设置对话框自定义布局
            setCustomTitle(mBuilder.mCustomTitleView);
        } else if (mBuilder.mTitleIcon == null && mBuilder.mTitleText == null) {
            removeTitle();
            hideTitle();
        } else {
            if (mBuilder.mTitleIcon != null) {
                setTitleIcon(mBuilder.mTitleIcon);
            } else {
                mTitleIcon.setVisibility(View.GONE);
            }

            if (mBuilder.mSubtitleText != null) {
                setSubtitle(mBuilder.mSubtitleText);
                if (mBuilder.mSubTitleColorResId != -1) {
                    mSubtitleText.setTextColor(mBuilder.mContext.getResources().getColor(mBuilder.mSubTitleColorResId));
                }
            } else if (mSubtitleText.getVisibility() == View.VISIBLE) {
                removeSubtitle();
            }

            setTitle(mBuilder.mTitleText);
            if (mBuilder.mTitleColorResId != -1) {
                mTitleText.setTextColor(mBuilder.mContext.getResources().getColor(mBuilder.mTitleColorResId));
            }
            showTitleProgress(mBuilder.mShowTitlebarProgress);

            if (mBuilder.mTitleCheckbox != null) {
                mTitleCheckBox.setVisibility(View.VISIBLE);
                mTitleCheckBox.setChecked(mBuilder.mTitleCheckbox);
                mTitleCheckBox
                        .setOnCheckedChangeListener(mBuilder.mTitleCheckBoxListener);
            } else {
                mTitleCheckBox.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @Description 设置标题分割线
     */
    private void setTitleDividerView() {
        if (mTitleDivider != null) {
            if (mBuilder.mTitleDividerColorResId != -1) {
                mTitleDivider.setBackgroundResource(mBuilder.mTitleDividerColorResId);
            }
        }
    }

    /**
     * @Description 设置对话框消息内容
     */
    private void setDialogView() {
        if (mBuilder.mMainDialogView != null) {
            // 对话框消息内容也可以自定义布局
            setDialogView(mBuilder.mMainDialogView);
        } else {
            if (mBuilder.mDialogMessage != null) {
                setDialogMessageVisibility(View.VISIBLE);
                mMessageText.setText(mBuilder.mDialogMessage);
                if (mBuilder.mMessageColorResId != -1) {
                    mMessageText.setTextColor(mBuilder.mContext.getResources().getColor(mBuilder.mMessageColorResId));
                }
            } else {
                setDialogMessageVisibility(View.GONE);
            }

            if (mBuilder.mShowIndeterminateProgress) {
                setIndeterminateProgressVisibility(View.VISIBLE);
                mIndeterminateProgressText.setText(mBuilder.mProgressMessage);
            } else {
                setIndeterminateProgressVisibility(View.GONE);
            }

            if (mBuilder.mShowHorzProgress) {
                if (mBuilder.mIndeterminateHorizontalProgress != null) {
                    setHorizontalProgressVisibility(View.VISIBLE);
                    mHorizontalProgress.setIndeterminate(true);
                    // 不确定进度条图片需要自定义
                    // int resId = R.drawable.dialog_pro_hor;
                    // mHorizontalProgress.setIndeterminateDrawable(getContext()
                    // .getResources().getDrawable(resId)); //
                    mHorizontalProgressPercentText.setVisibility(View.GONE);
                    mHorizontalProgressCountText.setVisibility(View.GONE);
                    mHorizontalProgressMessageText
                            .setText(mBuilder.mHorzProgressMessage);
                } else {
                    setHorizontalProgressVisibility(View.VISIBLE);
                    mHorizontalProgressPercentText.setVisibility(View.VISIBLE);
                    mHorizontalProgressCountText.setVisibility(View.VISIBLE);
                    setProgress(mBuilder.mHorzMaxProgress,
                            mBuilder.mHorzMinProgress);
                    updateProgress(mBuilder.mHorzMaxProgress,
                            mBuilder.mHorzMinProgress,
                            mBuilder.mHorzProgressMessage);
                }
            } else {
                setHorizontalProgressVisibility(View.GONE);
            }

            if (mBuilder.mWebViewUrl != null) {
                setWebViewVisibility(View.VISIBLE);
                loadUrl(mBuilder.mWebViewUrl,
                        mBuilder.mOverrideLoadingOnWebView);
                if (mBuilder.mWebViewBackgroundColor != -1) {
                    setWebViewBackgroundColor(mBuilder.mWebViewBackgroundColor);
                }
            } else {
                setWebViewVisibility(View.GONE);
            }

            if (mBuilder.mListItems != null) {
                mAdapter = new EasyDialogListAdapter(mBuilder);
                mGridView.setFastScrollEnabled(mBuilder.mSetFastScrollEnabled);
                mListView.setFastScrollEnabled(mBuilder.mSetFastScrollEnabled);
                if (mBuilder.mListStyle != LIST_STYLE_GRIDVIEW) {
                    setGridViewVisibility(View.GONE);
                    setListViewVisibility(View.VISIBLE);
                    if (mBuilder.mListStyle == LIST_STYLE_SINGLE_CHOICE) {
                        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    } else {
                        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    }
                    setListViewAdapter(mAdapter);
                    setListViewItemClickListener(mDefaultListItemClickListener);
                } else {
                    setListViewVisibility(View.GONE);
                    setGridViewVisibility(View.VISIBLE);
                    if (mBuilder.mListStyle == LIST_STYLE_SINGLE_CHOICE) {
                        mGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                    } else {
                        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
                    }
                    setGridViewAdapter(mAdapter);
                    setGridViewItemClickListener(mDefaultListItemClickListener);
                }
            }
        }
    }

    /**
     * @Description 设置对话框内输入内容
     */
    private void setEditText() {
        if (mBuilder.mEditTextText != null || mBuilder.mEditTextHint != null) {
            mEditText.setVisibility(View.VISIBLE);
            mEditText.setText(mBuilder.mEditTextText);
            mEditText.setHint(mBuilder.mEditTextHint);
            if (mBuilder.mTextWatcher != null) {
                mEditText.addTextChangedListener(mBuilder.mTextWatcher);
            }
        } else {
            mEditText.setVisibility(View.GONE);
        }
    }

    /**
     * @Description 设置对话框选择框
     */
    private void setCheckBoxView() {
        if (mBuilder.mCheckBoxText != null) {
            setCheckBoxVisibility(View.VISIBLE);
            setCheckBox(mBuilder.mCheckBoxText, mBuilder.mCheckBoxIsChecked,
                    mBuilder.mOnCheckedChangeListener);
        } else {
            setCheckBoxVisibility(View.GONE);
        }
    }

    /**
     * @Description 设置自定义字体
     */
    private void setCustomFonts() {
        if (mBuilder.mTitleFont != null) {
            setFonts(mBuilder.mTitleFont, mTitleText, mSubtitleText);
        }

        if (mBuilder.mMainFont != null) {
            setFonts(mBuilder.mMainFont, mMessageText,
                    mIndeterminateProgressText, mHorizontalProgressPercentText,
                    mHorizontalProgressMessageText,
                    mHorizontalProgressCountText, mEditText, mCheckBox,
                    mNegativeButton, mNeutralButton, mPositiveButton);
        }
    }

    /**
     * @param typeface 样式
     * @param views    view组件
     * @Description 给view组件设置样式
     */
    private void setFonts(Typeface typeface, View... views) {
        for (View view : views) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(typeface);
            } else if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(typeface);
            }
        }
    }

    /**
     * 设置对话框按钮
     */
    private void setButtonView() {
        setButtonEnabledState(BUTTON_NEGATIVE,
                mBuilder.mNegativeButtonEnabledState);
        setButtonEnabledState(BUTTON_NEUTRAL,
                mBuilder.mNeutralButtonEnabledState);
        setButtonEnabledState(BUTTON_POSITIVE,
                mBuilder.mPositiveButtonEnabledState);

        if (mBuilder.mNegativeButtonText != null) {
            setNegativeButton(mBuilder.mNegativeButtonText,
                    mBuilder.mNegativeButtonClickListener);
            if (mBuilder.mButtonColorResId != -1) {
                mNegativeButton.setTextColor(mBuilder.mContext.getResources().getColor(mBuilder.mButtonColorResId));
            }
            if (mBuilder.mButtonBackgroundResId != -1) {
                mNegativeButton.setBackgroundResource(mBuilder.mButtonBackgroundResId);
            }
        } else {
            setNegativeButtonVisibility(View.GONE);
        }

        if (mBuilder.mNeutralButtonText != null) {
            setNeutralButton(mBuilder.mNeutralButtonText,
                    mBuilder.mNeutralButtonClickListener);
            if (mBuilder.mButtonColorResId != -1) {
                mNeutralButton.setTextColor(mBuilder.mContext.getResources().getColor(mBuilder.mButtonColorResId));
            }
            if (mBuilder.mButtonBackgroundResId != -1) {
                mNeutralButton.setBackgroundResource(mBuilder.mButtonBackgroundResId);
            }
        } else {
            setNeutralButtonVisibility(View.GONE);
        }

        if (mBuilder.mPositiveButtonText != null) {
            setPositiveButton(mBuilder.mPositiveButtonText,
                    mBuilder.mPositiveButtonClickListener);
            if (mBuilder.mButtonColorResId != -1) {
                mPositiveButton.setTextColor(mBuilder.mContext.getResources().getColor(mBuilder.mButtonColorResId));
            }
            if (mBuilder.mButtonBackgroundResId != -1) {
                mPositiveButton.setBackgroundResource(mBuilder.mButtonBackgroundResId);
            }
        } else {
            setPositiveButtonVisibility(View.GONE);
        }
    }

    /**
     * @param background 背景图
     * @Description 设置对话框背景
     */
    @SuppressWarnings("deprecation")
    public void setBackground(Drawable background) {
        View rootView = findViewById(android.R.id.content).getRootView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.setBackground(background);
        } else {
            rootView.setBackgroundDrawable(background);
        }
    }

    /**
     * @param resid 资源ID
     * @Description 设置对话框背景
     */
    public void setBackground(int resid) {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setBackgroundResource(resid);
        // findViewById(R.id.dialog_bg).setBackgroundResource(
        // resid);
    }

    public void setContentBackground(int resid) {
        findViewById(R.id.dialog_bg).setBackgroundResource(resid);
    }

    /**
     * @Description 清除对话框标题布局内的所有子控件
     */
    public void removeTitle() {
        mTitleLayout.removeAllViews();
    }

    /**
     * @Description 隐藏对话框标题区域
     */
    public void hideTitle() {
        mTitleLayout.setVisibility(View.GONE);
    }

    /**
     * @Description 显示对话框标题区域
     */
    public void showTitle() {
        mTitleLayout.setVisibility(View.VISIBLE);
    }

    /**
     * @param view 标题布局
     * @Description 设置对话框自定义标题布局
     */
    public void setCustomTitle(View view) {
        mTitleLayout.removeAllViews();
        mTitleLayout.addView(view);
    }

    /*
     * (non-Javadoc) 设置对话框标题
     *
     * @see android.app.Dialog#setTitle(java.lang.CharSequence)
     */
    @Override
    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
        if (title.length() >= 25) {
            mTitleText.setSelected(true);
        }
    }

    /*
     * (non-Javadoc) 设置对话框标题
     *
     * @see android.app.Dialog#setTitle(int)
     */
    @Override
    public void setTitle(int stringId) {
        setTitle(getContext().getString(stringId));
    }

    /**
     * @Description 显示对话框标题中进度条
     */
    public void showTitleProgress() {
        showTitleProgress(true);
    }

    /**
     * @Description 隐藏对话框标题中进度条
     */
    public void hideTitleProgress() {
        showTitleProgress(false);
    }

    /**
     * @param show 进度条是否显示
     * @Description 设置对话框标题中进度条是否显示
     */
    public void showTitleProgress(boolean show) {
        mTitleProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * @param icon 标题icon
     * @Description 设置对话框标题前的Icon
     */
    public void setTitleIcon(Drawable icon) {
        mTitleIcon.setImageDrawable(icon);
    }

    /**
     * @param resId 标题icon
     * @Description 设置对话框标题前的Icon
     */
    public void setTitleIcon(int resId) {
        mTitleIcon.setImageResource(resId);
    }

    /**
     * @param subtitle 副标题
     * @Description 设置对话框副标题
     */
    public void setSubtitle(String subtitle) {
        mSubtitleText.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.dialog_title_ic);
        layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.dialog_title_pro);
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        layoutParams.leftMargin = (int) (6 * (metrics.densityDpi / 160f));
        mTitleText.setLayoutParams(layoutParams);
        mSubtitleText.setText(subtitle);
        if (subtitle.length() >= 45)
            mSubtitleText.setSelected(true);
    }

    /**
     * @param stringId 副标题
     * @Description 设置对话框副标题
     */
    public void setSubtitle(int stringId) {
        setSubtitle(getContext().getString(stringId));
    }

    /**
     * @Description 删除对话框副标题
     */
    public void removeSubtitle() {
        mSubtitleText.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.dialog_title_ic);
        layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.dialog_title_pro);
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        layoutParams.leftMargin = (int) (6 * (metrics.densityDpi / 160f));
        mTitleText.setLayoutParams(layoutParams);
    }

    /**
     * @param view 对话框内容 view
     * @Description 设置对话框内容自定义view
     */
    public void setDialogView(View view) {
        mDialogLayout.removeAllViews();
        mDialogLayout.addView(view);
    }

    /**
     * @param visibility 内容区域是否显示
     * @Description 设置对话框消息内容区域是否显示
     */
    public void setDialogMessageVisibility(int visibility) {
        mMessageLayout.setVisibility(visibility);
    }

    /**
     * @param visibility 进度条是否显示
     * @Description 设置不明确进度条区域是否显示
     */
    public void setIndeterminateProgressVisibility(int visibility) {
        mIndeterminateProgressLayout.setVisibility(visibility);
    }

    /**
     * @param visibility 进度条是否显示
     * @Description 设置水平进度条区域是否显示
     */
    public void setHorizontalProgressVisibility(int visibility) {
        mHorizontalProgressLayout.setVisibility(visibility);
    }

    /**
     * @param max      最大
     * @param progress 当前进度
     * @Description 设置进度条进度
     */
    public void setProgress(int max, int progress) {
        mHorizontalProgress.setMax(max);
        mHorizontalProgress.setProgress(progress);
    }

    /**
     * @param message 进度条标题信息
     * @Description 更新进度条文本
     */
    public void updateProgressMessage(String message) {
        mHorizontalProgressMessageText.setText(message);
        if (message.length() >= 35)
            mHorizontalProgressMessageText.setSelected(true);
    }

    /**
     * @param max      最大进度
     * @param progress 当前进度
     * @Description 更新进度条进度
     */
    public void updateProgress(int max, int progress) {
        int percent = (int) Math.floor((((double) progress / max) * 100));
        mHorizontalProgressCountText.setText(progress + "/" + max);
        mHorizontalProgressPercentText.setText(percent + "%");
    }

    /**
     * @param max      最大进度
     * @param progress 当前进度
     * @param message  进度条标题信息
     * @Description 更新进度条
     */
    public void updateProgress(int max, int progress, String message) {
        updateProgress(max, progress);
        updateProgressMessage(message);
    }

    /**
     * @param progress 当期进度
     * @param message  进度条标题
     * @Description 更新进度条
     */
    public void updateProgress(int progress, String message) {
        updateProgress(mBuilder.mHorzMaxProgress, progress);
        updateProgressMessage(message);
    }

    /**
     * @param progress 当前进度
     * @Description 更新进度条进度
     */
    public void updateProgress(int progress) {
        updateProgress(mBuilder.mHorzMaxProgress, progress);
    }

    /**
     * @param handler 消息分发器
     * @Description 自动增加进度条进度，进度增加1%
     */
    public void incrementProgress(Handler handler) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                incrementProgressBy(1);
            }
        });
    }

    /**
     * @Description 自动增加进度条进度，进度增加1%
     */
    public void incrementProgress() {
        incrementProgressBy(1);
    }

    /**
     * @param diff 增加进度，步长进度
     * @Description 自动增加进度条进度
     */
    public void incrementProgressBy(int diff) {
        mHorizontalProgress.incrementProgressBy(diff);
        updateProgress(mHorizontalProgress.getProgress());
    }

    /**
     * @param message 进度条标题
     * @Description 自动增加进度，进度条标题自定义
     */
    public void incrementProgress(String message) {
        incrementProgressBy(1, message);
    }

    /**
     * @param diff    增长进度
     * @param message 标题内容
     * @Description 自动增加进度，给定进度步长与进度条标题
     */
    public void incrementProgressBy(int diff, String message) {
        mHorizontalProgress.incrementProgressBy(diff);
        updateProgress(mHorizontalProgress.getProgress());
        updateProgressMessage(message);
    }

    /**
     * @param visibility 显示标记
     * @Description 设置对话框WebView区域是否显示
     */
    public void setWebViewVisibility(int visibility) {
        mWebView.setVisibility(visibility);
    }

    /**
     * @param url 内容url
     * @Description webview加载url
     */
    public void loadUrl(String url) {
        loadUrl(url, false);
    }

    /**
     * @param url             内容url
     * @param overrideLoading 是否拦截加载过程
     * @Description webview加载url
     */
    public void loadUrl(String url, boolean overrideLoading) {
        if (overrideLoading) {
            mWebView.setWebViewClient(new WebViewClient() {
                /*
                 * On Android 1.1 shouldOverrideUrlLoading() will be called
                 * every time the user clicks a link, but on Android 1.5 it will
                 * be called for every page load, even if it was caused by
                 * calling loadUrl()!
                 */
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!url.startsWith("http")) {
                        view.loadUrl(url);
                        return true;
                    }
                    return false;
                }
            });
        }
        if (url.startsWith("http") || url.endsWith("html")) {
            mWebView.loadUrl(url);
        } else {
            mWebView.loadData(url, "text/html", null);
        }
    }

    /**
     * @param color 颜色值
     * @Description 设置对话框webview组件背景色
     */
    public void setWebViewBackgroundColor(int color) {
        mWebView.setBackgroundColor(color);
    }

    /**
     * @return
     * @Description 获取当前内容list类型，gridview类型、listview
     */
    public int getCurrentListStyle() {
        if (mAdapter != null) {
            return mAdapter.getListStyle();
        }

        return mBuilder.mListStyle;
    }

    /**
     * @return 内容适配器
     * @Description 获取列表内容适配器
     */
    public ListAdapter getAdapter() {
        return getCurrentListStyle() == LIST_STYLE_GRIDVIEW ? mGridView
                .getAdapter() : mListView.getAdapter();
    }

    /**
     * @return
     * @Description 返回对话框内容适配器
     */
    public EasyDialogListAdapter getDialogListAdapter() {
        if (mAdapter instanceof EasyDialogListAdapter) {
            return (EasyDialogListAdapter) mAdapter;
        }

        return null;
    }

    /**
     * @param listItems 内容显示列表项
     * @Description 增加对话框列表内容项
     */
    public void addListItems(List<ListItem> listItems) {
        if (mAdapter != null) {
            mAdapter.getListItems().addAll(listItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param labels 内容显示数组
     * @Description 增加对话框列表内容项
     */
    public void addListItems(String[] labels) {
        if (mAdapter != null) {
            List<ListItem> items = new ArrayList<ListItem>();
            for (String label : labels) {
                items.add(new ListItem(label));
            }
            mAdapter.getListItems().addAll(items);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param listItems 列表项
     * @Description 设置对话框列表项内容
     */
    public void setListItems(List<ListItem> listItems) {
        setListItems(mBuilder.mListStyle, listItems);
    }

    /**
     * @param listStyle 列表样式，listview或者gridview
     * @param listItems 列表项内容
     * @Description 设置对话框列表项内容
     */
    public void setListItems(int listStyle, List<ListItem> listItems) {
        if (mAdapter == null) {
            mAdapter = new EasyDialogListAdapter(getContext(), listItems,
                    listStyle);
            if (listStyle != LIST_STYLE_GRIDVIEW) {
                mListView.setAdapter(mAdapter);
            } else {
                mGridView.setAdapter(mAdapter);
            }
        } else {
            mAdapter.setListStyle(listStyle);
            mAdapter.setListItems(listItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @param listener 监听者
     * @Description 设置列表项内容点击事件监听者
     */
    public void setListViewItemClickListener(OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }

    /**
     * @param visibility 显示标记
     * @Description 设置对话框列表内容区域是否显示
     */
    public void setListViewVisibility(int visibility) {
        mListView.setVisibility(visibility);
    }

    /**
     * @param adapter 内容适配器
     * @Description 设置listview内容适配器
     */
    public void setListViewAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    /**
     * @param listener 监听者
     * @Description 设置gridview条目点击事件监听者
     */
    public void setGridViewItemClickListener(OnItemClickListener listener) {
        mGridView.setOnItemClickListener(listener);
    }

    /**
     * @param visibility 显示标记
     * @Description 设置对话框gridview区域是否显示
     */
    public void setGridViewVisibility(int visibility) {
        mGridView.setVisibility(visibility);
    }

    /**
     * @param adapter 内容适配器
     * @Description 设置gridview内容适配器
     */
    public void setGridViewAdapter(ListAdapter adapter) {
        mGridView.setAdapter(adapter);
    }

    /**
     * @return 返回内容项
     * @Description 返回listview内容项
     */
    public List<ListItem> getListItems() {
        if (mAdapter == null)
            return null;
        return mAdapter.getListItems();
    }

    /**
     * @param position 列表项索引
     * @return 返回单个列表项
     * @Description 获取列表内容项中特定项
     */
    public ListItem getListItem(int position) {
        if (mAdapter != null) {
            return mAdapter.getItem(position);
        }
        return null;
    }

    /**
     * @return 选中的条目
     * @Description 获取列表项中选中的条目
     */
    public List<ListItem> getCheckedItems() {
        List<ListItem> items = new ArrayList<ListItem>();
        if (mAdapter != null) {
            for (ListItem item : mAdapter.getListItems()) {
                if (item.checked) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    /**
     * @param check 条目选中状态
     * @Description 设置列表项条目状态
     */
    public void checkAll(boolean check) {
        if (mAdapter == null)
            return;
        for (ListItem item : mAdapter.getListItems()) {
            item.checked = check;
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * @Fields mDefaultListItemClickListener : 默认条目点击事件接收者
     */
    private OnItemClickListener mDefaultListItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (mBuilder.mListStyle == LIST_STYLE_SINGLE_CHOICE) {
                // uncheck all radio buttons except the list item that was
                // clicked.
                for (ListItem listItem : getListItems()) {
                    listItem.checked = false;
                }

                ListItem listItem = mAdapter.getItem(position);
                listItem.checked = true;
                mAdapter.notifyDataSetChanged();

                if (mBuilder.mOnItemClickListener != null) {
                    mBuilder.mOnItemClickListener.onClick(EasyDialog.this,
                            position);
                }
            } else {
                // Toggle the CheckBox if it is visible.
                ListItem listItem = mAdapter.getItem(position);
                if (listItem.checked != null) {
                    listItem.checked = !listItem.checked;
                    mAdapter.notifyDataSetChanged();
                }

                if (mBuilder.mListStyle == LIST_STYLE_MULTI_CHOICE) {
                    if (mBuilder.mOnMultiChoiceClickListener != null) {
                        mBuilder.mOnMultiChoiceClickListener.onClick(
                                EasyDialog.this, position, listItem.checked);
                    }
                } else if (mBuilder.mOnItemClickListener != null) {
                    mBuilder.mOnItemClickListener.onClick(EasyDialog.this,
                            position);
                }
            }
        }

    };

    /**
     * @param visibility 显示标记
     * @Description 设置对话框中选择框是否显示
     */
    public void setCheckBoxVisibility(int visibility) {
        mCheckBox.setVisibility(visibility);
    }

    /**
     * @param text     显示文本
     * @param checked  选中状态
     * @param listener 选中/取消 事件监听者
     * @Description 设置对话框中选择框信息
     */
    public void setCheckBox(String text, boolean checked,
                            CompoundButton.OnCheckedChangeListener listener) {
        mCheckBox.setText(text);
        mCheckBox.setChecked(checked);
        if (listener != null) {
            mCheckBox.setOnCheckedChangeListener(listener);
        }
        setCheckBoxVisibility(View.VISIBLE);
    }

    /**
     * @Description 设置对话框中button是之间分割符
     */
    private void setButtonDividers() {
        int numButtons = 0;
        Button[] buttons = {mNegativeButton, mNeutralButton, mPositiveButton};

        for (Button button : buttons) {
            if (button.getVisibility() == View.VISIBLE) {
                numButtons++;
            }
        }

        if (numButtons > 0
                && mDialogButtonLayout.getVisibility() != View.VISIBLE) {
            mDialogButtonLayout.setVisibility(View.VISIBLE);
        }

        if (mHideButtonDividers) {
            mDialogButtonDivider.setVisibility(View.GONE);
            mNegativeButtonDivider.setVisibility(View.GONE);
            mPositiveButtonDivider.setVisibility(View.GONE);
            return;
        }

        switch (numButtons) {
            case 0:
                mDialogButtonDivider.setVisibility(View.GONE);
                mNegativeButtonDivider.setVisibility(View.GONE);
                mPositiveButtonDivider.setVisibility(View.GONE);
                break;
            case 1:
                mDialogButtonDivider.setVisibility(View.VISIBLE);
                mNegativeButtonDivider.setVisibility(View.GONE);
                mPositiveButtonDivider.setVisibility(View.GONE);
                break;
            case 2:
                mDialogButtonDivider.setVisibility(View.VISIBLE);
                mNegativeButtonDivider.setVisibility(View.VISIBLE);
                mPositiveButtonDivider.setVisibility(View.GONE);
                break;
            case 3:
                mDialogButtonDivider.setVisibility(View.VISIBLE);
                mNegativeButtonDivider.setVisibility(View.VISIBLE);
                mPositiveButtonDivider.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * @Description 隐藏对话框buttons之间分隔符
     */
    public void hideButtonDividers() {
        mHideButtonDividers = false;
        setButtonDividers();
    }

    /**
     * @Description 显示对话框buttons之间分隔符
     */
    public void showButtonDividers() {
        mHideButtonDividers = true;
        setButtonDividers();
    }

    /**
     * @param whichButton button，-1：ok按钮，-2：取消按钮，-3：confirm按钮
     * @param enabled     按钮状态，是否可用
     * @Description 设置对话框中button状态
     */
    public void setButtonEnabledState(int whichButton, boolean enabled) {
        if (whichButton == BUTTON_NEGATIVE) {
            mNegativeButton.setEnabled(enabled);
        } else if (whichButton == BUTTON_NEUTRAL) {
            mNeutralButton.setEnabled(enabled);
        } else if (whichButton == BUTTON_POSITIVE) {
            mPositiveButton.setEnabled(enabled);
        }
    }

    /**
     * @param visibility 显示状态
     * @Description 设置取消按钮是否显示
     */
    public void setNegativeButtonVisibility(int visibility) {
        mNegativeButton.setVisibility(visibility);
        setButtonDividers();
    }

    /**
     * @param text     按钮标题
     * @param listener 按钮点击事件监听者
     * @Description 设置取消按钮标题以及点击事件
     */
    public void setNegativeButton(String text,
                                  final DialogInterface.OnClickListener listener) {
        setNegativeButtonVisibility(View.VISIBLE);
        mNegativeButton.setText(text);
        mNegativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(EasyDialog.this, BUTTON_NEGATIVE);
                }
            }
        });
    }

    /**
     * @param visibility 显示标记
     * @Description 设置confirm按钮是否显示
     */
    public void setNeutralButtonVisibility(int visibility) {
        mNeutralButton.setVisibility(visibility);
        setButtonDividers();
    }

    /**
     * @param text     按钮标题
     * @param listener 按钮点击事件监听者
     * @Description 设置confirm按钮标题以及点击事件监听者
     */
    public void setNeutralButton(String text,
                                 final DialogInterface.OnClickListener listener) {
        setNeutralButtonVisibility(View.VISIBLE);
        mNeutralButton.setText(text);
        mNeutralButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(EasyDialog.this, BUTTON_NEUTRAL);
                }
            }
        });
    }

    /**
     * @param visibility 显示标记
     * @Description 设置ok按钮是否显示
     */
    public void setPositiveButtonVisibility(int visibility) {
        mPositiveButton.setVisibility(visibility);
        setButtonDividers();
    }

    /**
     * @param text     按钮标题
     * @param listener 按钮点击事件监听者
     * @Description 设置ok按钮标题以及点击事件监听者
     */
    public void setPositiveButton(String text,
                                  final DialogInterface.OnClickListener listener) {
        setPositiveButtonVisibility(View.VISIBLE);
        mPositiveButton.setText(text);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(EasyDialog.this, BUTTON_POSITIVE);
                }
            }
        });
    }

    /**
     * @return
     * @Description 返回对话框构建器
     */
    public Builder getBuilder() {
        return mBuilder;
    }

    /**
     * @return 布局
     * @Description 返回对话框标题布局
     */
    public RelativeLayout getTitleLayout() {
        return mTitleLayout;
    }

    /**
     * @return 标题文本
     * @Description 返回对话框标题
     */
    public TextView getTitleText() {
        return mTitleText;
    }

    /**
     * @return 副标题文本
     * @Description 返回对话框副标题
     */
    public TextView getSubtitleText() {
        return mSubtitleText;
    }

    /**
     * @return icon图
     * @Description 返回标题icon
     */
    public ImageView getTitleIcon() {
        return mTitleIcon;
    }

    /**
     * @return
     * @Description 返回标题右侧进度条
     */
    public ProgressBar getTitleProgress() {
        return mTitleProgress;
    }

    /**
     * @return
     * @Description 返回标题选择框
     */
    public CheckBox getTitleCheckBox() {
        return mTitleCheckBox;
    }

    /**
     * @return
     * @Description 返回标题分隔符
     */
    public View getTitleDivider() {
        return mTitleDivider;
    }

    /**
     * @return
     * @Description 返回标题内容区域布局
     */
    public RelativeLayout getDialogLayout() {
        return mDialogLayout;
    }

    /**
     * @return
     * @Description 返回标题内容滚动布局容器
     */
    public ScrollView getMessageLayout() {
        return mMessageLayout;
    }

    /**
     * @return
     * @Description 返回标题内容布局
     */
    public TextView getMessageText() {
        return mMessageText;
    }

    /**
     * @return
     * @Description 返回不确定进度条布局
     */
    public LinearLayout getIndeterminateProgressLayout() {
        return mIndeterminateProgressLayout;
    }

    /**
     * @return
     * @Description 返回不确定进度条
     */
    public ProgressBar getIndeterminateProgress() {
        return mIndeterminateProgress;
    }

    /**
     * @return
     * @Description 返回不确定进度条标题
     */
    public TextView getIndeterminateProgressText() {
        return mIndeterminateProgressText;
    }

    /**
     * @return
     * @Description 返回进度条容器布局
     */
    public RelativeLayout getHorizontalProgressLayout() {
        return mHorizontalProgressLayout;
    }

    /**
     * @return
     * @Description 返回进度条
     */
    public ProgressBar getHorizontalProgress() {
        return mHorizontalProgress;
    }

    /**
     * @return
     * @Description 返回进度条进度标题
     */
    public TextView getHorizontalProgressPercentText() {
        return mHorizontalProgressPercentText;
    }

    /**
     * @return
     * @Description 返回进度条标题
     */
    public TextView getHorizontalProgressMessageText() {
        return mHorizontalProgressMessageText;
    }

    /**
     * @return
     * @Description 返回进度条计数标题
     */
    public TextView getHorizontalProgressCountText() {
        return mHorizontalProgressCountText;
    }

    /**
     * @return
     * @Description 返回webview
     */
    public WebView getWebView() {
        return mWebView;
    }

    /**
     * @return
     * @Description 返回对话框内容listview
     */
    public ListView getListView() {
        return mListView;
    }

    /**
     * @return
     * @Description 返回对话框内容gridview
     */
    public GridView getGridView() {
        return mGridView;
    }

    /**
     * @return
     * @Description 返回输入框
     */
    public EditText getEditText() {
        return mEditText;
    }

    /**
     * @return
     * @Description 返回选择框
     */
    public CheckBox getCheckBox() {
        return mCheckBox;
    }

    /**
     * @return
     * @Description 返回button分隔符
     */
    public View getDialogButtonDivider() {
        return mDialogButtonDivider;
    }

    /**
     * @return
     * @Description 返回buttons布局容器
     */
    public LinearLayout getDialogButtonLayout() {
        return mDialogButtonLayout;
    }

    /**
     * @return
     * @Description 返回cancel按钮
     */
    public Button getNegativeButton() {
        return mNegativeButton;
    }

    /**
     * @return
     * @Description 返回confirm按钮
     */
    public Button getNeutralButton() {
        return mNeutralButton;
    }

    /**
     * @return
     * @Description 返回ok按钮
     */
    public Button getPositiveButton() {
        return mPositiveButton;
    }

    /**
     * @return
     * @Description 返回confirm按钮分割线
     */
    public View getNegativeButtonDivider() {
        return mNegativeButtonDivider;
    }

    /**
     * @return
     * @Description 返回ok按钮分割线
     */
    public View getPositiveButtonDivider() {
        return mPositiveButtonDivider;
    }

    /**
     * @author
     * @ClassName: Builder
     * @Description: 对话框内容构建器
     * @date 2015年5月14日 上午9:50:50
     */
    public static class Builder {

        protected Context mContext;
        /**
         * @Fields mThemeId : 主题id
         */
        protected int mThemeId;

        /**
         * @Fields mDialogBackground : 对话框背景
         */
        protected Drawable mDialogBackground;
        /**
         * @Fields mDialogBackgroundResId : 对话框背景 资源
         */
        protected int mDialogBackgroundResId = -1;
        // 内容区域背景
        protected int mDialogContentBackgroundResId = -1;

        protected int mTitleColorResId = -1;

        protected int mSubTitleColorResId = -1;

        protected int mTitleDividerColorResId = -1;

        protected int mMessageColorResId = -1;

        protected int mButtonColorResId = -1;

        protected int mButtonBackgroundResId = -1;
        /**
         * @Fields mCancelable : 设置对话框是否可以取消
         */
        protected boolean mCancelable = true;
        /**
         * @Fields mCancelableOutsideTouch : 点击对话框外部，对话框是否取消
         */
        protected boolean mCancelableOutsideTouch = false;

        /**
         * @Fields mCustomTitleView : 自定义标题
         */
        protected View mCustomTitleView;
        /**
         * @Fields mMainDialogView : 对话框内容区域
         */
        protected View mMainDialogView;

        /**
         * @Fields mTitleFont : 标题样式
         */
        protected Typeface mTitleFont;
        /**
         * @Fields mMainFont : 内容样式
         */
        protected Typeface mMainFont;

        /**
         * @Fields mTitleIcon : 标题icon
         */
        protected Drawable mTitleIcon;
        /**
         * @Fields mTitleText : 标题文本
         */
        protected String mTitleText;
        /**
         * @Fields mSubtitleText : 副标题
         */
        protected String mSubtitleText;
        /**
         * @Fields mShowTitlebarProgress : 是否显示标题右侧进度条
         */
        protected boolean mShowTitlebarProgress;
        /**
         * @Fields mTitleCheckbox : 是否显示标题选择框
         */
        protected Boolean mTitleCheckbox;
        /**
         * @Fields mTitleCheckBoxListener : 标题选择框选中事件
         */
        protected CompoundButton.OnCheckedChangeListener mTitleCheckBoxListener;

        /**
         * @Fields mDialogMessage : 对话框内容
         */
        protected String mDialogMessage;
        /**
         * @Fields mShowIndeterminateProgress : 是否显示不确定型进度条
         */
        protected boolean mShowIndeterminateProgress;
        /**
         * @Fields mProgressMessage : 进度条标题
         */
        protected String mProgressMessage;

        /**
         * @Fields mShowHorzProgress : 是否显示水平进度条
         */
        protected boolean mShowHorzProgress;
        /**
         * @Fields mHorzMaxProgress : 进度条最大进度
         */
        protected int mHorzMaxProgress;
        /**
         * @Fields mHorzMinProgress : 进度条最小进度
         */
        protected int mHorzMinProgress;
        /**
         * @Fields mHorzProgressMessage : 水平进度条标题
         */
        protected String mHorzProgressMessage;
        /**
         * @Fields mIndeterminateHorizontalProgress : 是否显示水平进度条
         */
        protected Boolean mIndeterminateHorizontalProgress;

        /**
         * @Fields mWebViewBackgroundColor : webview背景色
         */
        protected int mWebViewBackgroundColor;
        /**
         * @Fields mWebViewUrl : webview中内容url
         */
        protected String mWebViewUrl;
        /**
         * @Fields mOverrideLoadingOnWebView : 是否拦截内容加载过程
         */
        protected boolean mOverrideLoadingOnWebView;

        /**
         * @Fields mEditTextText : 对话框中输入框内容
         */
        protected String mEditTextText;
        /**
         * @Fields mEditTextHint : 输入框提示内容
         */
        protected String mEditTextHint;
        /**
         * @Fields mTextWatcher : 输入框内容变化监听器
         */
        protected TextWatcher mTextWatcher;

        /**
         * @Fields mCheckBoxText : 复选框标题
         */
        protected String mCheckBoxText;
        /**
         * @Fields mCheckBoxIsChecked : 复选框是否选中
         */
        protected boolean mCheckBoxIsChecked;
        /**
         * @Fields mOnCheckedChangeListener : 复选框选中事件
         */
        protected CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

        /**
         * @Fields mSetFastScrollEnabled : 是否快速滑动
         */
        protected boolean mSetFastScrollEnabled;
        /**
         * @Fields mListRowTheme : 列表内容行样式
         */
        protected int mListRowTheme = EasyDialogListAdapter.DARK_THEME;
        /**
         * @Fields mListItemTextColor : 列表内容文字颜色
         */
        protected int mListItemTextColor = -1;
        /**
         * @Fields mListStyle : 列表样式
         */
        protected int mListStyle;
        /**
         * @Fields mListItems : 列表内容项
         */
        protected List<ListItem> mListItems;

        /**
         * @Fields mOnItemClickListener : 单击事件
         */
        protected DialogInterface.OnClickListener mOnItemClickListener;
        /**
         * @Fields mOnMultiChoiceClickListener : 多选点击事件
         */
        protected DialogInterface.OnMultiChoiceClickListener mOnMultiChoiceClickListener;

        /**
         * @Fields mNegativeButtonText : cancel按钮标题
         */
        protected String mNegativeButtonText;
        /**
         * @Fields mNeutralButtonText : confirm按钮标题
         */
        protected String mNeutralButtonText;
        /**
         * @Fields mPositiveButtonText : ok按钮标题
         */
        protected String mPositiveButtonText;

        /**
         * @Fields mNegativeButtonEnabledState : cancel按钮是否显示
         */
        protected boolean mNegativeButtonEnabledState = true;
        /**
         * @Fields mNeutralButtonEnabledState : confirm按钮是否显示
         */
        protected boolean mNeutralButtonEnabledState = true;
        /**
         * @Fields mPositiveButtonEnabledState : ok按钮是否显示
         */
        protected boolean mPositiveButtonEnabledState = true;

        /**
         * @Fields mNegativeButtonClickListener : cancel按钮点击事件
         */
        protected DialogInterface.OnClickListener mNegativeButtonClickListener;
        /**
         * @Fields mNeutralButtonClickListener : confirm按钮点击事件
         */
        protected DialogInterface.OnClickListener mNeutralButtonClickListener;
        /**
         * @Fields mPositiveButtonClickListener : ok按钮点击事件
         */
        protected DialogInterface.OnClickListener mPositiveButtonClickListener;

        /**
         * @Fields mOnCancelListener : 对话框取消事件监听
         */
        protected DialogInterface.OnCancelListener mOnCancelListener;
        /**
         * @Fields mOnKeyListener : 对话框按键事件监听
         */
        protected DialogInterface.OnKeyListener mOnKeyListener;
        /**
         * @Fields mOnShowListener : 对话框显示事件监听
         */
        protected DialogInterface.OnShowListener mOnShowListener;
        /**
         * @Fields mOnDismissListener : 对话框关闭事件监听
         */
        protected DialogInterface.OnDismissListener mOnDismissListener;

        /**
         * @param context 应用context
         * @Description 构造对话框构建器
         */
        public Builder(Context context) {
            this(context, EasyDialog.THEME_HOLO_LIGHT);
        }

        /**
         * @param context 应用context
         * @param themeId 主题id
         * @Description 构造对话框构建器
         */
        public Builder(Context context, int themeId) {
            mContext = context;
            mThemeId = themeId;
            if (themeId == EasyDialog.THEME_HOLO_LIGHT) {
                mListRowTheme = EasyDialogListAdapter.LIGHT_THEME;
            }
        }

        /**
         * @param background 背景图片
         * @return
         * @Description 设置对话框背景
         */
        public Builder setBackground(Drawable background) {
            this.mDialogBackground = background;
            return this;
        }

        /**
         * @param resid 背景资源
         * @return
         * @Description 设置对话框背景
         */
        public Builder setBackground(int resid) {
            this.mDialogBackgroundResId = resid;
            return this;
        }

        public Builder setContentBackground(int resid) {
            this.mDialogContentBackgroundResId = resid;
            return this;
        }

        /**
         * @param cancelable 是否取消
         * @return
         * @Description 设置对话框是否可以取消，按返回键取消
         */
        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        /**
         * @param cancelableOutsideTouch 是否取消
         * @return
         * @Description 设置在对话框外部触摸点击是否取消对话框显示
         */
        public Builder setCanceledOnTouchOutside(boolean cancelableOutsideTouch) {
            this.mCancelableOutsideTouch = cancelableOutsideTouch;
            return this;
        }

        /**
         * @param view 标题布局
         * @return
         * @Description 设置自定义标题布局
         */
        public Builder setCustomTitle(View view) {
            this.mCustomTitleView = view;
            return this;
        }

        /**
         * @param view 内容区域布局
         * @return
         * @Description 设置自定义对话框内容区域布局
         */
        public Builder setView(View view) {
            this.mMainDialogView = view;
            return this;
        }

        /**
         * @param icon 标题icon
         * @return
         * @Description 设置标题icon
         */
        public Builder setTitleIcon(Drawable icon) {
            this.mTitleIcon = icon;
            return this;
        }

        /**
         * @param drawableId 标题icon资源
         * @return
         * @Description 设置标题icon
         */
        public Builder setTitleIcon(int drawableId) {
            this.mTitleIcon = mContext.getResources().getDrawable(drawableId);
            return this;
        }

        /**
         * @param title 对话框标题
         * @return
         * @Description 设置对话框标题
         */
        public Builder setTitle(String title) {
            this.mTitleText = title;
            return this;
        }

        /**
         * @param resId 颜色资源ID
         * @return
         * @Description 设置标题颜色
         */
        public Builder setTitleColorRes(int resId) {
            this.mTitleColorResId = resId;
            return this;
        }

        /**
         * @param resId 颜色资源ID
         * @return
         * @Description 设置按钮颜色
         */
        public Builder setButtonColorRes(int resId) {
            this.mButtonColorResId = resId;
            return this;
        }

        public Builder setButtonBackgroundRes(int resId) {
            this.mButtonBackgroundResId = resId;
            return this;
        }

        /**
         * @param resId 颜色资源ID
         * @return
         * @Description 设置副标题颜色
         */
        public Builder setSubTitleColorRes(int resId) {
            this.mSubTitleColorResId = resId;
            return this;
        }

        public Builder setTitleDividerColorRes(int resId) {
            this.mTitleDividerColorResId = resId;
            return this;
        }

        /**
         * @param stringId 标题资源
         * @return
         * @Description 设置对话框标题
         */
        public Builder setTitle(int stringId) {
            this.mTitleText = mContext.getResources().getString(stringId);
            return this;
        }

        /**
         * @param checked  是否可用
         * @param listener 点击事件监听者
         * @return
         * @Description 设置标题复选框是否可用以及复选框点击事件
         */
        public Builder setTitleCheckBox(boolean checked,
                                        CompoundButton.OnCheckedChangeListener listener) {
            this.mTitleCheckbox = checked;
            this.mTitleCheckBoxListener = listener;
            return this;
        }

        /**
         * @param message 内容文本
         * @return
         * @Description 设置对话框显示内容
         */
        public Builder setMessage(String message) {
            this.mDialogMessage = message;
            return this;
        }

        /**
         * @param resId 颜色资源ID
         * @return
         * @Description 设置显示内容颜色
         */
        public Builder setMessageColorRes(int resId) {
            this.mMessageColorResId = resId;
            return this;
        }

        /**
         * @param stringId 内容文本资源
         * @return
         * @Description 设置对话框显示内容
         */
        public Builder setMessage(int stringId) {
            this.mDialogMessage = mContext.getString(stringId);
            return this;
        }

        /**
         * @param text     cancel按钮显示标题
         * @param listener 点击事件监听者
         * @return
         * @Description 设置cancel按钮标题以及点击事件监听者
         */
        public Builder setNegativeButton(String text,
                                         DialogInterface.OnClickListener listener) {
            this.mNegativeButtonText = text;
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        /**
         * @param stringId cancel按钮显示标题资源
         * @param listener 点击事件监听者
         * @return
         * @Description 设置cancel按钮标题以及点击事件监听者
         */
        public Builder setNegativeButton(int stringId,
                                         DialogInterface.OnClickListener listener) {
            this.mNegativeButtonText = mContext.getString(stringId);
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        /**
         * @param text     confirm按钮显示标题
         * @param listener 点击事件监听者
         * @return
         * @Description 设置confirm按钮标题以及点击事件监听者
         */
        public Builder setNeutralButton(String text,
                                        DialogInterface.OnClickListener listener) {
            this.mNeutralButtonText = text;
            this.mNeutralButtonClickListener = listener;
            return this;
        }

        /**
         * @param stringId confirm按钮显示标题资源
         * @param listener 点击事件监听者
         * @return
         * @Description 设置confirm按钮标题以及点击事件监听者
         */
        public Builder setNeutralButton(int stringId,
                                        DialogInterface.OnClickListener listener) {
            this.mNeutralButtonText = mContext.getString(stringId);
            this.mNeutralButtonClickListener = listener;
            return this;
        }

        /**
         * @param text     ok按钮显示标题
         * @param listener 点击事件监听者
         * @return
         * @Description 设置ok按钮标题以及点击事件监听者
         */
        public Builder setPositiveButton(String text,
                                         DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = text;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        /**
         * @param stringId ok按钮显示标题资源
         * @param listener 点击事件监听者
         * @return
         * @Description 设置ok按钮标题以及点击事件监听者
         */
        public Builder setPositiveButton(int stringId,
                                         DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = mContext.getString(stringId);
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        /**
         * @param state 是否可用
         * @return
         * @Description 设置cancel按钮是否可用
         */
        public Builder setNegativeButtonEnabledState(boolean state) {
            this.mNegativeButtonEnabledState = state;
            return this;
        }

        /**
         * @param state 可用状态
         * @return
         * @Description 设置confirm按钮是否可用
         */
        public Builder setNeutralButtonEnabledState(boolean state) {
            this.mNeutralButtonEnabledState = state;
            return this;
        }

        /**
         * @param state 可用状态
         * @return
         * @Description 设置ok按钮可用状态
         */
        public Builder setPositiveButtonEnabledState(boolean state) {
            this.mPositiveButtonEnabledState = state;
            return this;
        }

        /**
         * @param listener 监听者
         * @return
         * @Description 设置对话框取消事件监听者
         */
        public Builder setOnCancelListener(
                DialogInterface.OnCancelListener listener) {
            this.mOnCancelListener = listener;
            return this;
        }

        /**
         * @param listener 关闭事件监听者
         * @return
         * @Description 设置对话框关闭事件监听者
         */
        public Builder setOnDismissListener(
                DialogInterface.OnDismissListener listener) {
            this.mOnDismissListener = listener;
            return this;
        }

        /**
         * @param listener 按键监听者
         * @return
         * @Description 设置对话框按键监听者
         */
        public Builder setOnKeyListener(DialogInterface.OnKeyListener listener) {
            this.mOnKeyListener = listener;
            return this;
        }

        /**
         * @param listener 显示事件监听者
         * @return
         * @Description 设置对话框显示事件监听者
         */
        public Builder setOnShowListener(DialogInterface.OnShowListener listener) {
            this.mOnShowListener = listener;
            return this;
        }

        /**
         * @param visible 是否显示
         * @return
         * @Description 设置标题右侧进度条是否显示
         */
        public Builder setTitleBarProgress(boolean visible) {
            this.mShowTitlebarProgress = visible;
            return this;
        }

        /**
         * @param subtitle 副标题
         * @return
         * @Description 设置副标题
         */
        public Builder setSubtitle(String subtitle) {
            this.mSubtitleText = subtitle;
            return this;
        }

        /**
         * @param stringId 副标题资源
         * @return
         * @Description 设置副标题
         */
        public Builder setSubtitle(int stringId) {
            this.mSubtitleText = mContext.getString(stringId);
            return this;
        }

        /**
         * @param visible 显示标记
         * @return
         * @Description 设置不确定型进度条是否显示
         */
        public Builder setIndeterminateProgressVisibility(boolean visible) {
            this.mShowIndeterminateProgress = visible;
            return this;
        }

        /**
         * @param message 进度条标题
         * @return
         * @Description 设置不确定型进度条标题
         */
        public Builder setIndeterminateProgress(String message) {
            setIndeterminateProgressVisibility(true);
            this.mProgressMessage = message;
            return this;
        }

        /**
         * @param stringId 进度条标题资源
         * @return
         * @Description 设置不确定型进度条标题
         */
        public Builder setIndeterminateProgress(int stringId) {
            setIndeterminateProgressVisibility(true);
            this.mProgressMessage = mContext.getString(stringId);
            return this;
        }

        /**
         * @param message 进度条标题
         * @return
         * @Description 设置水平进度条标题
         */
        public Builder setIndeterminateHorizontalProgress(String message) {
            this.mShowHorzProgress = true;
            this.mIndeterminateHorizontalProgress = true;
            this.mHorzProgressMessage = message;
            return this;
        }

        /**
         * @param max      最大进度
         * @param progress 当前进度
         * @param message  进度条标题
         * @return
         * @Description 设置水平进度条最大进度、当前进度、进度条标题
         */
        public Builder setHorizontalProgress(int max, int progress,
                                             String message) {
            this.mShowHorzProgress = true;
            this.mHorzMaxProgress = max;
            this.mHorzMinProgress = progress;
            this.mHorzProgressMessage = message;
            return this;
        }

        /**
         * @param max      最大进度
         * @param progress 当前进度
         * @param stringId 进度条标题资源
         * @return
         * @Description 设置水平进度条最大进度、当前进度、进度条标题
         */
        public Builder setHorizontalProgress(int max, int progress, int stringId) {
            this.mShowHorzProgress = true;
            this.mHorzMaxProgress = max;
            this.mHorzMinProgress = progress;
            this.mHorzProgressMessage = mContext.getString(stringId);
            return this;
        }

        /**
         * @param url 内容链接url
         * @return
         * @Description 设置webview内容地址
         */
        public Builder setWebViewUrl(String url) {

            this.mWebViewUrl = url;
            return this;
        }

        /**
         * @param url             内容链接url
         * @param overrideLoading webview内容加载过程是否拦截处理
         * @return
         * @Description 设置webview内容地址
         */
        public Builder setWebViewUrl(String url, boolean overrideLoading) {
            this.mWebViewUrl = url;
            this.mOverrideLoadingOnWebView = overrideLoading;
            return this;
        }

        /**
         * @param backgroundColor 颜色值
         * @return
         * @Description 设置webview背景色
         */
        public Builder setWebViewBackgroundColor(int backgroundColor) {
            this.mWebViewBackgroundColor = backgroundColor;
            return this;
        }

        /**
         * @param fastScroll 是否快速滑动
         * @return
         * @Description 设置对话框内容是否可以快速滑动
         */
        public Builder setFastScrollEnabled(boolean fastScroll) {
            this.mSetFastScrollEnabled = fastScroll;
            return this;
        }

        /**
         * @param theme 内容行主题
         * @return
         * @Description 设置对话框内容ListView中内容行主题
         */
        public Builder setListRowTheme(int theme) {
            if (theme == EasyDialogListAdapter.DARK_THEME
                    || theme == EasyDialogListAdapter.LIGHT_THEME) {
                this.mListRowTheme = theme;
            }
            return this;
        }

        /**
         * @param color 颜色值
         * @return
         * @Description 设置对话框listview内容行颜色值
         */
        public Builder setListItemTextColor(int color) {
            this.mListItemTextColor = color;
            return this;
        }

        /**
         * @param items    对话框列表内容
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setGridViewItems(List<ListItem> items,
                                        DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_GRIDVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = items;
            return this;
        }

        /**
         * @param items    对话框列表内容
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setGridViewItems(String[] items,
                                        DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_GRIDVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            for (String label : items) {
                this.mListItems.add(new ListItem(label));
            }
            return this;
        }

        /**
         * @param arrayId  对话框列表内容资源，数组资源ID
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setGridViewItems(int arrayId,
                                        DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_GRIDVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            String[] items = mContext.getResources().getStringArray(arrayId);
            for (String label : items) {
                this.mListItems.add(new ListItem(label));
            }
            return this;
        }

        /**
         * @param items    对话框列表内容
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setItems(List<ListItem> items,
                                DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_LISTVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = items;
            return this;
        }

        /**
         * @param items    对话框列表内容（string数组）
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setItems(String[] items,
                                DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_LISTVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            for (String label : items) {
                this.mListItems.add(new ListItem(label));
            }
            return this;
        }

        /**
         * @param icons    列表项行icon
         * @param items    列表项内容
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setItems(Drawable[] icons, String[] items,
                                DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_LISTVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            for (int i = 0; i < icons.length; i++) {
                this.mListItems.add(new ListItem(icons[i], items[i]));
            }
            return this;
        }

        /**
         * @param icons    列表项行icon
         * @param arrayId  列表项内容(string数组资源ID)
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setItems(Drawable[] icons, int arrayId,
                                DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_LISTVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            String[] items = mContext.getResources().getStringArray(arrayId);
            for (int i = 0; i < icons.length; i++) {
                this.mListItems.add(new ListItem(icons[i], items[i]));
            }
            return this;
        }

        /**
         * @param arrayId  列表项内容(string数组资源ID)
         * @param listener 行单击事件监听者
         * @return
         * @Description 设置对话框列表内容以及行单击事件监听者
         */
        public Builder setItems(int arrayId,
                                DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_LISTVIEW;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            String[] items = mContext.getResources().getStringArray(arrayId);
            for (String label : items) {
                this.mListItems.add(new ListItem(label));
            }
            return this;
        }

        /**
         * @param items    列表项内容
         * @param listener 列表项选中/取消监听者
         * @return
         * @Description 设置对话框多选列表内容以及行单击事件监听者
         */
        public Builder setMultiChoiceItems(List<ListItem> items,
                                           DialogInterface.OnMultiChoiceClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_MULTI_CHOICE;
            this.mOnMultiChoiceClickListener = listener;
            this.mListItems = items;
            return this;
        }

        /**
         * @param items        列表项内容
         * @param checkedItems 列表项内容选中/未选中初始状态
         * @param listener     列表项选中/取消监听者
         * @return
         * @Description 设置对话框多选列表内容以及行单击事件监听者
         */
        public Builder setMultiChoiceItems(String[] items,
                                           boolean[] checkedItems,
                                           DialogInterface.OnMultiChoiceClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_MULTI_CHOICE;
            this.mOnMultiChoiceClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            for (int i = 0; i < checkedItems.length; i++) {
                this.mListItems.add(new ListItem(items[i], checkedItems[i]));
            }
            return this;
        }

        /**
         * @param arrayId      列表项内容(string数组资源ID)
         * @param checkedItems 列表项内容选中/未选中初始状态
         * @param listener     列表项选中/取消监听者
         * @return
         * @Description 设置对话框多选列表内容以及行单击事件监听者
         */
        public Builder setMultiChoiceItems(int arrayId, boolean[] checkedItems,
                                           DialogInterface.OnMultiChoiceClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_MULTI_CHOICE;
            this.mOnMultiChoiceClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            String[] items = mContext.getResources().getStringArray(arrayId);
            for (int i = 0; i < checkedItems.length; i++) {
                this.mListItems.add(new ListItem(items[i], checkedItems[i]));
            }
            return this;
        }

        /**
         * @param items    列表项内容
         * @param listener 列表项选中/取消监听者
         * @return
         * @Description 设置对话框单选列表内容以及行单击事件监听者
         */
        public Builder setSingleChoiceItems(List<ListItem> items,
                                            DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_SINGLE_CHOICE;
            this.mListItems = items;
            this.mOnItemClickListener = listener;
            return this;
        }

        /**
         * @param items       列表项内容
         * @param checkedItem 列表项内容初始选中项索引
         * @param listener    列表项选中/取消监听者
         * @return
         * @Description 设置对话框单选列表内容以及行单击事件监听者
         */
        public Builder setSingleChoiceItems(String[] items, int checkedItem,
                                            DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_SINGLE_CHOICE;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            for (int i = 0; i < items.length; i++) {
                this.mListItems.add(new ListItem(items[i], (i == checkedItem)));
            }
            return this;
        }

        /**
         * @param arrayId     列表项内容资源(数组资源ID)
         * @param checkedItem 列表项内容初始选中项索引
         * @param listener    列表项选中/取消监听者
         * @return
         * @Description 设置对话框单选列表内容以及行单击事件监听者
         */
        public Builder setSingleChoiceItems(int arrayId, int checkedItem,
                                            DialogInterface.OnClickListener listener) {
            this.mListStyle = EasyDialog.LIST_STYLE_SINGLE_CHOICE;
            this.mOnItemClickListener = listener;
            this.mListItems = new ArrayList<ListItem>();
            String[] items = mContext.getResources().getStringArray(arrayId);
            for (int i = 0; i < items.length; i++) {
                this.mListItems.add(new ListItem(items[i], (i == checkedItem)));
            }
            return this;
        }

        /**
         * @param text        输入框文本
         * @param textWatcher 文本变动监听者
         * @return
         * @Description 设置对话框输入框文本以及文本变动监听者
         */
        public Builder setEditText(String text, TextWatcher textWatcher) {
            this.mEditTextText = text;
            this.mTextWatcher = textWatcher;
            return this;
        }

        /**
         * @param text        输入框文本
         * @param hint        未输入时提示文本
         * @param textWatcher 文本变动监听者
         * @return
         * @Description 设置对话框输入框文本以及文本变动监听者
         */
        public Builder setEditText(String text, String hint,
                                   TextWatcher textWatcher) {
            this.mEditTextText = text;
            this.mEditTextHint = hint;
            this.mTextWatcher = textWatcher;
            return this;
        }

        /**
         * @param text     复选框标题
         * @param checked  是否选中
         * @param listener 选中/取消监听事件监听者
         * @return
         * @Description 设置对话框中复选框显示标题、是否选中、以及选中/取消监听事件监听者
         */
        public Builder setCheckBox(String text, boolean checked,
                                   CompoundButton.OnCheckedChangeListener listener) {
            this.mCheckBoxText = text;
            this.mCheckBoxIsChecked = checked;
            this.mOnCheckedChangeListener = listener;
            return this;
        }

        /**
         * @param stringId 复选框标题资源ID
         * @param checked  是否选中
         * @param listener 选中/取消监听事件监听者
         * @return
         * @Description 设置对话框中复选框显示标题、是否选中、以及选中/取消监听事件监听者
         */
        public Builder setCheckBox(int stringId, boolean checked,
                                   CompoundButton.OnCheckedChangeListener listener) {
            this.mCheckBoxText = mContext.getString(stringId);
            this.mCheckBoxIsChecked = checked;
            this.mOnCheckedChangeListener = listener;
            return this;
        }

        /**
         * @param typeface 样式
         * @return
         * @Description 设置对话框标题样式
         */
        public Builder setTitleFont(Typeface typeface) {
            this.mTitleFont = typeface;
            return this;
        }

        /**
         * @param path 样式文件路径
         * @return
         * @Description 设置对框框内容区域样式文件路径
         */
        public Builder setMainFont(String path) {
            this.mMainFont = Typeface.createFromFile(path);
            return this;
        }

        /**
         * @param path 样式文件路径
         * @return
         * @Description 设置标题样式文件路径
         */
        public Builder setTitleFont(String path) {
            this.mTitleFont = Typeface.createFromFile(path);
            return this;
        }

        /**
         * @param mgr  asset文件管理器
         * @param path 样式文件路径
         * @return
         * @Description 设置对框框内容区域样式
         */
        public Builder setMainFont(AssetManager mgr, String path) {
            try {
                this.mMainFont = Typeface.createFromAsset(mgr, path);
            } catch (RuntimeException e) {
            }

            return this;
        }

        /**
         * @param mgr  asset文件管理器
         * @param path 样式文件路径
         * @return
         * @Description 设置对框框标题样式
         */
        public Builder setTitleFont(AssetManager mgr, String path) {
            try {
                this.mTitleFont = Typeface.createFromAsset(mgr, path);
            } catch (RuntimeException e) {
            }

            return this;
        }

        /**
         * @param typeface 样式
         * @return
         * @Description 设置对话框内容区域样式
         */
        public Builder setMainFont(Typeface typeface) {
            this.mMainFont = typeface;
            return this;
        }

        /**
         * @return
         * @Description 创建对话框
         */
        public EasyDialog create() {
            return new EasyDialog(this);
        }

        /**
         * @return
         * @Description 显示对话框
         */
        public EasyDialog show() {
            EasyDialog dialog = new EasyDialog(this);
            dialog.show();
            return dialog;
        }

        /**
         * @param handler 消息分发器
         * @Description 显示对话框
         */
        public void show(Handler handler) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EasyDialog dialog = new EasyDialog(Builder.this);
                    dialog.show();
                }
            });
        }
    }

    /**
     * @author
     * @ClassName: ListItem
     * @Description: 列表项自定义类
     * @date 2015年5月14日 上午11:54:38
     */
    public static class ListItem {

        /**
         * @Fields label : 标题
         */
        public String label;

        /**
         * @Fields icon : icon
         */
        public Drawable icon;

        /**
         * @Fields subLabel : 副标题
         */
        public String subLabel;

        /**
         * @Fields checked : 选中状态
         */
        public Boolean checked;

        /**
         * @Fields data : 数据
         */
        public Object data;

        /**
         * @Fields labelColor : 标题颜色
         */
        public int labelColor = -1;

        /**
         * @Fields subLabelColor : 副标题颜色
         */
        public int subLabelColor = -1;

        /**
         * @Description 列表项构造函数
         */
        public ListItem() {
        }

        /**
         * @param label 列表项标题
         * @Description 列表项构造函数
         */
        public ListItem(String label) {
            this(null, label, null, null);
        }

        /**
         * @param label   列表项标题
         * @param checked 列表项是否选中
         * @Description 列表项构造函数
         */
        public ListItem(String label, Boolean checked) {
            this(null, label, null, checked);
        }

        /**
         * @param icon  列表项icon
         * @param label 列表项标题
         * @Description 列表项构造函数
         */
        public ListItem(Drawable icon, String label) {
            this(icon, label, null, null);
        }

        /**
         * @param icon    列表项icon
         * @param label   列表项标题
         * @param checked 列表项是否选中
         * @Description 列表项构造函数
         */
        public ListItem(Drawable icon, String label, Boolean checked) {
            this(icon, label, null, checked);
        }

        /**
         * @param icon     列表项icon
         * @param label    列表项标题
         * @param subLabel 列表项副标题
         * @Description 列表项构造函数
         */
        public ListItem(Drawable icon, String label, String subLabel) {
            this(icon, label, subLabel, null);
        }

        /**
         * @param icon     列表项icon
         * @param label    列表项标题
         * @param subLabel 列表项副标题
         * @param checked  列表项是否选中
         * @Description 列表项构造函数
         */
        public ListItem(Drawable icon, String label, String subLabel,
                        Boolean checked) {
            this.icon = icon;
            this.label = label;
            this.subLabel = subLabel;
            this.checked = checked;
        }
    }

    /**
     * @Fields LIST_ITEM_COMPARATOR : 列表项内容排序器
     */
    public static final Comparator<ListItem> LIST_ITEM_COMPARATOR = new Comparator<ListItem>() {

        @Override
        public int compare(ListItem item1, ListItem item2) {
            return item1.label.compareToIgnoreCase(item2.label);
        }

    };
}
