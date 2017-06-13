package com.wisdomregulation.utils;

import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryDangerousCheck;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryDangerousFlag;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryLawDependence;
import com.wisdomregulation.data.entitylibrary.Entity_LibrarySafetyProduce;
import com.wisdomregulation.data.entitylibrary.Entity_LibrarySafetyProduceLaw;
import com.wisdomregulation.data.entitylibrary.Entity_LibraryTypicalCase;
import com.wisdomregulation.data.entityother.Entity_BigPlan;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_CheckDetail;
import com.wisdomregulation.data.entityother.Entity_Company;
import com.wisdomregulation.data.entityother.Entity_Law;
import com.wisdomregulation.data.entityother.Entity_LibraryLawDep;
import com.wisdomregulation.data.entityother.Entity_OrgData;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Util_Json {
    /**
     * json网络数据转为本地数据库字段
     * 记住与数据库字段相同 字母大小写一样 出问题找后台 后台给的大小写有问题
     * @param jsonstring
     * @param jsontype
     * @return
     */
    public static void get2insertResultSql(String jsonstring,String jsontype,CallBack back) {


        try {
            //System.out.println("获得事务");
            List<JSONObject> total = new ArrayList<JSONObject>();


            String jsontmp = jsonstring;
            JSONObject ob = new JSONObject(jsontmp);
            JSONArray array = ob.getJSONArray("rows");
            int max=array.length();
            if(max>0){

                //System.out.println("打开事务:"+max);
                Help_DB.create().beginTransaction();
            }
//				if(back!=null){
//					back.back(new int[]{max,0});
//				}

            for (int i = 0; i < array.length(); i++) {
                //System.out.println("事务操作");
                Base_Entity ldc = null;
                if (jsontype.equals("aqscbz")||jsontype.equals("saveproducebaseinsert")||jsontype.equals("saveproducebaseupdate")) {
                    ldc = new Entity_LibrarySafetyProduce().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.nput("标准名称", obt.getString("bzName"));
                    ldc.nput("颁布部门", obt.getString("promDepartment"));
                    ldc.nput("颁布日期", obt.getString("publishTime"));
                    ldc.nput("标准类型", obt.getString("bzType"));
                    ldc.nput("内容", obt.getString("content"));
                    ldc.nput("标准编号", obt.getString("bzNum"));
                    ldc.nput("生效日期", obt.getString("effectiveTime"));
                    ldc.nput("实施日期", obt.getString("implementTime"));

                }else if(jsontype.equals("saveproducebasedelete")){
                    ldc = new Entity_LibrarySafetyProduce().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));

                }
                else if (jsontype.equals("whp")||jsontype.equals("dangerousbaseinsert")||jsontype.equals("dangerousbaseupdate")) {
                    ldc = new Entity_LibraryDangerousFlag().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");

                    ldc.setId(obt.getString("id"));

//						ldc.setStatus(obt.getString("status"));
//						ldc.setCreated(obt.getString("CREATED"));
//						ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
//						ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("zwm"), "化学品中文名");
                    ldc.uput(obt.getString("ywm"), "化学品英文名");
                    ldc.uput(obt.getString("scqy"), "生产企业");
                    ldc.uput(obt.getString("fzs"), "分子式");
                    ldc.uput(obt.getString("fzl"), "分子量");
                    ldc.uput(obt.getString("cas"), "CAS号");
                    ldc.uput(obt.getString("rtecs"), "RTECS号");
                    ldc.uput(obt.getString("un"), "UN编号");
                    ldc.uput(obt.getString("wxhwbh"), "危险货物编号");
                    ldc.uput(obt.getString("imdg"), "IMDG规则页码");
                    ldc.uput(obt.getString("wgyxz"), "外观与形状");
                    ldc.uput(obt.getString("zyyt"), "主要用途");
                    ldc.uput(obt.getString("rd"), "熔点");
                    ldc.uput(obt.getString("fd"), "沸点");
                    ldc.uput(obt.getString("xdmds"), "相对密度水");
                    ldc.uput(obt.getString("xdmdkq"), "相对密度空气");
                    ldc.uput(obt.getString("bhzqs"), "饱和蒸汽水");
                    ldc.uput(obt.getString("rjx"), "溶解性");
                    ldc.uput(obt.getString("rsx"), "燃烧性");
                    ldc.uput(obt.getString("jghxfj"), "监规火险分级");
                    ldc.uput(obt.getString("sd"), "闪点");
                    ldc.uput(obt.getString("zrwd"), "自然温度");
                    ldc.uput(obt.getString("bzxx"), "爆炸下限");
                    ldc.uput(obt.getString("bzsx"), "爆炸上限");
                    ldc.uput(obt.getString("wxtx"), "危险特性");
                    ldc.uput(obt.getString("wxfjch"), "燃烧分解产物");
                    ldc.uput(obt.getString("wdx"), "稳定性");
                    ldc.uput(obt.getString("jhwh"), "聚合危害");
                    ldc.uput(obt.getString("jjw"), "禁忌物");
                    ldc.uput(obt.getString("mhff"), "灭火方法");
                    ldc.uput(obt.getString("wxxlb"), "危险性类别");
                    ldc.uput(obt.getString("wxhwbzbz"), "危险货物包装标志");
                    ldc.uput(obt.getString("bzlb"), "包装类别");
                    ldc.uput(obt.getString("cyzysx"), "储运注意事项");
                    ldc.uput(obt.getString("jcxz"), "接触限值");
                    ldc.uput(obt.getString("qrtj"), "侵入途径");
                    ldc.uput(obt.getString("dx"), "毒性");
                    ldc.uput(obt.getString("jkwh"), "健康危害");
                    ldc.uput(obt.getString("pfjc"), "皮肤接触");
                    ldc.uput(obt.getString("yjjc"), "眼镜接触");
                    ldc.uput(obt.getString("xr"), "吸入");
                    ldc.uput(obt.getString("sr"), "食入");
                    ldc.uput(obt.getString("gckz"), "工程控制");
                    ldc.uput(obt.getString("hxxtbh"), "呼吸系统保护");
                    ldc.uput(obt.getString("yjbh"), "眼镜保护");
                    ldc.uput(obt.getString("fhf"), "防护服");
                    ldc.uput(obt.getString("sfh"), "手防护");
                    ldc.uput(obt.getString("ljwd"), "临界温度");
                    ldc.uput(obt.getString("ljyl"), "临界压力");
                    ldc.uput(obt.getString("rsr"), "燃烧热");
                    ldc.uput(obt.getString("bmjctj"), "避免接触的条件");
                    ldc.uput(obt.getString("xlcz"), "泄漏处置");
                    ldc.uput(obt.getString("qt"), "其他");
                    ldc.uput(obt.getString("num"), "num");
                    ldc.uput(obt.getString("slxlglbj"), "少量泄漏隔离半径");
                    ldc.uput(obt.getString("slxlbtssbj"), "少量泄漏白天疏散半径");
                    ldc.uput(obt.getString("slxlyjssbj"), "少量泄漏夜间疏散半径");
                    ldc.uput(obt.getString("dlxlglbj"), "大量泄漏隔离半径");
                    ldc.uput(obt.getString("dlxlbtssbj"), "大量泄漏白天疏散半径");
                    ldc.uput(obt.getString("dlxlyjssbj"), "大量泄漏夜间疏散半径");
                    ldc.uput(obt.getString("zcljl"), "贮存临界量(kg)");
                    ldc.uput(obt.getString("scljl"), "生产临界量(kg)");
                    ldc.uput(obt.getString("version"), "版本号");

                }else if(jsontype.equals("dangerousbasedelete")){
                    ldc = new Entity_LibraryDangerousFlag().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));

                }
                else if (jsontype.equals("yh")) {
                    ldc = new Entity_LibraryDangerousCheck().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.nput("隐患排查名字", obt.getString("sanjyhzcbz"));
                    ldc.nput("自查标准具体内容", obt.getString("zcbzxjtnr"));
                    ldc.nput("IV四级隐患自查标准", obt.getString("sijyhzcbz"));
                    ldc.nput("III三级隐患自查标准", obt.getString("sanjyhzcbz"));
                    ldc.nput("II二级隐患自查标准", obt.getString("ejyhzcbzName"));
                    ldc.nput("I一级隐患自查标准", obt.getString("yjyhzcbzName"));
                    ldc.nput("监督行业大类", obt.getString("jcbhylxName"));
                    ldc.nput("参考依据", obt.getString("jcbhylx"));
                    ldc.nput("监督行业小类", obt.getString("jghylx"));

                }else if(jsontype.equals("checkoptinonrel")||jsontype.equals("lawdependencebaseinsert")||jsontype.equals("lawdependencebaseupdate")){
                    ldc = new Entity_LibraryLawDependence().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("yiJiFenLei"), "检查项目一级");
                    ldc.uput(obt.getString("erJiFenLei"), "检查项目二级");
                    ldc.uput(obt.getString("sanJiFenLe"), "检查项目三级");
                    ldc.uput(obt.getString("siJiFenLei"), "检查项目四级");
                    ldc.uput(obt.getString("jianChaYao"), "检查要求");
                    ldc.uput(obt.getString("zhiFaYiJu"), "执法依据关联");


                }else if(jsontype.equals("lawdependencebasedelete")){
                    ldc = new Entity_LibraryLawDependence().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));

                }
                else if (jsontype.equals("dxsgal")||jsontype.equals("casespecialbaseinsert")||jsontype.equals("casespecialbaseupdate")) {
                    ldc = new Entity_LibraryTypicalCase().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.nput("事故标题", obt.getString("caseTitle"));
                    ldc.nput("涉及行业", obt.getString("caseTrade"));
                    ldc.nput("事故类型", obt.getString("caseType"));
                    ldc.nput("事故模型", obt.getString("model"));
                    ldc.nput("发生日期", obt.getString("caseTime"));
                    ldc.nput("财产损失", obt.getString("propertyToll"));
                    ldc.nput("预防措施", obt.getString("precaution"));
                    ldc.nput("事故起因", obt.getString("caseCause"));
                    ldc.nput("发生地点", obt.getString("caseAddress"));
                    ldc.nput("轻伤人数", obt.getString("walkingCase"));
                    ldc.nput("重伤人数", obt.getString("stretcherCase"));
                    ldc.nput("死亡人数", obt.getString("deathToll"));

                }else if(jsontype.equals("casespecialbasedelete")){
                    ldc = new Entity_LibraryTypicalCase().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));

                }
                else if (jsontype.equals("aqscfg")||jsontype.equals("savelawbaseinsert")||jsontype.equals("savelawbaseupdate")) {
                    ldc = new Entity_LibrarySafetyProduceLaw()
                            .init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.nput("法规名称", obt.getString("fgName"));
                    ldc.nput("法规类型", obt.getString("fgTypeId"));
                    ldc.nput("法规编号", obt.getString("fgNum"));
                    ldc.nput("颁布地区", obt.getString("promAreaId"));
                    ldc.nput("颁布部门", obt.getString("promDepartment"));
                    ldc.nput("颁布日期", obt.getString("publishTime"));
                    ldc.nput("内容", obt.getString("content"));
                    ldc.nput("生效日期", obt.getString("effectiveTime"));
                    ldc.nput("实施日期", obt.getString("implementTime"));

                }else if(jsontype.equals("savelawbasedelete")){
                    ldc = new Entity_LibrarySafetyProduceLaw().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));

                }
                else if(jsontype.equals("qiyemax")||jsontype.equals("companyinsert")||jsontype.equals("companyupdate")){
                    ldc = new Entity_Company().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("ID"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("QYMC"),"企业名称");
                    ldc.uput(obt.getString("gszch"),"工商注册号");
                    ldc.uput(obt.getString("ZZJGDM"),"组织机构代码");
                    ldc.uput(obt.getString("clrq"),"成立日期");
                    ldc.uput(obt.getString("FDDBR"),"法定代表人");
                    ldc.uput(obt.getString("LXDH"),"联系电话");
                    ldc.uput(obt.getString("dzyx"),"电子邮箱");
                    ldc.uput(obt.getString("zcdz"),"注册地址");
                    ldc.uput(obt.getString("yzbm"),"邮政编码");
                    ldc.uput(obt.getString("xzqh"),"行政区划市");
                    ldc.uput(obt.getString("jjlx"),"经济类型");
                    ldc.uput(obt.getString("HYLB"),"行业类别");
                    ldc.uput(obt.getString("lsgx"),"隶属关系");
                    ldc.uput(obt.getString("jyfw"),"经营范围");
                    ldc.uput(obt.getString("zczj"),"注册资金（万）");
                    ldc.uput(obt.getString("QYWZJD"),"企业位置经度");
                    ldc.uput(obt.getString("QYWZWD"),"企业位置纬度");
                    ldc.uput(obt.getString("ZYFZR"),"主要负责人");
                    ldc.uput(obt.getString("ZYFZRgddh"),"主要负责人固定电话");
                    ldc.uput(obt.getString("ZYFZRyddh"),"主要负责人移动电话");
                    ldc.uput(obt.getString("ZYFZRdzyx"),"主要负责人电子邮箱");
                    ldc.uput(obt.getString("aqfzr"),"安全负责人");
                    ldc.uput(obt.getString("aqfzrgddh"),"安全负责人固定电话");
                    ldc.uput(obt.getString("aqfzryddh"),"安全负责人移动电话");
                    ldc.uput(obt.getString("aqfzrdzyx"),"安全负责人电子邮箱");
                    ldc.uput(obt.getString("cyrysl"),"从业人员数量");
                    ldc.uput(obt.getString("tzzyrysl"),"特种作业人员数量");
                    ldc.uput(obt.getString("zzaqscglrysl"),"专职安全生产管理人员数量");
                    ldc.uput(obt.getString("zcaqgcsrys"),"注册安全工程师人员数量");
                    ldc.uput(obt.getString("scjydz"),"生产经营地址");
                    ldc.uput(obt.getString("QYGM"),"企业规模");
                    ldc.uput(obt.getString("gmqk"),"规模情况");
                    ldc.uput(obt.getString("jgfl"),"监管分类");
                    ldc.uput(obt.getString("XZQHJD"),"所在街道");
                    ldc.uput(obt.getString("xzqhs"),"行政区划省");
                    ldc.uput(obt.getString("XZQHQX"),"所在区县");
                    ldc.uput(obt.getString("sfylsjt"),"是否有隶属集团");
                    ldc.uput(obt.getString("lsjtmc"),"隶属集团名称");
                    ldc.uput(obt.getString("ZXJGHYA"),"专项监管大类");
                    ldc.uput(obt.getString("ZXJGHYB"),"专项监管小类");
                    ldc.uput(obt.getString("clique"),"是否是集团");
                    ldc.uput(obt.getString("HYLBxl"),"行业类别小类");
                    ldc.uput(obt.getString("jjlxxl"),"经济类型小类");
                    ldc.uput(obt.getString("zxzllxname"),"专项治理类型");
                    ldc.uput(obt.getString("QYZPDJ"), "企业自评等级");
                    ldc.uput(obt.getString("jghyxl"),"监管行业小类");
                    ldc.uput(obt.getString("deptName"), "主管部门");//返回数据和数据库字段非完全匹配 将会写兼容查询

                }else if(jsontype.equals("companydelete")){
                    ldc = new Entity_Company().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("ID"));

                }
                else if(jsontype.equals("law2checkcontent")){
                    ldc = new Entity_LibraryLawDep().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("LAW_CONTENT"), "法规内容");
                    ldc.uput(obt.getString("LAW_NAME"), "法规名字");
                    ldc.uput(obt.getString("TYPE"), "检查项属性");


                }
                else if(jsontype.equals("orgdata")){
                    ldc = new Entity_OrgData().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("ID"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("PARENT_ID"), "父id");
                    ldc.uput(obt.getString("TYPE_ID"), "类型id");
                    ldc.uput(obt.getString("DATA_CODE"), "元数据数据码");
                    ldc.uput(obt.getString("DATA_NAME"), "元数据名");


                }
