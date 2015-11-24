package bmob.store.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject implements Serializable{
	
	public String orderInfo;
	public Float orderPrice;
	public String orderNumber;
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public Float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Float orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
