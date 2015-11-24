package bmob.store.demo.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class mBaseAdapter<T> extends BaseAdapter{

	protected Context mcontext;//上下�?
	protected List<T> dataList;//装shop或其他的list
	protected LayoutInflater mInflater;
	
	public List<T> getDataList(){
		return dataList;
	}
	public void setArrayList(List<T> dataList){
		this.dataList = dataList;
	}
	public void setList(List<T> dataList) {
		this.dataList = dataList;
		notifyDataSetChanged();
	}

	public void add(T e) {
		this.dataList.add(e);
		notifyDataSetChanged();
	}
	public mBaseAdapter(Context context,List<T> dataList){
		mcontext = context;
		this.dataList = dataList;
		mInflater = LayoutInflater.from(mcontext);
	}
	public int getCount() {
		return dataList.size();
	}
	public T getItem(int postion) {
		return dataList.get(postion);
	}
	public long getItemId(int postion) {
		return postion;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = getConvertView(position, convertView, parent);
		addInternalClickListener(convertView, position, dataList.get(position));//添加内部点击事件
		return convertView;
	}
	// adapter中的内部点击事件
	public Map<Integer, onInternalClickListener> canClickItem;
	
	/**
	 * Map的作用是将旗下所有的adapter的点击事件收集起�?
	 * @param itemV
	 * @param position
	 * @param valuesMap
	 */
	private void addInternalClickListener(final View itemV, final Integer position,final Object valuesMap) {
		if (canClickItem != null) {
			for (Integer key : canClickItem.keySet()) {
				View inView = itemV.findViewById(key);
				final onInternalClickListener inviewListener = canClickItem.get(key);
				if (inView != null && inviewListener != null) {
					inView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							inviewListener.OnClickListener(itemV, v, position,
									valuesMap);
						}
					});
				}
			}
		}
	}
	/**
	 * �?2个方法是接口常用的了,供ac可以调用adapter里面的onclick方法
	 * @param key
	 * @param onClickListener
	 */
	public void setOnInViewClickListener(Integer key,
			onInternalClickListener onClickListener) {
		if (canClickItem == null)
			canClickItem = new HashMap<Integer, onInternalClickListener>();
		canClickItem.put(key, onClickListener);
	}
	public interface onInternalClickListener {
		public void OnClickListener(View parentV, View v, Integer position,
				Object values);
	}
	public abstract View getConvertView(int position, View convertView, ViewGroup parent);
}
