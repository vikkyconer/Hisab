package com.ideabank.vikky.hisab;

import com.ideabank.vikky.hisab.Adapters.RVAdapter;

/**
 * Created by vikky on 8/22/15.
 */
public class SimpleItemTouchHelperCallback{

    private final RVAdapter mAdapter;

    public SimpleItemTouchHelperCallback(RVAdapter adapter) {
        mAdapter = adapter;
    }

//    @Override
//    public boolean isLongPressDragEnabled() {
//        return true;
//    }
//
//    @Override
//    public boolean isItemViewSwipeEnabled() {
//        return true;
//    }

//    @Override
//    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(dragFlags, swipeFlags);
//    }

//    @Override
//    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
//                          RecyclerView.ViewHolder target) {
////        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        return true;
//    }
//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
//    }
}
