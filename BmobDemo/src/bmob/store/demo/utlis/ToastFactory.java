package bmob.store.demo.utlis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import bmob.store.demo.R;
import bmob.store.demo.base.BaseApplication;

/**
 * 解决Toast重复出现多次，保持全局只有一个Toast实例
 * 
 * @author adison
 * 
 */
public class ToastFactory {
	private static Context context = null;
	private static Toast mToast = null;
	
	
	@SuppressLint("ShowToast") 
	public static Toast showToast(Context context, String text) {
		if (ToastFactory.context == context) {
			// toast.cancel();
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		} else {
			ToastFactory.context = context;
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		return mToast;
	}
	
	  public interface MessageFilter {

	        String filter(String msg);
	    }
	public static MessageFilter msgFilter;
    public static void show(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                // Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                Toast toast=ToastFactory.showToast(activity, message);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setMargin(0.0f, 0.1f);//0.1f = 10%
                toast.show();
            }
        });
    }
    private static ProgressDialog pd;
    public static void showProgressDialog(Context context,String content){
    	pd = ProgressDialog.show(context, "",content);
    }
    public static void dismissProgressDialog(){
    	pd.dismiss();
    }
	@SuppressLint("NewApi") 
	public static void showNotNotification(Context context,String title,String text){
		Notification noti = new Notification.Builder(context)        
		 .setContentTitle(title)    
		 .setContentText(text)//设置跳转可不设置
		 .setSmallIcon(R.drawable.ic_launcher) 
		 .setAutoCancel(true)
		 .build();
		BaseApplication.getInstance().getNotificationManager().notify(0,noti);
	}
}
