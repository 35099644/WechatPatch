package com.example.xposedupwallet;

import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * 
 * @author Xu 2017-02-28
 *
 */
public class WePatch_6_5_4_new implements IXposedHookLoadPackage {

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
				// wechatHook01(lpparam.classLoader);
				wechatHook011(lpparam.classLoader);
				// wechatReceiveTimingIdentifierHook(lpparam.classLoader);
				// luckymoneyDetail(lpparam.classLoader);
				openLog(lpparam.classLoader);
				// luckymoneyDetailMore(lpparam.classLoader);
				// findChatRoom(lpparam.classLoader);
				// delRoomMember(lpparam.classLoader);
				sendFakeMsg(lpparam.classLoader);
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

							if (v1.contains(">\n")) {
								Log.i("MyTest", "***********************************************");
							} else {
								Log.i("MyTest", "------------------------------------------------");
							}
							Log.i("MyTest", "11111111111v1: " + v1);

							Log.i("MyTest", "1111111111v2: " + v2);
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
								// sendTalkMsg(classLoader, v3, "this is a 消息
								// ");

							}
						}
					});

			XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.c.a", classLoader, "a", LinkedList.class,
					XposedHelpers.findClass("com.tencent.mm.sdk.c.b", classLoader), new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						}
					});

			XposedHelpers.findAndHookMethod("com.tencent.mm.modelmulti.i", classLoader, "a", int.class, int.class,
					int.class, String.class, XposedHelpers.findClass("com.tencent.mm.network.p", classLoader),
					byte[].class, new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						}
					});

			XposedHelpers.findAndHookConstructor("com.tencent.mm.modelmulti.i", classLoader, String.class, String.class,
					int.class, new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							// Log.i("MyTest", "com.tencent.mm.modelmulti.i
							// constructor() 0:" + arg15.args[0]);
							// Log.i("MyTest", "com.tencent.mm.modelmulti.i
							// constructor() 1:" + arg15.args[1]);
							// Log.i("MyTest", "com.tencent.mm.modelmulti.i
							// constructor() 2:" + arg15.args[2]);
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.pluginsdk.k.o", classLoader, "c", Context.class, long.class,
					boolean.class, new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							// Log.i("MyTest", "-------------------> 0:" +
							// arg15.getResult());

						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.conversation.a", classLoader, "i",
					XposedHelpers.findClass("com.tencent.mm.storage.ad", classLoader), new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							/*
							 * Log.i("MyTest",
							 * "a------------------- old result> 0:" +
							 * arg15.getResult()); String field_username =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_username") .toString(); String
							 * field_content =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_content") .toString(); Log.i("MyTest",
							 * "a------------------- field_username> 0:" +
							 * field_username); Log.i("MyTest",
							 * "a------------------- field_content> 0:" +
							 * field_content); arg15.setResult(field_username);
							 */

						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.conversation.b", classLoader, "i",
					XposedHelpers.findClass("com.tencent.mm.storage.ad", classLoader), new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							/*
							 * Log.i("MyTest",
							 * "b------------------- old result> 0:" +
							 * arg15.getResult()); String field_username =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_username") .toString(); String
							 * field_content =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_content") .toString(); Log.i("MyTest",
							 * "b------------------- field_username> 0:" +
							 * field_username); Log.i("MyTest",
							 * "b------------------- field_content> 0:" +
							 * field_content); arg15.setResult(field_username);
							 */

						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.conversation.c", classLoader, "i",
					XposedHelpers.findClass("com.tencent.mm.storage.ad", classLoader), new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							/*
							 * Log.i("MyTest",
							 * "c------------------- old result> 0:" +
							 * arg15.getResult()); String field_username =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_username") .toString(); String
							 * field_content =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_content") .toString(); Log.i("MyTest",
							 * "c------------------- field_username> 0:" +
							 * field_username); Log.i("MyTest",
							 * "c------------------- field_content> 0:" +
							 * field_content); arg15.setResult(field_username);
							 */

						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.conversation.b", classLoader, "b",
					XposedHelpers.findClass("com.tencent.mm.storage.ad", classLoader), int.class, boolean.class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							/*
							 * Log.i("MyTest",
							 * "b.b------------------- old result> 0:" +
							 * arg15.getResult()); String field_username =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_username") .toString(); String
							 * field_content =
							 * XposedHelpers.getObjectField(arg15.args[0],
							 * "field_content") .toString(); Log.i("MyTest",
							 * "b.b------------------- field_username> 0:" +
							 * field_username); Log.i("MyTest",
							 * "b.b------------------- field_content> 0:" +
							 * field_content); arg15.setResult("[" +
							 * field_username + "]" + arg15.getResult());
							 */
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.model.l", classLoader, "eu", String.class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							// Log.i("MyTest", "**************** > 0:" +
							// param.args[0]);
							// Log.i("MyTest", "*************** > 1:" +
							// param.getResult());
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.modelbiz.a.k", classLoader, "in", String.class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							Object jObject = param.getResult();
							String field_userId = XposedHelpers.getObjectField(jObject, "field_userId").toString();
							String field_userName = XposedHelpers.getObjectField(jObject, "field_userName").toString();
							String field_userNamePY = XposedHelpers.getObjectField(jObject, "field_userNamePY")
									.toString();
							String field_brandUserName = XposedHelpers.getObjectField(jObject, "field_brandUserName")
									.toString();
							// Log.i("MyTest", "k.in(String) param :" +
							// param.args[0]);
							// Log.i("MyTest", "k.in(String) result field_userId
							// :" + field_userId);
							// Log.i("MyTest", "k.in(String) result
							// field_userName :" + field_userName);
							// Log.i("MyTest", "k.in(String) result
							// field_userNamePY :" + field_userNamePY);
							// Log.i("MyTest", "k.in(String) result
							// field_brandUserName:" + field_brandUserName);
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.storage.ac", classLoader, "MF", String.class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							Object jObject = param.getResult();
							String[] fields = new String[] { "bDA", "bDD", "bDE", "bDF", "bDG", "bDH", "bDI", "bDJ",
									"bDK", "bDm", "bDp", "bDq", "bDt", "bDu", "bDv", "bDw", "bDy", "bDz", "biA",
									"field_alias", "field_conRemark", "field_conRemarkPYFull", "field_conRemarkPYShort",
									"field_contactLabelIds", "field_domainList", "field_encryptUsername",
									"field_nickname", "field_pyInitial", "field_quanPin", "field_username",
									"field_weiboNickname" };

							// Log.i("MyTest", "ac.MF(String) param :" +
							// param.args[0]);
							for (int i = 0; i < fields.length; i++) {
								// Log.i("MyTest", "ac.MF(String) result " +
								// fields[i] + ":"
								// + XposedHelpers.getObjectField(jObject,
								// fields[i]));
							}
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.bbom.c", classLoader, "b",
					XposedHelpers.findClass("com.tencent.mm.storage.w", classLoader),
					XposedHelpers.findClass("com.tencent.mm.storage.w", classLoader),
					XposedHelpers.findClass("com.tencent.mm.protocal.c.ajd", classLoader), byte[].class, boolean.class,
					boolean.class, new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							for (int i = 0; i < 6; i++) {
								// Log.i("MyTest01", "bbom c.b() param " + i + "
								// : " + param.args[i]);
							}

						}
					});
		} catch (Exception e) {
			Log.i("MyTest", "exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void sendFakeMsg(ClassLoader classLoader, String talker, String msg) {
		try {
			Class avClass = XposedHelpers.findClass("com.tencent.mm.storage.av", classLoader);
			Object avObj = avClass.newInstance();
			XposedHelpers.callMethod(avObj, "cJ", talker);
			XposedHelpers.callMethod(avObj, "setType", 10000);
			XposedHelpers.callMethod(avObj, "z", System.currentTimeMillis());
			XposedHelpers.callMethod(avObj, "dh", 4);
			XposedHelpers.callMethod(avObj, "di", 2);
			XposedHelpers.callMethod(avObj, "setContent", "群id是:" + talker);

			Object tempObj = XposedHelpers
					.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.model.c", classLoader), "wH");
			XposedHelpers.callMethod(tempObj, "R", new Object[] { avObj });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendTalkMsg(ClassLoader classLoader, String talker, String msg) {
		try {
			/*
			 * .line 9009 new-instance v3, Lcom/tencent/mm/modelmulti/i;
			 * 
			 * invoke-direct {v3, v0, v1, v6},
			 * Lcom/tencent/mm/modelmulti/i;-><init>(Ljava/lang/String;Ljava/
			 * lang/String;I)V
			 * 
			 * .line 9010 invoke-static {},
			 * Lcom/tencent/mm/model/ak;->vy()Lcom/tencent/mm/u/n;
			 * 
			 * move-result-object v0
			 * 
			 * invoke-virtual {v0, v3, v2},
			 * Lcom/tencent/mm/u/n;->a(Lcom/tencent/mm/u/k;I)Z
			 */

			Class iClass = XposedHelpers.findClass("com.tencent.mm.modelmulti.i", classLoader);
			Object iObject = XposedHelpers.newInstance(iClass, talker, msg, 1);

			Object tempObject = XposedHelpers
					.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.model.ak", classLoader), "vy");
			XposedHelpers.callMethod(tempObject, "a", iObject, 0);

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("MyTest", "sen talk msg exception ****************");
		}
	}

	private static void sendFakeMsg(final ClassLoader classLoader) {
		// a(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;ZLjava/lang/String;)V
		XposedHelpers.findAndHookMethod("com.tencent.mm.model.h", classLoader, "a", String.class, List.class,
				String.class, boolean.class, String.class, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						for (int i = 0; i < 5; i++) {
							// Log.i("MyTest", "com.tencent.mm.model.h.a para" +
							// i + ": " + param.args[i]);
						}

					}
				});

		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.aw", classLoader, "R",
				XposedHelpers.findClass("com.tencent.mm.storage.av", classLoader), new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Object avParam = param.args[0];
						String cJ_field_talker = XposedHelpers.getObjectField(avParam, "field_talker").toString();

						String cJ_field_content = XposedHelpers.getObjectField(avParam, "field_content").toString();
						// Log.i("MyTest", "aw.R() talker:" + cJ_field_talker);
						// Log.i("MyTest", "aw.R() content:" +
						// cJ_field_content);

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
		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.MMActivity", classLoader, "bB", View.class,
				new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Log.i("MyTest01", "MMActivity.setview:" + param.args[0].getClass().getName());
					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader, "bHD",
				new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						Object object = param.thisObject;
						Object result = XposedHelpers.callMethod(object, "getStringExtra", "Chat_User");

						Log.i("MyTest01", "ChattingUI$a   Chat_User: " + result);
					}
				});

	}

	private static void wechatHook011(final ClassLoader classLoader) {
		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.aw", classLoader, "dZ", String.class, String.class,
				new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						// Log.i("MyTest01", "aw.dZ() param 0: " +
						// arg15.args[0]);
						// Log.i("MyTest01", "aw.dZ() param 1: " +
						// arg15.args[1]);

					}
				});

		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.cv", classLoader, "Ow", new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
				// Log.i("MyTest01", "chatting.cv.Ow() -------------> ");

			}
		});
		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.j", classLoader, "setCursor", Cursor.class,
				new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						Cursor cursor = (Cursor) arg15.args[0];
						Log.i("MyTest01", "j.setCursor() -------------> cursor:" + arg15.args[0] + "  obj："
								+ arg15.thisObject + "  size:" + cursor.getCount());
						int j = 0;
						while (cursor.moveToNext()) {
							int columnnum = cursor.getColumnCount();
							String columnStr = "";
							for (int i = 0; i < columnnum; i++) {
								// String columnName = cursor.getColumnName(i);
								// String columnValue = cursor.getString(i);
								// columnStr=columnStr+","+columnName+":"+columnValue;
							}
							// Log.i("MyTest01", "j.setCursor() index:"+j+"
							// "+columnStr);
							j++;
						}

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.cp", classLoader, "getView", int.class, View.class,
				ViewGroup.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						// Log.i("MyTest01", "cp.getView() int: " +
						// arg15.args[0]);

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.aw", classLoader, "k", String.class, long.class,
				long.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						Log.i("MyTest01", "aw.k()----> 0:" + arg15.args[0] + "   1:" + arg15.args[1] + "    2:"
								+ arg15.args[2] + " ----->cursor:" + arg15.getResult());

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.o", classLoader, "i", String.class, long.class,
				int.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						Log.i("MyTest01", "o.i()----> 0:" + arg15.args[0] + "   1:" + arg15.args[1] + "    2:"
								+ arg15.args[2] + " ----->cursor:" + arg15.getResult());

					}
				});
		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.o", classLoader, "b", String.class, long.class,
				long.class, long.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						Log.i("MyTest01", "o.b()----> 0:" + arg15.args[0] + "   1:" + arg15.args[1] + "    2:"
								+ arg15.args[2] + "    3:" + arg15.args[3] + " ----->cursor:" + arg15.getResult());

					}
				});

		// query(String paramString1, String[] paramArrayOfString1, String
		// paramString2, String[] paramArrayOfString2, String paramString3,
		// String paramString4, String paramString5)
		XposedHelpers.findAndHookMethod("com.tencent.mm.bg.g", classLoader, "query", String.class, String[].class,
				String.class, String[].class, String.class, String.class, String.class, new XC_MethodHook() {

					@Override
					protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
						Cursor cursor = (Cursor) arg15.getResult();
						Log.i("MyTest01", "g.query() ---> obj:" + arg15.thisObject + "   cursor:" + cursor + "  size:"
								+ cursor.getCount());
						Log.i("MyTest01", "g.query()----> 0:" + arg15.args[0] + "   1:" + arg15.args[1] + "    2:"
								+ arg15.args[2] + " ----->cursor:" + arg15.getResult());

					}
				});

		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.cp", classLoader, "Ow", new XC_MethodHook() {

			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Log.i("MyTest01",
						"chatting.cp.Ow() before ***************************************> " + param.thisObject);
			}

			@Override
			protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
				Log.i("MyTest01",
						"chatting.cp.Ow() after ***************************************> " + arg15.thisObject);
			}
		});
		XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.cp", classLoader, "Ox", new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
				Log.i("MyTest01", "chatting.cp.Ox() ***************************************> ");
			}
		});
	}

}
