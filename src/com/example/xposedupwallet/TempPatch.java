package com.example.xposedupwallet;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class TempPatch implements IXposedHookLoadPackage {

	/**
	 * 包加载时候的回调closeCustom  Keyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		// 将包名不是 com.example.login 的应用剔除掉
		// XposedBridge.log("Load package before");
		// if (!lpparam.packageName.equals("com.unionpay.uppay")) {
		if (!lpparam.packageName.equals("com.tencent.mm")) {
			return;
		}
		Log.i("MyTest02", "load app:     " + lpparam.packageName);

		try {
			XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.luckymoney.c.ab", lpparam.classLoader,
					int.class, int.class, String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam arg15) throws Throwable {
							for (int i = 0; i < 9; i++) {
								Log.i("MyTest02", "param " + i + ":  " + arg15.args[i]);
							}
						}
					});
		} catch (Exception e) {
			Log.i("MyTest02", "exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

}
