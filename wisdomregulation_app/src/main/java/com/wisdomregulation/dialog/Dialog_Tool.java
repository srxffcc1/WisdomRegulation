package com.wisdomregulation.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.adapter.Adapter_BookChoseListNew;
import com.wisdomregulation.adapter.Adapter_BookChoseListOld;
import com.wisdomregulation.adapter.Adapter_BookDetail;
import com.wisdomregulation.adapter.Adapter_EditChoseLaw;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.allactivity.single.Activity_DownLoad;
import com.wisdomregulation.allactivity.single.Activity_LawList;
import com.wisdomregulation.allactivity.single.Activity_Main;
import com.wisdomregulation.allactivity.single.Activity_PlanDetail;
import com.wisdomregulation.allactivity.single.Activity_PlanInsert;
import com.wisdomregulation.allactivity.tab.Tab_Check;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitydemo.Entity_Demo;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.entityfile.EntityS_File;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.AutoCheckGroup;
import com.wisdomregulation.frame.AutoDialogBuilder;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.frame.OnCheckedChangeListener;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.helporg2017.Pdf_Shark2017;
import com.wisdomregulation.staticlib.Static_BookLib;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_Sdk;
import com.wisdomregulation.utils.Util_String;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dialog_Tool {
    public static View showDialog_EditLaw(final Activity activity, Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_edit_law, null);
        final List<Base_Entity> editlist = (List<Base_Entity>) entity;
        LinearLayout content = (LinearLayout) dialogview.findViewById(R.id.lawContent);
        final TextView pageNumber = (TextView) dialogview.findViewById(R.id.pageNumber);
        TextView pre = (TextView) dialogview.findViewById(R.id.previousPage);
        TextView next = (TextView) dialogview.findViewById(R.id.nextPage);
        Util_MatchTip.initAllScreenText(dialogview);
        final int allpage = editlist.size();
        final int[] nowpage = new int[1];
        nowpage[0] = 1;
        pageNumber.setText(nowpage[0] + "/" + allpage);
        final Adapter_EditChoseLaw adapter = new Adapter_EditChoseLaw(activity, editlist.get(nowpage[0] - 1), content).setEditState(1).initView();
        pre.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nowpage[0] != 1) {
                    nowpage[0]--;
                    Toast.makeText(activity, "已暂存", Toast.LENGTH_SHORT).show();
                    adapter.saveEntity();
                    pageNumber.setText(nowpage[0] + "/" + allpage);
                    adapter.setDetailMapData(editlist.get(nowpage[0] - 1)).initView();
                }

            }
        });
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nowpage[0] != allpage) {
                    nowpage[0]++;
                    Toast.makeText(activity, "已暂存", Toast.LENGTH_SHORT).show();
                    adapter.saveEntity();
                    pageNumber.setText(nowpage[0] + "/" + allpage);
                    adapter.setDetailMapData(editlist.get(nowpage[0] - 1)).initView();
                }

            }
        });
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.08), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.23)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.saveEntity();
                Toast.makeText(activity, "选择完成", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < editlist.size(); i++) {
                    Help_DB.create().save2update(editlist.get(i).put(1, "执法检查发现").put(11, "1.立案阶段"));
                }
                Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_LawList.refreshadapter));
                activity.finish();

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    /**
     * 避免误点
     *
     * @param activity
     * @param entity
     * @return
     */
    public static View showDialog_CheckFinishOr(final Activity activity, final CallBack callback) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_checkfinishor, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 5.7)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                callback.back(null);

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;


    }

    public static View showDialog_InspectChoseType(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_checktype, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 5.5)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String companyid = ((Base_Entity) entity).getValue(37);
                String companyname = ((Base_Entity) entity).getValue(0);
                Base_Entity checkEntity = new Entity_Check().init().initId().put(1, Static_ConstantLib.companyduty + companyname + "_" + Util_String.getDate()).put(4, companyname).put(5, companyid);
                if (group.getCheckTagIndex() == 1) {
                    checkEntity.put(2, "职业卫生检查");


                } else if (group.getCheckTagIndex() == 2) {
                    checkEntity.put(2, "企业安全检查");


                } else {
                    checkEntity.put(2, "职业卫生检查");

                }
