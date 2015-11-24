package bmob.store.demo.base;

import java.io.File;

import android.app.Application;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.util.Log;
import bmob.store.demo.config.MyConfig;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.BmobInstallation;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class BaseApplication extends Application{
	
	
	private static BaseApplication mApplication;
	
	public static BaseApplication getInstance(){
		return mApplication == null?new BaseApplication():mApplication;
	}
	public void aadd(){
		Log.i("tag","test");
	}
	public void onCreate() {
		super.onCreate();
		init();
	}
	public void init(){
		mApplication = this;
	    BmobInstallation.getCurrentInstallation(this).save();
	    // 启动推送服务
	    BmobPush.startWork(this,MyConfig.APP_ID);
		initImageLoader();
	}
	NotificationManager mNotificationManager;

	public NotificationManager getNotificationManager() {
		
		return mNotificationManager == null?(NotificationManager) getSystemService
				(android.content.Context.NOTIFICATION_SERVICE):mNotificationManager;
	}
	public void initImageLoader(){
		File cacheDir = StorageUtils.getCacheDirectory(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
										.memoryCache(new LruMemoryCache(5*1024*1024))
										.memoryCacheSize(10*1024*1024)
										.discCache(new UnlimitedDiscCache(cacheDir))
										.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
										.build();
		ImageLoader.getInstance().init(config);
	}
	/**
	 * 设置imageloader默认设置
	 * @param drawableId
	 * @return
	 */
	public DisplayImageOptions getOptions(int drawableId){
		return new DisplayImageOptions.Builder()
		.showImageOnLoading(drawableId)
		.showImageForEmptyUri(drawableId)
		.showImageOnFail(drawableId)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
}
