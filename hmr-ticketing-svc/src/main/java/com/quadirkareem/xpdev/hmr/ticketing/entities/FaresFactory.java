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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quadirkareem.xpdev.hmr.ticketing.fares.FareCalculator;

public class FaresFactory {

	private static final Logger LOG = LoggerFactory.getLogger(FaresFactory.class);
	private static final String FARES_JSON = "/fares.json";

	public static void loadFares() throws URISyntaxException, IOException {
		Path filePath = Paths.get(FaresFactory.class.getResource(FARES_JSON).toURI());
		FaresEntity fares = JsonUtil.deserialize(filePath, FaresEntity.class);
		if (fares != null) {
			FareCalculator.setFares(fares);
		} else {
			LOG.error("Deserialized fares is NULL for {}", filePath);
		}
	}

}
