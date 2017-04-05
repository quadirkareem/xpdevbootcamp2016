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

import java.io.Console;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quadirkareem.xpdev.hmr.ticketing.entities.FaresFactory;
import com.quadirkareem.xpdev.hmr.ticketing.entities.LineFactory;
import com.quadirkareem.xpdev.hmr.ticketing.fares.FareCalculator;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;
import com.quadirkareem.xpdev.hmr.ticketing.model.StationRegistry;
import com.quadirkareem.xpdev.hmr.ticketing.routing.Route;
import com.quadirkareem.xpdev.hmr.ticketing.routing.RouteFinder;

/**
 *
 * <p>
 * This is the Main class which prompts the user for input, gets fare details
 * and passes it to TicketPrinter to print the ticket.
 * </p>
 * 
 * @author quadir@gmail.com
 */
public class TicketingService {

	private static final Logger LOG = LoggerFactory.getLogger(TicketingService.class);
	private static Station src;
	private static Station dest;

	public static void main(String[] args) {

		loadData();

		getUserInput();

		List<Route> routeList = RouteFinder.getRouteList(src, dest);
		FareCalculator fareCalculator = new FareCalculator(routeList);
		TicketPrinter.print(src, dest, fareCalculator.getFareResult());
	}

	/**
	 * 
	 */
	private static void getUserInput() {
		Console cons = System.console();
		if (cons != null) {
			src = getStation(cons, "\nEnter Source Station Code");
			dest = getStation(cons, "Enter Destination Station Code");
		} else {
			src = StationRegistry.getStation("C11");
			dest = StationRegistry.getStation("A17");
		}
	}

	private static Station getStation(Console console, String prompt) {
		while (true) {
			String stationCode = console.readLine("%s: ", prompt);
			Station station = StationRegistry.getStation(stationCode);
			if (station == null) {
				System.err.println("Error: Invalid Station Code - " + stationCode + "\n");
			} else {
				return station;
			}
		}
	}

	private static void loadData() {
		try {
			LineFactory.loadLines();
			FaresFactory.loadFares();
		} catch (IOException | URISyntaxException e) {
			LOG.error("Exception while loading Station & Fare data", e);
			System.err.println("Could NOT load Station & Fare data. Please check log files.");
			System.err.println("Exiting");
			System.exit(1);
		}
	}

}
