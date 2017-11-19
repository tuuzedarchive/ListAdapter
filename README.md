[![](https://jitpack.io/v/tuuzed/adapter.svg)](https://jitpack.io/#tuuzed/adapter)

## 0x00如何添加该开源库

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

## 0x01如何使用
```java
RecyclerView recyclerView = findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
BaseAdapter adapter = BaseAdapter.create()
        .register(String.class, R.layout.item_string, new BaseItemProvider<String>() {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, String item, final int position) {
                holder.text(R.id.text, "# " + item + ": " + "STRING");
            }
        })
        .register(Integer.class, R.layout.item_integer, new BaseItemProvider<Integer>() {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, Integer item, int position) {
                holder.text(R.id.text, "# " + item + ": " + "INTEGER");
            }
        })
        .register(Long.class, R.layout.item_long, new BaseItemProvider<Long>() {
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, Long item, int position) {
                holder.text(R.id.text, "# " + item + ": " + "LONG");
            }
        })
        .attach(recyclerView);
for (int i = 0; i < 10; i++) {
    mAdapter.items().add((long) i);
    mAdapter.items().add(String.valueOf(i));
    mAdapter.items().add(i);
}
adapter.notifyDataSetChanged();
```
