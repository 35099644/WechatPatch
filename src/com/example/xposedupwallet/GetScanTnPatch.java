package com.example.xposedupwallet;

import android.content.Intent;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class GetScanTnPatch implements IXposedHookLoadPackage {

	/**
	 * 包加载时候的回调closeCustomKeyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam)
			throws Throwable {

		// 将包名不是 com.example.login 的应用剔除掉
		// XposedBridge.log("Load package before");
		// if (!lpparam.packageName.equals("com.unionpay.uppay")) {
		if (!lpparam.packageName.equals("com.unionpay")) {
			// XposedBridge.log("Loaded return: " + lpparam.packageName);
			return;
		}
		XposedBridge.log("Loaded app right: " + lpparam.packageName);

		XposedHelpers.findAndHookMethod(
				"com.unionpay.activity.selection.UPActivitySelection",
				lpparam.classLoader, "onActivityResult", int.class, int.class,
				Intent.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						Intent parm1 = (Intent) param.args[0];
						Log.i(parm1 + "UPActivitySelection result tn:",
								parm1.getStringExtra("tn"));
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});

		XposedHelpers.findAndHookMethod(
				"com.unionpay.base.UPActivityPayPlugin", lpparam.classLoader,
				"l", String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						Log.i("MyTest", "UPActivityPayPlugin l param:" + parm1);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});

		XposedHelpers.findAndHookMethod(
				"com.unionpay.base.UPActivityPayPlugin", lpparam.classLoader,
				"a", String.class, String.class, String.class, boolean.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						String parm2 = (String) param.args[1];
						String parm3 = (String) param.args[2];
						boolean parm4 = (Boolean) param.args[3];
						Log.i("MyTest", "UPActivityPayPlugin a param1:" + parm1
								+ " param2:" + parm2 + " param3:" + parm3
								+ " param4:" + parm4);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
		XposedHelpers.findAndHookMethod(
				"com.unionpay.base.UPActivityPayPlugin", lpparam.classLoader,
				"b", String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						Log.i("MyTest", "UPActivityPayPlugin b param1:" + parm1);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
		XposedHelpers.findAndHookMethod(
				"com.unionpay.base.UPActivityPayPlugin", lpparam.classLoader,
				"e", String.class, String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						String parm2 = (String) param.args[1];
						Log.i("MyTest", "UPActivityPayPlugin e param1:" + parm1
								+ " param2:" + parm2);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
	}
}
