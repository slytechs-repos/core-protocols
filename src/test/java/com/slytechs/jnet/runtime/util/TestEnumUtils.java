/*
 * Apache License, Version 2.0
 * 
 * Copyright 2013-2022 Sly Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.slytechs.jnet.runtime.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.slytechs.jnet.runtime.util.Enums;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author mark
 *
 */
class TestEnumUtils {

	@Test
	void replaceDollarSignAsDot() {
		String src = "HLEN$BYTES";
		String result = Enums.toDotName(src);
		String expected = "hlen_bytes";

		assertEquals(expected, result);

	}

	@Test
	void toLowerCaseWithUnderscores() {
		String src = "HLEN_BYTES";
		String result = Enums.toDotName(src);
		String expected = "hlen.bytes";

		assertEquals(expected, result);

	}

}