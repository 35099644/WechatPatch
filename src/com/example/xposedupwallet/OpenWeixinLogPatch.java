package com.example.xposedupwallet;

import android.app.Activity;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class OpenWeixinLogPatch implements IXposedHookLoadPackage {

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

		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.c",
				lpparam.classLoader, "a", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						String parm2 = (String) param.args[1];
						Log.i(parm1 + "_MyTest", parm2);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.c",
				lpparam.classLoader, "b", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						String parm2 = (String) param.args[1];
						Log.i(parm1 + "_MyTest", parm2);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.c",
				lpparam.classLoader, "c", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						String parm2 = (String) param.args[1];
						Log.i(parm1 + "_MyTest", parm2);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.c",
				lpparam.classLoader, "d", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						String parm1 = (String) param.args[0];
						String parm2 = (String) param.args[1];
						Log.i(parm1 + "_MyTest", parm2);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {

					}
				});
	}
}
