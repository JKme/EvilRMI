package com.rmi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RMITest {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext =  new InitialContext();
        Object lookup = initialContext.lookup("rmi://127.0.0.1:1099/tomcat_xstream");
//        Object lookup = initialContext.lookup("ldap://127.0.0.1:1389/Exploit");

        System.out.println(lookup);
    }
}
