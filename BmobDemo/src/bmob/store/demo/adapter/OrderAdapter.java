package bmob.store.demo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import bmob.store.demo.R;
import bmob.store.demo.bean.Goods;
import bmob.store.demo.bean.Order;
import bmob.store.demo.ui.PayActivity;

public class OrderAdapter extends BaseAdapter{
	
	private ArrayList<Order> datalist;
	private Context context;
	private LayoutInflater mInflater; 
	public OrderAdapter(Context context,ArrayList<Order> datalist){
		this.context = context;
		this.datalist = datalist;
		mInflater = LayoutInflater.from(context);
	}
	
	
	public int getCount() {
		return datalist.size();
	}	
	
	public Object getItem(int position) {
		return position;
	}
	
	public long getItemId(int position) {
		return position;
	}
	 OnClick oc;
	public void setAdapterOnclickLinstener(OnClick oc){
		this.oc = oc;
	};
	public interface OnClick{
		void onclick(int op);
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		viewHolder vh ;
		if(convertView == null){
			vh = new viewHolder();
			convertView = mInflater.inflate(R.layout.item_order,null);
			vh.orderPrice = (TextView)convertView.findViewById(R.id.item_order_price);
			vh.orderTime = (TextView)convertView.findViewById(R.id.item_order_time);
			vh.orderInfo = (TextView)convertView.findViewById(R.id.item_order_info);
			vh.orderNumber = (TextView)convertView.findViewById(R.id.item_order_number);
			convertView.setTag(vh);
		}else
			vh = (viewHolder)convertView.getTag();
		vh.orderTime.setText("订单时间："+datalist.get(position).getCreatedAt());
		vh.orderPrice.setText("订单价格："+datalist.get(position).getOrderPrice()+"元");
		vh.orderInfo.setText("订单信息："+datalist.get(position).getOrderInfo());
		vh.orderNumber.setText("订单编号："+datalist.get(position).getOrderNumber());
		return convertView;
	}
	class viewHolder{
		TextView orderPrice,orderTime,orderNumber,orderInfo;
	}
}
