package com.tisson.test;

import java.util.List; 

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DragAdapter extends BaseAdapter {
	/** TAG */
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition = -1;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<ChannelItem> channelList;
	/** TextView 频道内容 */
	private TextView item_text;
	
	private ImageView item_image;
	
	/** 要删除的position */
	public int remove_position = -1;
	public boolean isIconVisible = false;
	public static int selectedPos = -1;
	public boolean isReset = false;

	public DragAdapter(Context context, List<ChannelItem> channelList) {
		this.context = context;
		this.channelList = channelList;
		selectedPos = -1;
	}

	public int getHoldPosition() {
		return holdPosition;
	}

	public void setHoldPosition(int holdPosition) {
		this.holdPosition = holdPosition;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChannelItem getItem(int position) {
		// TODO Auto-generated method stub
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.subscribe_category_item, null);
		item_text = (TextView) view.findViewById(R.id.text_item);
		item_image = (ImageView) view.findViewById(R.id.iv_item);
		ChannelItem channel = getItem(position);
		item_text.setText(channel.getName());
		item_image.setBackgroundResource(channel.getImage());
		ImageView item_icon = (ImageView) view.findViewById(R.id.delete_icon);
		item_icon.setVisibility(View.GONE);

		if ((position == holdPosition && isItemShow)) {
			item_icon.setVisibility(View.VISIBLE);
			selectedPos = position;
			if (isReset) {
				item_icon.setVisibility(View.GONE);
				selectedPos = -1;
				holdPosition = -1;
			}
		}
		
		if (isChanged && (position == holdPosition) && !isItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (remove_position == position) {
			item_text.setText("");
		}
		return view;
	}

	/** 添加频道列表 */
	public void addItem(ChannelItem channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		ChannelItem dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition="
				+ dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}

	/** 获取频道列表 */
	public List<ChannelItem> getChannnelLst() {
		return channelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}
	
	public void remove(int deletePosition) {
		channelList.remove(deletePosition);
		notifyDataSetChanged();
	}
	

	/** 设置频道列表 */
	public void setListDate(List<ChannelItem> list) {
		channelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}

	/** 是否初始化 */
	public boolean isReset() {
		return isReset;
	}

	/** 是否初始化 */
	public void setReset(boolean isReset) {
		this.isReset = isReset;
	}

}