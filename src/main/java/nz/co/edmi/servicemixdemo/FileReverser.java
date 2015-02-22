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
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


public class FileReverser extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:///Users/simonvandersluis/camel/FileReverser")
            .log("Reversing file ${file:name}")
            .process(new ReversingProcessor())
            .log("Reversed file ${file:name}")
            .to("file:///Users/simonvandersluis/camel/dump/");
  }


  /**
   * It's not a good idea to implement Processor as it creates coupling to camels Exchange and Message classes,
   * making unit testing much more difficult.
   */
  private class ReversingProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
      Message inMessage = exchange.getIn();
      org.apache.camel.component.file.GenericFile inFile = (org.apache.camel.component.file.GenericFile) inMessage.getBody();
      InputStream is = new FileInputStream((File) inFile.getFile());
      java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
      String content = s.hasNext() ? s.next() : "";
      is.close();
      String backwards = new StringBuilder(content).reverse().toString() + "\r\n";

      // In only  will keep all of the header from the file that was picked up
      inMessage.setBody(backwards);

      // In out will loose the headers
//      exchange.getOut().setBody(backwards, String.class);
    }
  }
}
