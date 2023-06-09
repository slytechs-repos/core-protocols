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

import com.slytechs.protocol.Address;
import com.slytechs.protocol.AddressType;

/**
 * The Class IpAddress.
 *
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public abstract class IpAddress implements Address {

	/** The type. */
	private final AddressType type;

	/**
	 * Instantiates a new ip address.
	 *
	 * @param type the type
	 */
	protected IpAddress(AddressType type) {
		super();
		this.type = type;
	}

	/**
	 * Type.
	 *
	 * @return the address type
	 * @see com.slytechs.protocol.Address#type()
	 */
	@Override
	public AddressType type() {
		return this.type;
	}

}
