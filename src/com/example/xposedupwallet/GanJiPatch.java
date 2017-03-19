package com.example.xposedupwallet;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSession;

import android.app.Application;
import android.content.Context;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class GanJiPatch implements IXposedHookLoadPackage {

	/**
	 * 包加载时候的回调closeCustomKeyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		// 将包名不是 com.unionpay 的应用剔除掉
		if (!lpparam.packageName.equals("com.ganji.android")) {
			return;
		}

		XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				super.afterHookedMethod(param);
				Log.i("MyTest", "Application attach");
				wechatHook01(lpparam.classLoader);
			}

		});

	}

	public static void wechatHook01(ClassLoader classLoader) {
		String[] classNames = new String[] { "com.common.gmacs.downloader.HTTPSTrustManager",
				"com.sina.weibo.sdk.net.e$a", "com.tencent.open.d.e.c", "com.wuba.wbpush.c.e", "com.wuba.wmdalite.e.e",
				"com.wuba.wrtc.util.d", "com.wuba.wrtc.al", "com.yintong.secure.b.d" };
		for (int i = 0; i < classNames.length; i++) {
			XposedHelpers.findAndHookMethod(classNames[i], classLoader, "checkServerTrusted", X509Certificate[].class,
					String.class, new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							Log.i("MyTest", "checkServerTrusted() return");
							param.setResult(null);
						}
					});
		}

		String[] classNames02 = new String[] { "com.common.gmacs.downloader.HTTPSTrustManager$1",
				"com.squareup.okhttp.internal.tls.OkHostnameVerifier" };
		for (int i = 0; i < classNames02.length; i++) {
			XposedHelpers.findAndHookMethod(classNames02[i], classLoader, "verify", String.class, SSLSession.class,
					new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							// Log.i("MyTest01", "CA return");
							param.setResult(true);
						}
					});
		}

		String[] classNames03 = new String[] { "android.webkit.WebViewClient" };
		for (int i = 0; i < classNames03.length; i++) {
			XposedHelpers.findAndHookMethod(classNames03[i], classLoader, "onReceivedSslError", WebView.class,
					SslErrorHandler.class, SslError.class, new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							Log.i("MyTest", "onReceivedSslError() return");
							SslErrorHandler handler = (SslErrorHandler) param.args[1];
							handler.proceed();
							param.setResult(null);
						}
					});
		}

		XposedHelpers.findAndHookMethod("com.common.gmacs.utils.GLog", classLoader, "d", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest05", param.args[0] + "   :   " + param.args[1]);
					}
				});

		XposedHelpers.findAndHookMethod("com.common.gmacs.utils.GLog", classLoader, "d", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest05", param.args[0] + "   :   " + param.args[1]);
					}
				});

		Class msgClass = XposedHelpers.findClass("com.wuba.wchat.api.Define$Msg", classLoader);

		XposedHelpers.findAndHookMethod("com.common.gmacs.parse.message.Message", classLoader, "buildMessage", msgClass,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						super.afterHookedMethod(param);
						Log.i("MyTest05", "buildMessage:" + param.getResult());

					}
				});
	}
}
