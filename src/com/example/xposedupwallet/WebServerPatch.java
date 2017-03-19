package com.example.xposedupwallet;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLSession;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class WebServerPatch implements IXposedHookLoadPackage {

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

		// 将包名不是 com.unionpay 的应用剔除掉
		if (!lpparam.packageName.equals("com.unionpay")) {
			return;
		}

		// hook IJniInterface.encryptPwdOnce(String)函数
		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "encryptPwdOnce",
				String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						String paramString = (String) param.args[0];
						String newSessionKey = (String) param.getResult();
						Log.i("MyTest", "encryptPwdOnce   param:" + paramString + "    result:" + newSessionKey);
					}
				});

		String[] classNames = new String[] { "com.tendcloud.tenddata.bt", "com.unionpay.mobile.android.net.b",
				"com.unionpay.mpay.net.b", "com.unionpay.network.d", "com.unionpay.sdk.am", "com.unionpay.tsm.io.c",
				"com.tencent.open.utils.i", "com.amap.api.services.core.k$b", "com.huawei.android.pushagent.c.c.i",
				"com.huawei.android.pushagent.plugin.tools.b.c" };
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

		String[] classNames5 = new String[] { "com.unionpay.mobile.android.net.b", "com.unionpay.mpay.net.b",
				"com.unionpay.network.d", "com.unionpay.sdk.am", "com.unionpay.tsm.io.c" };
		XposedBridge.log("Loaded app right1: " + lpparam.packageName);
		for (int i = 0; i < classNames5.length; i++) {
			XposedHelpers.findAndHookMethod(classNames5[i], lpparam.classLoader, "checkClientTrusted",
					X509Certificate[].class, String.class, new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							Log.i("MyTest", "checkClientTrusted() return");
							param.setResult(null);
						}

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {

						}
					});

		}

		XposedBridge.log("Loaded app right2: " + lpparam.packageName);
		XposedHelpers.findAndHookMethod("com.tencent.tauth.AuthActivity", lpparam.classLoader, "onCreate", Bundle.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "AuthActivity onCreate");
						Activity activity = (Activity) param.thisObject;

						String myDataString = activity.getIntent().getStringExtra("myData");
						Class<?> cls = lpparam.classLoader.loadClass("com.unionpay.utils.IJniInterface");
						XposedHelpers.callStaticMethod(cls, "encryptMsg", new Class[] { String.class }, myDataString);

					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {

					}
				});

		XposedBridge.log("Loaded app right: " + lpparam.packageName);
		XposedHelpers.findAndHookMethod("com.unionpay.share.weibo.UPWeiboActivity", lpparam.classLoader, "onCreate",
				Bundle.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						// Log.i("MyTest01", "CA return");
						Activity activity = (Activity) param.thisObject;

						String myDataString = activity.getIntent().getStringExtra("myData");
						Class<?> cls = lpparam.classLoader.loadClass("com.unionpay.utils.IJniInterface");
						String result = (String) XposedHelpers.callStaticMethod(cls, "decryptMsg",
								new Class[] { String.class }, myDataString);
						Log.i("MyTest", "decrypt invoke result:" + result);

					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {

					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "encryptMsg",
				String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {

						String paramString = (String) param.args[0];
						String result = (String) param.getResult();
						Log.i("MyTest", "IJniInterface.encrypteMsg() param:" + paramString + "  result:" + result);
						if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
							try {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(new FileOutputStream("/sdcard/aaa.txt", true)));
								String splitor = getSplitor(paramString, result);
								String content = splitor + paramString + splitor + result + "\n";
								out.write(content);
								out.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "decryptMsg",
				String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {

						String paramString = (String) param.args[0];
						String result = (String) param.getResult();
						Log.i("MyTest", "IJniInterface.decryptMsg() param:" + paramString + "  result:" + result);
						if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
							try {
								BufferedWriter out = new BufferedWriter(
										new OutputStreamWriter(new FileOutputStream("/sdcard/aaa.txt", true)));
								String splitor = getSplitor(paramString, result);
								String content = splitor + result + splitor + paramString + "\n";
								out.write(content);
								out.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "encryptPwdOnce",
				String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						String paramString = (String) param.args[0];
						String newSessionKey = (String) param.getResult();
						Log.i("MyTest", "encryptPwdOnce   param:" + paramString + "    result:" + newSessionKey);
					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "encryptPwd",
				String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						String paramString = (String) param.args[0];
						String newSessionKey = (String) param.getResult();
						Log.i("MyTest", "encryptPwd   param:" + paramString + "    result:" + newSessionKey);
					}
				});

		String[] classNames01 = new String[] { "com.unionpay.network.model.req.UPBillCardListReqParam",
				"com.unionpay.network.model.req.UPInstiAtReqParam",
				"com.unionpay.network.model.req.UPMyBillDeleteReqParam",
				"com.unionpay.network.model.req.UPMyBillListReqParam",
				"com.unionpay.network.model.req.UPMyPointsDetailReqParam" };
		XposedBridge.log("Loaded app right: " + lpparam.packageName);
		for (int i = 0; i < classNames01.length; i++) {
			XposedHelpers.findAndHookMethod(classNames01[i], lpparam.classLoader, "setCdhdUsrId", String.class,
					new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							// Log.i("MyTest01", "CA return");
							Log.i("MyTest", "set cdhdusrid:" + param.args[0]);
						}

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {

						}
					});
		}

		String[] classNames02 = new String[] { "com.huawei.android.pushagent.c.c.k",
				"com.huawei.android.pushagent.plugin.tools.b.b", "com.squareup.okhttp.internal.tls.b",
				"com.unionpay.sdk.x" };
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
		XposedHelpers.findAndHookMethod("com.unionpay.network.model.req.UPInstiReqParam", lpparam.classLoader,
				"setCdhdUsrId", String.class, String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						// Log.i("MyTest01", "CA return");
						Log.i("MyTest", "set cdhdusrid upinstireqparam:" + param.args[0] + "  param:" + param.args[1]);
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {

					}
				});

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

		XposedHelpers.findAndHookMethod("com.unionpay.mpay.widgets.UPPinWidget", lpparam.classLoader, "encryptPwdOnce",
				int.class, String.class, new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "UPPinWidget.encryptPwdOnce() para0:" + param.args[0] + "  param1:"
								+ param.args[1] + "  result:" + param.getResult());

					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.mpay.widgets.UPPinWidget", lpparam.classLoader, "b",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "UPPinWidget.b() result:" + param.getResult());

					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "getPin",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "encryptPwd  result:" + param.getResult());
					}
				});
		XposedHelpers.findAndHookMethod("com.unionpay.utils.IJniInterface", lpparam.classLoader, "getnewPin",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "encryptPwd  result:" + param.getResult());
					}
				});
		XposedHelpers.findAndHookMethod("com.unionpay.mpay.widgets.UPPinWidget", lpparam.classLoader, "a", int.class,
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "UPPinWidget.encryptPwdOnce()  param0:" + param.args[0]);

					}
				});
		XposedHelpers.findAndHookMethod("com.unionpay.widget.UPEditTextPayword", lpparam.classLoader, "b",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "UPEditTextPayword.b()  result:" + param.getResult());

					}
				});

		XposedHelpers.findAndHookMethod("com.unionpay.widget.UPEditText", lpparam.classLoader, "f",
				new XC_MethodHook() {

					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					}

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						Log.i("MyTest", "UPEditText.f()  result:" + param.getResult());

					}
				});
	}
}
