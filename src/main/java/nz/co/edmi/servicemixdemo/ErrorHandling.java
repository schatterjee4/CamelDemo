/*
 * Copyright (c) 2015 EDMI NZ
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of EDMI. 
 * ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with EDMI.
 */
package nz.co.edmi.servicemixdemo;

import java.net.ConnectException;

import nz.co.edmi.servicemixdemo.json.Foo;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class ErrorHandling extends RouteBuilder {

  public static final String USER_HOME = System.getProperty("user.home");

  @Override
  public void configure() throws Exception {

//    errorHandler(deadLetterChannel("Error in file ${file:name} Sending to dead letter channel?level=ERROR"));
    errorHandler(deadLetterChannel("direct:errors"));

    onException(ConnectException.class) // smtp server unavailable
            .maximumRedeliveries(100)
            .backOffMultiplier(2)
            .maximumRedeliveryDelay(10000)
            .retryAttemptedLogLevel(LoggingLevel.WARN);


    from("file://" + USER_HOME + "/CamelDemo/ErrorHandling")
            .unmarshal().json(JsonLibrary.Gson, Foo.class)
            .marshal().json(JsonLibrary.Gson)
            .to("smtp://localhost:1025?password=somepwd&username=someuser")
            .log("Email sent");


    from("direct:errors")
            .log("Error in file ${file:name}");
  }


}
