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
package nz.co.edmi.servicemixdemo.json;

import java.util.ArrayList;
import java.util.List;

public class Foo {

  private String name;
  private List<Bar> bars = new ArrayList<>();

  public Foo(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Bar> getBars() {
    return bars;
  }

  public void addBar(Bar bar) {
    this.bars.add(bar);
  }


  @Override
  public String toString() {
    return "I'm a foo named " + name + " with " + bars.size() + " bars";
  }

}
