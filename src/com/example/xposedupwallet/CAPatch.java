package com.example.xposedupwallet;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSession;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class CAPatch implements IXposedHookLoadPackage {

	/**
	 * 包加载时候的回调closeCustomKeyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		// 将包名不是 com.example.login 的应用剔除掉
		// XposedBridge.log("Load package before");
		// if (!lpparam.packageName.equals("com.unionpay.uppay")) {
		if (!lpparam.packageName.equals("com.unionpay")) {
			// XposedBridge.log("Loaded return: " + lpparam.packageName);
			return;
		}

		String[] classNames = new String[] { "com.tendcloud.tenddata.bt", "com.unionpay.mobile.android.net.b",
				"com.unionpay.mpay.net.b", "com.unionpay.network.d", "com.unionpay.sdk.bf", "com.unionpay.tsm.io.c",
				"com.unionpay.mobile.android.net.b", "com.tencent.open.utils.i", "com.amap.api.services.core.k$b",
				"com.huawei.android.pushagent.c.c.i", "com.huawei.android.pushagent.plugin.tools.b.c" };
		XposedBridge.log("Loaded app right1: " + lpparam.packageName);
		for (int i = 0; i < classNames.length; i++) {
			XposedHelpers.findAndHookMethod(classNames[i], lpparam.classLoader, "checkServerTrusted",
					X509Certificate[].class, String.class, new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							Log.i("MyTest", "checkServerTrusted() return");
							param.setResult(null);
						}

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {

						}
					});

		}
		String[] classNames02 = new String[] { "com.huawei.android.pushagent.c.c.k",
				"com.huawei.android.pushagent.plugin.tools.b.b", "com.squareup.okhttp.internal.tls.b",
				"com.unionpay.sdk.ap" };
		XposedBridge.log("Loaded app right: " + lpparam.packageName);
		for (int i = 0; i < classNames02.length; i++) {
			XposedHelpers.findAndHookMethod(classNames02[i], lpparam.classLoader, "verify", String.class,
					SSLSession.class, new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							// Log.i("MyTest01", "CA return");
							param.setResult(true);
						}

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {

						}
					});
		}
	}
}
