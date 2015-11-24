package bmob.store.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Shop extends BmobObject implements Serializable{
	
	private String shopName;
	private BmobFile shopAvator;
	private String shopType;
	private String adress;
	private MyUser myuser;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public BmobFile getShopAvator() {
		return shopAvator;
	}
	public void setShopAvator(BmobFile shopAvator) {
		this.shopAvator = shopAvator;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public MyUser getMyuser() {
		return myuser;
	}
	public void setMyuser(MyUser myuser) {
		this.myuser = myuser;
	}
}
