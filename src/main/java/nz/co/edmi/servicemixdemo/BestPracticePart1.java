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

import nz.co.edmi.servicemixdemo.json.Foo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static nz.co.edmi.servicemixdemo.ErrorHandling.USER_HOME;

public class BestPracticePart1 extends RouteBuilder {


  @Override
  public void configure() throws Exception {


    from("file://" + USER_HOME + "/CamelDemo/BestPractice")
            .unmarshal().json(JsonLibrary.Gson, Foo.class)
            .to("velocity:nz/co/edmi/servicemixdemo/tooManyFoosEmail.vm")

              // don't go direct to smtp, as that is an external service and could be offline
              // which would cause this route to go crazy with re-tries,
              // or be slowed down when during re-try backoff
              // instead decouple external services with active-mq
            .to("activemq:fooMail");

  }


}