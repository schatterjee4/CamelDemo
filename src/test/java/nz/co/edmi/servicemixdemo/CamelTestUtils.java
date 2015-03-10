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


public class CamelTestUtils {

  public static void waitForCamel() {
    waitForCamel(1000);
  }


  public static void waitForCamel(long timeMillis) {
    try {
       Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("Interrupted while waiting for camel");
    }
  }

}
