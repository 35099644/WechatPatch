package com.example.xposedupwallet;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class WePatch01 implements IXposedHookLoadPackage {

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
		Log.i("MyTest", "load app:  " + lpparam.packageName);

		try {
			XposedHelpers.findAndHookMethod("com.liuxu.wechat.RedEnvelope", lpparam.classLoader, "go", Object.class,
					new XC_MethodHook() {

						@Override
						protected void beforeHookedMethod(MethodHookParam arg15) throws Throwable {
							if(arg15.args[0]==null){
								Log.i("MyTest01", "xposed null");
								return;
							}
							String v1 = XposedHelpers.getObjectField(arg15.args[0], "field_content").toString();
							int v2 = ((Integer) XposedHelpers.getObjectField(arg15.args[0], "field_type")).intValue();
							String v3 = XposedHelpers.getObjectField(arg15.args[0], "field_talker").toString();
							int v4 = ((Integer) XposedHelpers.getObjectField(arg15.args[0], "field_status")).intValue();
							int v5 = ((Integer) XposedHelpers.getObjectField(arg15.args[0], "field_isSend")).intValue();
							Log.i("MyTest01", "11111111111v1: " + v1);
							Log.i("MyTest01", "11111111111v2: " + v2);
							Log.i("MyTest01", "11111111111v3: " + v3);
							Log.i("MyTest01", "11111111111v4: " + v4);
							Log.i("MyTest01", "11111111111v5: " + v5);

							boolean boolean1 = (Boolean) (XposedHelpers
									.callStaticMethod(XposedHelpers.findClass(e.c, lpparam.classLoader), e.d, v3));

							Object tempObj = XposedHelpers.callStaticMethod(
									XposedHelpers.findClass(e.f, lpparam.classLoader), "b",
									new Object[] { arg15.args[0] });

							boolean boolean2 = (Boolean) (XposedHelpers.callStaticMethod(
									XposedHelpers.findClass(e.e, lpparam.classLoader), "a",
									new Object[] { v3, arg15.args[0], tempObj, Boolean.valueOf(false) }));

							Log.i("MyTest01", "11111111111boolean 1: " + boolean1);
							Log.i("MyTest01", "11111111111boolean 1: " + boolean2);

						}
					});
		} catch (Exception e) {
			Log.i("MyTest", "exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

}
