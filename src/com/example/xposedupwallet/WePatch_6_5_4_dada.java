package com.example.xposedupwallet;

import java.io.StringReader;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class WePatch_6_5_4_dada implements IXposedHookLoadPackage {

	private static String v_1;
	private static String v_3;

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
				wechatHook01(lpparam.classLoader);
				wechatReceiveTimingIdentifierHook(lpparam.classLoader);
				luckymoneyDetail(lpparam.classLoader);
				openLog(lpparam.classLoader);
				luckymoneyDetailMore(lpparam.classLoader);

			}

		});
	}

	private static void wechatHook01(final ClassLoader classLoader) {
		try {
			XposedHelpers.findAndHookMethod("com.tencent.mm.e.b.by", classLoader, "b", Cursor.class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							String v1 = XposedHelpers.getObjectField(arg15.thisObject, "field_content").toString();
							int v2 = ((Integer) XposedHelpers.getObjectField(arg15.thisObject, "field_type"))
									.intValue();
							String v3 = XposedHelpers.getObjectField(arg15.thisObject, "field_talker").toString();
							int v4 = ((Integer) XposedHelpers.getObjectField(arg15.thisObject, "field_status"))
									.intValue();
							int v5 = ((Integer) XposedHelpers.getObjectField(arg15.thisObject, "field_isSend"))
									.intValue();
							Log.i("MyTest", "11111111111v1: " + v1);
							Log.i("MyTest", "11111111111v2: " + v2);
							Log.i("MyTest", "11111111111v3: " + v3);
							Log.i("MyTest", "11111111111v4: " + v4);
							Log.i("MyTest", "11111111111v5: " + v5);

							boolean boolean1 = (Boolean) (XposedHelpers.callStaticMethod(
									XposedHelpers.findClass("com.tencent.mm.sdk.platformtools.bf", classLoader), "la",
									v3));

							Object tempObj = XposedHelpers.callStaticMethod(
									XposedHelpers.findClass("com.tencent.mm.h.i", classLoader), "b",
									new Object[] { arg15.thisObject });

							boolean boolean2 = (Boolean) (XposedHelpers.callStaticMethod(
									XposedHelpers.findClass("com.tencent.mm.booter.notification.c", classLoader), "a",
									new Object[] { v3, arg15.thisObject, tempObj, Boolean.valueOf(false) }));

							Log.i("MyTest", "11111111111boolean 1: " + boolean1);
							Log.i("MyTest", "11111111111boolean 1: " + boolean2);
							if (!boolean1 && boolean2) {
								if (v4 == 4) {
									return;
								}
								if (v5 != 0) {
									return;
								}
								if (v2 != 436207665 && v2 != 469762097) {
									return;
								}
								String wxpay = b(v1);
								String sendid = getSendid(wxpay);
								Log.i("MyTest", "b(str1) : " + b(v1));
								Log.i("MyTest", "sendid : " + sendid);
								sendOpen(classLoader, wxpay, sendid);
								v_1 = v1;
								v_3 = v3;
							}

						}
					});
		} catch (Exception e) {
			Log.i("MyTest", "exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void wechatReceiveTimingIdentifierHook(final ClassLoader classLoader) {
		XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.luckymoney.c.ae", classLoader, "a", int.class,
				String.class, JSONObject.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						super.afterHookedMethod(param);
						JSONObject jsonObject = (JSONObject) param.args[2];
						String timingIdentifier = jsonObject.optString("timingIdentifier");
						Log.i("MyTest", "timingIdentifier: " + timingIdentifier);
						Log.i("MyTest", "JSONObject :" + jsonObject);

						String v0 = b(v_1);
						Log.i("MyTest", "b(v1):" + v0);
						Uri v1_1 = Uri.parse(v0);
						Object objectCaller = XposedHelpers.callStaticMethod(
								XposedHelpers.findClass("com.tencent.mm.model.ak", classLoader), "vy", new Object[0]);
						Object para1 = XposedHelpers.newInstance(
								XposedHelpers.findClass("com.tencent.mm.plugin.luckymoney.c.ab", classLoader),
								new Object[] { Integer.valueOf(v1_1.getQueryParameter("msgtype")),
										Integer.valueOf(v1_1.getQueryParameter("channelid")),
										v1_1.getQueryParameter("sendid"), v0, "", "", v_3, "v1.0", timingIdentifier });
						// XposedHelpers.callMethod(objectCaller, "d", new
						// Object[] { para1 });

					}

				});
	}

	private static void luckymoneyDetail(final ClassLoader classLoader) {
		XposedHelpers.findAndHookConstructor("com.tencent.mm.plugin.luckymoney.c.u", classLoader, String.class,
				int.class, int.class, String.class, String.class, String.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						super.afterHookedMethod(param);
						for (int i = 0; i < 6; i++) {
							Log.i("MyTest", "luckymoney.c.u construct parm " + i + ": " + param.args[i]);
						}
					}

				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI", classLoader,
				"onCreate", Bundle.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						super.afterHookedMethod(param);
						Activity activity = (Activity) param.thisObject;
						String hqi = activity.getIntent().getStringExtra("key_sendid");
						String hqk = activity.getIntent().getStringExtra("key_native_url");
						int hqh = activity.getIntent().getIntExtra("key_jump_from", 2);
						Log.i("MyTest", "LuckyMoneyDetailUI key_sendid:" + hqi + " key_native_url:" + hqk
								+ " key_jump_from:" + hqh);
					}

				});
	}

	private static void luckymoneyDetailMore(final ClassLoader classLoader) {
		XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.luckymoney.c.u", classLoader, "a", int.class,
				String.class, JSONObject.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						super.afterHookedMethod(param);

						JSONObject jsonObject = (JSONObject) param.args[2];
						Log.i("MyTest", "Detail :" + jsonObject);
					}

				});
	}

	private static void openLog(final ClassLoader classLoader) {
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.v", classLoader, "i", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "i-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.v", classLoader, "v", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "v-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.v", classLoader, "w", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "w-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.v", classLoader, "d", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "d-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.v", classLoader, "e", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "e-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.v", classLoader, "f", String.class,
				String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("Wechatlog", "f-->" + param.args[0] + "  :  " + param.args[1]);
					}
				});
	}

	private static String getSendid(String wxpay) {
		String[] tempSplits = wxpay.split("&");
		for (int i = 0; i < tempSplits.length; i++) {
			String temp = tempSplits[i];
			if (temp.startsWith("sendid=")) {
				return temp.substring(7);
			}
		}
		return null;
	}

	private static String b(String paramString) {
		paramString = paramString.substring(paramString.indexOf("<msg>"));
		try {
			Object localObject = XmlPullParserFactory.newInstance();
			((XmlPullParserFactory) localObject).setNamespaceAware(true);
			localObject = ((XmlPullParserFactory) localObject).newPullParser();
			((XmlPullParser) localObject).setInput(new StringReader(paramString));
			for (int i = ((XmlPullParser) localObject).getEventType(); i != 1; i = ((XmlPullParser) localObject)
					.next()) {
				if ((i == 2) && (((XmlPullParser) localObject).getName().equals("nativeurl"))) {
					((XmlPullParser) localObject).nextToken();
					return ((XmlPullParser) localObject).getText();
				}
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static void sendOpen(ClassLoader classLoader, String wxpy, String sendid) {
		boolean ifTest = false;
		if (ifTest) {
			Class aeClass = XposedHelpers.findClass("com.tencent.mm.plugin.luckymoney.c.ae", classLoader);
			Object aeObject = XposedHelpers.newInstance(aeClass, 1, sendid, wxpy, 1, "v1.0");

			Object tempObject = XposedHelpers
					.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.model.ak", classLoader), "vy");
			XposedHelpers.callMethod(tempObject, "a", aeObject, 0);
		} else {
			Class aeClass = XposedHelpers.findClass("com.tencent.mm.plugin.luckymoney.c.u", classLoader);
			Object aeObject = XposedHelpers.newInstance(aeClass, sendid, 11, 0, wxpy, "v1.0", "");

			Object tempObject = XposedHelpers
					.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.model.ak", classLoader), "vy");
			XposedHelpers.callMethod(tempObject, "a", aeObject, 0);
		}

	}
}
