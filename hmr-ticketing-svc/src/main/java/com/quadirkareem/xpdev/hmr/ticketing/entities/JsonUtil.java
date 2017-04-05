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
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

	private static final String JSON_EXT = ".json";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T deserialize(Path filePath, Class<T> clazz) throws IOException {
		if (isJsonFile(filePath)) {
			LOG.debug("Deserializing {}", filePath);
			return objectMapper.readValue(Files.readAllBytes(filePath), clazz);
		} else {
			return null;
		}
	}

	private static boolean isJsonFile(Path filePath) {
		return (Files.isRegularFile(filePath) && filePath.toString().toLowerCase().endsWith(JSON_EXT));
	}

}
