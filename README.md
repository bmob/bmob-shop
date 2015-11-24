# bmob-shop
Bmob 电商类的demo和文档


BMOB Shop Demo for Android 实战文档

一、初识BMOB
　　*本小节主要讲解BMOB的登录及注册及后台数据库、自定义User类等应用*
1.首先我们create a new android project，名为BmobDemo，包名为bmob.store.demo
然后我们建立如下包（右击-->new-->package）：
　　bmob.store.demo.ui(存放ui主界面)
　　Bmob.store.demo.base(存放基础类)
　　bmob.store.demo.fragment(存放fragment)
　　bmob.store.demo.config(存放静态string字段)
　　bmob.store.demo.bean(存放实体类)
　　bmob.store.demo.adapter（存放适配器）  
　　bmob.store.demo.utlis（存放常用工具类）
　　Bmob.store.demo.view（存放自定义view）
1.1 导入bmob libs（我这是3.4.1，最新是3.4.4）
　　将Bmob sdk_xx.jar放入workplace/BmobDemo/libs下
　　
1.2 在BMOB官网后台新建一个应用
　　
接下来在bmob.store.demo.config包下新建一个类，名为MyConfig，将刚刚新建好的应用的APP_ID存放进去
　　
2.关于Base类
（对于以后的布局文件的说明）由于本教程注重对BMOB的应用，对于没有特殊要求的布局文件就不多做说明了~
　　在此之前，我们在.base包下新建一个class，名为BaseFragmentActivity，并且继承自FragmentActivity，因为在后面要使用Fragment，所以用fragmentActivity，以下是详细代码：
　　
　　
　　getLayoutViewID()：获取一个layout布局文件的id
　　FindViews()：findViewById()集合
　　SetupViews()：设置View属性
　　setLinstener()：设置监听器
　　StartAnimActivity(class<?> cla)：intent不含数据的activity跳转
　　StartAnimActivity(Intent intent)：intent含数据的activity跳转
然后新建一个类baseApplication，继承application，并初始化ImageLoader相关属性

3.UI界面
　　1.在.ui包下新建一个类，名为SplashActivity

　　然后在layout文件夹下新建一个xml文件，名为activty_splash
　　布局内容：
　　
　　SplashActivity继承自BaseFragmentActivity，我们在getLayoutViewID()方法中返回R.layout.splash_activity

并重写OnCreate，初始化Bmob SDK，代码如下：


　　2.然后创建一个自定义user的实体类，在.bean包下新建一个Class，名为MyUser，继承自BmobUser，这样我们就可以有了自己定义的属性的user，代码如下：
　　
　　需要说明的是上传Bmob后台所需的数据类型必须是包装类，比如Integer，Boolean等
　　还有这里为什么没有写userName和passWord属性，这是因为这些方法都是BmobUser自带的属性，可以直接set和get，详细请看bmob类库
　　回到SplashActivity，新建一个handler线程，用于跳转主界面或登录界面，利用两个静态String常量来代表当前的状态
　　
　　
　　
　　然后判断是否有过登录用户，如果登录过直接进入主界面，如果没有进入登录界面，首先声明一个我们自定义的MyUser的变量myuser，然后在setupViews判断是否为Null（也可以在onCreate方法中判断），null说明没有登录过。
　　
　　
2.登录界面
　　-->布局文件，activity_login.xml
　　
　　editText的命名为：login_edUsername、login_edPassword
　　Button命名：login_BtnLogin
　　TextView命名：login_tvRegister、login_tvFound
相关布局：

相关selector:
Edittext_bg.xml

Login_btn_selector.xml:

资源文件浏览：

在.ui包下新建一个类：LoginActivity,继承baseFragmentActivity，然后命名，获取控件对象，判断登陆等操作.
-->LoginActivity.java代码：


3.修改androidMainfest.xml文件设置SplashActivity为主活动，代码如下：





关于注册界面这里暂且略过吧~注意BmobUser类的注册方法是signUp而不是save。
这里暂且不要登录，因为mainAcitiviy还没写好
我们先在androidMainfest.xml添加网络及相关权限，不然是登录不了的。

3.搭建mainActivity
　　由于时间和效率的关系，我就不写复杂了，就用3个fragment吧
首先是布局文件
Activity_main.xml：



layout_ActionBar.xml:


Layout_mainBottom.xml:

Tab_fragment1_btn.xml(其他两个一样):

效果：

你还可以在图标下面加文字，这个效果很简单，自己做吧~

接下来在.Base类下新建一个baseFragment类，代码如下：

新建一个xml，名为fragment_shop
布局如下：


@style:baseBtn:

Colors.xml

提醒：最好把dp、sp等常量放入dimens.xml下，以及常用的布局style放入styles.xml,常用的String常量让入strings.xml，还有常用的颜色

接下来在.fragment下新建3个类，名为ShopFragment、TalkFragment、BBSFragemnt，都继承自baseFragment，这是ShopFragment代码，如下：


