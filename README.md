[![](https://jitpack.io/v/TuuZed/Adapter.svg)](https://jitpack.io/#TuuZed/Adapter)
## 如何添加该开源库
### 添加JitPack仓库
在当前项目的根目录下的 `build.gradle` 文件中添加如下内容:
```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
### 添加项目依赖
```groovy
dependencies {
        compile 'com.github.tuuzed:adapter:version'
}
```
## 如何使用
### 新建一个类实现ItemProvider接口
```java
// StringItemProvider.java
public class StringItemProvider extends ItemProvider<String> {


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item, int position) {
        holder.$(R.id.text, TextView.class).setText("String =>" + item);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_string;
    }
}

```
### 实例化RecyclerViewAdapter并注册ItemProvider
```java
Items items = new Items();
RecyclerViewAdapter adapter = new RecyclerViewAdapter(context,items);
adapter.register(String.class, new StringItemProvider());
```
### 为RecyclerView设置LayoutManager和Adapter
```java
recyclerView.setLayoutManager(new LinearLayoutManager(context));
recyclerView.setAdapter(adapter);
```
### 添加数据项并通知适配器刷新数据
```java
for (int i = 0; i < 5; i++) {
    items.add(String.valueOf(i));
}
adapter.notifyDataSetChanged();
```
