package nz.co.edmi.servicemixdemo;

import org.apache.camel.builder.RouteBuilder;

import static nz.co.edmi.servicemixdemo.ErrorHandling.USER_HOME;

public class FileMover extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file://" + USER_HOME + "/CamelDemo/FileMover")
            .log("Moving ${file:name} to the output directory")
            .to("file://" + USER_HOME + "/CamelDemo/dump");
  }
}
