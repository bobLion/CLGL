package com.bob.android.clgl.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bob.android.clgl.R;
import com.bob.android.clgl.dialog.EasyDialog;
import com.wang.avi.AVLoadingIndicatorView;


public class DialogManager {

	private static AnimLoadingDialog getAnimDialog(Context context, String title, boolean enableCancel) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View contentView = inflater.inflate(R.layout.loading_anim, null);
		AnimLoadingDialog dialog = new AnimLoadingDialog(
				new AnimLoadingDialog.Builder(context).setTitleIcon(null)
						.setView(contentView).setTitle(null)
						.setBackground(R.color.transparent).setCancelable(enableCancel),
				R.id.loading);
		dialog.setLoadingTitle(title);
		return dialog;
	}


	public static EasyDialog getOriginalDialog(Context context, int theme,
                                               String message) {
		boolean enable = true;
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			if (activity.isFinishing()) {
				enable = false;
			}
		}
		EasyDialog dialog = null;
		if (enable) {
			dialog = new EasyDialog(new EasyDialog.Builder(context, theme)
					.setCancelable(true).setIndeterminateProgress(message));
		}
		return dialog;
	}

	public static EasyDialog getCircularDialog(Context context, boolean enableCancel) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View contentView = inflater.inflate(R.layout.dialog_circular, null);
		EasyDialog dialog = new EasyDialog(
				new EasyDialog.Builder(context)
						.setView(contentView).setBackground(R.color.transparent).setCancelable(enableCancel));
		if (dialog != null) {
			dialog.show();
			AVLoadingIndicatorView mProgress = (AVLoadingIndicatorView) contentView.findViewById(R.id.progress_dialog_circular);
			mProgress.show();
		}
		return dialog;
	}

	public static EasyDialog getCircularDialog(Context context, boolean enableCancel, boolean isShow) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View contentView = inflater.inflate(R.layout.dialog_circular, null);
		EasyDialog dialog = new EasyDialog(
				new EasyDialog.Builder(context)
						.setView(contentView).setBackground(R.color.transparent).setCancelable(enableCancel));
		AVLoadingIndicatorView mProgress = (AVLoadingIndicatorView) contentView.findViewById(R.id.progress_dialog_circular);
		mProgress.show();
		if (dialog != null && isShow) {
			dialog.show();
		}
		return dialog;
	}

	/**
	 * @Method @param context
	 * @Method @param theme
	 * @Method @param title
	 * @Method @param message
	 * @Method @param dialogType 窗口类型 0：跑跑动画 1：原生EasyDialog
	 * @Method @return :
	 */
	public static EasyDialog showDialog(Context context, int theme,
                                        String message, int dialogType) {
		EasyDialog dialog = null;
		if (dialogType == 0) {
			dialog = getAnimDialog(context, message,true);
			dialog.show();
		} else if (dialogType == 1) {
			dialog = getOriginalDialog(context, theme, message);
			dialog.show();
		}
		return dialog;
	}
}
