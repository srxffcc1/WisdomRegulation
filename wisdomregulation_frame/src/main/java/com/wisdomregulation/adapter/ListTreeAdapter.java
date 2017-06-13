package com.wisdomregulation.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by KingGT80 on 2017/3/15.
 */

public class ListTreeAdapter extends BaseAdapter {
    public Activity context;
    public List<ListItem> alllist = new ArrayList<>();//第一层进去的展示列表
    public List<ListItem> showlist = new ArrayList<>();//用户看到的展示列表
    public int whillclicklevel;//设置第几层为点击跳转
    public List<String> requestrules=new ArrayList<>();//网络请求规则

    /**
     *
     * @param context  context
     * @param alllist  一层目录
     * @param whillclicklevel  需要进行跳转反馈的层级
     */
    public ListTreeAdapter(Activity context, List<ListItem> alllist,int whillclicklevel) {
        this.context = context;
        this.alllist = alllist;//构造方法中传入的是第一层目录
        this.whillclicklevel = whillclicklevel;
        changeData();
    }

    @Override
    public int getCount() {
        return showlist.size();
    }

    @Override
    public Object getItem(int position) {
        return showlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

        } else {

        }
        ListItem entity = showlist.get(position);
        int level = entity.getLevel();
        switch (level) {//层级判断
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checktoshowchild(v, position);
            }
        });
        return convertView;
    }

    public void checktoshowchild(View v, int position) {
        int nowlevel = showlist.get(position).getLevel();
        boolean listhaschange=false;
        if (position < getCount() - 1) {
            if (nowlevel < showlist.get(position + 1).getLevel()) {//说明应该是收起操作
                listhaschange= hidechild(v, position);
            } else {//说明是展示操作
                listhaschange=showchild(v, position);
            }
        } else {//说明按到了底部了那就应该是展示操作了
            listhaschange= showchild(v, position);
        }
        if(listhaschange){
            changeData();//说明直接就有数据在大list里直接展示或者隐藏就行
        }else{
            if(nowlevel==whillclicklevel){
                pressenent(v,position);
            }else{
                if(position==getCount() - 1||nowlevel==showlist.get(position+1).getLevel()){//说明有下级且没有进行过载入 需要进行网络请求进行载入
                    loaddatafromserver(v,position);
                }
            }
        }

    }
    public void loaddatafromserver(View v, int position){
        //会按照网络请求规则去访问网络
        List<ListItem> tmplist = new ArrayList<>();//第一层进去的展示列表 //获得一个list listitem由网络请求结果构造 设置为show
        alllist.addAll(position,tmplist);//将数据插入到完整list 再执行changedata操作；
        changeData();
    }
    public boolean showchild(View v, int position) {
        boolean listhaschange=false;
        int nowlevel = showlist.get(position).getLevel();
        for (int i = showlist.get(position).getPostionorg()+1; i < alllist.size(); i++) {//从大list看是否已经在大list中加载过child了
            if(nowlevel<alllist.get(i).getLevel()){
                if(alllist.get(i).isshow()){
                    break;
                }else{
                    listhaschange=true;
                    alllist.get(i).setIsshow(true);
                }
            }else{
                break;
            }

        }
        return listhaschange;
    }

    public boolean hidechild(View v, int position) {//将对菜单进行收起 做标记
        boolean listhaschange=false;
        int nowlevel = showlist.get(position).getLevel();
        for (int i = position+1; i < showlist.size(); i++) {
            if(nowlevel<showlist.get(i).getLevel()){
                if(!showlist.get(i).isshow()){
                    break;
                }else{
                    listhaschange=true;
                    alllist.get(showlist.get(i).getPostionorg()).setIsshow(false);
                }
            }else{
                break;
            }
        }
        return listhaschange;
    }

    public void changeData() {
        showlist.clear();
        for (int i = 0; i <alllist.size() ; i++) {
            if(alllist.get(i).isshow()){
                showlist.add(alllist.get(i).setPostionorg(i));
            }
        }
        notifyDataSetChanged();
    }
    public void pressenent(View v, int position){
//写跳转逻辑
    }
    class ViewHolder {
//界面绑定
    }

    class ListItem {
        public int postionorg;
        public int level;
        public boolean isshow;
        public Objects includeobj;
        public String showtitle;

        public int getLevel() {
            return level;
        }

        public int getPostionorg() {
            return postionorg;
        }

        public ListItem setPostionorg(int postionorg) {
            this.postionorg = postionorg;
            return this;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public boolean isshow() {
            return isshow;
        }

        public void setIsshow(boolean isshow) {
            this.isshow = isshow;
        }

        public Objects getIncludeobj() {
            return includeobj;
        }

        public void setIncludeobj(Objects includeobj) {
            this.includeobj = includeobj;
        }

        public String getShowtitle() {
            return showtitle;
        }

        public void setShowtitle(String showtitle) {
            this.showtitle = showtitle;
        }
    }
}
