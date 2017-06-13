package com.wisdomregulation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisdomregulation.R;
import com.wisdomregulation.allactivity.mode.Mode_EvidenceCollect;
import com.wisdomregulation.dialog.Dialog_Tool;
import com.wisdomregulation.frame.AutoCheckBox;
import com.wisdomregulation.frame.OnCheckedChangeListener;
import com.wisdomregulation.help.Help_ImageLoader;
import com.wisdomregulation.map.ExpandMap;
import com.wisdomregulation.staticlib.Static_InfoApp;
import com.wisdomregulation.utils.Util_MatchTip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by King6rf on 2017/6/8.
 */

public class Adapter_CollectList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<ExpandMap> menuListData;
    private int mCurrentItemId = 0;
    int orientation = 1;//0为横1为竖直
    private Map<Integer, Bitmap> bitmapmap = new HashMap<Integer, Bitmap>();
    private boolean cancheck=false;
    public  Map<Integer,Boolean> checkmap=new HashMap<Integer,Boolean>();
    public Adapter_CollectList(Activity context, List<ExpandMap> maplist) {
        this.context = context;

//        menuListData = new ArrayList<ExpandMap>(maplist.size());
//        for (int i = 0; i <maplist.size() ; i++) {
//            addItem(maplist.get(i));
//        }
        menuListData = maplist;

    }

    public void setCancheck(boolean cancheck) {
        this.cancheck = cancheck;
    }
    public void clear(){
        cancheck=false;
        checkmap.clear();
    }
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case 0:
                            return 4;
                        case 1:
                            return 1;
                        default:
                            return 1;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.item_activity_collect_parent, parent, false);
                holder = new ViewHolderHead(view);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.item_activity_collect, parent, false);
                holder = new ViewHolderContent(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final View itemView = holder.itemView;
        boolean isTwoStaggered = true;
        if (isTwoStaggered) {
            switch (getItemViewType(position)) {
                case 0:
                    ((ViewHolderHead) holder).text.setText(menuListData.get(position).getName());
                    break;
                case 1:
                    final ImageView tmp = ((ViewHolderContent) holder).logimage;
                    final AutoCheckBox checklogo=((ViewHolderContent) holder).check;
                    final ImageView deletemark=((ViewHolderContent) holder).deletemark;
                    Help_ImageLoader.getInstance().load2(tmp,context, menuListData.get(position).getEntityvalue().getFileallpath(),Mode_EvidenceCollect.dialogdismiss);
//                    Help_ImageLoader.getInstance().loadImage(menuListData.get(position).getEntityvalue().getFileallpath(),tmp);
                    ((ViewHolderContent) holder).logimage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if(!cancheck){
                                menuListData.get(position).getEntityvalue().open();
                            }else{
                                checklogo.performClick();
                            }
                        }
                    });
                    ((ViewHolderContent) holder).logo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if(!cancheck){
                                Dialog_Tool.showDialog_Rename(context, menuListData.get(position).getEntityvalue());
                            }else{
                                checklogo.performClick();
                            }

                        }
                    });
                    ((ViewHolderContent) holder).logimage.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            cancheck=!cancheck;
                            checkBoxFresh();
                            notifyDataSetChanged();
                            return true;
                        }
                    });
                    ((ViewHolderContent) holder).check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(View buttonView, boolean isChecked) {
                            System.out.println("checkmap:put"+position+":"+isChecked);
                            checkmap.put(position, isChecked);
                        }
                    });

                    deletemark.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            menuListData.get(position).getEntityvalue().delete();
                            menuListData.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    if(cancheck){
                        ((ViewHolderContent) holder).check.setVisibility(View.VISIBLE);
                    }else{
                        ((ViewHolderContent) holder).check.setVisibility(View.GONE);
                    }
                    if(checkmap.get(position)!=null&&checkmap.get(position)){
                        ((ViewHolderContent) holder).check.setChecked(true);
                    }else{
                        ((ViewHolderContent) holder).check.setChecked(false);
                    }
                    switch (menuListData.get(position).getEntityvalue().getFileType()) {
                        case 2:
                            ((ViewHolderContent) holder).fileflag.setImageResource(R.drawable.flag_06);
                            break;
                        default:
                            break;
                    }
                    break;
            }
        } else {

        }

    }
    public List<File> getFileSelectList(){
        List<File> fileselect=new ArrayList<File>();
        for (Map.Entry<Integer, Boolean> entry : checkmap.entrySet()) {
            if(entry.getValue()){
                fileselect.add(menuListData.get(entry.getKey()).getEntityvalue().getOrg());
            }
        }
        return fileselect;
    }
    public void checkBoxFresh(){
        if(cancheck){
            Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.show));
        }else{
            Static_InfoApp.create().getContext().sendBroadcast(new Intent(Mode_EvidenceCollect.dismiss));
        }
    }
    @Override
    public int getItemViewType(int position) {
        return menuListData.get(position).getViewtype();
    }

//    public void addItem(ExpandMap expandMap) {
//        final int id = mCurrentItemId++;
//        menuListData.add(id, expandMap);
//        notifyItemInserted(id);
//    }

//    public void removeItem(int position) {
//        menuListData.remove(position);
//        notifyItemRemoved(position);
//    }

    @Override
    public int getItemCount() {
        return menuListData.size();
    }

    public static class ViewHolderHead extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolderHead(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Static_InfoApp.create().getAppScreenHigh() / 18));
            Util_MatchTip.initAllScreenText(itemView);
            text = ((TextView) itemView.findViewById(R.id.inspectOption));
        }
    }

    public static class ViewHolderContent extends RecyclerView.ViewHolder {
        TextView logo;
        ImageView logimage;
        ImageView fileflag;
        AutoCheckBox check;
        ImageView deletemark;
        public ViewHolderContent(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) Static_InfoApp.create().getAppScreenWidth() / 4));
            logo = (TextView) (itemView.findViewById(R.id.menuImage));
            logimage = (ImageView) itemView.findViewById(R.id.needfiximage);
            check = (AutoCheckBox) itemView.findViewById(R.id.checkBox1);
            fileflag = (ImageView) itemView.findViewById(R.id.fileflag);
            deletemark=(ImageView) itemView.findViewById(R.id.deletemark);
            RelativeLayout.LayoutParams pra=new RelativeLayout.LayoutParams((int)Static_InfoApp.create().getAppScreenWidth()/13, (int)Static_InfoApp.create().getAppScreenWidth()/13);
            pra.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            pra.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            check.setLayoutParams(pra);
            Util_MatchTip.initAllScreenText(itemView);
        }
    }

}
