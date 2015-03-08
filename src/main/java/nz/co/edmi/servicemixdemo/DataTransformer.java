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
import org.apache.camel.Handler;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static nz.co.edmi.servicemixdemo.ErrorHandling.USER_HOME;

public class DataTransformer extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file://" + USER_HOME + "/CamelDemo/DataTransformer")
            .unmarshal().json(JsonLibrary.Gson, Foo.class)
            .bean(new FooLoggingHandler());
  }

  public class FooLoggingHandler {

    @Handler
    public void handle(Foo foo) {
      System.out.println("Foo " + foo.getName() + " has " + foo.getBars().size() + " bars");
    }

  }

}
