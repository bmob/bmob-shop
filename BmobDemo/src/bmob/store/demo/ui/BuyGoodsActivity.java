package bmob.store.demo.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import bmob.store.demo.R;
import bmob.store.demo.adapter.GoodsAdapter;
import bmob.store.demo.adapter.GoodsAdapter.OnClick;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.Goods;
import bmob.store.demo.bean.Shop;
import bmob.store.demo.utlis.ImageViewUtil;
import bmob.store.demo.view.CircleImageView;

public class BuyGoodsActivity extends BaseFragmentActivity implements OnClick{
	
	CircleImageView shop_avator;
	TextView shop_name;
	ListView goods_list;
	Shop getshop;
	ArrayList<Goods> getlist;
	GoodsAdapter goodsAdapter;
	
	protected int getLayoutViewID() {
		return R.layout.activity_goods_list;
	}
	
	protected void findViews() {
		getshop = (Shop)getIntent().getSerializableExtra("shop");
		getlist = (ArrayList<Goods>)getIntent().getSerializableExtra("goods");
		shop_avator = (CircleImageView)findViewById(R.id.BuyGoodsAvator);
		shop_name = (TextView)findViewById(R.id.BuyGoodsShopName);
		goods_list = (ListView)findViewById(R.id.buy_listView);
	}

	protected void setupViews() {
		goodsAdapter = new GoodsAdapter(this, getlist);
		goodsAdapter.setAdapterOnclickLinstener(this);
		goods_list.setAdapter(goodsAdapter);
				shop_name.setText(getshop.getShopName());
		ImageViewUtil.setUrlImageView(getshop.getShopAvator().getFileUrl(this),shop_avator);
	}
	protected void setLinstener() {
		
	}

	public void onclick(int position) {
		//跳转到支付界面
		Intent intent = new Intent(this,PayActivity.class);
		Log.i("price", getlist.get(position).getGoods_price()+"");
		intent.putExtra("price",getlist.get(position).getGoods_price());
		startActivity(intent);
	}

}
