package com.example.xposedupwallet;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class WePatch_6_5_4_dataLoop implements IXposedHookLoadPackage {

	private static String v_1;
	private static String v_3;

	private static Thread mThread;
	private static Map<String, String> mLuckyMoneyKeys;
	private static ClassLoader mClassLoader;

	private static Object mChatFooter2;

	private static Map<String, Object> mChatRoomMemberNames = new HashMap<String, Object>();

	private static void sendMessage(String text) {
		if (mChatFooter2 == null) {
			return;
		}
		Log.i("MyTest", "send message----------------------");
		Object mpR = XposedHelpers.getObjectField(mChatFooter2, "mpR");
		Object chatb = XposedHelpers.callStaticMethod(mpR.getClass(), "j", mpR);
		XposedHelpers.callMethod(chatb, "wM", new Object[] { text });
	}

	private static void addNewLuckyMoney(String newLuckyMoney, String sendid) {
		if (mLuckyMoneyKeys == null) {
			mLuckyMoneyKeys = new HashMap<String, String>();
		}
		if (mLuckyMoneyKeys.containsKey(newLuckyMoney)) {
			return;
		}
		mLuckyMoneyKeys.put(newLuckyMoney, sendid);
		Log.i("MyTest", "add new *********************");
		if (mThread == null) {
			return;
		}
		if (mThread == null) {
			mThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						Log.i("MyTest", "Looping  *********************");

						for (Map.Entry<String, String> entry : mLuckyMoneyKeys.entrySet()) {
							Class aeClass = XposedHelpers.findClass("com.tencent.mm.plugin.luckymoney.c.u",
									mClassLoader);
							Object aeObject = XposedHelpers.newInstance(aeClass, entry.getValue(), 11, 0,
									entry.getKey(), "v1.0", "");

							Object tempObject = XposedHelpers.callStaticMethod(
									XposedHelpers.findClass("com.tencent.mm.model.ak", mClassLoader), "vy");
							XposedHelpers.callMethod(tempObject, "a", aeObject, 0);
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			});
			mThread.start();
		}
	}

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
				//wechatHook01(lpparam.classLoader);
				// wechatReceiveTimingIdentifierHook(lpparam.classLoader);
				// luckymoneyDetail(lpparam.classLoader);
				openLog(lpparam.classLoader);
				// luckymoneyDetailMore(lpparam.classLoader);
				// findChatRoom(lpparam.classLoader);
				//delRoomMember(lpparam.classLoader);

			}

		});
	}

	private static void delRoomMember(final ClassLoader classLoader) {
		try {
			// a(Ljava/lang/String;Lcom/tencent/mm/g/a/a/a;Z)
			// a(IIILjava/lang/String;Lcom/tencent/mm/network/p;[B)V
			XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.chatroom.c.i", classLoader, "a", int.class,
					int.class, int.class, String.class,
					XposedHelpers.findClass("com.tencent.mm.network.p", classLoader), byte[].class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							Log.i("MyTest06", "param 0:" + arg15.args[0]);
							Log.i("MyTest06", "param 1:" + arg15.args[1]);
							Log.i("MyTest06", "param 2:" + arg15.args[2]);
							Log.i("MyTest06", "param 3:" + arg15.args[3]);
							Log.i("MyTest06", "param 5:" + new String((byte[]) arg15.args[5]));
							Field field = arg15.thisObject.getClass().getDeclaredField("bji");
							field.setAccessible(true);
							String v1 = (String) field.get(arg15.thisObject);
							Log.i("MyTest06", "test: " + v1);

							getChatroomMembers(classLoader, v1);
						}
					});

			XposedHelpers.findAndHookMethod("com.tencent.mm.plugin.chatroom.c.i$1", classLoader, "oV",
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							Log.i("MyTest06", "ChatroomInfoUI$8 ***************");
							Field field1 = arg15.thisObject.getClass().getDeclaredField("eVD");
							field1.setAccessible(true);
							LinkedList vs = (LinkedList) field1.get(arg15.thisObject);
							for (int i = 0; i < vs.size(); i++) {
								Object s2 = vs.get(i);
								Log.i("MyTest06", "linkedlist index:" + i + " value:" + s2);
							}
							// getChatroomMembers(classLoader,
							// "7015279950@chatroom");

							Object tempObject = XposedHelpers.callStaticMethod(
									XposedHelpers.findClass("com.tencent.mm.storage.q", classLoader), "Mi",
									"7015279950@chatroom");

							try {

								Field field = tempObject.getClass().getDeclaredField("cnn");
								field.setAccessible(true);
								LinkedList<Object> linkedList = (LinkedList<Object>) field.get(tempObject);
								for (int i = 0; i < linkedList.size(); i++) {
									Object temp = linkedList.get(i);
									Field fieldccr = temp.getClass().getDeclaredField("ccr");
									fieldccr.setAccessible(true);
									Log.i("MyTest06", "ccr---- :  " + fieldccr.get(temp));
									Field fieldcct = temp.getClass().getDeclaredField("cct");
									fieldcct.setAccessible(true);
									Log.i("MyTest06", "cct---- :  " + fieldcct.get(temp));
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});
			// com/tencent/mm/storage/q;->a(Ljava/lang/String;Lcom/tencent/mm/g/a/a/a;Z)
			XposedHelpers.findAndHookMethod("com.tencent.mm.storage.q", classLoader, "a", String.class,
					XposedHelpers.findClass("com.tencent.mm.g.a.a.a", classLoader), boolean.class, new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							Log.i("MyTest06", "change member names xxxxxxxxxxxxx");
							Field field = arg15.thisObject.getClass().getDeclaredField("cFP");
							field.setAccessible(true);
							Map<String, Object> v1 = (Map<String, Object>) field.get(arg15.thisObject);
							for (Map.Entry<String, Object> entry : v1.entrySet()) {

								// Log.i("MyTest06",
								// "q.axxxxxxxxxxxx> Key = " + entry.getKey() +
								// ", Value = " + entry.getValue());
								Object temp = entry.getValue();
								Field fieldccr = temp.getClass().getDeclaredField("ccr");
								fieldccr.setAccessible(true);
								// Log.i("MyTest06", "ccrxxxxxxxxxxxx: " +
								// fieldccr.get(temp));
								Field fieldcct = temp.getClass().getDeclaredField("cct");
								fieldcct.setAccessible(true);
								// Log.i("MyTest06", "cctxxxxxxxxxxxx : " +
								// fieldcct.get(temp));
							}
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.storage.q", classLoader, "a",
					XposedHelpers.findClass("com.tencent.mm.g.a.a.a", classLoader), new XC_MethodHook() {

						@SuppressWarnings("unchecked")
						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							Log.i("MyTest06", "change member names ______________________");

							Object modelCObj = XposedHelpers.callStaticMethod(
									XposedHelpers.findClass("com.tencent.mm.model.k", classLoader), "xE");
							Log.i("MyTest06",
									"com.tencent.mm.model.k------------------------------> xE   :" + modelCObj);

							Object storageQ = param.thisObject;

							String chatRoomName = (String) storageQ.getClass().getSuperclass()
									.getDeclaredField("field_chatroomname").get(storageQ);

							Log.i("MyTest06", "change member names _----------_1");
							Map<String, String> newNickNames = getNickNames(storageQ);

							if (!mChatRoomMemberNames.containsKey(chatRoomName)) {
								mChatRoomMemberNames.put(chatRoomName, newNickNames);
								return;
							}

							Log.i("MyTest06", "change member names _----------_2");
							Map<String, String> oldNickNames = (Map<String, String>) mChatRoomMemberNames
									.get(chatRoomName);
							Log.i("MyTest06", "change member names _----------_3");
							for (Map.Entry<String, String> entry : newNickNames.entrySet()) {
								String newName = entry.getValue();
								String idString = entry.getKey();
								String oldName = oldNickNames.get(idString);
								Log.i("MyTest06", "id:" + idString + " old:" + oldName + "  new:" + newName);
								if (newName != null && oldName != null && !newName.equals(oldName)) {
									Log.i("MyTest06",
											idString + "    chage name   old:" + oldName + "     new:" + newName);
								}
							}
							mChatRoomMemberNames.put(chatRoomName, newNickNames);

						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.storage.q", classLoader, "byW", new XC_MethodHook() {

				@SuppressWarnings("unchecked")
				@Override
				protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
					Log.i("MyTest06", "byW &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&_");
				}
			});
			XposedHelpers.findAndHookMethod("com.tencent.mm.storage.q", classLoader, "dX", String.class, String.class,
					new XC_MethodHook() {

						@SuppressWarnings("unchecked")
						@Override
						protected void afterHookedMethod(MethodHookParam arg15) throws Throwable {
							Log.i("MyTest06",
									"dx dx dx dx dx dx dx dx para1:" + arg15.args[0] + " param1:" + arg15.args[1]);
						}
					});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static Map<String, String> getNickNames(Object storageQ) {
		Map<String, String> newNicks = new HashMap<String, String>();

		try {
			Field field = storageQ.getClass().getDeclaredField("cFP");
			field.setAccessible(true);
			String displaynames = (String) storageQ.getClass().getSuperclass().getDeclaredField("field_displayname")
					.get(storageQ);
			String memberlists = (String) storageQ.getClass().getSuperclass().getDeclaredField("field_memberlist")
					.get(storageQ);
			String[] ids = memberlists.split(";");
			String[] names = displaynames.split("、");
			for (int i = 0; i < ids.length; i++) {
				newNicks.put(ids[i], names[i]);
			}

			Map<String, Object> v1 = (Map<String, Object>) field.get(storageQ);
			for (Map.Entry<String, Object> entry : v1.entrySet()) {
				Object temp = entry.getValue();
				Field fieldccr = temp.getClass().getDeclaredField("ccr");
				fieldccr.setAccessible(true);

				if (fieldccr.get(temp) != null) {
					newNicks.put(entry.getKey(), (String) fieldccr.get(temp));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Map.Entry<String, String> entry : newNicks.entrySet()) {
			Log.i("MyTest06", "change member names put id:" + entry.getKey() + " name:" + entry.getValue());
		}
		return newNicks;
	}

	private static void delRoomMember1(ClassLoader classLoader) {
		try {
			List<String> members = new ArrayList<String>();
			members.add("wxid_27tg6xnkojqp22");
			Class cgClass = XposedHelpers.findClass("com.tencent.mm.plugin.chatroom.c.g", classLoader);
			Object cgObject = XposedHelpers.newInstance(cgClass, "7825458583@chatroom", members);
			Object tempObject = XposedHelpers
					.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.model.ak", classLoader), "vy");
			XposedHelpers.callMethod(tempObject, "a", cgObject, 0);
			Log.i("MyTest06", "del room member ======================");

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("MyTest06", "del room member exception---------------------");

		}
	}

	private static void getChatroomMembers(ClassLoader classLoader, String roomId) {
		/*
		 * invoke-static {},
		 * Lcom/tencent/mm/model/ak;->yV()Lcom/tencent/mm/model/c;
		 * 
		 * move-result-object v0
		 * 
		 * invoke-virtual {v0},
		 * Lcom/tencent/mm/model/c;->wM()Lcom/tencent/mm/storage/r;
		 * 
		 * move-result-object v0
		 * 
		 * iget-object v1, p0,
		 * Lcom/tencent/mm/plugin/chatroom/ui/ChatroomInfoUI;->eVY:Ljava/lang/
		 * String;
		 * 
		 * invoke-virtual {v0, v1},
		 * Lcom/tencent/mm/storage/r;->Mk(Ljava/lang/String;)Lcom/tencent/mm/
		 * storage/q;
		 * 
		 * move-result-object v0
		 * 
		 * iput-object v0, p0,
		 * Lcom/tencent/mm/plugin/chatroom/ui/ChatroomInfoUI;->eWd:Lcom/tencent/
		 * mm/storage/q;
		 */
		Object modelCObj = XposedHelpers
				.callStaticMethod(XposedHelpers.findClass("com.tencent.mm.model.ak", classLoader), "yV");
		Object storageRObj = XposedHelpers.callMethod(modelCObj, "wM", new Object[] {});
		Object storageQObj = XposedHelpers.callMethod(storageRObj, "Mk", new Object[] { roomId });

		String[] fields = new String[] { "field_chatroomname", "field_chatroomnick", "field_chatroomnotice",
				"field_chatroomnoticeEditor", "field_displayname", "field_memberlist", "field_roomowner",
				"field_selfDisplayName", };
		Log.i("MyTest05", "xxxxxxxx > class:" + storageQObj.getClass().getName());
		for (int i = 0; i < fields.length; i++) {
			try {
				Object v1 = (String) storageQObj.getClass().getSuperclass().getDeclaredField(fields[i])
						.get(storageQObj);

				Log.i("MyTest05", "xxxxxxxx > " + fields[i] + "    : " + v1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			/*
			 * Field field1 = storageQObj.getClass().getDeclaredField("nRY");
			 * field1.setAccessible(true); Object a = field1.get(storageQObj);
			 */

			Field field = storageQObj.getClass().getDeclaredField("cFP");
			field.setAccessible(true);
			Map<String, Object> v1 = (Map<String, Object>) field.get(storageQObj);

			for (Map.Entry<String, Object> entry : v1.entrySet()) {

				Log.i("MyTest06", "q.a----------> Key = " + entry.getKey() + ", Value = " + entry.getValue());
				Object temp = entry.getValue();
				Field fieldccr = temp.getClass().getDeclaredField("ccr");
				fieldccr.setAccessible(true);
				Log.i("MyTest06", "ccr :  " + fieldccr.get(temp));
				Field fieldcct = temp.getClass().getDeclaredField("cct");
				fieldcct.setAccessible(true);
				Log.i("MyTest06", "cct :  " + fieldcct.get(temp));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
								// sendOpen(classLoader, wxpay, sendid);
								// addNewLuckyMoney(wxpay, sendid);
								v_1 = v1;
								v_3 = v3;
								// sendMessage(sendid);
								// getChatroomMembers(classLoader);
								// delRoomMember1(classLoader);
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
						String jsonStr = jsonObject.toString();
						if (jsonStr.contains("被抢光")) {
							Log.i("MyTest", "****************被抢光");
							Log.i("MyTest", "Detail :" + jsonObject);
							String sendId = jsonObject.getString("sendId");
							for (Map.Entry<String, String> entry : mLuckyMoneyKeys.entrySet()) {
								if (entry.getValue().equals(sendId)) {
									Log.i("MyTest", "******************删除key");
									mLuckyMoneyKeys.remove(entry.getKey());

									break;
								}
							}
						}

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

		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.q", classLoader, "DO", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				super.afterHookedMethod(param);

				String[] fields = new String[] { "field_chatroomname", "field_chatroomnick", "field_chatroomnotice",
						"field_chatroomnoticeEditor", "field_displayname", "field_memberlist", "field_roomowner",
						"field_selfDisplayName", };
				for (int i = 0; i < fields.length; i++) {
					Object v1 = XposedHelpers.getObjectField(param.thisObject, fields[i]);
					Log.i("MyTest05", "-----> " + fields[i] + "    : " + v1);
				}
			}
		});

		XposedHelpers.findAndHookMethod("com.tencent.mm.storage.r", classLoader, "Mk", String.class,
				new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						super.afterHookedMethod(param);

						Log.i("MyTest05", "**********************> " + param.args[0]);
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

	private static void findChatRoom(final ClassLoader classLoader) {
		try {
			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader, "bHD",
					new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							super.beforeHookedMethod(param);
							Log.i("MyTest01", "chatting ui open *************************");
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.storage.ae", classLoader, "MM", String.class,
					new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							super.beforeHookedMethod(param);
							Log.i("MyTest01", "chatting user ---------->: " + param.args[0]);
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.ChattingUI$a", classLoader, "bIY",
					new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							super.beforeHookedMethod(param);
							Log.i("MyTest01", "chatting ui Closed *************************");
						}
					});

			Class tempClass = XposedHelpers.findClass("com.tencent.mm.pluginsdk.ui.chat.ChatFooter$c", classLoader);
			XposedHelpers.findAndHookMethod("com.tencent.mm.pluginsdk.ui.chat.ChatFooter", classLoader, "a", tempClass,
					new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							super.beforeHookedMethod(param);
							Log.i("MyTest01", "chatFooter class name:" + param.args[0].getClass().getName());
						}
					});
			Class tempClass1 = XposedHelpers.findClass("com.tencent.mm.pluginsdk.ui.chat.ChatFooter", classLoader);
			XposedHelpers.findAndHookMethod("com.tencent.mm.pluginsdk.ui.chat.ChatFooter", classLoader, "j", tempClass1,
					new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							Object result = param.getResult();
							Log.i("MyTest01", "chatFooter class 11   1:" + result.getClass().getName());
						}
					});
			XposedHelpers.findAndHookMethod("com.tencent.mm.pluginsdk.ui.chat.ChatFooter$2", classLoader, "onClick",
					View.class, new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							super.beforeHookedMethod(param);
							Log.i("MyTest01", "chatfooter 2 onclick");
						}
					});

			XposedHelpers.findAndHookMethod("com.tencent.mm.ui.chatting.ac", classLoader, "wM", String.class,
					new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							Log.i("MyTest01", "chatting ac param:" + param.args[0] + " result:" + param.getResult());
						}
					});

			XposedHelpers.findAndHookConstructor("com.tencent.mm.pluginsdk.ui.chat.ChatFooter$2", classLoader,
					tempClass1, new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param) throws Throwable {
							super.afterHookedMethod(param);
							Log.i("MyTest01", "chat footer $ 2 constructed");
							mChatFooter2 = param.thisObject;
						}
					});
		} catch (Exception e) {
			Log.i("MyTest", "exception:" + e.getMessage());
			e.printStackTrace();
		}
	}

}
