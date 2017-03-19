package com.example.xposedupwallet;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class WebViewPatch implements IXposedHookLoadPackage {

	public static String getSplitor(String arg1, String arg2) {
		String[] splitors = new String[] { "@", "#", "_", "-" };
		for (int i = 0; i < splitors.length; i++) {
			if (!arg1.contains(splitors[i]) && !arg2.contains(splitors[i])) {
				return splitors[i];
			}
		}
		return "@";
	}

	/**
	 * 包加载时候的回调closeCustomKeyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		// 将包名不是 com.example.login 的应用剔除掉
		// XposedBridge.log("Load package before");
		// if (!lpparam.packageName.equals("com.unionpay.uppay")) {
		if (!lpparam.packageName.equals("com.unionpay")) {
			return;
		}

		XposedHelpers.findAndHookMethod("android.webkit.WebViewClient", lpparam.classLoader, "onReceivedSslError",
				WebView.class, SslErrorHandler.class, SslError.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "onReceivedSslError() return");
						SslErrorHandler handler = (SslErrorHandler) param.args[1];
						handler.proceed();
						param.setResult(null);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
					}
				});
	}
}
