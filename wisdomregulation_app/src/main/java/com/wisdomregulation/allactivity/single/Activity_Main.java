package com.wisdomregulation.allactivity.single;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.imagescan.ScanMainActivity;
import com.igexin.sdk.PushManager;
import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.base.Base_AyActivity;
import com.wisdomregulation.allactivity.mode.Mode_BombMain;
import com.wisdomregulation.allactivity.mode.Mode_BookPrinter;
import com.wisdomregulation.allactivity.mode.Mode_ChartMain;
import com.wisdomregulation.allactivity.mode.Mode_CheckMain;
import com.wisdomregulation.allactivity.mode.Mode_CompanyInfo;
import com.wisdomregulation.allactivity.mode.Mode_DangerousCheckMain;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.allactivity.mode.Mode_GovernmentContacts;
import com.wisdomregulation.allactivity.mode.Mode_GovernmentNotice;
import com.wisdomregulation.allactivity.mode.Mode_History;
import com.wisdomregulation.allactivity.mode.Mode_Law_Main;
import com.wisdomregulation.allactivity.mode.Mode_Library;
import com.wisdomregulation.allactivity.tmp.Tmp_PhotoActivity;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryDangerousFlag;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryLawDependence;
import com.wisdomregulation.data.entitylibrary.Entity_LibrarySafetyProduce;
import com.wisdomregulation.data.entitylibrary.Entity_LibrarySafetyProduceLaw;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryTypicalCase;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.data.entityother.Entity_Notice;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.test.TestEdit;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_File;
import com.wisdomregulation.utils.Util_Json;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;
import com.wisdomregulation.utils.Util_Sync;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.widget.KJSlidingMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wisdomregulation.allactivity.single.Activity_Plan.areamap;

