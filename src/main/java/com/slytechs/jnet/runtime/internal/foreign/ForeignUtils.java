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
package com.slytechs.jnet.runtime.internal.foreign;

import java.lang.foreign.Addressable;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author mark
 *
 */
final class ForeignUtils {

	public static String toJavaString(Object value) {
		return toJavaString(((Addressable) value).address());
	}

	public static MemorySegment toUtf8String(String str, MemorySession scope) {
		return scope.allocateUtf8String(str);
	}

	public static String toJavaString(MemoryAddress addr) {
		if ((addr == null) || (addr == MemoryAddress.NULL))
			return null;

		return addr.getUtf8String(0);
	}

	private ForeignUtils() {
	}

}
