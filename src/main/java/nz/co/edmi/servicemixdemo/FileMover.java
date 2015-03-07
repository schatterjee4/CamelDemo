package nz.co.edmi.servicemixdemo;

import org.apache.camel.builder.RouteBuilder;

public class FileMover extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:///Users/simonvandersluis/CamelDemo/FileMover")
            .log("Moving ${file:name} to the output directory")
            .to("file:///Users/simonvandersluis/CamelDemo/dump");
  }
}
