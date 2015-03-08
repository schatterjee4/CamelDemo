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

import java.util.ArrayList;
import java.util.List;

import nz.co.edmi.servicemixdemo.json.Foo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import static nz.co.edmi.servicemixdemo.ErrorHandling.USER_HOME;


public class RecipientList extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file://" + USER_HOME + "/CamelDemo/RecipientList")
            .unmarshal().json(JsonLibrary.Gson, Foo.class)
            .recipientList().method(new RouteResolver(), "resolveRoutes").parallelProcessing();

    from("direct:notify")
           // .bean(new BarCounter())
            .to("velocity:nz/co/edmi/servicemixdemo/tooManyFoosEmail.vm")
            .to("smtp://localhost:1025?password=somepwd&username=tooManyBars@foo.org&from=simon@x.org&contentType=text/html");

    from("direct:log")
            .log("Received ${file:name}");

    from("direct:rl-archive")
            .marshal().xstream()
            .to("file://" + USER_HOME + "/CamelDemo/archive/?fileName=$simple{date:now:yyyy'/'MM'/'dd}/${file:name}");

  }


  public class RouteResolver {

    public List<String> resolveRoutes(Foo foo) {
      List<String> routes = new ArrayList<>();
      if (foo.getBars().size() > 2) {
        routes.add("direct:notify");
      }
      routes.add("direct:log");
      routes.add("direct:rl-archive");
      return routes;
    }
  }


}
