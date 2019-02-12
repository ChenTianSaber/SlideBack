# SlideBack

# Example
![示例](https://user-gold-cdn.xitu.io/2019/2/8/168cd3a35dd2f34d?w=289&h=596&f=gif&s=218272)

# Uasge

Step 1. add jitpack.io to your root build.gradle

````
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
````
Step 2. add SlideBack

````
dependencies {
    implementation 'com.github.ChenTianSaber:SlideBack:v0.8.2'
}
````

Step 3. extends SlideBackActivity

````
public class YourActivity extends SlideBackActivity
````

Step 4. you can call setSlideBackDirection in onCreate, and you can set slideBackDirection for single Activity, the default value SlideBackActivity.ALL

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

Step 5. override slideBackSuccess(it will callback when slideback success ,you can do something in this like finish your activity)

````
@Override
protected void slideBackSuccess() {
   finish();//or other
}
````

#### Please give me Star...
