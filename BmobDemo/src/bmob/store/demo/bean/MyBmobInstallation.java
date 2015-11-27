package bmob.store.demo.bean;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;

public class MyBmobInstallation extends BmobInstallation{
	
	private String pushId;
	//这是推送给指定用户的唯一标识，其值可以是obejctId,可以是用户名
	
	public MyBmobInstallation(Context context) {
		super(context);
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
}
