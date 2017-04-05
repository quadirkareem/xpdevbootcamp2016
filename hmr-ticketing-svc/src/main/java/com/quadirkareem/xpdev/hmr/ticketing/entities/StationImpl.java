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
package com.quadirkareem.xpdev.hmr.ticketing.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;
import com.quadirkareem.xpdev.hmr.ticketing.model.StationRegistry;

class StationImpl extends StationEntity implements Station {

	private Map<Line, Integer> lines = new HashMap<Line, Integer>();

	public static Station getStation(StationEntity stationEntity, Line line, int order) {
		Station station = StationRegistry.getStation(stationEntity.getCode());
		if (station == null) {
			station = new StationImpl(stationEntity);
			StationRegistry.addStation(station);
		}
		((StationImpl) station).addLine(line, order);

		return station;
	}

	@Override
	public List<Line> getLineList() {
		return new ArrayList<Line>(lines.keySet());
	}

	@Override
	public int getOrder(Line line) {
		return lines.get(line);
	}

	@Override
	public boolean isInterchange() {
		return (lines.size() > 1);
	}

	private StationImpl(StationEntity stationEntity) {
		super(stationEntity);
	}

	private void addLine(Line line, int order) {
		lines.put(line, order);
	}

}
