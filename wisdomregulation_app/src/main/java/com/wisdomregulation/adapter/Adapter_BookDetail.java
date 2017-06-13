package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdomregulation.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitydemo.Entity_Demo;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.AutoCheckGroup;
import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.frame.OnCheckedChangeListener;
import com.wisdomregulation.frame.PassLinearLayout;
import com.wisdomregulation.dialog.DateTimePickDialog;
import com.wisdomregulation.utils.Util_MatchTip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Adapter_BookDetail {
    private Activity context;
    private Base_Entity detailMapData;
    private LinearLayout content;

    private boolean editState;
    private boolean isshow = true;
    private Map<String, EditText> viewmap = new HashMap<String, EditText>();

    public Adapter_BookDetail(Activity context,
                              Base_Entity detailMapData, LinearLayout content) {
        super();
        this.context = context;
        this.detailMapData = detailMapData;
        this.content = content;
    }

    public Map<String, EditText> getViewmap() {
        return viewmap;
    }

    public Base_Entity getResult() {
        detailMapData.clear();
        for (int i = 0; i < detailMapData.size(); i++) {
            EditText textvalue = viewmap.get(detailMapData.getField(i));
            if (textvalue != null) {
//                System.out.println(textvalue.getText().toString());
                detailMapData.put(i, textvalue.getText().toString());
//				
            } else {
                detailMapData.put(i, "");
            }
        }
        return detailMapData;
    }

    public Adapter_BookDetail setFocus(int position) {
        (viewmap.get(detailMapData.getField(position))).setFocusable(true);
        (viewmap.get(detailMapData.getField(position))).setFocusableInTouchMode(true);
        (viewmap.get(detailMapData.getField(position))).requestFocus();
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        return this;
    }

    public Adapter_BookDetail initView() {
        viewmap.clear();
        content.removeAllViews();
        for (int i = 0; i < getCount(); i++) {
            View add = getView(i, content);
            if (add != null) {
                if (add.getVisibility() == View.VISIBLE) {
                    content.addView(add);
                }

            }
        }
        return this;
    }

    public Adapter_BookDetail setEditState(boolean editState) {
        if (viewmap.size() > 0) {
            detailMapData = getResult();
        }

        this.editState = editState;
        return this;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return detailMapData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return detailMapData.getFieldChinese(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public String getListTitle(String org) {
        String result = "";
        String[] orgarray = org.split("3");
        result = orgarray[0].trim().replace("list", "");
        Pattern pattern=Pattern.compile("(.*?)lim(.*)");
        Matcher matcher=pattern.matcher(result);
        if(matcher.find()){
            result=matcher.group(1)+"列表";
        }

        return result;
    }

    public View getView(int position, ViewGroup parent) {
        View convertView = null;
        String fieldtext = getItem(position).toString();
        if (fieldtext.matches("check(.*)")) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_book_content2, null);
            final AutoCheckGroup autogroup = (AutoCheckGroup) convertView.findViewById(R.id.checkGroup);
            String[] fieldtexts = fieldtext.replace("check", "").split("2");
            for (int i = 0; i < fieldtexts.length; i++) {
                PassLinearLayout passparent = new PassLinearLayout(context);
                passparent.setOrientation(LinearLayout.HORIZONTAL);
                passparent.setPadding(0, 15, 0, 15);
                passparent.setClickable(true);
                passparent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                TextView text = new TextView(context);
                text.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
                text.setText(fieldtexts[i].toString().trim());
                AutoCheckBox box = new AutoCheckBox(context);
                box.setPadding(0, 0, 7, 0);
                box.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                box.setImageResource(R.drawable.toggle);
                box.setClickable(true);
                passparent.addView(box);
                passparent.addView(text);
                autogroup.addView(passparent);
                if (editState) {
//					box.setEnabled(true);
                } else {
                    autogroup.setEnabled(false);
                    passparent.setClickable(false);
                    box.setClickable(false);
                }
            }

            final EditText valueedit2 = ((EditText) convertView.findViewById(R.id.bookcontentValue));

            autogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(View buttonView, boolean isChecked) {
                    if (isChecked) {
                        valueedit2.setText((autogroup.getCheckIndex() + 1) + "");
                    }


                }
            });
            autogroup.setCheck((detailMapData.getValue(position).equals(" ") || detailMapData.getValue(position).equals("")) ? 0 : Integer.parseInt(detailMapData.getValue(position)) - 1);
            String sso = ((detailMapData.getValue(position).equals(" ") || detailMapData.getValue(position).equals("")) ? 1 : Integer.parseInt(detailMapData.getValue(position))) + "";
            valueedit2.setText(sso);
            viewmap.put(detailMapData.getField(position), valueedit2);
        } else if (fieldtext.matches("list(.*)")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_book_content3, null);
            String titiletextstring = getListTitle(fieldtext);
            final String fieldtexttmp = fieldtext;
            final TextView listtitle = (TextView) convertView.findViewById(R.id.listtitle);
            final Button addtip = (Button) convertView.findViewById(R.id.addtip);
            final LinearLayout listcontent = (LinearLayout) convertView.findViewById(R.id.needaddtip);
            final TextView showhide = (TextView) convertView.findViewById(R.id.hidetip);
            listtitle.setText(titiletextstring);
            String tmpvalue=detailMapData.getValue(position);
            String tmpfield=fieldtext;
            List<Base_Entity> addlist = string2EntityList(tmpfield,tmpvalue);
            final Adapter_BookAddItem adapter_bookAddItem = new Adapter_BookAddItem(context, addlist, listcontent);
            adapter_bookAddItem.initView();
            final EditText valueedit2 = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            showhide.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    isshow = !isshow;
                    if (isshow) {
                        showhide.setText("隐藏↑");
                        listcontent.setVisibility(View.VISIBLE);
                    } else {
                        showhide.setText("显示↓");
                        listcontent.setVisibility(View.GONE);
                    }

                }
            });
            if (!editState) {
                showhide.setClickable(false);
                addtip.setVisibility(View.GONE);
            } else {

                addtip.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String org = fieldtexttmp;
                        String result = "";
                        String[] orgarray = org.split("3");
                        result = orgarray[0].trim().replace("list", "");
                        Pattern pattern=Pattern.compile("(.*?)lim(.*)");
                        Matcher matcher=pattern.matcher(result);
                        int limit=0;
                        if(matcher.find()){
                            limit=Integer.parseInt(matcher.group(2));
                        }
                        if(adapter_bookAddItem.getCount()>limit){
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String org = fieldtexttmp;
                                    String result = "";
                                    String[] orgarray = org.split("3");

                                    result = orgarray[0].trim().replace("list", "");
                                    Pattern pattern=Pattern.compile("(.*?)lim(.*)");
                                    Matcher matcher=pattern.matcher(result);
                                    int limit=0;
                                    if(matcher.find()){
                                        limit=Integer.parseInt(matcher.group(2));
                                    }
                                    Toast.makeText(context,"超过"+limit+"个不可继续添加",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            Dialog_Tool.showDialog_AddBookItem(context, fieldtexttmp, new CallBack() {
                                @Override
                                public void back(Object resultlist) {
                                    final Base_Entity result = (Base_Entity) resultlist;
                                    adapter_bookAddItem.addEntity(result);
                                    String newtext=adapter_bookAddItem.getResult();
                                    valueedit2.setText(newtext);
                                }
                            });
                        }

                    }
                });
            }

            ((TextView) convertView.findViewById(R.id.bookcontentName)).setText(fieldtext);
            final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            viewmap.put(detailMapData.getField(position), valueedit);
            valueedit.setText(detailMapData.getValue(position));
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_book_content, null);
            ((TextView) convertView.findViewById(R.id.bookcontentName)).setText(fieldtext);
            final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
            viewmap.put(detailMapData.getField(position), valueedit);
            valueedit.setText(detailMapData.getValue(position));
        }
        final EditText valueedit = ((EditText) convertView.findViewById(R.id.bookcontentValue));
        if (fieldtext.matches("(.*)null(.*)")) {
            convertView.setVisibility(View.GONE);
        } else if (fieldtext.matches("(.*)时间(.*)") || fieldtext.matches("(.*)日期(.*)")) {
            valueedit.setFocusable(false);
            valueedit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DateTimePickDialog(context, "").show(valueedit);

                }
            });
        } else if (fieldtext.matches("(.*)数量(.*)") || fieldtext.matches("(.*)序号(.*)") || fieldtext.matches("(.*)号码(.*)") || fieldtext.matches("(.*)电话(.*)") || fieldtext.matches("(.*)邮编(.*)")) {
            valueedit.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {

        }


        if (editState) {
//			valueedit.setEnabled(true);
        } else {
            valueedit.setOnClickListener(null);
            valueedit.setOnTouchListener(null);
            valueedit.setClickable(false);
            valueedit.setKeyListener(null);
        }
        Util_MatchTip.initAllScreenText(convertView);
        return convertView;
    }
    public List<Base_Entity> string2EntityList(String orgfieldstring,String orgliststring){
        List<Base_Entity> result=new ArrayList<Base_Entity>();
        String[] listitem=null;


        if(orgliststring!=null&&orgliststring.matches("(.*)@(.*)")){
            listitem=orgliststring.split("@");
            if(listitem!=null){
                for (int i = 0; i < listitem.length; i++) {
                    Base_Entity tmp=string2Entity(orgfieldstring,listitem[i]);
                    result.add(tmp);
                }
            }else{
            }
        }else{
        }
        return result;

    }
    public Base_Entity string2Entity(String orgfieldstring,String orgliststring){
        Base_Entity itementity = new Entity_Demo();
        String org = orgfieldstring;
        String result = "";
        String[] orgarray = org.split("3");
        String[] orgarray2 = orgarray[1].split("2");
        String[] valuestringarray=orgliststring.split("#");

        for (int i = 0; i < orgarray2.length; i++) {
            itementity.add(orgarray2[i].trim(), valuestringarray[i].trim());
        }


        return itementity;
    }

}
