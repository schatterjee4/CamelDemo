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

import org.apache.camel.Handler;
import org.apache.camel.builder.RouteBuilder;


public class WordReverser extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:///Users/simonvandersluis/camel/WordReverser")
            .log("Reversing words in file ${file:name}")
            .bean(new ReversingHandler())
            .log("Reversed words in file ${file:name}")
            .to("file:///Users/simonvandersluis/camel/dump/");
  }



  public class ReversingHandler {

    @Handler
    public String reverseWords(String words) throws Exception {
      StringBuilder output = new StringBuilder();
      for (String word : words.split(" ")) {
        output.append(reverseWord(word)).append(" ");
      }
      return output.toString();
    }
  }

  private String reverseWord(String word) {
    return new StringBuilder(word).reverse().toString();
  }
}
