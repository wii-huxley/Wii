# 使用


#### 1. 在Application的onCreate里初始化

```
PermissionCheckSDK.init(Application);
```

#### 2. 在app层动态添加权限的两种方式

* 使用PermissionCheckSDK.addCheckPermissionItem(CheckPermissionItem item)

> 通过这种方式可以给app里的Activity及依赖的第三方库的Activity类分配动态权限

例如：

```
//在Application里初始化app里所有Activity的权限
 String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        AOPSDK.addCheckPermissionItem(new CheckPermissionItem("com.hujiang.hjwordbookuikit.app.activity.RawWordActivity", permissions));

```
* 使用注解的方式添加权限@NeedPermission

@NeedPermission可以为任意的类及他们的方法(包括静态方法，私有方法)配置权限，

> 建议：如果是为方法配置权限，建议方法的返回为类型为void。否则当用户不授权时，逻辑往下执行可能出现NullPointException。

例如：

```
//作用于Activity
@NeedPermission(permissions = {Manifest.permission.READ_CONTACTS
        , Manifest.permission.WRITE_CONTACTS})
public class BActivity extends Activity {
}

//作用于Activity，Fragment的方法
    @NeedPermission(permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    private void startBActivity(String name, long id) {
        startActivity(new Intent(MainActivity.this, BActivity.class));
    }

```
