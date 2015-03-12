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

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;


public class BestPracticePart2 extends RouteBuilder {


  @Override
  public void configure() throws Exception {

    onException(ConnectException.class) // smtp server unavailable
            .maximumRedeliveries(100)
            .backOffMultiplier(2)
            .maximumRedeliveryDelay(10000)
            .retryAttemptedLogLevel(LoggingLevel.WARN);

    from("activemq:fooMail")
            .to("smtp://localhost:1025?password=somepwd&username=tooManyBarsViaMQ@foo.org&from=simon@x.org&contentType=text/html");

  }


}