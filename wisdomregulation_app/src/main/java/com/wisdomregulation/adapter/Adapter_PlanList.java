package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.single.Activity_Plan;
import com.wisdomregulation.allactivity.single.Activity_PlanDetail;
import com.wisdomregulation.allactivity.single.Activity_PlanInsert;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entityother.Entity_BigPlan;
import com.wisdomregulation.data.entityother.Entity_Check;
import com.wisdomregulation.data.entityother.Entity_Plan;
import com.wisdomregulation.frame.PassLinearLayout;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_Db;
import com.wisdomregulation.utils.Util_MatchTip;
import com.wisdomregulation.utils.Util_String;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_PlanList extends BaseAdapter {
    private Activity context;
    private Map<Integer, String> mapnamemap = new HashMap<Integer, String>();
    private List<Base_Entity> lawListData;

    public Adapter_PlanList(Activity context, List<Base_Entity> lawList) {
        super();
        this.context = context;
        this.lawListData = lawList;
        mapnamemap.clear();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lawListData.size();
    }


    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return lawListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_check_list, null);
            convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) (Static_InfoApp.create().getAppScreenHigh() / 10.5)));
            holder.lawArea = (TextView) convertView.findViewById(R.id.lawArea);
            holder.lawItem = (TextView) convertView.findViewById(R.id.lawItem);
            holder.lawOrder = (TextView) convertView.findViewById(R.id.lawOrder);
            holder.plandelete = (PassLinearLayout) convertView.findViewById(R.id.plandelete);
            holder.plandetail = (PassLinearLayout) convertView.findViewById(R.id.plandetail);



            if (Static_InfoApp.create().isshow()) {
                holder.plandelete.setVisibility(View.VISIBLE);
            }


            Util_MatchTip.initAllScreenText(convertView);
            Util_MatchTip.initAllScreenText(holder.lawOrder, Static_ConstantLib.TEXT_SMALL);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.plandetail.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, Activity_PlanInsert.class).putExtra("editstate", 0).putExtra("entityPlan", lawListData.get(position)));
                    }
                });
        holder.plandelete.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (Static_InfoApp.create().isshow()) {
                            final Base_Entity plantmp = new Entity_Plan().setId(lawListData.get(position).getId());
                            Base_Entity checktmp = new Entity_Check().put(0, lawListData.get(position).getId());
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    Util_Db.deleteFix(plantmp, new Intent(Activity_Plan.refresh));
                                }
                            }).start();
                        } else {
                            Toast.makeText(context, "不可进行删除操作", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        holder.lawArea.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final String areaid = Util_Db.code2idtoStringSpecial(lawListData.get(position).getValue(0), 2);
                        context.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                context.startActivity(new Intent(context, Activity_PlanDetail.class).putExtra("planid", lawListData.get(position).getId()).putExtra("areaid", areaid));

                            }
                        });

                    }
                }).start();


            }
        });
        holder.lawItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final String areaid = Util_Db.code2idtoStringSpecial(lawListData.get(position).getValue(0), 2);
                        context.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                context.startActivity(new Intent(context, Activity_PlanDetail.class).putExtra("planid", lawListData.get(position).getId()).putExtra("areaid", areaid));

                            }
                        });

                    }
                }).start();


            }
        });

        String quyuid = lawListData.get(position).getValue(0).trim();
        if (!quyuid.equals("")) {
            holder.lawArea.setText(Activity_Plan.areamap.get(quyuid + "") + "");
        } else {
            holder.lawArea.setText("");
        }

        final String bigplanid = lawListData.get(position).getValue(4).trim();
        String tmpname = mapnamemap.get(position);
        if (tmpname != null) {
            holder.lawItem.setText(tmpname);
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    final String planname = Util_MatchTip.getSearchResultOnlyOne(
                            Help_DB.create().search(new Entity_BigPlan().init().setId(bigplanid))).getValue(0);
                    context.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            String tmpname = "";
                            tmpname = planname;
                            if (planname == null || "".equals(planname)) {
                                tmpname = "测试计划";
                                holder.lawItem.setText(tmpname);
                            } else {
                                holder.lawItem.setText(tmpname);
                            }

                            mapnamemap.put(position, tmpname);

                        }
                    });


                }
            }).start();
        }

        holder.lawOrder.setText("计\n" + "划\n" + Util_String.int2String(position + 1));


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();
        mapnamemap.clear();
    }

    class ViewHolder {
        TextView lawArea;
        TextView lawItem;
        TextView lawOrder;
        PassLinearLayout plandetail;
        PassLinearLayout plandelete;
    }


}
