package com.chniccs.dragtreeview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DragTreeView";
    private RecyclerView mRecyclerView;
    private ArrayList mData = new ArrayList<Integer>();
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        init();
    }

    private void init() {
        int i = 0;
        while (i < 100) {
            mData.add(i);
            i++;
        }
        DtvItemTouchHelper my=new DtvItemTouchHelper(new DtvItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    final int dragFlags = DtvItemTouchHelper.UP |
                            DtvItemTouchHelper.DOWN;
                    final int swipeFlags = 0;//滑动标志，暂时不考虑滑动
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
                return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //viewHolder拖动的item的holder
                //target当前目标item的holder
//                int fromPosition = viewHolder.getAdapterPosition();
//                int targetPosition = target.getAdapterPosition();
//                if (fromPosition < targetPosition) {
//                    for (int i = fromPosition; i < targetPosition; i++) {
//                        Collections.swap(mData, i, i + 1);
//                    }
//                } else {
//                    for (int i = fromPosition; i > targetPosition; i--) {
//                        Collections.swap(mData, i, i - 1);
//                    }
//                }
                Log.d(TAG,"拖的："+viewHolder.itemView.getTop());
                Log.d(TAG,"当前的："+target.itemView.getTop());
//                mAdapter.notifyItemMoved(fromPosition,targetPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
            //选中时状态变化
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if(actionState != DtvItemTouchHelper.ACTION_STATE_IDLE){//拖中
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }
            //清除状态变化
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                    final int dragFlags = ItemTouchHelper.UP |
//                            ItemTouchHelper.DOWN;
//                    final int swipeFlags = 0;//滑动标志，暂时不考虑滑动
//                    return makeMovementFlags(dragFlags, swipeFlags);
//                }
//                return 0;
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                //viewHolder拖动的item的holder
//                //target当前目标item的holder
//                int fromPosition = viewHolder.getAdapterPosition();
//                int targetPosition = target.getAdapterPosition();
//                if (fromPosition < targetPosition) {
//                    for (int i = fromPosition; i < targetPosition; i++) {
//                        Collections.swap(mData, i, i + 1);
//                    }
//                } else {
//                    for (int i = fromPosition; i > targetPosition; i--) {
//                        Collections.swap(mData, i, i - 1);
//                    }
//                }
//                Log.d(TAG,"拖的："+viewHolder.itemView.getTop());
//                Log.d(TAG,"当前的："+target.itemView.getTop());
//                mAdapter.notifyItemMoved(fromPosition,targetPosition);
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//
//            }
//
//            @Override
//            public boolean isLongPressDragEnabled() {
//                return true;
//            }
//            //选中时状态变化
//            @Override
//            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//                if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){//拖中
//                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
//                }
//                super.onSelectedChanged(viewHolder, actionState);
//            }
//            //清除状态变化
//            @Override
//            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                super.clearView(recyclerView, viewHolder);
//                viewHolder.itemView.setBackgroundColor(0);
//            }
//        });

        mAdapter = new RecyclerView.Adapter() {
            @Override
            public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((Holder) holder).setTv(mData.get(position) + "");
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);
        my.attachToRecyclerView(mRecyclerView);
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView mTv;

        public Holder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
        }

        public void setTv(String number) {
            mTv.setText(number);
        }
    }


}
