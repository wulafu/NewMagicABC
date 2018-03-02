package cn.com.magicabc.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.magicabc.R;
import cn.com.magicabc.ui.bean.GankEntity;

/**
 * Created by WuXiaolong
 * on 2015/7/2.
 */
public class WordLessonRecyclerViewAdapter extends RecyclerView.Adapter<WordLessonRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<GankEntity> dataList = new ArrayList<>();

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

    public void addAllData(List<GankEntity> dataList) {
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

        public TextView tv;
        public TextView tv1;


        public ViewHolder(View itemView) {
            super(itemView);
          //  tv = (TextView) itemView.findViewById(R.id.tv);
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