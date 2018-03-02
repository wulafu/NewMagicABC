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
 * Created by fengbei on 2016/3/10.
 */
public class CommentAdapter extends BaseRecyclerAdapter<List<GankEntity>> implements View.OnClickListener
    {


      private List<GankEntity> dataList = new ArrayList<>();
  /**
   * 构造函数
   */
  public CommentAdapter(Context context) {
    super(context);

  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_lesson_item, parent, false);
    view.setOnClickListener(this);
    CommentInfoViewHolder holder = new CommentInfoViewHolder(view);
    view.setTag(holder);
    return holder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    CommentInfoViewHolder commentInfoViewHolder = (CommentInfoViewHolder) holder;
    commentInfoViewHolder.tv.setText(dataList.get(position).getWho());
    commentInfoViewHolder.tv1.setText(dataList.get(position).getDesc());

  }

  public void addAllData(List<GankEntity> dataList) {
    this.dataList.addAll(dataList);
    notifyDataSetChanged();
  }

      @Override
      public void onClick(View v) {

      }

      public class CommentInfoViewHolder extends RecyclerView.ViewHolder {
   private TextView tv;
   private TextView tv1;


    public CommentInfoViewHolder(View itemView) {
      super(itemView);
      tv = (TextView) itemView.findViewById(R.id.tv);
      tv1 = (TextView) itemView.findViewById(R.id.tv1);

      //replyView.setOnClickListener(CommentAdapter.this);
    }
  }



}
