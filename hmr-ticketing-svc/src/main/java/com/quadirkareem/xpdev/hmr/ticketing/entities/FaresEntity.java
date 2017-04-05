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

import java.util.HashMap;
import java.util.Map;

import com.quadirkareem.xpdev.hmr.ticketing.model.BaseFare;
import com.quadirkareem.xpdev.hmr.ticketing.model.Fares;
import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.LineRegistry;

public class FaresEntity implements Fares {

	private BaseFareEntity baseFare;
	private Map<Line, Float> lineFareMap = new HashMap<Line, Float>();

	public void setBaseFare(BaseFareEntity baseFare) {
		this.baseFare = baseFare;
	}

	@Override
	public BaseFare getBaseFare() {
		return baseFare;
	}

	public void setLineFareMap(Map<String, Float> lineCodeFareMap) {
		for (String lineCode : lineCodeFareMap.keySet()) {
			Float fare = lineCodeFareMap.get(lineCode);
			this.lineFareMap.put(LineRegistry.getLine(lineCode), fare);
		}
	}

	@Override
	public Map<Line, Float> getLineFareMap() {
		return lineFareMap;
	}

	@Override
	public float getLineFare(Line line) {
		return lineFareMap.get(line);
	}

	@Override
	public String toString() {
		return "Fares [baseFare=" + baseFare + ", lineFareMap=" + lineFareMap + "]";
	}

}