//			for (int j = 1; j < resultentity.size(); j++) {
//				String resultfield=resultentity.getField(j);
//				if(!resultfield.startsWith("_")){
//					final String optionid=Util_String.get16Uuid();
//					String jianchaleixing=resultentity.getClass().getSimpleName();
////					if(jianchaleixing.equalsIgnoreCase("Entity_CheckHealthResult")){
////						Help_DB.create().save2update(new Entity_CheckHealthDetail().init().setId(optionid).put("来自何结果项目", resultentity.getId()));
////
////						
////					}else if(jianchaleixing.equalsIgnoreCase("Entity_CheckSaveResult")){
////						Help_DB.create().save2update(new Entity_CheckSaveDetail().init().setId(optionid).put("来自何结果项目", resultentity.getId()));
////
////						
////					}
////					resultentity.put(j, optionid);
//					
//				}
//			}
//			Help_DB.create().save2update(resultentity);
                checkEntity.put(6, "0");
                checkEntity.put(15, "0");
                checkEntity.put(14, Static_InfoApp.create().getUserName());
                checkEntity.put(16, "0");
                checkEntity.put(10, "0");
                checkEntity.put(0, Util_String.get16Uuid());
                Help_DB.create().save2update(checkEntity);
                activity.startActivity(new Intent(activity, Tab_Check.class).putExtra("checkEntity", checkEntity));

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;


    }

    public static View showDialog_PlanOrOnlyCheck(final Activity activity, Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_plan2check, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (group.getCheckTagIndex() == 1) {

                    activity.startActivity(new Intent(activity, Activity_PlanInsert.class).putExtra("entityPlan", new Entity_Plan()));
                } else {

                }

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    //待改
    public static View showDialog_FinishPlan(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_onfinishcheck, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (group.getCheckedId() == R.id.inspectType1) {
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_PlanDetail.refresh));
                            ((Base_AyActivity) activity).finishSuper();

                        }
                    }, 200);
                } else {
                    ((Base_AyActivity) activity).toMainPage();
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_Main.passToAgainCheck));

                        }
                    }, 200);
                }


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }


    public static View showDialog_FinishCheck(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_onfinishcheck, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {


                if (group.getCheckedId() == R.id.inspectType1) {
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_PlanDetail.refresh));
                            ((Base_AyActivity) activity).finishSuper();

                        }
                    }, 200);
                } else {
                    ((Base_AyActivity) activity).toMainPage();
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            Static_InfoApp.create().getContext().sendBroadcast(new Intent(Activity_Main.passToAgainCheck));

                        }
                    }, 200);
                }


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_FinishAgainCheck(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_onfinishagaincheck, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        final Base_Entity finsihAgain = (Base_Entity) entity;
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (group.getCheckedId() == R.id.inspectType1) {
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Help_DB.create().update(finsihAgain.put(10, "1"));
                            activity.finish();

                        }
                    }, 200);
                } else {
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            activity.finish();

                        }
                    }, 200);
                }


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_FinishLaw(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_onfinishlaw, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        final Base_Entity finsihlaw = (Base_Entity) entity;
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (group.getCheckedId() == R.id.inspectType1) {
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            Help_DB.create().save2update(finsihlaw);
                            activity.sendBroadcast(new Intent(Activity_LawList.refreshadapter));
                            ((Base_AyActivity) activity).finishThis();
                        }
                    }, 200);
                } else {
                    Static_InfoApp.create().getAllhandler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            ((Base_AyActivity) activity).finishThis();


                        }
                    }, 200);
                }


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_BookTypefromOldOrfromNew(final Activity activity, final Object entity1, final Object entity2) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_booktypehis2new, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (group.getCheckTagIndex() == 1) {
                    showDialog_BookTypeOld(activity, entity2);

                } else {
                    ((List) entity1).addAll(((List) entity2));
                    showDialog_BookTypeNew(activity, entity1);
                }

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_BookTypeOld(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_bookold, null);
        Util_MatchTip.initAllScreenText(dialogview);
