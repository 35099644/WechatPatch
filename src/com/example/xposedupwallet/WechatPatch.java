package com.example.xposedupwallet;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.database.Cursor;
import android.net.Uri;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WechatPatch {
	/*
	static XC_MethodHook.Unhook a;

	static {
		d.a = null;
	}

	static String a(String arg1) {
		return d.b(arg1);
	}

	public static void a(XC_LoadPackage.LoadPackageParam arg7) {
        int v3 = 2;
        if(arg7.packageName.equals("com.tencent.mm")) {
          

            String v0_1 = e.a;
            ClassLoader v1 = arg7.classLoader;
            String v2 = e.b;
            Object[] v3_1 = new Object[v3];
            v3_1[0] = Cursor.class;
            v3_1[1] = new XC_MethodHook() {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam arg15) {
                        String v1 = XposedHelpers.getObjectField(arg15.thisObject, "field_content").
                                toString();
                        int v2 = ((Integer)XposedHelpers.getObjectField(arg15.thisObject, "field_type")).intValue();
                        String v3 = XposedHelpers.getObjectField(arg15.thisObject, "field_talker").toString();
                        int v4 =((Integer) XposedHelpers.getObjectField(arg15.thisObject, "field_status")).intValue();
                        int v5 = ((Integer)XposedHelpers.getObjectField(arg15.thisObject, "field_isSend")).intValue();
                        if(l.h()) {
                            if(XposedHelpers.callStaticMethod(XposedHelpers.findClass(e.c, this.a.classLoader), 
                                    e.d, new Object[]{v3}).booleanValue()) {
                            }
                            else if(XposedHelpers.callStaticMethod(XposedHelpers.findClass(e.e, this
                                    .a.classLoader), "a", new Object[]{v3, arg15.thisObject, XposedHelpers
                                    .callStaticMethod(XposedHelpers.findClass(e.f, this.a.classLoader), 
                                    "b", new Object[]{arg15.thisObject}), Boolean.valueOf(false)}).booleanValue()
                                    ) {
                                goto label_70;
                            }

                            return;
                        }

                    label_70:
                        if(v4 == 4) {
                            return;
                        }

                        if((l.g()) && v5 != 0) {
                            return;
                        }

                        if(v2 != 436207665 && v2 != 469762097) {
                            return;
                        }

                        String v0 = d.a(v1);
                        Uri v1_1 = Uri.parse(v0);
                        XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass(
                                e.h, this.a.classLoader), e.i, new Object[0]), "d", new Object[]{XposedHelpers
                                .newInstance(XposedHelpers.findClass(e.g, this.a.classLoader), new Object
                                []{Integer.valueOf(v1_1.getQueryParameter("msgtype")), Integer.valueOf(
                                v1_1.getQueryParameter("channelid")), v1_1.getQueryParameter("sendid"), 
                                v0, "", "", v3, "v1.0"})});
                    }
            };
            d.a = XposedHelpers.findAndHookMethod(v0_1, v1, v2, v3_1);
        }
    }

	private static String b(String arg5) {
		String v1 = arg5.substring(arg5.indexOf("<msg>"));
		String v0 = "";
		try {
			XmlPullParserFactory v2 = XmlPullParserFactory.newInstance();
			v2.setNamespaceAware(true);
			XmlPullParser v2_1 = v2.newPullParser();
			v2_1.setInput(new StringReader(v1));
			int v1_2;
			for (v1_2 = v2_1.getEventType(); v1_2 != 1; v1_2 = v2_1.next()) {
				if (v1_2 == 2 && (v2_1.getName().equals("nativeurl"))) {
					v2_1.nextToken();
					return v2_1.getText();
				}
			}

			return v0;
		} catch (Exception v1_1) {
			v1_1.printStackTrace();
			return v0;
		}
	}
	*/
}