public class Activity_Main extends Base_AyActivity {
    ImageView headpicture;
    TextView smallneedcheck;
    TextView smallneedcheck2;
    private TextView checkcount;
    private TextView checkagaincount;
    private TextView noticteitle;
    private TextView noticetime;
    private TextView more;
    ImageView isnewnotice;
    private KJSlidingMenu mSliding;
    private String checknumber = "0";
    private String checkagainnumber = "0";
    private List<String> menuList;
    private Dialog dialog;
    private Dialog menu;
    public static final String refreshHeadPicture = "Activity_Main.refreshHeadPicture";
    public static final String passToCheck = "Activity_Main.passToCheck";
    public static final String passToAgainCheck = "Activity_Main.passToAgainCheck";
    private TmpBroadcastReceiver receiver;
    private SharedPreferences sp;
    private String ishead;
    private Base_Entity noticeEntity;
    private boolean isrefresh = false;
    TextView usernametext;
    private int syncint = 0;
    private boolean crash = false;
    private int needsyncint;
    private List<String> crashSyncTable = new ArrayList<String>();
    private boolean canclicktosync = true;
    private final int synctime = 60000;// 手动点击同步间隔
    private int synctimeout = 60000000;//同步网络请求时限
    private boolean synctimeoutkey = true;
    private boolean isfromclick = false;
    private boolean isback = false;
    private int trytimes = 0;
    private String checkcountint;
    private String checkagaincountint;
    private String accountname;
    private List<String> tasklist;
    private int daytime;//一般日常同步
    private int markerdaytime;//程序员日常同步
    private long allsize = 0;
    private long nowsize = 0;
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case R.string.finishRefresh:
                    Activity_Main.this.dissmissWait();
                    if (noticeEntity != null) {
                        refreahNew(noticeEntity.getValue(0),
                                noticeEntity.getValue(3));
                    }
                    refreahPend(checkcountint, checkagaincountint);
                    //System.out.println("刷新状态恢复");
                    break;
                case R.string.finishDownLoadTableTmp:
                    //System.out.println("暂时回来了");
                    syncint++;
                    System.out.println("需要:"+needsyncint+",已经:"+syncint);
                    if (syncint < needsyncint) {

                    } else {
                        if (crash) {
//						Toast.makeText(Activity_Main.this, "同步数据返回有错，请稍后再试", Toast.LENGTH_SHORT).show();
//                            crash = false;
//                            refreshtip();
                            System.out.println("出错了");
                            trytimes++;

                            if(trytimes==2){
                                refreshtip();
                            }else{
                                toSync("网络问题重试"+trytimes+":");
                            }

                        } else {
                            crashSyncTable.clear();
                            //System.out.println("结束全部插入");
                            synctimeoutkey = false;
                            if (!synctimeoutkey) {
                                refreshtip();
                            }
                        }
                    }
                    break;
                case R.string.truncateTableFinish:
                    onfinishtruncate();
                    break;
                case R.string.changeprograss:
                    nowsize++;
                    Activity_Main.this.showWaitSecondNotAuto((int) allsize, (int) nowsize);
                    break;
                default:
                    break;
            }
        }

    };


    /**
     * 当数据完全清空
     */
    public void onfinishtruncate() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < tasklist.size(); i++) {
                    //System.out.println("分发同步任务");
                    getTableOnLine(tasklist.get(i));
                }
                //System.out.println("恢复同步状态");
                canclicktosync = true;
            }
        }).start();
    }

    @Override
    public void setRootView() {
        this.setContentView(R.layout.activity_main);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        inittasklist(0);
        checkispush();
        isfromclick = false;
//		if (Static_InfoApp.create().istest()) {
//			Intent intent = new Intent(this, FloatViewService.class);
//			// 启动FloatViewService
//			startService(intent);
//		}
        if (areamap.size() > 0) {

        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Base_Entity> areaquyu = Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_OrgData().put(1, "0b8415e08bf3474686e643318c0a497c")));
                    for (int i = 0; i < areaquyu.size(); i++) {
//                        System.out.println("来吧" + areaquyu.get(i).getId() + ":" + areaquyu.get(i).getValue(2) + ":" + areaquyu.get(i).getValue(3));
                        areamap.put(areaquyu.get(i).getValue(2), areaquyu.get(i).getValue(3));
                        areamap.put(areaquyu.get(i).getId(), areaquyu.get(i).getValue(3));
                    }
                }
            }).start();

        }
        sp = Activity_Main.this.getSharedPreferences("releaseconfig", MODE_PRIVATE);
        checkinit();
        inithead();


    }

    /**
     * 初始化头像
     */
    private void inithead() {
        ishead = sp.getString("ishead", "0");
        if (ishead.equals("1")) {
            headpicture.setImageBitmap(BitmapFactory.decodeFile(Static_InfoApp
                    .create().getPath() + "/ZhiHead/head.jpg"));
        }
    }

    /**
     * 判断是否是第一次打开app 是否需要 直接进行首次同步 是否数据同步状态被重置
     */
    private void checkinit() {
        String isfirst = "0";
        isfirst = sp.getString("isfirst", "0");

        PushManager.getInstance().initialize(this.getApplicationContext(), null);
        Util_MatchTip.initAllScreenText(smallneedcheck,
                Static_ConstantLib.TEXT_SMALL);
        Util_MatchTip.initAllScreenText(smallneedcheck2,
                Static_ConstantLib.TEXT_SMALL);

        if (Static_InfoApp.create().isshow()) {
            Activity_Main.this.showWait("测试更新代办");
            sp.edit().putLong("oldtime", System.currentTimeMillis()).commit();
            refreshtip();
        } else {
            if (isfirst.equals("0")) {
                Activity_Main.this.showWait("数据初始化,请保持网络畅通");
//				Activity_Main.this.showWaitSecondNotAuto("数据初始化,请保持网络畅通",0,0);
                inittasklist(0);
                checktoSync(0, false);
                sp.edit().putString("isfirst", "1").commit();
            } else {
                boolean isreset = sp.getBoolean("isrest", false);
                if (isreset) {
                    inittasklist(0);
                    checktoSync(0, false);
                    sp.edit().putBoolean("isrest", false).commit();
                } else {
                    //System.out.println("进入日常同步");
                    isrefresh = true;//当以上几种都没走 则会进入6分钟同步
                }


            }
        }
    }

    /**
     * 判断是否来自 Notification 因为设置了不会自动消失
     */
    private void checkispush() {
        boolean ispush = this.getIntent().getBooleanExtra("ispush", false);
        if (ispush) {
            int pushid = this.getIntent().getIntExtra("pushid", 0);
            NotificationManager manager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(pushid);

        }
    }

    /**
     * 初始化同步所需的任务 参数同步哪几个库 和 同步数量 主要是下载线上的库
     */
    private void inittasklist(int flag) {
        tasklist = new ArrayList<String>();
        switch (flag) {
            case 0:

                tasklist.clear();
                tasklist.add("bigplan");
////		更新片区任务 检查 检查项目详情 案件 任务
                tasklist.add("smallplan");
                tasklist.add("check");
                tasklist.add("checkdetail");
                tasklist.add("law");
//			更新企业信息 任务
                tasklist.add("companyinsert");
                tasklist.add("companyupdate");
//			tasklist.add("companydelete");
                //更新6个库 大概是6个 任务
                tasklist.add("saveproducebaseinsert");
                tasklist.add("saveproducebaseupdate");
//			tasklist.add("saveproducebasedelete");

//			tasklist.add("dangerousbaseinsert");
//			tasklist.add("dangerousbaseupdate");//目前不打开
//			tasklist.add("dangerousbasedelete");

                tasklist.add("lawdependencebaseinsert");
                tasklist.add("lawdependencebaseupdate");
//			tasklist.add("lawdependencebasedelete");

                tasklist.add("casespecialbaseinsert");
                tasklist.add("casespecialbaseupdate");
//			tasklist.add("casespecialbasedelete");

                tasklist.add("savelawbaseinsert");
                tasklist.add("savelawbaseupdate");
//			tasklist.add("savelawbasedelete");
                break;
            case 1:
                tasklist.clear();
                tasklist.add("bigplan");
////			更新片区任务 检查 检查项目详情 案件 任务
                tasklist.add("smallplan");
                tasklist.add("check");
                tasklist.add("checkdetail");
                tasklist.add("law");
//			更新企业信息 任务
                tasklist.add("companyinsert");
                tasklist.add("companyupdate");
//			tasklist.add("companydelete");
                //更新6个库 大概是6个 任务
                tasklist.add("saveproducebaseinsert");
                tasklist.add("saveproducebaseupdate");
//			tasklist.add("saveproducebasedelete");

//			tasklist.add("dangerousbaseinsert");
//			tasklist.add("dangerousbaseupdate");//目前不打开
//			tasklist.add("dangerousbasedelete");

                tasklist.add("lawdependencebaseinsert");
                tasklist.add("lawdependencebaseupdate");
//			tasklist.add("lawdependencebasedelete");

                tasklist.add("casespecialbaseinsert");
                tasklist.add("casespecialbaseupdate");
//			tasklist.add("casespecialbasedelete");

                tasklist.add("savelawbaseinsert");
                tasklist.add("savelawbaseupdate");
//			tasklist.add("savelawbasedelete");
                break;

            default:
                break;
        }


        needsyncint = tasklist.size();
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

        receiver = new TmpBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(refreshHeadPicture);
        filter.addAction(passToCheck);
        filter.addAction(passToAgainCheck);
        this.registerReceiver(receiver, filter);
    }

    @Override
    public void initWidget() {
        headpicture = (ImageView) findViewById(R.id.headpicture);
        smallneedcheck = (TextView) findViewById(R.id.smallneedcheck);
        smallneedcheck2 = (TextView) findViewById(R.id.smallneedcheck2);
        checkcount = (TextView) findViewById(R.id.checkcount);
        checkagaincount = (TextView) findViewById(R.id.checkagaincount);
        noticteitle = (TextView) findViewById(R.id.noticetitle);
        noticetime = (TextView) findViewById(R.id.noticetime);
        more = (TextView) findViewById(R.id.more);
        isnewnotice = (ImageView) findViewById(R.id.isnewnotice);
        mSliding = (KJSlidingMenu) findViewById(R.id.main_group);
        usernametext = (TextView) findViewById(R.id.usernametext);
//		mSliding.setHorizontalFadingEdgeEnabled(false); 
        mSliding.setShowAnim(true);
        if (Static_InfoApp.create().isshow()) {
            more.setClickable(false);
            more.setVisibility(View.INVISIBLE);
        }
    }

    public void initView() {

    }

    /**
     * 上传文件夹
     */
    public void uploadfile() {
        Util_File.checkisEmptyFile2delete(Static_InfoApp.create().getPath() + "/ZhiCollect");
        Util_File.getFileList2upload(Static_InfoApp.create().getPath() + "/ZhiCollect", "tmp");
    }

    public void refreahNew(String newtitle, String newtime) {
        noticteitle.setText(newtitle);
        noticetime.setText(newtime);
        String newnoticeall = sp.getString("NewNotice", "");
        if (newnoticeall.equals(noticeEntity.getValue(0))) {
            isnewnotice.setVisibility(View.INVISIBLE);
        } else {
            isnewnotice.setVisibility(View.VISIBLE);
        }
    }

    public void refreahPend(final String checkcount,
                            final String checkagaincount) {
        checknumber = checkcount;
        checkagainnumber = checkagaincount;
        Activity_Main.this.checkcount.setText(checkcount);
        Activity_Main.this.checkagaincount.setText(checkagaincount);


    }

    public void passtoagaincheck(View view) {
        Activity_Main.this.startActivity(new Intent(Activity_Main.this,
                Mode_CheckMain.class).putExtra("checkoragain", 2));
    }

    public void passtocheck(View view) {
        Activity_Main.this.startActivity(new Intent(Activity_Main.this,
                Mode_CheckMain.class));
    }

    public void passtoonlynotice(View view) {
        if (noticeEntity != null) {
            sp.edit().putString("NewNotice", noticeEntity.getValue(0)).commit();
            Activity_Main.this.startActivity(new Intent(
                    Activity_Main.this, Activity_Notice.class).putExtra(
                    "noticeEntity", noticeEntity));
        } else {
            sp.edit().putString("NewNotice", "").commit();
        }

    }

    /**
     * 刷新登陆人名字 待办数量
     */
    public void refreshtip() {
        trytimes=0;
        syncint = 0;
        isrefresh = true;//当以上几种都没走 则会进入6分钟同步
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<Base_Entity> otu = Util_MatchTip.getSearchResult(Help_DB.create().search(new Entity_Notice().init()));
                if (otu != null && otu.size() > 0) {
                    noticeEntity = otu.get(otu.size() - 1);

                }
                Activity_Main.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (usernametext.getText().toString()
                                .equals("欢迎登录,未登录!")) {
                            usernametext.setText("欢迎登录,"
                                    + Static_InfoApp.create().getUserName() + "!");
                        }
                    }
                });
                checkcountint = Util_MatchTip.getSearchResult(
                        Help_DB.create().query(Help_DB.createS().justgetSqlUNION(new Entity_Plan().init().putHack(3).puthacklogic(3, "or").putlogic2value(3, "=", Static_InfoApp.create().getAccountId() + "," + Static_InfoApp.create().getAccountOrg() + "," + Static_InfoApp.create().getAccountDeptOnly()).putlogic2value(5, "<>", "1"), (!Static_InfoApp.create().getUserAuthority().equals("super")) ? new Entity_Plan().init().setCreated(Static_InfoApp.create().getAccountId()).putlogic2value(5, "<>", "1") : new Entity_Plan().init().putlogic2value(5, "<>", "1")), new Entity_Plan().init().putHack(3).puthacklogic(3, "or").putlogic2value(3, "=", Static_InfoApp.create().getAccountId() + "," + Static_InfoApp.create().getAccountOrg() + "," + Static_InfoApp.create().getAccountDeptOnly()).putlogic2value(5, "<>", "1"))).size()
                        + "";
                checkagaincountint = Util_MatchTip.getSearchResult(
                        Help_DB.create().search(
                                new Entity_Check().init()
                                        .setCreated(Static_InfoApp.create().getAccountId())
                                        .putlogic2value(16, "=", "1")
                                        .putlogic2value(10, "<>", "1"))).size()
                        + "";
                handler.sendEmptyMessage(R.string.finishRefresh);

            }
        }).start();
    }

    @Override
    protected void onResume() {
        daytime = 7200000;
        markerdaytime = 360000;
        super.onResume();
        try {
            if (isrefresh) {
//				//System.out.println("开始检查日常");  日常同步 只有线上
//				inittasklist(1);
//				if(Static_InfoApp.create().ismarker()){
//					Activity_Main.this.showWait("日常程序员测试同步,请保持网络畅通");
//					checktoSync(markerdaytime,true);
//				}else if(Static_InfoApp.create().isshow()){
//					refreshtip();
//					}else{
//						Activity_Main.this.showWait("日常同步,请保持网络畅通");
//						checktoSync(daytime,true);
//					}
//					
////				}
                inittasklist(1);
                if (!Static_InfoApp.create().istest()) {
                    checktoSync(daytime, true);
                } else {
                    refreshtip();
                }
            }

            boolean dberror = sp.getBoolean("dberror", false);
            if (dberror) {
                Activity_Main.this.getSharedPreferences("releaseconfig", MODE_PRIVATE).edit().putLong("oldtime", System.currentTimeMillis()).commit();
                //System.out.println("tosync508");
                toSyncImp("数据库出错紧急同步");
            } else {

            }
        } catch (Exception e) {
            //System.out.println("SRX—ResumeError");
            Activity_Main.this.startActivity(new Intent(
                    Activity_Main.this, Activity_Welcome.class));
            Activity_Main.this.finishThis();
        }


    }

    @Override
    protected void onDestroy() {
//		if (Static_InfoApp.create().istest()) {
//			Intent intent = new Intent(this, FloatViewService.class);
//			// 终止FloatViewService
//			stopService(intent);
//		}

        try {

            Activity_Main.this.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();

    }

    /**
     * 从线上获取最新的数据
     *
     * @param jsontype
     */
    public void getTableOnLine(final String jsontype) {
        //System.out.println("更新失败下载数据库:"+jsontype);
        KJHttp kjh = new KJHttp();
        HttpParams params = new HttpParams();
        String url = "";
        if (jsontype.equals("bigplan")) {
            url = Static_ConstantLib.bigplanurl;

        } else if (jsontype.equals("smallplan")) {
            url = Static_ConstantLib.smallplanurl;
        } else if (jsontype.equals("check")) {
            url = Static_ConstantLib.checkurl;
            //System.out.println("检查表参数:"+Static_InfoApp.create().getAccountId());
            params.put("enforcement.created", Static_InfoApp.create().getAccountId());
        } else if (jsontype.equals("checkdetail")) {
            url = Static_ConstantLib.checkoptionurl;
            params.put("yinHuanXinXi.created", Static_InfoApp.create().getAccountId());
        } else if (jsontype.equals("law")) {
            url = Static_ConstantLib.lawurl;
            params.put("xingZhengZhiFa.created", Static_InfoApp.create().getAccountId());
        } else if (jsontype.equals("companyinsert")) {
            url = Static_ConstantLib.companyurl;
            String time = Util_Db.getTopCreateTime(new Entity_Company());
            //System.out.println("companyinsert创建时间:"+time);
            if (time.equals("")) {

            } else {
                params.put("enterprise.createTime", time);
            }

        } else if (jsontype.equals("companyupdate")) {
            url = Static_ConstantLib.companyurl;
            String time = Util_Db.getTopUpdateTime(new Entity_Company());
            //System.out.println("companyupdate更新时间:"+time);
            if (time.equals("")) {
                params.put("enterprise.updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                params.put("enterprise.updateTime", time);
            }

        } else if (jsontype.equals("companydelete")) {
            url = Static_ConstantLib.companyurl;
//			params.put("enterprise.status", "0");
        } else if (jsontype.equals("saveproducebaseinsert")) {
            url = Static_ConstantLib.saveproducebase;
            String time = Util_Db.getTopCreateTime(new Entity_LibrarySafetyProduce());
            //System.out.println("saveproducebaseinsert创建时间:"+time);
            if (time.equals("")) {

            } else {
                params.put("criterion.createTime", time);
            }

        } else if (jsontype.equals("saveproducebaseupdate")) {
            url = Static_ConstantLib.saveproducebase;
            String time = Util_Db.getTopUpdateTime(new Entity_LibrarySafetyProduce());
            //System.out.println("saveproducebaseupdate更新时间:"+time);
            if (time.equals("")) {
                params.put("criterion.updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                params.put("criterion.updateTime", time);
            }

        } else if (jsontype.equals("saveproducebasedelete")) {
            url = Static_ConstantLib.saveproducebase;
//			params.put("criterion.status", "0");
        } else if (jsontype.equals("dangerousbaseinsert")) {
            url = Static_ConstantLib.dangerousbase;
            String time = Util_Db.getTopCreateTime(new Entity_LibraryDangerousFlag());
            //System.out.println("dangerousbaseinsert创建时间:"+time);
            if (time.equals("")) {

            } else {
                params.put("tbMsds.createTime", time);
            }

        } else if (jsontype.equals("dangerousbaseupdate")) {
            url = Static_ConstantLib.dangerousbase;
            String time = Util_Db.getTopUpdateTime(new Entity_LibraryDangerousFlag());
            //System.out.println("dangerousbaseupdate更新时间:"+time);
            if (time.equals("")) {
                params.put("tbMsds.updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                params.put("tbMsds.updateTime", time);
            }

        } else if (jsontype.equals("dangerousbasedelete")) {
            url = Static_ConstantLib.dangerousbase;
//			params.put("tbMsds.status", "0");
        } else if (jsontype.equals("lawdependencebaseinsert")) {
            url = Static_ConstantLib.lawdependencebase;
            String time = Util_Db.getTopCreateTime(new Entity_LibraryLawDependence());
            //System.out.println("lawdependencebaseinsert创建时间:"+time);
            if (time.equals("")) {

            } else {
                params.put("zhiFaYiJuKu.createTime", time);
            }

        } else if (jsontype.equals("lawdependencebaseupdate")) {
            url = Static_ConstantLib.lawdependencebase;
            String time = Util_Db.getTopUpdateTime(new Entity_LibraryLawDependence());
            //System.out.println("lawdependencebaseupdate更新时间:"+time);
            if (time.equals("")) {
                params.put("zhiFaYiJuKu.updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                params.put("zhiFaYiJuKu.updateTime", time);
            }

        } else if (jsontype.equals("lawdependencebasedelete")) {
            url = Static_ConstantLib.lawdependencebase;
//			params.put("zhiFaYiJuKu.status", "0");
        } else if (jsontype.equals("casespecialbaseinsert")) {
            url = Static_ConstantLib.casespecialbase;
            String time = Util_Db.getTopCreateTime(new Entity_LibraryTypicalCase());
            //System.out.println("casespecialbaseinsert创建时间:"+time);
            if (time.equals("")) {

            } else {
                params.put("accidentCase.createTime", time);
            }

        } else if (jsontype.equals("casespecialbaseupdate")) {
            url = Static_ConstantLib.casespecialbase;
            String time = Util_Db.getTopUpdateTime(new Entity_LibraryTypicalCase());
            //System.out.println("casespecialbaseupdate更新时间:"+time);
            if (time.equals("")) {
                params.put("accidentCase.updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                params.put("accidentCase.updateTime", time);
            }

        } else if (jsontype.equals("casespecialbasedelete")) {
            url = Static_ConstantLib.casespecialbase;
//			params.put("accidentCase.status", "0");
        } else if (jsontype.equals("savelawbaseinsert")) {
            url = Static_ConstantLib.savelawbase;
            String time = Util_Db.getTopCreateTime(new Entity_LibrarySafetyProduceLaw());
            //System.out.println("savelawbaseinsert创建时间:"+time);
            if (time.equals("")) {

            } else {
                params.put("law.createTime", time);
            }

        } else if (jsontype.equals("savelawbaseupdate")) {
            url = Static_ConstantLib.savelawbase;
            String time = Util_Db.getTopUpdateTime(new Entity_LibrarySafetyProduceLaw());
            //System.out.println("savelawbaseupdate更新时间:"+time);
            if (time.equals("")) {
                params.put("law.updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            } else {
                params.put("law.updateTime", time);
            }

        } else if (jsontype.equals("savelawbasedelete")) {
            url = Static_ConstantLib.savelawbase;
//			params.put("law.status", "0");
        }
        kjh.post(Static_InfoApp.create().getiphead()
                        + url, params, false,
                new HttpCallBack() {

                    @Override
                    public void onSuccess(String t) {
//						System.out.println("更新返回:"+jsontype+":"+t);
                        boolean isjson = Util_String.isjson(t);

                        if (isjson) {
                            updateTable(t, jsontype);

                        } else {
                            System.out.println("更新失败:"+"："+jsontype);
                            crash = true;
                            crashSyncTable.add(jsontype);
                            handler.sendEmptyMessage(R.string.finishDownLoadTableTmp);
                        }

                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        // TODO Auto-generated method stub
                        super.onFailure(errorNo, strMsg);
                        System.out.println("更新失败:"+"："+jsontype);
                        crash = true;
                        crashSyncTable.add(jsontype);
                        handler.sendEmptyMessage(R.string.finishDownLoadTableTmp);
                    }

                });
    }

    /**
     * 从获得的 json来填充数据库字段
     *
     * @param json
     * @param jsontype
     */
    public void updateTable(final String json, final String jsontype) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //System.out.println("进行数据同步");
                try {
                    long tmp = 0;

                    tmp = new JSONObject(json).getLong("total");

                    allsize += tmp;
                    Activity_Main.this.showWaitSecondNotAuto((int) allsize, (int) nowsize);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Util_Json.get2insertResultSql(json, jsontype, new CallBack() {
                    @Override
                    public void back(Object resultlist) {
                        handler.sendEmptyMessage(R.string.changeprograss);
                    }
                });
                handler.sendEmptyMessage(R.string.finishDownLoadTableTmp);

            }
        }).start();

    }

    /**
     * 同步方法
     */
    public void toSyncImp(String title) {
        syncint=0;
        allsize = 0;//初始化需要同步的大小
        //System.out.println("同步标题:"+title);
        Activity_Main.this.showWait("" + title + "数据同步中,请保持网络畅通...");
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (synctimeoutkey) {
                    Activity_Main.this.dissmissWait();
                    Toast.makeText(Activity_Main.this, "数据同步超时请检查网络", Toast.LENGTH_LONG).show();
                } else {

                }

            }
        }, synctimeout);
        if (crash) {
            if (crashSyncTable.size() > 0) {
                needsyncint = crashSyncTable.size();
                final List<String> crashtmp=new ArrayList<>();
                crashtmp.addAll(crashSyncTable);
                crashSyncTable.clear();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        crash = false;
                        for (int i = 0; i < crashtmp.size(); i++) {
                            getTableOnLine(crashtmp.get(i));
                        }

                    }
                }).start();

            } else {
                onfinishtruncate();
            }
        } else {

            toupJson();
//			toupJson2();

        }
    }

    private void toupJson() {
        KJHttp kjh = new KJHttp();
        HttpParams params = new HttpParams();
        String upjson = Util_Sync.getAllSyncJson();

        //System.out.println("同步上传的数据:"+upjson);
        if (upjson.equals("")) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    uploadfile();
                    truncatetable();
                    handler.sendEmptyMessage(R.string.truncateTableFinish);

                }
            }).start();
        } else {
            params.put("json", upjson);
            kjh.post(Static_InfoApp.create().getiphead() + Static_ConstantLib.syncurl,
                    params, true,
                    new HttpCallBack() {

                        @Override
                        public void onSuccess(String t) {
                            if (t.equals("1")) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        uploadfile();
                                        truncatetable();
                                        handler.sendEmptyMessage(R.string.truncateTableFinish);

                                    }
                                }).start();

                            } else if (t.equals("0")) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        uploadfile();
                                        truncatetable();
                                        handler.sendEmptyMessage(R.string.truncateTableFinish);

                                    }
                                }).start();
                            } else {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        uploadfile();
                                        truncatetable();
                                        handler.sendEmptyMessage(R.string.truncateTableFinish);
                                        Activity_Main.this.runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                Toast.makeText(Activity_Main.this, "线上出错请联系管理员", Toast.LENGTH_LONG).show();
//										Activity_Main.this.dissmissWait();

                                            }
                                        });
                                    }
                                }).start();
                            }
                        }

                        @Override
                        public void onFailure(int errorNo, String strMsg) {
                            // TODO Auto-generated method stub
                            super.onFailure(errorNo, strMsg);
                            Toast.makeText(Activity_Main.this, "连接出错,检查网络", Toast.LENGTH_LONG).show();
                            //System.out.println("连接出错,检查网络:"+strMsg);
                            Activity_Main.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Activity_Main.this.dissmissWait();
                                    canclicktosync = true;
                                }
                            });
                        }

                    });
        }
    }

    public void toSync() {
        if (!Static_InfoApp.create().isshow()) {
            toSyncImp("");
            sp.edit().putBoolean("isrest", false).commit();
            sp.edit().putBoolean("dberror", false).commit();
        }

    }
    public void toSync(String title) {
        if (!Static_InfoApp.create().isshow()) {
            toSyncImp(title);
            sp.edit().putBoolean("isrest", false).commit();
            sp.edit().putBoolean("dberror", false).commit();
        }

    }
    /**
     * 清空几个数据表
     */
    public void truncatetable() {
//		Help_DB.create().truncate(new Entity_BigPlan());
        Help_DB.create().truncate(new Entity_Plan());
        Help_DB.create().truncate(new Entity_Check());
        Help_DB.create().truncate(new Entity_CheckDetail());
        Help_DB.create().truncate(new Entity_Law());
    }

    /**
     * 判断是否可以同步 涉及同步间隔
     */
    public void checktoSync(long timeout, boolean justtimeout) {
        if (Static_InfoApp.create().isnoweb()) {
            refreshtip();//如果是离线状态就不同步
            return;
        }
        if (canclicktosync) {
            canclicktosync = false;
            long oldtime = Activity_Main.this.getSharedPreferences(
                    "releaseconfig", MODE_PRIVATE).getLong("oldtime", 0);
            if (justtimeout) {
                long nowtime = System.currentTimeMillis();
                if (nowtime - oldtime > timeout) {
                    //System.out.println("只是超时同步" + oldtime);
                    oldtime = nowtime;
                    Activity_Main.this
                            .getSharedPreferences("releaseconfig", MODE_PRIVATE)
                            .edit().putLong("oldtime", oldtime).commit();
                    //System.out.println("tosync942");
                    toSync();
                } else {
                    boolean isreset = sp.getBoolean("isrest", false);
                    if (isreset) {
                        //System.out.println("重置同步");
                        oldtime = nowtime;
                        Activity_Main.this
                                .getSharedPreferences("releaseconfig",
                                        MODE_PRIVATE).edit()
                                .putLong("oldtime", oldtime).commit();
                        //System.out.println("tosync950");
                        toSync();
                    } else {
                        if (isfromclick) {
                            if (Static_InfoApp.create().ismarker()) {
                                //System.out.println("debug同步");
                                oldtime = nowtime;
                                Activity_Main.this
                                        .getSharedPreferences("releaseconfig",
                                                MODE_PRIVATE).edit()
                                        .putLong("oldtime", oldtime).commit();
                                //System.out.println("tosync957");
                                toSync();
                            } else {
                                Activity_Main.this.showToast("一分钟之内不能同步,剩余:" + ((timeout - (nowtime - oldtime)) / 1000) + "秒");
                                canclicktosync = true;
                                refreshtip();
                                //System.out.println("程序员测试1");
                            }
                            isfromclick = false;
                        } else {
                            //System.out.println("程序员测试1");
                            canclicktosync = true;
                            refreshtip();
                        }


                    }

                }

            } else {
                if (oldtime == 0) {
                    //System.out.println("零时同步");
                    oldtime = System.currentTimeMillis();
                    Activity_Main.this
                            .getSharedPreferences("releaseconfig", MODE_PRIVATE)
                            .edit().putLong("oldtime", oldtime).commit();
                    //System.out.println("tosync933");
                    toSync();

                } else {
                    long nowtime = System.currentTimeMillis();
                    if (nowtime - oldtime > timeout) {
                        //System.out.println("超时同步");
                        oldtime = nowtime;
                        Activity_Main.this
                                .getSharedPreferences("releaseconfig",
                                        MODE_PRIVATE).edit()
                                .putLong("oldtime", oldtime).commit();
                        //System.out.println("tosync942");
                        toSync();
                    } else {
                        boolean isreset = sp.getBoolean("isrest", false);
                        if (isreset) {
                            //System.out.println("重置同步");
                            oldtime = nowtime;
                            Activity_Main.this
                                    .getSharedPreferences("releaseconfig",
                                            MODE_PRIVATE).edit()
                                    .putLong("oldtime", oldtime).commit();
                            //System.out.println("tosync950");
                            toSync();
                        } else {
                            if (isfromclick) {
                                if (Static_InfoApp.create().ismarker()) {
                                    //System.out.println("debug同步");
                                    oldtime = nowtime;
                                    Activity_Main.this
                                            .getSharedPreferences(
                                                    "releaseconfig",
                                                    MODE_PRIVATE).edit()
                                            .putLong("oldtime", oldtime)
                                            .commit();
                                    //System.out.println("tosync957");
                                    toSync();
                                } else {
                                    Activity_Main.this.showToast("一分钟之内不能同步,剩余:" + ((timeout - (nowtime - oldtime)) / 1000) + "秒");
                                    canclicktosync = true;
                                    refreshtip();
                                    //System.out.println("程序员测试1");
                                }
                                isfromclick = false;
                            } else {
                                //System.out.println("程序员测试1");
                                canclicktosync = true;
                                refreshtip();

                            }


                        }

                    }
                }
            }

        } else {
            //System.out.println("同步状态未释放");
            refreshtip();
            canclicktosync = true;
        }
        isfromclick = false;
    }

    public void toSync(View view) {
        //System.out.println("SRXFG1");
        if (Static_InfoApp.create().isshow()) {
            if (Static_InfoApp.create().ismarker()) {
                //System.out.println("SRXFG2");
                isfromclick = true;
//				inittasklist(1);//设置手动同步不进行删除同步 减少同步时间
                checktoSync(synctime, true);
            } else {
                toastshow("演示版本不可同步线上数据");
            }
        } else {
            //System.out.println("SRXFG4");
            isfromclick = true;
//			inittasklist(1);//设置手动同步不进行删除同步 减少同步时间
            checktoSync(synctime, true);
        }


    }

    public void showPhotoTmp(View view) {
        dialog.dismiss();
        Activity_Main.this.startActivity(new Intent(Activity_Main.this,
                Tmp_PhotoActivity.class));
    }

    /**
     * 刷新头像
     */
    public void refreshHead() {
        headpicture.setImageBitmap(Static_InfoApp.head);
        if (sp.getString("ishead", "0").equals("0")) {
            sp.edit().putString("ishead", "1").commit();
        }

    }

    public void showPictureChose(View view) {
        dialog = showalbum();
    }

    public void cancelChosePicture(View view) {
        dialog.dismiss();
    }

    public void showChosePictureLibrary(View view) {
        dialog.dismiss();
        Activity_Main.this.startActivity(new Intent(Activity_Main.this,
                ScanMainActivity.class));
    }

    public void supertoback(View view) {
        menu.dismiss();
        Activity_Main.this.finishThis();
    }

    /**
     * 打开相册或者相机 来采集头像
     *
     * @return
     */
    private Dialog showalbum() {
        View view = getLayoutInflater().inflate(R.layout.dialog_photo_under,
                null);
        Util_MatchTip.initAllScreenText(view);
        Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * 打开测试菜单 后期将加入新功能 目前debug阶段
     *
     * @return
     */
    private Dialog showDialogMenu() {
        View view;
        if (Static_InfoApp.create().ismarker()) {
            view = getLayoutInflater().inflate(R.layout.dialog_menutest, null);
        } else {
            view = getLayoutInflater().inflate(R.layout.dialog_menu, null);
        }

        Util_MatchTip.initAllScreenText(view);
        final Dialog dialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散

        dialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        dialog.show();
        return dialog;
    }

    public void cancelMenu(View view) {
        if (menu != null) {
            menu.dismiss();
        }
    }

    public void checkshowmenu() {
        if (menu != null && menu.isShowing()) {
            menu.dismiss();
        } else {

            menu = showDialogMenu();


        }
    }

    public void showTest(View view) {
        menu.dismiss();
        this.startActivity(new Intent(this, TestEdit.class));
    }

    public void showUpdate(View view) {
        //更新接口还没有
//		try {
//			PackageManager pm = this.getPackageManager();
//			PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
//			String versionNamei = pi.versionName;
//			String versioncodei = pi.versionCode + "";
//
//			KJHttp kjh = new KJHttp();
//			HttpParams params = new HttpParams();
//			params.put("apklevel", versionNamei + "");
//			Activity_Main.this.showWait();
//			kjh.get(Static_ConstantLib.serviceip + "/IsNew", params, false,
//					new HttpCallBack() {
//						@Override
//						public void onSuccess(String t) {
//							super.onSuccess(t);
//							try {
//								JSONObject js = new JSONObject(t);
//								String nowlevel = js.getString("appLevel");
//								String newlevel = js.getString("newLevel");
//								String path = js.getString("upPath");
//								Activity_Main.this.dissmissWait();
//
//								selectUpdateDialog(nowlevel, newlevel, path);
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						}
//
//						@Override
//						public void onFailure(int errorNo, String strMsg) {
//							// TODO Auto-generated method stub
//							super.onFailure(errorNo, strMsg);
//							Activity_Main.this.dissmissWait();
//							Toast.makeText(Activity_Main.this, "网络连接出错",
//									Toast.LENGTH_SHORT).show();
//						}
//					});
//			menu.dismiss();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    public void selectUpdateDialog(String nowlevel, String newlevel, String path) {
        Map<String, String> datamap = new HashMap<String, String>();
        datamap.put("nowlevel", nowlevel);
        datamap.put("newlevel", newlevel);
        datamap.put("path", path);
        View selectdialog = Dialog_Tool.showDialog_UpAPP(Activity_Main.this,
                datamap);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            checkshowmenu();

        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void toMode(View view) {
        //System.out.println("按下分支");
        int key = view.getId();
        switch (key) {
            case R.id.Mode_CompanyInfo:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_CompanyInfo.class));
                break;
            case R.id.Mode_CheckMain:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_CheckMain.class));
                break;
            case R.id.Mode_Law_Main:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_Law_Main.class));
                    } else {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_Law_Main.class));
                    }
                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_DangerousMain:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_DangerousCheckMain.class));
                    } else {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_DangerousCheckMain.class));
                    }

                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_History:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_History.class));


                break;
            case R.id.Mode_BookPrinter:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_BookPrinter.class));
                    } else {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_BookPrinter.class));
                    }

                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_GovernmentContacts:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_GovernmentContacts.class));
                    } else {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_GovernmentContacts.class));
                    }

                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_GovernmentNotice:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_GovernmentNotice.class));
                    } else {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_GovernmentNotice.class));
                    }

                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_ChartAll:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_ChartMain.class));


                break;
            case R.id.Mode_MaxDangerous:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_BombMain.class));
                    } else {
                        if (Static_InfoApp.create().isshow()) {
                            Activity_Main.this.startActivity(new Intent(
                                    Activity_Main.this, Mode_BombMain.class));
                        } else {
                            Activity_Main.this.toastshow("该功能开发中");
                        }

                    }

                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_EvidenceCollect:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_EvidenceCollect.class));
                break;
            case R.id.Mode_Library1:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_Library.class)
                        .putExtra("level", 1));
                break;
            case R.id.Mode_Library2:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_Library.class)
                        .putExtra("level", 2));
                break;
            case R.id.Mode_Library3:
                if (Static_InfoApp.create().isshow()) {
                    if (Static_InfoApp.create().ismarker()) {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_Library.class)
                                .putExtra("level", 3));
                    } else {
                        Activity_Main.this.startActivity(new Intent(
                                Activity_Main.this, Mode_Library.class)
                                .putExtra("level", 3));
                    }

                } else {
                    Activity_Main.this.toastshow("该功能开发中");
                }

                break;
            case R.id.Mode_Library4:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_Library.class)
                        .putExtra("level", 4));
                break;
            case R.id.Mode_Library5:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_Library.class)
                        .putExtra("level", 5));
                break;
            case R.id.Mode_Library6:
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_Library.class)
                        .putExtra("level", 6));
                break;
            case R.id.loginout:
                sp = Activity_Main.this.getSharedPreferences(
                        "releaseconfig", MODE_PRIVATE);
                sp.edit().putString("autologin", "0").commit();
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Activity_Login.class));
                Activity_Main.this.finishThis();

                break;
            case R.id.loginout2:
                sp = Activity_Main.this.getSharedPreferences(
                        "releaseconfig", MODE_PRIVATE);
                sp.edit().putString("autologin", "0").commit();
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Activity_Login.class));
                Activity_Main.this.finishThis();

                break;
            case R.id.mainicon:
                checkshowmenu();
                break;
            case R.id.usernametext:
//			sss=null;
            default:
                break;
        }
    }


    class TmpBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(refreshHeadPicture)) {
                Activity_Main.this.refreshHead();
            }
            if (intent.getAction().equals(passToCheck)) {
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_CheckMain.class));
            }
            if (intent.getAction().equals(passToAgainCheck)) {
                Activity_Main.this.startActivity(new Intent(
                        Activity_Main.this, Mode_CheckMain.class).putExtra(
                        "checkoragain", 2));
            }

        }

    }

}
