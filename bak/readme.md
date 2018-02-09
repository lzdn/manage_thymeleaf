在mapper.xml中使用redis作为二级缓存，配置主要有：

在mapper.xml最上面加入：
   <!-- 开启基于redis的二级缓存 -->
 <!--  <cache type="com.lzdn.manage.utils.RedisCache" >
   	<property name="eviction" value="LRU" />
     <property name="flushInterval" value="6000000" />
      <property name="size" value="1024" />
       <property name="readOnly" value="false" />
  </cache>
 -->
 和
 RedisCache.java
 
 http://blog.csdn.net/zhe1110/article/details/52993082