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

import com.slytechs.protocol.runtime.util.HexStrings;

/**
 * The Class MacAddress.
 *
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public class MacAddress {

	/**
	 * Instantiates a new mac address.
	 *
	 * @param destination the destination
	 */
	public MacAddress(byte[] destination) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * To oui string.
	 *
	 * @param destination the destination
	 * @return the string
	 */
	public static String toOuiString(byte[] destination) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	/**
	 * To string.
	 *
	 * @param address the address
	 * @return the string
	 */
	public static String toString(byte[] address) {
		return HexStrings.toMacString(address);
	}

}
