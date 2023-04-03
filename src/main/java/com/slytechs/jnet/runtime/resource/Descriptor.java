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
package com.slytechs.jnet.runtime.resource;

import java.util.function.IntSupplier;

/**
 * A descriptor structure used to describe contents that follow typeically in a
 * memory buffer.
 * 
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 *
 */
public abstract class Descriptor extends MemoryBinding {

	public interface Type extends IntSupplier {
	}

	private final int type;

	public Descriptor(int type) {
		this.type = type;
	}

	public final int type() {
		return type;
	}

	public int byteSizeMinHint() {
		return byteSize();
	}

	public int byteSizeMaxHint() {
		return byteSize();
	}

	public int byteSize() {
		return buffer().limit();
	}

}
