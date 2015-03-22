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
package nz.co.edmi.servicemixdemo.converters;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.camel.Converter;

@Converter
public final class URLConverter {

  @Converter
  public static URL toUrl(String url) throws MalformedURLException {
    return new URL(url);
  }

  @Converter
  public static InputStream toIs(URL url) throws Exception {
    return url.openStream();
  }

}

