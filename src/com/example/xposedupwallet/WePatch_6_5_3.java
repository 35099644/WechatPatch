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

public class WePatch_6_5_3 implements IXposedHookLoadPackage {

	/**
	 * ������ʱ��Ļص�closeCustomKeyboard() +++
	 */
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

		// ���������� com.example.login ��Ӧ���޳���
		// XposedBridge.log("Load package before");
		// if (!lpparam.packageName.equals("com.unionpay.uppay")) {
		if (!lpparam.packageName.equals("com.tencent.mm")) {
			return;
		}
		Log.i("MyTest", "load app:     " + lpparam.packageName);

		try {
			XposedHelpers.findAndHookMethod("com.tencent.mm.e.b.bx", lpparam.classLoader, "b", Cursor.class,
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
							if (v3.equals("weixin") && v1.equals("��ӭ���ٴλص�΢�š��������ʹ�ù��������κε�������飬�ǵø��ҷ��ŷ���Ŷ��")) {
								Log.i("MyTest", "equalllllllllllllllllllllllllllllll");
								XposedHelpers.setObjectField(arg15.thisObject, "field_content",
										"����ǰ���е���΢��6.5.3���ش���汾���������Զ���������ܣ�ʹ�ñ����ʱ����������Լ����\n1.���ش���汾��ȫ��ѣ��Ͻ������κ���ҵ��Ϊ��\n2.ʹ�ñ��汾����κκ���Լ��������ŵȣ�\n3.�����ж�apk����ʵ������֤����ֹ���δ�����������롣\n\nף���������ÿ��ģ����ÿ���~\n\n    ��made by �᳤��");
							}

							boolean boolean1 = (Boolean) (XposedHelpers
									.callStaticMethod(XposedHelpers.findClass(e.c, lpparam.classLoader), e.d, v3));

							Object tempObj = XposedHelpers.callStaticMethod(
									XposedHelpers.findClass(e.f, lpparam.classLoader), "b",
									new Object[] { arg15.thisObject });

							boolean boolean2 = (Boolean) (XposedHelpers.callStaticMethod(
									XposedHelpers.findClass(e.e, lpparam.classLoader), "a",
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
								String v0 = b(v1);
								Log.i("MyTest", "b(v1):" + v0);
								Uri v1_1 = Uri.parse(v0);
								Object objectCaller = XposedHelpers.callStaticMethod(
										XposedHelpers.findClass(e.h, lpparam.classLoader), e.i, new Object[0]);
								Object para1 = XposedHelpers.newInstance(
										XposedHelpers.findClass(e.g, lpparam.classLoader),
										new Object[] { Integer.valueOf(v1_1.getQueryParameter("msgtype")),
												Integer.valueOf(v1_1.getQueryParameter("channelid")),
												v1_1.getQueryParameter("sendid"), v0, "", "", v3, "v1.0" });
								XposedHelpers.callMethod(objectCaller, "d", new Object[] { para1 });
							}

						}
					});
		} catch (Exception e) {
			Log.i("MyTest", "exception:" + e.getMessage());
			e.printStackTrace();
		}
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
}
