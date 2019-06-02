# ListAdapter
Android RecyclerView Adapter

[ ![Download](https://api.bintray.com/packages/tuuzed/maven/com.tuuzed.androidx.list%3Aadapter/images/download.svg) ](https://bintray.com/tuuzed/maven/com.tuuzed.androidx.list%3Aadapter/_latestVersion)

### 添加依赖

``` groovy
repositories {
    maven { url 'https://dl.bintray.com/tuuzed/maven' }
}

dependencies {

    def list_version = 'latest.release'
    // adapter
    implementation "com.tuuzed.androidx.list:adapter:$list_version"
    implementation "com.tuuzed.androidx.list:adapter-ktx:$list_version"
    
    // loadmore
    implementation "com.tuuzed.androidx.list:loadmore:$list_version"
    implementation "com.tuuzed.androidx.list:loadmore-ktx:$list_version"
    
    // pageable
    implementation "com.tuuzed.androidx.list:pageable:$list_version"
    
    // preference
    implementation "com.tuuzed.androidx.list:preference:$list_version"
    implementation "com.tuuzed.androidx.list:preference-ktx:$list_version"
   
}
```