//					 else if(jsontype.equals("user")){
//						 	ldc = new Entity_User().init();
//							JSONObject obt = new JSONObject(array.get(i).toString());
//							ldc.setDatastate("server");
//							ldc.setId(obt.getString("id"));
//							ldc.setCreated(obt.getString("created"));
//							ldc.setUpDatadate(obt.getString("update_time"));
//							ldc.setCreatedatadate(obt.getString("create_time"));
//
//							ldc.uput(obt.getString("account"), "登录名");
//							ldc.uput(obt.getString("user_name"), "用户名");
//							ldc.uput(obt.getString("business"), "职业");
//							ldc.uput(obt.getString("user_type"), "用户类型");
//
//
//					 }
                else if(jsontype.equals("bigplan")){
                    ldc = new Entity_BigPlan().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("PLAN_NAME"), "执法计划名称");
                    ldc.uput(obt.getString("PLAN_TYPE"), "执法计划类型");
                    ldc.uput(obt.getString("PLAN_LEIB"), "检查类别");
                    ldc.uput(obt.getString("PLAN_TIMES"), "检查时间起");
                    ldc.uput(obt.getString("PLAN_TIMEE"), "检查时间至");
                    ldc.uput(obt.getString("PLAN_FZR"), "负责人名称");
                    ldc.uput(obt.getString("PLAN_FZRBH"), "负责人");
                    ldc.uput(obt.getString("PLAN_FZDW"), "负责单位名称");
                    ldc.uput(obt.getString("PLAN_FZDWBH"), "负责单位");
                    ldc.uput(obt.getString("PLAN_STATUS"), "计划状态");


                }
                else if(jsontype.equals("smallplan")){
                    ldc = new Entity_Plan().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.uput(obt.getString("CHECK_AREA"), "检查区域");
                    ldc.uput(obt.getString("CHECK_TIME"), "检查时间");
                    ldc.uput(obt.getString("CHECK_PERSON"), "检查人");
                    ldc.uput(obt.getString("CHECK_PERSONName"), "检查人名字");// 修改
                    ldc.uput(obt.getString("PLAN_ID"), "所属计划id");
                    ldc.uput(obt.getString("ISCOMPLETE"), "是否完成");
                    ldc.uput(obt.getString("TYPE"), "检查类型");


                }else if(jsontype.equals("check")){
                    ldc = new Entity_Check().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.nput("企业id", obt.getString("ENT_ID"));
                    ldc.nput("企业名称", obt.getString("ENT_NAME"));
                    ldc.nput("所属计划", obt.getString("AREA_ID"));
                    ldc.nput("检查人", obt.getString("CHECK_PERSON"));
                    ldc.nput("检查日期", obt.getString("CHECK_DATE"));
                    ldc.nput("是否完成", obt.getString("IS_COMPLETE"));
                    ldc.nput("检查项目名称", obt.getString("CHECK_ITEM_NAME"));
                    ldc.nput("复查状态", obt.getString("COMPLETE_AGAIN"));
                    ldc.nput("检查项目id", obt.getString("CHECK_ITEM_ID"));
                    ldc.nput("是否是计划内的", obt.getString("isplan"));
                    ldc.nput("是否要复查", obt.getString("isneedagain"));//和服务端 同步
                    ldc.nput("检查人账号", obt.getString("CHECK_PERSONAccount")); //


                }else if(jsontype.equals("checkdetail")){
                    ldc = new Entity_CheckDetail().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));


                    ldc.nput("所属计划", obt.getString("OWNPLAN"));
                    ldc.nput("所属企业", obt.getString("OWNENT"));
                    ldc.nput("关联检查项", obt.getString("CHECKITEM"));
                    ldc.nput("检查时间", obt.getString("CHECKTIME"));
                    ldc.nput("检查人员", obt.getString("CHECKPEOPLE"));
                    ldc.nput("隐患级别", obt.getString("YINHUANJIEBIE"));
                    ldc.nput("隐患描述", obt.getString("YINHUANINFO"));
                    ldc.nput("隐患整改前图片", obt.getString("YINHUANTU"));
                    ldc.nput("隐患类型", obt.getString("YINHUANLIE"));
                    ldc.nput("标准", obt.getString("BIAOZHUN"));
                    ldc.nput("整改措施", obt.getString("ZHENGAICUOSHI"));
                    ldc.nput("整改方案", obt.getString("ZHENGAIFANGAN"));
                    ldc.nput("是否上报", obt.getString("ISSHANGBAO"));
                    ldc.nput("上报时间", obt.getString("SHANGBAOTIME"));
                    ldc.nput("整改后图片", obt.getString("ZHENGAIHOUTU"));
                    ldc.nput("整改落实资金", obt.getString("ZHENGAIZIJIN"));
                    ldc.nput("整改人", obt.getString("ZHENGAIPEOPLE"));
                    ldc.nput("整改完成时间", obt.getString("ZHENGAITIME"));
                    ldc.nput("是否整改完成", obt.getString("ISWANCHENZHENGAI"));
                    ldc.nput("复查描述", obt.getString("ISXIAOCUYINHUAN"));
                    ldc.nput("上次复查时间", obt.getString("UPFUCHATIME"));
                    ldc.nput("上次复查时间", obt.getString("UPFUCHATIME"));


                }else if(jsontype.equals("law")){
                    ldc= new Entity_Law().init();
                    JSONObject obt = new JSONObject(array.get(i).toString());
                    ldc.setDatastate("server");
                    ldc.setStatus(obt.getString("status"));
                    ldc.setId(obt.getString("id"));
                    ldc.setCreated(obt.getString("CREATED"));
                    ldc.setUpDatadate(obt.getString("UPDATE_TIME"));
                    ldc.setCreatedatadate(obt.getString("CREATE_TIME"));

                    ldc.nput("被检查单位", obt.getString("BeiJianCha"));
                    ldc.nput("被检查单位id", obt.getString("BeiJianChaId"));
//							ldc.nput("检查人联系方式", obt.getString("jianChaRenContact"));
                    ldc.nput("检查人", obt.getString("JianChaRen"));
                    ldc.nput("检查时间", obt.getString("JianChaShi"));
                    ldc.nput("检查情况", obt.getString("JianChaQin"));
                    ldc.nput("案件来源", obt.getString("AnJianLaiY"));
                    ldc.nput("检查记录id", obt.getString("ZhiFaJiLuI"));
                    ldc.nput("文书类型", obt.getString("WenShuLeiX"));
                    ldc.nput("文书名称", obt.getString("WenShuMing"));
                    ldc.nput("移交人", obt.getString("YIJIAOREN"));
                    ldc.nput("复查情况", obt.getString("FUCHAQIN"));
                    ldc.nput("案件名称", obt.getString("NAME"));
                    ldc.nput("当前阶段", obt.getString("STEP"));

                }else {

                }


                if(jsontype.endsWith("delete")){
                    Util_ServerString.deleteValueSqlSingleTransaction(ldc);
                }else{
                    if((jsontype.endsWith("update")||jsontype.endsWith("insert"))&&ldc!=null){
                        Util_ServerString.deleteValueSqlSingleTransaction(ldc);
                    }
                    if(ldc!=null){
                        Util_ServerString.insertValueSqlSingleTransaction(ldc);
                    }
                }
                if(back!=null){
                    back.back(new int[]{max,i+1});
                }

            }
            if(max>0){
                //System.out.println("关闭事务:"+max);
                Help_DB.create().endbeginTransaction();
            }
            jsonstring=null;

        } catch (Exception e) {

            e.printStackTrace();

        }


    }
}
