package com.example.xposedupwallet;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WechatPatch02 {
	/*
	static XC_MethodHook.Unhook a = null;
	
	static ClassLoader classLoader = null;

	public static void a(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) {
		if (!paramLoadPackageParam.packageName.equals("com.tencent.mm")) {
			return;
		}
		classLoader = paramLoadPackageParam.classLoader;

		a = XposedHelpers.findAndHookMethod(e.a, paramLoadPackageParam.classLoader, e.b,
				new Object[] { Cursor.class, new XC_MethodHook() {
					protected void afterHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam) {
						boolean l_g_self = true;
						int i;
						String str1;
						int j;
						int k;
						do {
							do {
								do {
									return;
									String str2 = XposedHelpers
											.getObjectField(paramAnonymousMethodHookParam.thisObject, "field_content")
											.toString();
									i = ((Integer) XposedHelpers
											.getObjectField(paramAnonymousMethodHookParam.thisObject, "field_type"))
													.intValue();
									str1 = XposedHelpers
											.getObjectField(paramAnonymousMethodHookParam.thisObject, "field_talker")
											.toString();
									j = ((Integer) XposedHelpers
											.getObjectField(paramAnonymousMethodHookParam.thisObject, "field_status"))
													.intValue();
									k = ((Integer) XposedHelpers
											.getObjectField(paramAnonymousMethodHookParam.thisObject, "field_isSend"))
													.intValue();
									boolean l_h = true;
									if (!l_h) {
										break;
									}
								} while (((Boolean) XposedHelpers.callStaticMethod(
										XposedHelpers.findClass(e.c, classLoader), e.d, new Object[] { str1 }))
												.booleanValue());
							} while (!((Boolean) XposedHelpers.callStaticMethod(
									XposedHelpers.findClass(e.e, classLoader), "a",
									new Object[] { str1, paramAnonymousMethodHookParam.thisObject,
											XposedHelpers.callStaticMethod(
													XposedHelpers.findClass(e.f, this.a.classLoader), "b",
													new Object[] { paramAnonymousMethodHookParam.thisObject }),
											Boolean.valueOf(false) })).booleanValue());
							
						} while ((j == 4) || ((l_g_self) && (k != 0)) || ((i != 436207665) && (i != 469762097)));
						paramAnonymousMethodHookParam = d.a(str2);
						Object localObject = Uri.parse(paramAnonymousMethodHookParam);
						String str2 = ((Uri) localObject).getQueryParameter("msgtype");
						String str3 = ((Uri) localObject).getQueryParameter("sendid");
						localObject = ((Uri) localObject).getQueryParameter("channelid");
						paramAnonymousMethodHookParam = (MethodHookParam)XposedHelpers.newInstance(
								XposedHelpers.findClass(e.g, classLoader),
								new Object[] { Integer.valueOf(str2), Integer.valueOf((String) localObject), str3,
										paramAnonymousMethodHookParam, "", "", str1, "v1.0" });
						XposedHelpers
								.callMethod(
										XposedHelpers.callStaticMethod(XposedHelpers.findClass(e.h, this.a.classLoader),
												e.i, new Object[0]),
										"d", new Object[] { paramAnonymousMethodHookParam });
					}
				} });
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
	*/
}
