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
import bmob.store.demo.ui.PayActivity;

public class GoodsAdapter extends BaseAdapter{
	
	
	private ArrayList<Goods> datalist;
	private Context context;
	private LayoutInflater mInflater; 
	public GoodsAdapter(Context context,ArrayList<Goods> datalist){
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
			convertView = mInflater.inflate(R.layout.item_goods,null);
			vh.goodsname = (TextView)convertView.findViewById(R.id.item_goods_name);
			vh.goodsinfo = (TextView)convertView.findViewById(R.id.item_goods_info);
			vh.goodsavator = (ImageView)convertView.findViewById(R.id.item_goodsavator);
			vh.buyBtn = (Button)convertView.findViewById(R.id.item_goods_buyBtn);
			convertView.setTag(vh);
		}else
			vh = (viewHolder)convertView.getTag();
		vh.goodsname.setText(datalist.get(position).getGoods_name());
		vh.goodsavator.setBackgroundResource(R.drawable.ic_launcher);
		vh.goodsinfo.setText(datalist.get(position).getInfo());
		vh.buyBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				oc.onclick(position);
			}
		});
		return convertView;
	}
	class viewHolder{
		TextView goodsname;
		ImageView goodsavator;
		TextView goodsinfo;
		Button buyBtn;
	}
}
