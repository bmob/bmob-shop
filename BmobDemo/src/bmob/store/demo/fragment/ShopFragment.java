package bmob.store.demo.fragment;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragment;
import bmob.store.demo.bean.Goods;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.bean.Order;
import bmob.store.demo.bean.Shop;
import bmob.store.demo.ui.BuyGoodsActivity;
import bmob.store.demo.ui.LoginActivity;
import bmob.store.demo.ui.MainActivity;
import bmob.store.demo.ui.MyOrderActivity;
import bmob.store.demo.ui.RealeseGoodsActivity;
import bmob.store.demo.ui.RealeseShopActivity;
import bmob.store.demo.utlis.ToastFactory;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class ShopFragment extends BaseFragment implements OnClickListener{

	Button shop_buy,shop_myShop,shop_myOrder,shop_addGoods,logout;
	private static final int BUY_GOODS = 100;
	private static final int ORDER = 200;
	MyUser u ;
	protected int getLayoutID() {
		return R.layout.fragment_shop;
	}
	protected void findViews() {
		Log.i("shopFragment","shop_Fragment");
		u = BmobUser.getCurrentUser(getActivity(), MyUser.class);
		shop_buy = (Button)contentView.findViewById(R.id.shop_BtnBuy);
		shop_myShop = (Button)contentView.findViewById(R.id.shop_BtnShop);
		shop_myOrder = (Button)contentView.findViewById(R.id.shop_BtnOrder);
		shop_addGoods = (Button)contentView.findViewById(R.id.shop_BtnAddGoods);
		logout = (Button)contentView.findViewById(R.id.shop_BtnLogout);
	}
	
	protected void setupViews() {
		
	}
	
	protected void setListener() {
		shop_buy.setOnClickListener(this);
		shop_myShop.setOnClickListener(this);
		shop_myOrder.setOnClickListener(this);
		shop_addGoods.setOnClickListener(this);
		logout.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shop_BtnBuy:
			myHandler.sendEmptyMessage(BUY_GOODS);
			break;
		case R.id.shop_BtnShop:
			if(!u.getIs_openShop())
				startActivity(new Intent(getActivity(),RealeseShopActivity.class));
			else 
				ToastFactory.show(getActivity(),"您已经有小店了!");
			break;
		case R.id.shop_BtnOrder:
				myHandler.sendEmptyMessage(ORDER);
			break;
		case R.id.shop_BtnAddGoods:
				startActivity(new Intent(getActivity(),RealeseGoodsActivity.class));
			break;
		case R.id.shop_BtnLogout:
				u.logOut(getActivity());
				startActivity(new Intent(getActivity(),LoginActivity.class));
				getActivity().finish();
			break;
		}
	}
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case BUY_GOODS:
				//查询商品信息
				queryGoods();
				break;
			case ORDER:
				//查询订单信息
				queryOrders();
				break;
			}
		}
	};
	public void queryOrders(){
		BmobQuery<Order> bo = new BmobQuery<Order>();
		bo.order("-createdAt");//降序
		bo.findObjects(getActivity(), new FindListener<Order>() {
			public void onSuccess(List<Order> list) {
				if(list.size()!=0){
					Intent i = new Intent(getActivity(),MyOrderActivity.class);
					i.putExtra("order",(Serializable)list);
					startActivity(i);
				}else{
					ToastFactory.show(getActivity(),"没有订单信息！");
				}
			}
			public void onError(int code, String message) {
				ToastFactory.show(getActivity(),"查询订单失败！");
			}
		});
	}
	public void queryGoods(){
		BmobQuery<Goods> gs = new BmobQuery<Goods>();
		gs.include("goods_shop");
		gs.addWhereEqualTo("MyUser", u);
		gs.findObjects(getActivity(),new FindListener<Goods>() {
			public void onSuccess(List<Goods> list) {
				if(list.size()!=0){
					Shop s = list.get(0).getGoods_shop();//获取店铺信息
					Intent i = new Intent(getActivity(),BuyGoodsActivity.class);
					i.putExtra("shop", s);
					i.putExtra("goods", (Serializable)list);
					startActivity(i);
				}
			}
			public void onError(int code, String message) {
			}
		});
	}
}
