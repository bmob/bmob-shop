package bmob.store.demo.ui;


import java.util.List;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.Goods;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.bean.Shop;
import bmob.store.demo.utlis.ToastFactory;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RealeseGoodsActivity extends BaseFragmentActivity{
	
	Button realese_OK;
	protected int getLayoutViewID() {
		return R.layout.realese_goods_activity;
	}
	
	protected void findViews() {
		realese_OK = (Button)findViewById(R.id.release_Goods_ok);
	}
		
	protected void setupViews() {
		
	}
		
	protected void setLinstener() {
		realese_OK.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final ProgressDialog pd = ProgressDialog.show(RealeseGoodsActivity.this, "","正在操作....");
				MyUser mu = BmobUser.getCurrentUser(RealeseGoodsActivity.this,MyUser.class);
				BmobQuery<Shop> bs = new BmobQuery<Shop>();
				bs.findObjects(RealeseGoodsActivity.this, new FindListener<Shop>() {
					public void onSuccess(List<Shop> list) {
						if(list.size()!=0){
						    Goods g = new Goods();
							g.setGoods_price(0.2f);
							g.setGoods_type("小吃");
							g.setInfo("好吃");
							g.setStock(99);
							g.setGoods_shop(list.get(0));
							g.setGoods_name("辣条");
							g.save(RealeseGoodsActivity.this, new SaveListener() {
							public void onSuccess() {
								pd.dismiss();
								ToastFactory.show(RealeseGoodsActivity.this,"创建商品成功!");
								finish();
							}
							public void onFailure(int arg0, String arg1) {
								pd.dismiss();
							}
							});
						}else
							pd.dismiss();
					}
					public void onError(int arg0, String arg1) {
						pd.dismiss();
					}
				});
			}
		});
	}
}
