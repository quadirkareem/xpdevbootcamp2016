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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;

class RouteImpl implements Route {
	private Set<Station> stationSet;
	private Map<Station, Line> stationLineMap;
	private Station lastButOneStation;
	private Station lastStation;

	RouteImpl() {
		super();
		this.stationSet = new LinkedHashSet<Station>();
		this.stationLineMap = new HashMap<Station, Line>();
	}

	@Override
	public String toString() {
		return "Route [stations=" + stationSet + "]";
	}

	@Override
	public Line getLine(Station station) {
		return stationLineMap.get(station);
	}

	@Override
	public List<Station> getStationList() {
		return new ArrayList<Station>(stationSet);
	}

	void add(Station station) {
		lastButOneStation = lastStation;
		lastStation = station;
		stationSet.add(station);
	}

	protected RouteImpl clone() {
		return new RouteImpl(this);
	}

	boolean hasStation(Station station) {
		return stationSet.contains(station);
	}

	void addLine(Station station, Line line) {
		stationLineMap.put(station, line);
	}

	Station getLastButOneStation() {
		return lastButOneStation;
	}

	private RouteImpl(RouteImpl routeImpl) {
		super();
		this.stationSet = new LinkedHashSet<Station>(routeImpl.stationSet);
		this.stationLineMap = new HashMap<Station, Line>(routeImpl.stationLineMap);
		this.lastStation = routeImpl.lastStation;
		this.lastButOneStation = routeImpl.lastButOneStation;
	}

}