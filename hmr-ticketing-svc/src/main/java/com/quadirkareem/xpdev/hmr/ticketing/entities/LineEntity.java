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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.Station;

public class LineEntity implements Line {

	private String code;
	private String name;
	private String length;
	private List<Station> interchangeStationList = new ArrayList<Station>();
	private Map<String, Station> stationMap = new LinkedHashMap<String, Station>();

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Override
	public String getLength() {
		return length;
	}

	public void setStationList(List<StationEntity> stationEntityList) {
		int currentOrder = 0;
		for (StationEntity stationEntity : stationEntityList) {
			currentOrder++;
			Station station = StationImpl.getStation(stationEntity, this, currentOrder);
			this.stationMap.put(station.getCode(), station);
		}
	}

	@Override
	public boolean hasStation(Station station) {
		return stationMap.containsKey(station.getCode());
	}

	@Override
	public List<Station> getInterchangeStationList() {
		return interchangeStationList;
	}

	void updateState() {
		setInterchangeStationList();
	}

	private void setInterchangeStationList() {
		for (Station station : stationMap.values()) {
			if (station.isInterchange()) {
				interchangeStationList.add(station);
			}
		}
	}

	@Override
	public String toString() {
		return "Line [" + code + ": " + name + "]";
	}

}
