package com.bob.android.clgl.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.widget.Toast;


import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AppInstallUtils {
	private static String ASSETS_ROOT_PATH = "file:///android_asset/";
	private Context mContext;
	PackageManager packageManager = null;
	private EasyDialog dialog;
	private static AppInstallUtils instance;

	public static AppInstallUtils getInstance(Context context) {
		if (instance == null) {
			instance = new AppInstallUtils(context);
		}
		return instance;
	}

	public AppInstallUtils(Context context) {
		this.mContext = context;
		packageManager = context.getPackageManager();
		instance=this;
	}

	public void verify(String fileName, String packageName, String msg, boolean showDialog) {
		new AppInstallTask(msg,showDialog).execute(fileName, packageName);
		// installApp(fileName,packageName);
	}

	public void installApp(String fileName, String packageName) {
		if (isAppInstalled(packageName)) {
			int oldVersion = getInstalledPackageVersion(packageName);
			int newVersion = getAssertsPackageVerson(ASSETS_ROOT_PATH
					+ fileName);
			if (newVersion > oldVersion) {
				installApp(fileName);
			}
		} else {
			installApp(fileName);
		}
	}

	private void installApp(String fileName) {
		String targetFileName = AppConfig.getLocalDefaultPath().concat("/")
				.concat(fileName);
		copyApkFromAssets(mContext, fileName, targetFileName);
//		PackageUtils.installNormal(mContext, targetFileName);
	}

	/**
	 * @Method @param packageName
	 * @Method @return :判断应用是否安装
	 */
	public boolean isAppInstalled(String packageName) {
		try {
			PackageInfo info = packageManager.getPackageInfo(packageName, 0);
			if (info != null) {
				return true;
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean versionUpdated(String fileName, String packageName, int versionConfigId) {
		int oldVersion = getInstalledPackageVersion(packageName);
		// int newVersion = getAssertsPackageVerson(ASSETS_ROOT_PATH
		// + fileName);
		int newVersion = mContext.getResources().getInteger(versionConfigId);
		return newVersion > oldVersion;
	}

	/**
	 * @Method @param filePath
	 * @Method @return :获取未安装应用版本
	 */
	private int getAssertsPackageVerson(String filePath) {
		PackageManager packageManager = mContext.getPackageManager();
		PackageInfo info = packageManager.getPackageArchiveInfo(filePath, 0);
		if (info != null) {
			return info.versionCode;
		} else {
			return 0;
		}
	}

	/**
	 * @Method @param packageName
	 * @Method @return :获取已安装应用版本
	 */
	private int getInstalledPackageVersion(String packageName) {
		try {
			PackageInfo info = packageManager.getPackageInfo(packageName, 0);
			if (info != null) {
				return info.versionCode;
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	private long getAssetsFileLength(String fileName) {
		long length = 0;
		InputStream in = null;
		try {
			in = mContext.getAssets().open(fileName);
			int i = 0;
			byte[] temp = new byte[1024];
			while ((i = in.read(temp)) > 0) {
				length += i;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return length;
	}

	public class AppInstallTask extends AsyncTask<String, Integer, Integer> {
		private String message;
		private boolean showDialog;
		public AppInstallTask(String msg, boolean showDialog){
			this.message=msg;
			this.showDialog=showDialog;
		}
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			String fileName = params[0];
			try {
//				long freeSize = SystemUtils.getSDCardFreeSize();
				long fileSize = getAssetsFileLength(fileName);
				fileSize = fileSize / 1024 / 1024;
				/*if (freeSize > (fileSize + 2)) {
					installApp(fileName);
					return 1;
				} else {*/
					return 0;
//				}
			} catch (Exception e) {
				return -1;
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if(showDialog){
				dialog = DialogManager.getCircularDialog(mContext, false);
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if(showDialog){
				if (dialog != null) {
					dialog.dismiss();
				}
			}
			if (result == 0) {
				Toast.makeText(mContext, "手机存储空间不足，无法安装", Toast.LENGTH_SHORT)
						.show();
			} else if (result == -1) {
				Toast.makeText(mContext, "安装失败", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	public boolean copyApkFromAssets(Context context, String fileName,
                                     String path) {
		boolean copyIsFinish = false;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = context.getAssets().open(fileName);
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			copyIsFinish = true;
		}
		return copyIsFinish;
	}

}
