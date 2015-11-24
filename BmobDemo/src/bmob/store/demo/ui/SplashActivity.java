package bmob.store.demo.ui;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.config.MyConfig;

public class SplashActivity extends BaseFragmentActivity{
	
	public static final int HOME =1;
	public static final int LOGIN = 2;
	
	MyUser myuser;
	protected int getLayoutViewID() {
		return R.layout.activity_splash;
	}
	
	
	protected void findViews() {
		Bmob.initialize(this,MyConfig.APP_ID);
		myuser = BmobUser.getCurrentUser(this,MyUser.class);
		if(myuser == null)
			Log.i("log", "user == null");
	}
	
	protected void setupViews() {
		if(myuser == null){
			mHandler.sendEmptyMessageDelayed(LOGIN,2000);
		}else{
			mHandler.sendEmptyMessageDelayed(HOME,2000);
		}
	}

	protected void setLinstener() {
		
	}
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HOME:
				//跳转到主页
				startAnimActivity(MainActivity.class);
				finish();
				break;
			case LOGIN:
				//跳转到登录界面
				startAnimActivity(LoginActivity.class);
				finish();
				break;
			default:
				break;
			}
		}
	};
}
