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


import java.io.File;
import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import static nz.co.edmi.servicemixdemo.CamelTestUtils.waitForCamel;

public class TestableRouteTest extends CamelTestSupport {


  String src = "build/testableRouteSrc";
  String dest = "build/testableRouteDest";

  String json;
  String xml;


  TestableRoute route;

  @Before
  public void cleanUp() {
    FileSystemUtils.deleteRecursively(new File(src));
    FileSystemUtils.deleteRecursively(new File(dest));
  }

  @Before
  public void loadJson() throws Exception {
    InputStream in = this.getClass().getResourceAsStream("foo.json");
    json = IOUtils.toString(in);
  }

  @Before
  public void loadXml() throws Exception {
    InputStream in = this.getClass().getResourceAsStream("foo.xml");
    xml = IOUtils.toString(in);
  }


  @Override
  protected RouteBuilder createRouteBuilder() throws Exception {

    route = new TestableRoute();
    route.setSrc("file://" + src);
    route.setDest("file://" + dest);

    return route;
  }


  @Test
  public void testMove() throws Exception {


    template.sendBodyAndHeader("file://" + src, json, Exchange.FILE_NAME, "foo.json");
    waitForCamel(4000);

    File output = new File(dest + "/foo.xml");
//    Assertions.assertThat(output).exists();

//    String actualXml = context().getTypeConverter().convertTo(String.class, output);
//    Assertions.assertThat(actualXml).isEqualTo(xml);
  }


}