//	final AutoCheckGroup group=(AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        final Map<String, Boolean> bookViewMap = new HashMap<String, Boolean>();
        GridView bookchosemap = (GridView) dialogview.findViewById(R.id.bookchosemap);
        int splitcount = 2;
        bookchosemap.setNumColumns(splitcount);
        bookchosemap.setAdapter(new Adapter_BookChoseListOld(activity, (List<Base_Entity>) entity, bookViewMap));
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.08), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.12)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final List<String> realChoseList = new ArrayList<String>();
                for (Map.Entry<String, Boolean> entry : bookViewMap.entrySet()) {
                    if (entry.getValue()) {

                        realChoseList.add(entry.getKey());
                    }
                }
                Intent intent = new Intent(Static_ConstantLib.AddBookOld);
                intent.putExtra("realChoseList", (Serializable) realChoseList);
                Static_InfoApp.create().getContext().sendBroadcast(intent);


            }
        }).setNegativeButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> realChoseList = new ArrayList<String>();
                Intent intent = new Intent(Static_ConstantLib.AddBookNew);
                intent.putExtra("realChoseList", (Serializable) realChoseList);
                Static_InfoApp.create().getContext().sendBroadcast(intent);

            }
        });
        Dialog dialog = builder.show();
        return dialogview;
    }

    public static View showDialog_BookTypeNew(final Activity activity, Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_booknew, null);
        Util_MatchTip.initAllScreenText(dialogview);
