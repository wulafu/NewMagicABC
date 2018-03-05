package cn.com.magicabc.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.magicabc.R;
import cn.com.magicabc.ui.bean.GankEntity;
import cn.com.magicabc.ui.bean.HomeWorkBean;

/**
 * Created by WuXiaolong
 * on 2015/7/2.
 */
public class WordLessonRecyclerViewAdapter extends RecyclerView.Adapter<WordLessonRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<HomeWorkBean> dataList = new ArrayList<>();

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (GankEntity) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, GankEntity data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void addAllData(List<HomeWorkBean> dataList) {
        clearData();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public WordLessonRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv1;
        public ImageView iv2;
        public ImageView iv3;
        public ImageView iv4;
        public ImageView iv5;
        public ImageView iv6;




        public ViewHolder(View itemView) {
            super(itemView);
           iv1 = (ImageView) itemView.findViewById(R.id.iv1);
           iv2 = (ImageView) itemView.findViewById(R.id.iv2);
           iv3 = (ImageView) itemView.findViewById(R.id.iv3);
           iv4 = (ImageView) itemView.findViewById(R.id.iv4);
           iv5 = (ImageView) itemView.findViewById(R.id.iv5);
           iv6 = (ImageView) itemView.findViewById(R.id.iv6);
          //  tv1 = (TextView) itemView.findViewById(R.id.tv1);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_lesson_item, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


       // holder.tv.setText(dataList.get(position).getDesc());
       // holder.tv1.setText(dataList.get(position).getWho());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}