### 说明
本库原理模仿的是InterStellar，在实现上功能有所精简，比如没有实现gradle中配置进程以及
in、out等入参注解，没有太多错误处理以及兼容兼容问题，专注于解决不写aidl实现跨进程通信
的问题，因此不适合拿来即用，但是适合研究学习。

### 基本使用
1、框架初始化：
```
InterStellar.init(this);
```
2、服务接口定义：
```
public interface IApi {

    int testBasicType(int a, int b); 

    String testParcelable(ManInfo manInfo);

    void testInOut(ManInfo manInfo);

    void testCallback(ICallback callback);
}
```
以上是java代码，并非写在aidl中，可见和一般的java接口完全无异，其中ManInfo是实现了
Parcelable的一般对象，ICallback是框架提供的Callback类，如果要以回调的形式接收
结果，必须用框架提供的这个ICallback类，不能是其子类或者其他自定义的接口。这是框架
实现原理导致的，具体可以参考源码。<br/>
2、注册服务接口的实现：
```
InterStellar.registerRemoteService(IApi.class, new IApi() {
        @Override
        public int testBasicType(int a, int b) {
            return a + b;
        }

        @Override
        public String testParcelable(ManInfo manInfo) {
            return manInfo.getName() + ":" + manInfo.getAge();
        }

        @Override
        public void testInOut(ManInfo manInfo) {
            manInfo.setName(manInfo.getName() + " append lisi");
            manInfo.setAge(manInfo.getAge() + 20);
        }

        @Override
        public void testCallback(ICallback callback) {
            try {
                callback.onFail("哦豁， 失败了");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });
```
3、获取服务接口：
```
IApi api = InterStellar.getRemoteService(IApi.class);
```
4、调用服务接口方法：
```
    int add = api.testBasicType(2, 3);
   
    String s = api.testParcelable(new ManInfo("wangwu", 12));
   
    ManInfo info = new ManInfo("zhangsan", 10);
    api.testInOut(info);
    
    // Callback实例需要继承ICallback.Stub，这BaseCallback还完成了线程切换到主线程
    api.testCallback(new BaseCallback() {
        @Override
        public void onSucceed(Bundle result) {

        }

        @Override
        public void onFailed(String errorMsg) {
            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    });
```
以上4个demo接口方法，基本上涵盖平时开发的大部分场景了。可见，这个使用下来，完全没涉及任何aidl的编写，
并且在使用上和单进程中非常类似。