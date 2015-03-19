
/**
 * TerminatingExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

package com.readycashng.www.ws.api._1_0.test;

public class TerminatingExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1418058895947L;
    
    private com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.TerminatingException faultMessage;

    
        public TerminatingExceptionException() {
            super("TerminatingExceptionException");
        }

        public TerminatingExceptionException(java.lang.String s) {
           super(s);
        }

        public TerminatingExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public TerminatingExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.TerminatingException msg){
       faultMessage = msg;
    }
    
    public com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.TerminatingException getFaultMessage(){
       return faultMessage;
    }
}
    