在baseFragmentActivity声明fragment管理器和事务：

在findviews()中获取:

然后声明3个按钮和shop_fragment并实例化以及adtionbar的设置:
首先是声明：


然后是相关控件和布局获取Id:

然后是获取第一个fragment的实例并添加等操作：

然后我们要写一个底部按钮的点击后触发的方法，名字叫OnTabBtnClick(View v)
并在底部布局中的每一个Button加入onClick属性，并赋值为onTabBtnClick，需要注意的如果没有View v这个参数那么这个方法是无效的。



Index 和lastindex的声明：

HideFragments方法：

不要着急运行，最后在splashActivity的handler线程里面添加：

然后在TalkFragment的类和BBSFragment的类中都设置返回这个值，因为默认为0会报错

然后在后台新建一条user表数据
账号bmob，密码123456

运行效果如图（真机），现在可以随意切换了actionbar的标题也会改变，其他的fragment以后添加吧，因为我们接下来要做的是购买物品和开店、发布商品，订单管理系统。
























二、强大的BMOB后端云
1.我们应该是先有店——再有商品——有人购买——产生订单
所以先来创建RealeseShopActivity并返回layout ID


然后创建创建一个布局文件，名为realese_shop_activity
布局详情看源码吧,因为里面有dimens,color,style,selector等,这里就不放出来了
最后在ShopFragment中添加跳转的代码:

效果图：

回到realeseActivity,由于效率关系,这里只介绍BmobFile上传和shop类的存储
1.首先获取到加号按钮的ID，以及显示dialog

跳转到相机界面的方法:

回调ActivityResult方法:

在返回的相机状态码之后跳转到裁剪界面:

然后在获取的裁剪状态码后进行压缩和上传:


接下来在.bean中新建一个类,叫Shop,并在shop中建立user对象

在User类中设定一个boolean值来判断是否有开了小店

然后获得创建小店的Button并创建:


最后我们再到shopFragemnt设置一下不能开启2个店铺:

如图




后台的店铺数据：

现在来shopfragment布局添加一个添加商品的按钮:

并在shopFragment中绑定ID和监听器,然后创建一个realese_goods_activity的布局文件

在.ui下创建一个RealeseGoodsActivity类并返回ID

在这之前,在.bean包下创建一个goods类

发布商品按钮的点击事件:


后台数据:

接下来构建商品列表页面,创建一个布局文件:activity_goods_list
里面是一个ListView
然后来构建listView每一行的Layout布局



创建一个类，作为购买的界面，buygoodsActivity，并继承baseFragmentActivity


然后在shopFragment的点击事件中将查询到的信息传给刚刚创建的buyGoodsActivity
这里查询我放在了handler里面，防止阻塞主线程
具体的查询代码在queryGoods里面


*用include方法可以查询出关联数据（但不能查询多对多）
然后在buygoodsactivity中接受并设置：

效果如图：

需要说明的是图标的设置是用imageloader这个库来加载，详细的设置在baseApplication里面以及setUrliamgeView方法。
还有goodsAdapter类主要代码：
创建activity_pay.xml及PayActivity

相关设置




在goodsadapter中设置一个接口供bugGoodsActivity使用：


BuyGoodsActivity中继承接口并实现该方法：


效果如图：

在使用bmob pay sdk之前，从官网下载最新的pay sdk for android
然后将下面的2个sdk放入libs中

在payActivity中的payZFB方法调用支付函数：

截图：



*注：pay方法的第一个参数是价格，第二个参数是订单信息，第三个是回调接口
具体回调的函数的含义请参考官方文档
在这里我们需要新建一个order订单实体类，来供我们查询

并在支付成功的方法中创建：


后台数据：

接下来在我的订单中写一个类来存放查询到的订单信息：
布局文件：

MyOrderActivity:

OrderAdapter类:


然后在主界面进行查询并通过Intent传order的数据集合
ShopFragment:


效果如图：

接下来做一个推送功能吧，用于告知店主有用户下单的情况
下载官网最新的推送sdk for android

把sdk放入libs文件夹：

在androidMainfest.xml中声明权限：

并注册服务及接收器：


并在application中启动push服务，并保存bmobinstallation数据

新建一个类名为MyPushMessageReceiver

*由于个人推送的内容是json所以这里需要json解析
需要注意的是推送给指定用户需要用到BmobInstallation表，并且需要自己自定义一个字段来标识用户
在bean包下新建一个类：

然后在注册的时候，将我们需要更新的字段的值更新，这里不能save

后台数据：

然后在payActivity中支付成功的地方进行推送

需要注意的是：这里pushId的值为bmobshop是店主的账户的pushId
以下是账号信息：
开店人：
账号：bmob 密码：123456 pushId:bmobshop
用户：
账号：bmobtest 密码：123456 pushId:bmobpushtest
后台数据：


效果如图（模拟器是用户，真机是店主）：
　　
这样就完成了指定用户推送。
