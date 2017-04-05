/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quadirkareem.xpdev.hmr.ticketing;

import java.util.Arrays;

import com.quadirkareem.xpdev.hmr.ticketing.fares.FareResult;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;

/**
 * This class provides methods to print Ticket information.
 * 
 * @author quadir@gmail.com
 *
 */
public class TicketPrinter {

	public static void print(Station src, Station dest, FareResult fareResult) {
		System.out.printf("%n%s%n", BORDER);
		System.out.printf("%47s%n", "Hyderabad Metro Rail");
		printFare(src, dest, fareResult);
		System.out.printf("%s%n%n", BORDER);
	}

	private static void printFare(Station src, Station dest, FareResult fareResult) {
		printLine(new Object[] { "Source:", src.getName(), "Distance (stations):", fareResult.getTotalStations() });
		String fareString = String.format("%c %.2f/-", '\u20B9', fareResult.getTotalFare());
		printLine(new Object[] { "Destination:", dest.getName(), "Cost:", fareString });
	}

	private static void printLine(Object[] values) {
		System.out.printf("%-15s %-30s %s %s%n", values);
	}

	private static final String BORDER;

	static {
		char[] b = new char[80];
		Arrays.fill(b, '*');
		BORDER = new String(b);
	}

}
