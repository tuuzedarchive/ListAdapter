[![](https://jitpack.io/v/tuuzed/adapter.svg)](https://jitpack.io/#tuuzed/adapter)

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
        compile 'com.github.tuuzed:adapter:v2.0.0'
}
```