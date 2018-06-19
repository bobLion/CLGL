package com.bob.android.clgl.util;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bob.android.clgl.R;
import com.bob.android.clgl.dialog.EasyDialog;


@SuppressLint("NewApi")
public class AnimLoadingDialog extends EasyDialog {
	/**
	 * @Field MIN_ANIM_TIME :动画最小时间
	 */
	private static final long MIN_ANIM_TIME = 1 * DateUtils.SECOND_IN_MILLIS;
	/**
	 * @Field loadingAnimation :动画图片
	 */
	private AnimationDrawable loadingAnimation;
	/**
	 * @Field startTime :动画开始时间
	 */
	private long startTime;
	/**
	 * @Field loadingTitle :进度提示内容
	 */
	private TextView loadingTitle;

	public AnimLoadingDialog(Builder builder, int animViewId) {
		super(builder);
		// TODO Auto-generated constructor stub
		RelativeLayout contentView = getDialogLayout();
		if (contentView != null) {
			loadingTitle = (TextView) contentView
					.findViewById(R.id.loading_title);
			View animView = contentView.findViewById(animViewId);

			if (animView != null) {
				Drawable drawable = animView.getBackground();
				if (drawable instanceof AnimationDrawable) {
					loadingAnimation = (AnimationDrawable) drawable;
				}
			}
		}
	}

	@Override
	public void show() {
		startTime = System.currentTimeMillis();
		super.show();
	}

	/**
	 * @Method :关闭dialog等待动画执行最小时间，次方法执行期间不可以做页面切换
	 */
	public void dismissWaitAnim() {
		new AnimTask(new AnimListener() {

			@Override
			public void onComplete() {
				dismiss();
			}
		}, startTime).execute();
	}

	/**
	 * @Method :关闭dialog等待动画执行最小时间，次方法执行期间不可以做页面切换
	 */
	public void cancelWaitAnim() {
		new AnimTask(new AnimListener() {

			@Override
			public void onComplete() {
				dismiss();
			}
		}, startTime).execute();
	}

	@Override
	public void dismiss() {
		if (loadingAnimation != null) {
			loadingAnimation.stop();
		}
		super.dismiss();
	}

	@Override
	public void cancel() {
		if (loadingAnimation != null) {
			loadingAnimation.stop();
		}
		super.cancel();

	}

	@Override
	protected void onStart() {
		if (loadingAnimation != null) {
			loadingAnimation.start();
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (loadingAnimation != null) {
			loadingAnimation.stop();
		}
		super.onStop();
	}

	/**
	 * @Method :如果查询过快，等待最小动画时间
	 */
	public void waitIfTooQuick() {
		// long endTime = System.currentTimeMillis();
		// long differ = endTime - startTime;
		// if (differ < MIN_ANIM_TIME) {
		// try {
		// Thread.sleep(MIN_ANIM_TIME - differ);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
	}

	public void setLoadingTitle(String title) {
		if (loadingTitle != null) {
			loadingTitle.setText(title);
		}
	}

	private interface AnimListener {
		public void onComplete();
	}

	private class AnimTask extends AsyncTask<Integer, Integer, Boolean> {
		private long startTime;
		private AnimListener listener;

		public AnimTask(AnimListener listener, long startTime) {
			this.listener = listener;
			this.startTime = startTime;
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			long endTime = System.currentTimeMillis();
			long differ = endTime - startTime;
			if (differ < MIN_ANIM_TIME) {
				try {
					Thread.sleep(MIN_ANIM_TIME - differ);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (listener != null) {
				listener.onComplete();
			}
		}
	}
}