//	final AutoCheckGroup group=(AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        final Map<String, Boolean> bookViewMap = new HashMap<String, Boolean>();
        GridView bookchosemap = (GridView) dialogview.findViewById(R.id.bookchosemap);
        int splitcount = 2;
        bookchosemap.setNumColumns(splitcount);
        bookchosemap.setAdapter(new Adapter_BookChoseListNew(activity, (List<Base_Entity>) entity, bookViewMap));
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.08), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.12)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> realChoseList = new ArrayList<String>();
                for (int i = 1; i < Static_BookLib.BookNameList2017.length; i++) {
                    if (bookViewMap.get(Static_BookLib.BookNameList2017[i]) != null) {
                        boolean checkFlag = bookViewMap.get(Static_BookLib.BookNameList2017[i]);
                        if (checkFlag) {
                            realChoseList.add(Static_BookLib.BookNameList2017[i]);
                        }
                    }
                }
                final List<String> finalstring = new ArrayList<String>();
                final List<Base_Entity> treadneed = new ArrayList<Base_Entity>();
                for (int i = 0; i < realChoseList.size(); i++) {
                    String bookname = realChoseList.get(i);
                    if (!bookname.equals("")) {
                        final Base_Entity booktmp = Pdf_Shark2017.getEneityBook(bookname);
                        String id = Util_String.get16Uuid();
                        booktmp.setId(id);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Help_DB.create().save2update(booktmp);

                            }
                        }).start();
                        treadneed.add(booktmp);
                        finalstring.add(id + "@" + bookname);
                    }

                }

                Intent intent = new Intent(Static_ConstantLib.AddBookNew);
                intent.putExtra("realChoseList", (Serializable) finalstring);
                Static_InfoApp.create().getContext().sendBroadcast(intent);


            }
        }).setNegativeButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> realChoseList = new ArrayList<String>();
                Intent intent = new Intent(Static_ConstantLib.AddBookNew);
                intent.putExtra("realChoseList", (Serializable) realChoseList);
                Static_InfoApp.create().getContext().sendBroadcast(intent);

            }
        });
        Dialog dialog = builder.show();
        return dialogview;
    }

    public static View showDialog_Rename(final Activity activity, Object entity) {
        LinearLayout dialogview = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.dialog_collect_rename, null);
        final EntityS_File filer = (EntityS_File) entity;
        final EditText ed = (EditText) dialogview.findViewById(R.id.renameedit);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 4.24)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String rname = ed.getText().toString();
                filer.rename(rname);
                Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.refresh));

            }
        }).setNegativeButton(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
        builder.show();
        return dialogview;
    }

    public static View showDialog_ChosePage(final Activity activity, final Object entity) {
        LinearLayout dialogview = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.dialog_chose_page, null);
        final EditText ed = (EditText) dialogview.findViewById(R.id.renameedit);
        TextView et = (TextView) dialogview.findViewById(R.id.pagecan);

        final int allpage = (Integer) entity;
        et.setText("选择页数跳转:1-" + allpage);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 4.24)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String rname = ed.getText().toString();
                if (rname == null) {
                    rname = "1";
                }
                int ordertag = 0;
                try {
                    ordertag = Integer.parseInt(rname);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    ordertag = 0;
                }
                if (allpage >= ordertag) {
                    ((Base_AyActivity) activity).refreshPage(ordertag);
                } else {
                    Toast.makeText(activity, "请输入1-" + allpage + "的数字", Toast.LENGTH_SHORT).show();
                }


            }
        }).setNegativeButton(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
        builder.show();
        return dialogview;
    }

    public static View showDialog_UpAPP(final Activity activity, Object entity) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_update, null);
        Util_MatchTip.initAllScreenText(dialogview);
        Map<String, String> mmp = (Map<String, String>) entity;
        String nowlevels = mmp.get("nowlevel");
        String newlevels = mmp.get("newlevel");
        final String paths = mmp.get("path");
        TextView nowlevel = (TextView) dialogview.findViewById(R.id.nowlevel);
        TextView newlevel = (TextView) dialogview.findViewById(R.id.newlevel);
        nowlevel.setText("当前版本号为:" + nowlevels);
        newlevel.setText("最新版本号为:" + newlevels);
        TextView isnew = (TextView) dialogview.findViewById(R.id.isnew);
        if (paths.equals("")) {
            isnew.setText("当前已经是最新版本不用更新");
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("更新提示")
                    .setView(dialogview)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();
            dialog.setCanceledOnTouchOutside(false);
            return dialogview;
        } else {
            isnew.setText("最新版本号：" + newlevels + "点击按钮确认更新");
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("更新提示")
                    .setView(dialogview)
                    .setPositiveButton("确认更新", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            activity.startActivity(new Intent(activity, Activity_DownLoad.class).putExtra("path", paths));
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();
            dialog.setCanceledOnTouchOutside(false);
            return dialogview;
        }


    }

    public static View showDialog_AllEdittextInsert(final Activity activity, Object entity, final CallBack callback) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_alledittext, null);
        final AutoCheckGroup group = (AutoCheckGroup) dialogview.findViewById(R.id.autogroup);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 2.25)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (group.getCheckTagIndex() == 1) {
                    if (callback != null) {
                        callback.back(null);
                    }
                } else {

                }

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_ChangePersonTypePass(final Activity activity, Object entity, final CallBack callback) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_chosepersontypepass, null);
        Util_MatchTip.initAllScreenText(dialogview);
        final EditText renameedit = (EditText) dialogview.findViewById(R.id.renameedit);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 4.24)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String renameeditt = renameedit.getText().toString();
                if (renameeditt.equals("980128")) {
                    callback.back(1);
                }
                if (renameeditt.equals("980122")) {
                    callback.back(2);
                }

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    /**
     * 测试用的一个弹出
     *
     * @param activity
     * @param entity
     * @param callback
     * @return
     */
    public static View showDialog_ChangePersonType3(final Activity activity, Object entity, final CallBack callback) {
        View dialogview = activity.getLayoutInflater().inflate(R.layout.dialog_chose_chosepersontype3, null);
//	Util_MatchTip.initAllScreenText(dialogview);
//	final EditText inspectType0t=(EditText) dialogview.findViewById(R.id.inspectType0t);
//	final TextView inspectType1t=(TextView) dialogview.findViewById(R.id.inspectType1t);
//	final TextView inspectType2t=(TextView) dialogview.findViewById(R.id.inspectType2t);
//
//
//	final AutoCheckBox inspectType1=(AutoCheckBox) dialogview.findViewById(R.id.inspectType1);
//	final AutoCheckBox inspectType2=(AutoCheckBox) dialogview.findViewById(R.id.inspectType2);
//
//
//	inspectType1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//		@Override
//		public void onCheckedChanged(View buttonView, boolean isChecked) {
//			if(!isChecked){
//				inspectType0t.setText("http://221.192.132.39:8001/sjzzhaj/");
//				inspectType1t.setText("线上数据库");
//			}else{
//				inspectType0t.setText("http://10.0.0.219:8080/sjzzhaj/");
//				inspectType1t.setText("本地测试数据库");
//			}
//
//		}
//	});
//	inspectType2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//		@Override
//		public void onCheckedChanged(View buttonView, boolean isChecked) {
//			if(!isChecked){
////				inspectType0t.setText("http://221.192.132.39:8001/sjzzhaj/");
//				inspectType2t.setText("非演示");
//			}else{
////				inspectType0t.setText("http://10.0.0.96:8080/");
//				inspectType2t.setText("演示");
//				inspectType1.setChecked(true);
//			}
//
//		}
//	});
//
//
//	String iptmp=Static_InfoApp.create().getiphead();
//	boolean istesttmp=Static_InfoApp.create().istest();
//	boolean isshowtmp=Static_InfoApp.create().isshow();
//	boolean ismarkertmp=Static_InfoApp.create().ismarker();
//	inspectType0t.setText(iptmp);
//	inspectType1.setChecked(istesttmp);
//	inspectType2.setChecked(isshowtmp);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.85)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_ChangePersonType2(final Activity activity, Object entity, final CallBack callback) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_chosepersontype2, null);
        Util_MatchTip.initAllScreenText(dialogview);
        final EditText inspectType0t = (EditText) dialogview.findViewById(R.id.inspectType0t);
        final TextView inspectType1t = (TextView) dialogview.findViewById(R.id.inspectType1t);
        final TextView inspectType2t = (TextView) dialogview.findViewById(R.id.inspectType2t);


        final AutoCheckBox inspectType1 = (AutoCheckBox) dialogview.findViewById(R.id.inspectType1);
        final AutoCheckBox inspectType2 = (AutoCheckBox) dialogview.findViewById(R.id.inspectType2);


        inspectType1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (!isChecked) {
                    inspectType0t.setText("http://221.192.132.39:8001/sjzzhaj/");
                    inspectType1t.setText("线上数据库");
                } else {
                    inspectType0t.setText("http://10.0.0.219:8080/sjzzhaj/");
                    inspectType1t.setText("本地测试数据库");
                }

            }
        });
        inspectType2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (!isChecked) {
//				inspectType0t.setText("http://221.192.132.39:8001/sjzzhaj/");
                    inspectType2t.setText("非演示");
                } else {
//				inspectType0t.setText("http://10.0.0.96:8080/");
                    inspectType2t.setText("演示");
                    inspectType1.setChecked(true);
                }

            }
        });


        String iptmp = Static_InfoApp.create().getiphead();
        boolean istesttmp = Static_InfoApp.create().istest();
        boolean isshowtmp = Static_InfoApp.create().isshow();
        boolean ismarkertmp = Static_InfoApp.create().ismarker();
        inspectType0t.setText(iptmp);
        inspectType1.setChecked(istesttmp);
        inspectType2.setChecked(isshowtmp);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.85)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean istest = inspectType1.isChecked();
                boolean isshow = inspectType2.isChecked();
                String ip = inspectType0t.getText().toString();
                Util_Sdk.initializeMode(activity, ip, istest, isshow, false, callback);


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    public static View showDialog_ChangePersonType(final Activity activity, Object entity, final CallBack callback) {
        LinearLayout dialogview = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_chose_chosepersontype, null);
        Util_MatchTip.initAllScreenText(dialogview);
        final EditText inspectType0t = (EditText) dialogview.findViewById(R.id.inspectType0t);
        final TextView inspectType1t = (TextView) dialogview.findViewById(R.id.inspectType1t);
        final TextView inspectType2t = (TextView) dialogview.findViewById(R.id.inspectType2t);
        final TextView inspectType3t = (TextView) dialogview.findViewById(R.id.inspectType3t);

        final AutoCheckBox inspectType1 = (AutoCheckBox) dialogview.findViewById(R.id.inspectType1);
        final AutoCheckBox inspectType2 = (AutoCheckBox) dialogview.findViewById(R.id.inspectType2);
        final AutoCheckBox inspectType3 = (AutoCheckBox) dialogview.findViewById(R.id.inspectType3);

        inspectType1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (!isChecked) {
                    inspectType0t.setText("http://221.192.132.39:8001/sjzzhaj/");
                    inspectType1t.setText("线上数据库");
                } else {
                    inspectType0t.setText("http://10.0.0.219:8080/sjzzhaj/");
                    inspectType1t.setText("本地测试数据库");
                }

            }
        });
        inspectType2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (!isChecked) {
                    inspectType0t.setText("http://221.192.132.39:8001/sjzzhaj/");
                    inspectType2t.setText("非演示");
                } else {
                    inspectType0t.setText("http://10.0.0.219:8080/sjzzhaj/");
                    inspectType2t.setText("演示");
                    inspectType1.setChecked(true);
                }

            }
        });
        inspectType3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (!isChecked) {
                    inspectType3t.setText("非程序员调试");
                } else {
                    inspectType3t.setText("程序员调试");
                }

            }
        });

        String iptmp = Static_InfoApp.create().getiphead();
        boolean istesttmp = Static_InfoApp.create().istest();
        boolean isshowtmp = Static_InfoApp.create().isshow();
        boolean ismarkertmp = Static_InfoApp.create().ismarker();
        inspectType0t.setText(iptmp);
        inspectType1.setChecked(istesttmp);
        inspectType2.setChecked(isshowtmp);
        inspectType3.setChecked(ismarkertmp);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.85)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean istest = inspectType1.isChecked();
                boolean isshow = inspectType2.isChecked();
                boolean ismarker = inspectType3.isChecked();
                String ip = inspectType0t.getText().toString();
                Util_Sdk.initializeMode(activity, ip, istest, isshow, ismarker, callback);


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    /**
     * 弹出的文书中增加tip的弹窗
     * @param activity
     * @param entity
     * @param back
     * @return
     */
    public static View showDialog_AddBookItem(final Activity activity, Object entity, final CallBack back) {
        LinearLayout dialogview = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        Util_MatchTip.initAllScreenText(dialogview);
        TextView dialogtitle = (TextView) dialogview.findViewById(R.id.dialogtitle);
        LinearLayout itemcontent = (LinearLayout) dialogview.findViewById(R.id.itemcontent);

        String org = (String) entity;
        String result = "";
        String[] orgarray = org.split("3");
        String[] orgarray2 = orgarray[1].split("2");
        result = orgarray[0].trim().replace("list", "");
        Pattern pattern=Pattern.compile("(.*?)lim(.*)");
        Matcher matcher=pattern.matcher(result);
        if(matcher.find()){
            result=matcher.group(1)+"-->限制:"+matcher.group(2);
        }
        dialogtitle.setText(result);
        Base_Entity itementity = new Entity_Demo();
        for (int i = 0; i < orgarray2.length; i++) {
            itementity.add(orgarray2[i].trim(), "");
        }
        final Adapter_BookDetail adapter = new Adapter_BookDetail(activity, itementity, itemcontent);
        adapter.setEditState(true).initView();
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.1), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.5)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Base_Entity resultentity = adapter.getResult();
                back.back(resultentity);

            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

    /**
     * 原始的 可以复制来修改
     *
     * @param activity
     * @param entity
     * @return
     */
    public static View showDialog_Org(final Activity activity, Object entity) {
        LinearLayout dialogview = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.dialog_chose_plan2check, null);
        Util_MatchTip.initAllScreenText(dialogview);
        AutoDialogBuilder builder = new AutoDialogBuilder(activity, dialogview,
                new LayoutParams((int) (Static_InfoApp.create().getAppScreenWidth() / 1.3), (int) (Static_InfoApp.create().getAppScreenHigh() / 1.85)));
        builder.setPositiveButton(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        }).setNegativeButton(null);
        builder.show();
        return dialogview;
    }

}
