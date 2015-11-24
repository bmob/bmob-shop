package bmob.store.demo.ui;

import java.util.ArrayList;

import android.util.Log;
import android.widget.ListView;
import bmob.store.demo.R;
import bmob.store.demo.adapter.OrderAdapter;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.Order;

public class MyOrderActivity extends BaseFragmentActivity{
	
	ListView orderListView;
	OrderAdapter orderAdapter;
	ArrayList<Order> getlist;
	protected int getLayoutViewID() {
		return R.layout.activity_myorder;
	}

	protected void findViews() {
		getlist = (ArrayList<Order>)getIntent().getSerializableExtra("order");
		orderListView = (ListView)findViewById(R.id.order_listView);
	}

	protected void setupViews() {
		orderAdapter = new OrderAdapter(this, getlist);
		orderListView.setAdapter(orderAdapter);
	}

	protected void setLinstener() {
		
	}

}
