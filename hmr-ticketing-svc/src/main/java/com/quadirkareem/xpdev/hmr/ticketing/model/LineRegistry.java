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
package com.quadirkareem.xpdev.hmr.ticketing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineRegistry {
	private static final Logger LOG = LoggerFactory.getLogger(LineRegistry.class);

	private static Map<String, Line> lineMap = new HashMap<String, Line>();

	public static List<Line> getLineList() {
		return new ArrayList<Line>(lineMap.values());
	}

	public static Line getLine(String lineCode) {
		return lineMap.get(lineCode);
	}

	public static void addLine(Line line) {
		if (line != null) {
			lineMap.put(line.getCode(), line);
			LOG.debug("Registered Line: {}", line);
		} else {
			LOG.error("Cannot register NULL line");
		}
	}

}
