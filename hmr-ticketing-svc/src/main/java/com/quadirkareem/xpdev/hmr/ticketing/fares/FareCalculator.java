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
package com.quadirkareem.xpdev.hmr.ticketing.fares;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quadirkareem.xpdev.hmr.ticketing.model.Fares;
import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;
import com.quadirkareem.xpdev.hmr.ticketing.routing.Route;


/**
 * This class computes the minimum Fare given a list of Routes.
 *  
 * @author quadir@gmail.com
 *
 */
public class FareCalculator {
	private static final Logger LOG = LoggerFactory.getLogger(FareCalculator.class);
	private static Fares fares;

	private List<Route> routeList;
	private FareResult fareResult;

	public static void setFares(Fares fares) {
		LOG.debug("{}", fares);
		FareCalculator.fares = fares;
	}

	public FareCalculator(List<Route> routeList) {
		this.routeList = routeList;
		computeFare();
	}

	public FareResult getFareResult() {
		return fareResult;
	}

	private void computeFare() {
		for (Route route : routeList) {
			FareResult fr = getFareResult(route);
			computeMinimumFareResult(fr);
		}
	}

	private void computeMinimumFareResult(FareResult fr) {
		if ((fareResult == null) || (fr.getTotalFare() < fareResult.getTotalFare())) {
			fareResult = fr;
		}
	}

	private FareResult getFareResult(Route route) {
		FareResult fareResult = new FareResult();
		List<Station> stationList = route.getStationList();
		for (int i = 0; i < stationList.size() - 1; i++) {
			Station currentStation = stationList.get(i);
			Station nextStation = stationList.get(i + 1);
			Line line = route.getLine(currentStation);
			int stationCount = Math.abs(currentStation.getOrder(line) - nextStation.getOrder(line));
			LOG.debug("Stations Between {} & {}: {}", currentStation.getName(), nextStation.getName(), stationCount);
			add(fareResult, line, stationCount);
		}
		LOG.debug("{}", fareResult);
		return fareResult;
	}

	private void add(FareResult fareResult, Line line, int stationCount) {
		int totalStations = fareResult.getTotalStations();
		if (stationCount > 0 && totalStations == 0) {
			fareResult.updateFare(fares.getBaseFare().getMinFare());
		}
		int maxStations = fares.getBaseFare().getMaxStations();
		float lineFare = fares.getLineFare(line);
		if (totalStations >= maxStations) {
			fareResult.updateFare(lineFare * stationCount);
		} else {
			int minFareStations = maxStations - totalStations;
			if (stationCount > minFareStations) {
				fareResult.updateFare((stationCount - minFareStations) * lineFare);
			}
		}
		fareResult.incrementStations(stationCount);
	}

}
