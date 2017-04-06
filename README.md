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
### 新建一个类实现ItemComponent接口
```java
// StringItemProvider.java
public class StringItemProvider extends ItemProvider<String, StringItemProvider.ViewHolder> {


    @Override
    public void onBindViewHolder(ViewHolder holder, String item, int position) {
        holder.textView.setText("String => " + item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new StringItemProvider.ViewHolder(inflater.inflate(R.layout.item_string, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}

```
### 实例化RecyclerViewAdapter并注册ItemComponent
```java
Items items = new Items();
RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
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
