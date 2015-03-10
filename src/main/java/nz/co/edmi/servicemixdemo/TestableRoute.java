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
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.model.dataformat.JsonLibrary;

import static nz.co.edmi.servicemixdemo.ErrorHandling.USER_HOME;

public class TestableRoute extends RouteBuilder {

  private String src = "file://" + USER_HOME + "/CamelDemo/TestableFileMover";

  private String dest = "file://" + USER_HOME + "/CamelDemo/dump";


  @Override
  public void configure() throws Exception {
     from(src)
             .unmarshal().json(JsonLibrary.Gson, Foo.class)
             .marshal().xstream()
             .log("transformed file")
             .to(dest + "?fileName=$simple{file:name.noext}.xml");
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }
}
