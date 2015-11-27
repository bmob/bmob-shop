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
	
	CircleImageView shop_avator;//头像
	TextView shop_name;//店铺名字
	ListView goods_list;//商品列表listview
	Shop getshop;//传进来的Shop对象
	ArrayList<Goods> getlist;//从主界面传进来的goods集合
	GoodsAdapter goodsAdapter;//适配器
	
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
		//ImageLoader图片加载
		ImageViewUtil.setUrlImageView(getshop.getShopAvator().getFileUrl(this),shop_avator);
	}
	protected void setLinstener() {
		
	}
	//adapter里面的onclik接口
	public void onclick(int position) {
		//跳转到支付界面
		Intent intent = new Intent(this,PayActivity.class);
		Log.i("price", getlist.get(position).getGoods_price()+"");
		intent.putExtra("price",getlist.get(position).getGoods_price());
		startActivity(intent);
	}
}
