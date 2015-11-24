package bmob.store.demo.ui;

import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseFragmentActivity;
import bmob.store.demo.bean.MyBmobInstallation;
import bmob.store.demo.bean.MyUser;
import bmob.store.demo.utlis.ToastFactory;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends BaseFragmentActivity{
	
	Button BtnRegister;
	protected int getLayoutViewID() {
		return R.layout.activity_register;
	}
	
	protected void findViews() {
		BtnRegister = (Button)findViewById(R.id.register_BtnRegister);
	}
	
	protected void setupViews() {
		
	}

	protected void setLinstener() {
		BtnRegister.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				myHandler.sendEmptyMessage(1);
			}
		});
	}
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				createUser();
				break;
			}
		}
	};
	public void createUser(){
		ToastFactory.showProgressDialog(this,"正在注册...");
		MyUser mu = new MyUser();
		mu.setUsername("bmobtest");
		mu.setPassword("123456");
		mu.setAge(22);
		mu.setIs_openShop(false);
		mu.setSex(false);
		mu.signUp(this,new SaveListener() {
			public void onSuccess() {
				saveInstallationInfo();
			}
			public void onFailure(int arg0, String arg1) {
				ToastFactory.dismissProgressDialog();ToastFactory.show(RegisterActivity.this,"注册失败");
			}
		});
	}
	public void saveInstallationInfo(){
		BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
		query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
		query.findObjects(this, new FindListener<MyBmobInstallation>() {
		    public void onSuccess(List<MyBmobInstallation> object) {
		        if(object.size() != 0){
		        	//有了bmobinstallation
		            MyBmobInstallation mbi = object.get(0);
		            mbi.setPushId("bmobpushtest");
		            mbi.update(RegisterActivity.this,new UpdateListener() {
		                public void onSuccess() {
		                	ToastFactory.dismissProgressDialog();
		    				ToastFactory.show(RegisterActivity.this,"注册成功！");
		    				finish();
		                }
		                public void onFailure(int code, String msg) {
		                }
		            });
		        }
		    }
		    public void onError(int code, String msg) {
		    }
		});
	}
}
