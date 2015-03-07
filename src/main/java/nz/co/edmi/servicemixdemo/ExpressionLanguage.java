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

import java.util.Map;

import nz.co.edmi.servicemixdemo.json.Foo;
import org.apache.camel.Handler;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;


/** Shows off file filtering, and some simple el examples. */
public class ExpressionLanguage extends RouteBuilder {


  @Override
  public void configure() throws Exception {


    from("file:///Users/simonvandersluis/CamelDemo/ExpressionLanguage?include=(?i).*.json")
            .unmarshal().json(JsonLibrary.Gson, Foo.class)
            .to("direct:process");

    from("file:///Users/simonvandersluis/CamelDemo/ExpressionLanguage?include=(?i).*.xml")
            .unmarshal().xstream()
            .to("direct:process");


    from("direct:process")
            .bean(new BarCounter())
            .log("Counted ${headers[barCount]} bars")
            .choice()
            .when(simple("${body.bars.size} > 2")).to("direct:lotsOfBars")
            .otherwise().to("direct:archive");

    from("direct:lotsOfBars")
            .log("Lots of bars in ${file:name}")
            .to("direct:archive");


    from("direct:archive")
            .marshal().xstream()
            .to("file:///Users/simonvandersluis/CamelDemo/archive/?fileName=$simple{date:now:yyyy'/'MM'/'dd}/${file:name}");


  }


  public class BarCounter {

    @Handler
    public void countBars(Foo foo, @Headers Map<String, Object> headers) {
      headers.put("barCount", foo.getBars().size());
    }

  }

}