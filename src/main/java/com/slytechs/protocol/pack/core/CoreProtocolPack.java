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

import com.slytechs.protocol.HeaderId;
import com.slytechs.protocol.HeaderInfo;
import com.slytechs.protocol.HeaderNotFound;
import com.slytechs.protocol.ProtocolPack;
import com.slytechs.protocol.pack.core.constants.CoreHeaders;
import com.slytechs.protocol.pack.core.constants.PackInfo;

/**
 * The Class CorePack.
 *
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public final class CoreProtocolPack extends ProtocolPack<CoreHeaders> {

	/** Core Protocol Pack singleton definition. */
	private static final CoreProtocolPack SINGLETON = new CoreProtocolPack();

	/**
	 * Singleton.
	 *
	 * @return the core pack
	 */
	public static CoreProtocolPack singleton() {
		return SINGLETON;
	}

	/**
	 * Pack definitions are designed to be singltons.
	 */
	private CoreProtocolPack() {
		super(PackInfo.CORE, CoreHeaders.values());
	}

	/**
	 * Gets the header.
	 *
	 * @param id the id
	 * @return the header
	 * @throws HeaderNotFound the header not found
	 * @see com.slytechs.protocol.ProtocolPack#getHeader(int)
	 */
	@Override
	public HeaderInfo getHeader(int id) throws HeaderNotFound {
		int packId = HeaderId.decodePackId(id);
		int hdrOrdinal = HeaderId.decodeIdOrdinal(id);
		if (packId != PackInfo.PACK_ID_CORE)
			throw new HeaderNotFound("invalid pack id [%d] not applicable to [%s] pack"
					.formatted(packId, super.getPackName()));

		var headers = CoreHeaders.values();

		if (hdrOrdinal > headers.length)
			throw new HeaderNotFound("header id [%d] in [%s] pack"
					.formatted(id, super.getPackName()));

		return CoreHeaders.values()[hdrOrdinal];
	}

}
