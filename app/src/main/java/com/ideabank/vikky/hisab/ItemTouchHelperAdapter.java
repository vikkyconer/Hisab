package com.ideabank.vikky.hisab;

/**
 * Created by vikky on 8/22/15.
 */

public interface ItemTouchHelperAdapter {
    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br/>
     * <br/>
     * adjusting the underlying data to reflect this move.
     *
     * @param fromPosition The start position of the moved item.
     * @param toPosition   Then resolved position of the moved item.
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * Called when an item has been dismissed by a swipe.<br/>
     * <br/>
     * adjusting the underlying data to reflect this removal.
     *
     * @param position The position of the item dismissed.
     */
    void onItemDismiss(int position);
}
