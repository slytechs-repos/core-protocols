/*
 * Sly Technologies Free License
 * 
 * Copyright 2023 Sly Technologies Inc.
 *
 * Licensed under the Sly Technologies Free License (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.slytechs.com/free-license-text
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.slytechs.protocol.pack.core;

import java.util.Optional;

import com.slytechs.protocol.HeaderInfo;
import com.slytechs.protocol.HeaderNotFound;
import com.slytechs.protocol.pack.Pack;
import com.slytechs.protocol.pack.PackId;
import com.slytechs.protocol.pack.ProtocolPackTable;
import com.slytechs.protocol.pack.core.constants.CoreIdTable;

/**
 * Core protocol pack definition.
 *
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public final class CoreProtocolPack extends Pack<CoreIdTable> {

	/**
	 * @see com.slytechs.protocol.pack.Pack#isCore()
	 */
	@Override
	public boolean isCore() {
		return true;
	}

	/** Core Protocol Pack singleton definition. */
	private static final CoreProtocolPack SINGLETON = new CoreProtocolPack();

	/**
	 * Core pack is always loaded and reference kept as a singleton.
	 *
	 * @return the core protocol pack
	 */
	public static CoreProtocolPack singleton() {
		return SINGLETON;
	}

	/**
	 * Pack definitions are designed to be singltons.
	 */
	private CoreProtocolPack() {
		super(ProtocolPackTable.CORE, CoreIdTable.values());
	}

	/**
	 * Gets the header.
	 *
	 * @param id the id
	 * @return the header
	 * @throws HeaderNotFound the header not found
	 * @see com.slytechs.protocol.pack.Pack#findHeader(int)
	 */
	@Override
	public Optional<HeaderInfo> findHeader(int id) {
		int packId = PackId.decodePackId(id);
		int hdrOrdinal = PackId.decodeIdOrdinal(id);
		if (packId != ProtocolPackTable.PACK_ID_CORE)
			return Optional.empty();

		var headers = CoreIdTable.values();

		if (hdrOrdinal > headers.length)
			return Optional.empty();

		return Optional.of(CoreIdTable.values()[hdrOrdinal]);
	}

}
