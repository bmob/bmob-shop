package bmob.store.demo.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.Order;
import bmob.store.demo.utlis.ToastFactory;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;

public class PayActivity extends BaseFragmentActivity implements OnClickListener{
	
	TextView price;
	Button zfb,weix,pay;
	Float get_price;
	public int STATE =-1;//0 :zfb 1:weixin
	protected int getLayoutViewID() {
		return R.layout.activity_pay;
	}

	protected void findViews() {
		get_price = getIntent().getFloatExtra("price",0.5f);
		price = (TextView)findViewById(R.id.pay_Allmoney);
		zfb = (Button)findViewById(R.id.pay_zhifubaoBtn);
		weix = (Button)findViewById(R.id.pay_weixinBtn);
		pay = (Button)findViewById(R.id.pay_okBtn);
	}
	
	protected void setupViews() {
		price.setText("总计："+get_price+"元");
	}
	
	protected void setLinstener() {
		zfb.setOnClickListener(this);
		weix.setOnClickListener(this);
		pay.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_weixinBtn:
			weix.setBackgroundResource(R.drawable.check_selected_green);
			zfb.setBackgroundResource(R.drawable.check_unselected);
			STATE = 1;
			break;
		case R.id.pay_zhifubaoBtn:
			zfb.setBackgroundResource(R.drawable.check_selected_green);
			weix.setBackgroundResource(R.drawable.check_unselected);
			STATE = 0;
			break;
		case R.id.pay_okBtn:
			myHandler.sendEmptyMessage(STATE);
			break;
		}
	}
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				//zfb
				payZFB();
				break;
			case 1:
				//weixin
				payWEIXIN();
				break;
			}
		}
	};
	public void payZFB(){
		//第一个参数是价格，为double类型
		//第二个参数是订单详情
		//第三个参数是回调接口
		new BmobPay(this).pay(get_price, "测试单号：123456", new PayListener() {
			public void unknow() {
				
			}
			public void succeed() {
				//创建订单
				createOrder();
			}
			
			public void orderId(String s) {
				//订单id，这是支付宝产生的
			}
			
			public void fail(int code, String message) {
				ToastFactory.show(PayActivity.this, "支付失败！");
				finish();
			}
		});
	}
	public void createOrder(){
		ToastFactory.showProgressDialog(this,"正在创建订单...");
		Order order = new Order();
		order.setOrderNumber("123456");
		order.setOrderPrice(get_price);
		order.setOrderInfo("测试商品");
		order.save(this,new SaveListener() {
			public void onSuccess() {
				ToastFactory.dismissProgressDialog();
//				ToastFactory.show(PayActivity.this, "支付成功！");
				ToastFactory.showNotNotification(PayActivity.this, "", "恭喜！下单成功！——bmob store");
				//推送给用户
				BmobPushManager bpm =new BmobPushManager(PayActivity.this);
				BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
				query.addWhereEqualTo("pushId","bmobshop");
				bpm.setQuery(query);
			   try {
				bpm.pushMessage(new JSONObject().put("bmobpushText","有人下单了~~ "));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				finish();
			}
			public void onFailure(int arg0, String arg1) {
				ToastFactory.dismissProgressDialog();
			}
		});
	}
	public void payWEIXIN(){
		
	}
}
