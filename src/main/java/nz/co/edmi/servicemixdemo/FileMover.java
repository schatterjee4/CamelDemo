package nz.co.edmi.servicemixdemo;

import org.apache.camel.builder.RouteBuilder;

public class FileMover extends RouteBuilder{

  @Override
  public void configure() throws Exception {
    from("file:///Users/simonvandersluis/camel/FileMover")
            .log("Moving ${file:name} to the output directory")
            .to("file:///Users/simonvandersluis/camel/dump");
  }
}
