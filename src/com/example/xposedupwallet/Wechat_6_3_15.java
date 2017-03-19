package com.example.xposedupwallet;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Wechat_6_3_15 implements IXposedHookLoadPackage {

	/**
	 * 包加载时候的回调closeCustomKeyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		// 将包名不是 com.example.login 的应用剔除掉
		// XposedBridge.log("Load package before");
		// if (!lpparam.packageName.equals("com.unionpay.uppay")) {
		if (!lpparam.packageName.equals("com.tencent.mm")) {
			return;
		}
		Log.i("MyTest", "load app:     " + lpparam.packageName);
		XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				super.afterHookedMethod(param);
				Log.i("MyTest", "Application attach");
				openLog(lpparam.classLoader);

			}

		});
	}

	private static void openLog(final ClassLoader classLoader) {
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.u", classLoader, "i", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "i-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.u", classLoader, "v", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "v-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.u", classLoader, "w", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "w-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.u", classLoader, "d", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "d-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.u", classLoader, "e", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "e-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.u", classLoader, "f", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "f-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		// (Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
		XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.luckymoney.c.u", classLoader, String.class,
				int.class, int.class, String.class, String.class, String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						int num = 6;
						for (int i = 0; i < num; i++) {
							// Log.i("MyTest02", "conStru param " + i + " : " +
							// param.args[i]);
						}
					}
				});
		// s(Ljava/lang/String;ILjava/lang/String;JLjava/lang/String;Ljava/lang/String;)V
		XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.luckymoney.c.u", classLoader, String.class,
				int.class, String.class, long.class, String.class, String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						int num = 6;
						for (int i = 0; i < num; i++) {
							// Log.i("MyTest02", "conStru param " + i + " : " +
							// param.args[i]);
						}
					}
				});
		// s(Ljava/lang/String;ILjava/lang/String;JLjava/lang/String;Ljava/lang/String;)V
		XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.luckymoney.c.u", classLoader, String.class,
				String.class, String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						int num = 3;
						for (int i = 0; i < num; i++) {
							// Log.i("MyTest02", "conStru param " + i + " : " +
							// param.args[i]);
						}
					}
				});
		// com/tencent/mm/r/m;->d(Lcom/tencent/mm/r/j;)Z
		XposedHelpers.findAndHookMethod("com.tencent.mm.r.m", classLoader, "d",
				XposedHelpers.findClass("com.tencent.mm.r.j", classLoader), new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("MyTest02", "------------------> r.d()");
						Object temp = param.args[0];
						Log.i("MyTest02", "------------------> ----------"+temp.getClass().getName());
					}
				});
		// com/tencent/mm/r/m;->d(Lcom/tencent/mm/r/j;)Z
		XposedHelpers.findAndHookMethod("com.tencent.mm.r.m", classLoader, "c",
				XposedHelpers.findClass("com.tencent.mm.r.j", classLoader), new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("MyTest02", "------------------> r.c()");
					}
				});

		// s(Ljava/lang/String;ILjava/lang/String;JLjava/lang/String;Ljava/lang/String;)V
		XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.chatroom.a.a", classLoader, String.class,
				List.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						int num = 2;
						for (int i = 0; i < num; i++) {
							Log.i("MyTest02", "a.a() param " + i + "  : " + param.args[i]);
						}
					}
				});
	}

}
