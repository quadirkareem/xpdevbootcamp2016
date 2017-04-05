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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quadirkareem.xpdev.hmr.ticketing.model.Line;
import com.quadirkareem.xpdev.hmr.ticketing.model.LineRegistry;

public class LineFactory {

	private static final Logger LOG = LoggerFactory.getLogger(LineFactory.class);
	private static final String LINES_BASEDIR = "/lines";

	public static void loadLines() throws URISyntaxException, IOException {
		Files.walk(getPath()).forEach(filePath -> {
			try {
				LineEntity line = JsonUtil.deserialize(filePath, LineEntity.class);
				if (line != null) {
					LineRegistry.addLine(line);
				} else {
					LOG.warn("Deserialized line is NULL for {}", filePath);
				}
			} catch (IOException e) {
				LOG.error("Error deserializing {}", filePath, e);
				throw new RuntimeException(e);
			}
		});
		updateLines();
	}

	private static void updateLines() {
		for (Line line : LineRegistry.getLineList()) {
			((LineEntity) line).updateState();
		}
	}

	public static Path getPath() throws URISyntaxException, IOException {
		URI uri = JsonUtil.class.getResource(LINES_BASEDIR).toURI();
		Path path;
		if (uri.getScheme().equals("jar")) {
			FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object> emptyMap());
			path = fileSystem.getPath(LINES_BASEDIR);
		} else {
			path = Paths.get(uri);
		}
		return path;
	}
}
