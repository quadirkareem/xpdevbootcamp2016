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
package com.quadirkareem.xpdev.hmr.ticketing.routing;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;

/**
 * This class provides methods to get route information.
 * 
 * @author quadir@gmail.com
 *
 */
public class RouteFinder {

	private static final Logger LOG = LoggerFactory.getLogger(RouteFinder.class);

	/**
	 * Returns all possible unique routes from Source to Destination.
	 * 
	 * @param src
	 *            Source Station
	 * @param dest
	 *            Destination Station
	 * @return List of {@link Route} objects
	 */
	public static List<Route> getRouteList(Station src, Station dest) {
		List<Route> routeList = new ArrayList<Route>();
		getRoute(src, dest, new RouteImpl(), routeList);
		return routeList;
	}

	/**
	 * Computes and returns a {@link Route} from Source to Destination.
	 * 
	 * @param src
	 *            Source Station
	 * @param dest
	 *            Destination Station
	 * @param currentRouteImpl
	 *            Current Route
	 * @param routeList
	 *            List of {@link Route} objects to populate
	 */
	private static void getRoute(Station src, Station dest, RouteImpl currentRouteImpl, List<Route> routeList) {
		LOG.debug("Getting route from {} to {}", src.getName(), dest.getName());
		currentRouteImpl.add(src);
		LOG.debug("Added {} to Current Route", src.getName());
		Line commonLine = getCommonLine(src, dest);
		if (commonLine == null) {
			for (Line srcLine : src.getLineList()) {
				List<Station> interchangeStations = srcLine.getInterchangeStationList();
				for (Station interchangeStation : interchangeStations) {
					if (!currentRouteImpl.hasStation(interchangeStation)
							&& areNotOnSameLine(currentRouteImpl.getLastButOneStation(), interchangeStation)) {
						currentRouteImpl.addLine(src, srcLine);
						LOG.debug("Added [{}:{}] to Current Route", src.getName(), srcLine.getName());
						getRoute(interchangeStation, dest, currentRouteImpl.clone(), routeList);
					}
				}
			}
		} else {
			currentRouteImpl.add(dest);
			currentRouteImpl.addLine(src, commonLine);
			routeList.add(currentRouteImpl);
			LOG.debug("Found Route to Destination: {}", currentRouteImpl);
		}
	}

	/**
	 * Determines & returns common {@link Line} between Source and Destination
	 * stations.
	 * 
	 * @param src
	 *            Source Station
	 * @param dest
	 *            Destination Station
	 * @return Common {@link Line} between Source and Destination. If NOT found,
	 *         returns null.
	 */
	private static Line getCommonLine(Station src, Station dest) {
		for (Line srcLine : src.getLineList()) {
			if (srcLine.hasStation(dest)) {
				LOG.debug("Common Line between {} & {}: {}", src.getName(), dest.getName(), srcLine.getName());
				return srcLine;
			}
		}

		return null;
	}

	/**
	 * Checks if last but one station & next possible station are NOT on same
	 * line.
	 * 
	 * @param src
	 *            Last but one Station
	 * @param dest
	 *            Next possible Station
	 * @return true if last but one station & next possible station are NOT on
	 *         same line. Else, false.
	 */
	private static boolean areNotOnSameLine(Station lastButOneStation, Station nextPossibleStation) {
		if (lastButOneStation == null || getCommonLine(lastButOneStation, nextPossibleStation) == null) {
			return true;
		} else {
			return false;
		}
	}

}
