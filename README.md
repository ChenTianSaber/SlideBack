# SlideBack
一个让你的页面支持 滑动返回 的小东西

# 效果
![示例](https://user-gold-cdn.xitu.io/2019/2/8/168cd3a35dd2f34d?w=289&h=596&f=gif&s=218272)

# 使用方法：

Step 1. 在你项目的根build.gradle中添加jitpack.io库

````
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
````
Step 2. 添加SlideBack的依赖

````
dependencies {
    implementation 'com.github.ChenTianSaber:SlideBack:v0.8.1'
}
````

Step 3. 将项目中继承的Activity换成SlideBackActivity

````
public class YourActivity extends SlideBackActivity
````

Step 4. 你可以在onCreate方法中调用setSlideBackDirection，可以给每一个Activity单独配置，如果没有配置这个，那么默认是 SlideBackActivity.ALL

````
protected void onCreate(Bundle savedInstanceState) {
    //Other Code...

    //有三个值可以设置
    //SlideBackActivity.RIGHT  表示只能从屏幕右侧滑出
    //SlideBackActivity.LEFT  表示只能从屏幕左侧滑出
    //SlideBackActivity.ALL  表示从屏幕两边都可以滑出
    setSlideBackDirection(SlideBackActivity.RIGHT);
}
````

Step 5. 重写slideBackSuccess(当滑动有效时会回调这个方法，可以在这里进行回退操作或其他)

````
@Override
protected void slideBackSuccess() {
   finish();//或者其他
}
````

#### 希望大家能给我Star...
