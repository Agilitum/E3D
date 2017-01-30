package com.e3d.ui.tasks.helper;

/**
 * Created by ludwig on 27/01/17.
 */
public interface ItemTouchHelperAdapter {
	
	boolean onItemMove(int fromAdapterPosition, int toAdapterPosition);

	void onItemDismiss(int adapterPosition);
}
