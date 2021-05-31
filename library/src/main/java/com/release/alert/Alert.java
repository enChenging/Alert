package com.release.alert;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.release
 * @create 2019-12-27
 * @Describe
 */
public class Alert {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg, dialog_loading_bg;
    private TextView txt_title, txt_msg, txt_cancel;
    private Button btn_neg, btn_pos;
    private View img_line;
    private Display display;
    private RecyclerView bottom_rv_content;
    private Type mType = Type.NORMAL;
    private TextView mProMsgText;
    private List<ItemBean> mAlertViewItems;
    private AlertAdapter mAlertViewAdapter;
    private View mDialogLayout;

    public enum Type {
        NORMAL, PROGRESS, BOTTOM, CUSTOM_DIALOG
    }

    public Alert(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    /**
     * 默认NORMAL类型
     *
     * @return
     */
    public Alert builder() {
        initNormalType();
        return this;
    }

    public Alert builder(Type type) {
        this.mType = type;
        switch (type) {
            case NORMAL:
                initNormalType();
                break;
            case PROGRESS:
                initProgressType();
                break;
            case BOTTOM:
                initBottomType();
                break;
        }
        return this;
    }

    public Alert builder(Type type, int resource) {
        this.mType = type;
        if (type == Type.CUSTOM_DIALOG) {
            initCustomDialog(resource);
        }
        return this;
    }


    private void initNormalType() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alert, null);

        lLayout_bg = view.findViewById(R.id.lLayout_bg);
        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = view.findViewById(R.id.v_line);
        img_line.setVisibility(View.GONE);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void initProgressType() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_alert_loading, null);
        mProMsgText = view.findViewById(R.id.tipTextView);
        dialog_loading_bg = view.findViewById(R.id.dialog_loading_bg);
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //背景改透明
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //去除半透明阴影
        window.setDimAmount(0.0f);
        dialog_loading_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.4), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void initBottomType() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alert_bottom, null);

        view.setMinimumWidth(display.getWidth());
        bottom_rv_content = view.findViewById(R.id.rv_content);
        txt_cancel = view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mAlertViewItems = new ArrayList();
        mAlertViewAdapter = new AlertAdapter(context, mAlertViewItems);
        bottom_rv_content.setAdapter(mAlertViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dec = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.alert_line);
        dec.setDrawable(drawable);
        bottom_rv_content.addItemDecoration(dec);
        bottom_rv_content.setLayoutManager(layoutManager);
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
    }

    private void initCustomDialog(int resource) {
        mDialogLayout = LayoutInflater.from(context).inflate(resource, null);
        dialog = new Dialog(context, R.style.CustomDialogStyle);
        dialog.setContentView(mDialogLayout);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setDimAmount(0.0f);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    public interface OnInitDialogLayoutListener {
        void initView(View view, Dialog dialog);
    }

    public Alert initDialogLayout(OnInitDialogLayoutListener onInitDialogLayoutListener) {
        onInitDialogLayoutListener.initView(mDialogLayout, dialog);
        return this;
    }

    public Alert setBottomCancelBtnColor(@ColorInt int color) {
        if (txt_cancel != null) {
            txt_cancel.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置标题
     *
     * @param msg
     * @return
     */
    public Alert setProgressText(String msg) {
        if (mProMsgText != null) {
            mProMsgText.setVisibility(View.VISIBLE);
            mProMsgText.setText(msg == null ? "" : msg);
        }
        return this;
    }

    public Alert setProgressTextGone() {
        if (mProMsgText != null) mProMsgText.setVisibility(View.GONE);
        return this;
    }


    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public Alert setTitle(String title) {

        if (txt_title != null) {
            txt_title.setVisibility(View.VISIBLE);
            txt_title.setText(title == null ? "" : title);
        }
        return this;
    }

    /**
     * 设置内容
     *
     * @param msg
     * @return
     */
    public Alert setMsg(String msg) {
        if (txt_msg != null){
            txt_msg.setVisibility(View.VISIBLE);
            txt_msg.setText(msg == null ? "" : msg);
        }
        return this;
    }

    public Alert setMsgGravity(int gravity) {
        if (txt_msg != null)
            txt_msg.setGravity(gravity);
        return this;
    }

    /**
     * 设置false点击屏幕或物理返回键，dialog不消失
     *
     * @param cancel
     * @return
     */
    public Alert setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置false点击屏幕：dialog不消失；点击物理返回键：dialog消失
     *
     * @param cancel
     * @return
     */
    public Alert setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置确定键及监听事件
     *
     * @param text
     * @param listener
     * @return
     */
    public Alert setPositiveButton(String text, final View.OnClickListener listener) {
        if (btn_pos != null) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alert_btn_bottom_selector);
            btn_pos.setText(TextUtils.isEmpty(text) ? "确定" : text);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 设置确定监听事件
     *
     * @param listener
     * @return
     */
    public Alert setPositiveButton(final View.OnClickListener listener) {
        if (btn_pos != null) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alert_btn_bottom_selector);
            btn_pos.setText("确定");
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 设置取消键及监听事件
     *
     * @param text
     * @param listener
     * @return
     */
    public Alert setNegativeButton(String text,
                                   final View.OnClickListener listener) {
        if (btn_neg != null) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alert_btn_left_selector);
            img_line.setVisibility(btn_pos.getVisibility());
            btn_neg.setText(TextUtils.isEmpty(text) ? "取消" : text);
            btn_neg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    dialog.dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 设置取消监听事件
     *
     * @param listener
     * @return
     */
    public Alert setNegativeButton(final View.OnClickListener listener) {
        if (btn_neg != null) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alert_btn_left_selector);
            img_line.setVisibility(btn_pos.getVisibility());
            btn_neg.setText("取消");
            btn_neg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    dialog.dismiss();
                }
            });
        }
        return this;
    }


    public interface OnAlertItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 条目点击事件
     *
     * @return
     */
    public Alert setOnItemClickListener(final OnAlertItemClickListener onAlertItemClickListener) {
        if (mAlertViewAdapter != null) {
            mAlertViewAdapter.setOnItemClickLitener(new AlertAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    onAlertItemClickListener.onItemClick(view, position);
                    dialog.dismiss();
                }
            });
            dialog.dismiss();
        }
        return this;
    }

    /**
     * @param strItem 条目名称
     * @param color   条目字体颜色，设置null则默认蓝色
     * @return
     */
    public Alert addItem(String strItem, int color) {
        if (mAlertViewItems != null)
            mAlertViewItems.add(new ItemBean(strItem, color));
        return this;
    }

    public Alert addItem(String strItem) {
        if (mAlertViewItems != null)
            mAlertViewItems.add(new ItemBean(strItem, 0));
        return this;
    }

    public Alert addItem(String[] strItem) {
        if (mAlertViewItems != null) {
            for (String s : strItem)
                mAlertViewItems.add(new ItemBean(s, 0));
        }
        return this;
    }

    public Alert addItem(List<ItemBean> strItem) {
        if (mAlertViewItems != null && strItem != null) {
            for (ItemBean bean : strItem)
                mAlertViewItems.add(bean);
        }

        return this;
    }

    private void notifyData() {
        if (mAlertViewItems == null || mAlertViewItems.size() <= 0)
            return;
        if (mAlertViewItems.size() == 1) {

        }
        mAlertViewAdapter.notifyDataSetChanged();
    }

    public void show() {
        switch (mType) {
            case NORMAL:
                break;
            case PROGRESS:
                break;
            case BOTTOM:
                notifyData();
                break;
        }
        dialog.show();
    }

    public void dissmiss() {
        dialog.dismiss();
    }
}