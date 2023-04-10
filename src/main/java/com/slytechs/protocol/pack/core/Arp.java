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

import java.util.concurrent.locks.Lock;

import com.slytechs.protocol.Header;
import com.slytechs.protocol.meta.Meta;
import com.slytechs.protocol.meta.MetaResource;
import com.slytechs.protocol.pack.core.constants.ArpOp;
import com.slytechs.protocol.pack.core.constants.CoreConstants;
import com.slytechs.protocol.pack.core.constants.CoreIdTable;

/**
 * Address Resolution Protocol (ARP).
 * 
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 */
@MetaResource("arp-meta.json")
public class Arp extends Header {

	/** Core protocol pack assigned header ID */
	public static final int ID = CoreIdTable.CORE_ID_ARP;

	/**
	 * Instantiates a new ARP header.
	 */
	public Arp() {
		super(ID);
	}

	/**
	 * Instantiates a new ARP header.
	 *
	 * @param lock new custom lock for synchronizing on this object, if desired
	 */
	public Arp(Lock lock) {
		super(ID, lock);
	}

	@Meta
	public int hardwareSize() {
		return Byte.toUnsignedInt(ArpLayout.HSIZE.getByte(buffer()));
	}

	@Meta
	public int hardwareType() {
		return Short.toUnsignedInt(ArpLayout.HTYPE.getShort(buffer()));
	}

	@Meta
	public int operation() {
		return Short.toUnsignedInt(ArpLayout.OPCODE.getShort(buffer()));
	}

	public ArpOp operationGetAsEnum() {
		return ArpOp.valueOfArpOp(operation());
	}

	@Meta
	public int protocolSize() {
		return Byte.toUnsignedInt(ArpLayout.PSIZE.getByte(buffer()));
	}

	@Meta
	public int protocolType() {
		return Short.toUnsignedInt(ArpLayout.PTYPE.getShort(buffer()));
	}

	@Meta
	public byte[] senderHa() {
		return senderHa(new byte[CoreConstants.ARP_LEN_HALEN], 0);
	}

	public byte[] senderHa(byte[] dst) {
		return senderHa(dst, 0);
	}

	public byte[] senderHa(byte[] dst, int offset) {
		buffer().get(CoreConstants.ARP_FIELD_SHA, dst, offset, CoreConstants.ARP_LEN_HALEN);
		return dst;
	}

	@Meta
	public byte[] senderPa() {
		return senderPa(new byte[CoreConstants.ARP_LEN_PALEN], 0);
	}

	public byte[] senderPa(byte[] dst) {
		return senderPa(dst, 0);
	}

	public byte[] senderPa(byte[] dst, int offset) {
		buffer().get(CoreConstants.ARP_FIELD_SPA, dst, offset, CoreConstants.ARP_LEN_PALEN);
		return dst;
	}

	@Meta
	public byte[] targetHa() {
		return targetHa(new byte[CoreConstants.ARP_LEN_HALEN], 0);
	}

	public byte[] targetHa(byte[] dst) {
		return senderHa(dst, 0);
	}

	public byte[] targetHa(byte[] dst, int offset) {
		buffer().get(CoreConstants.ARP_FIELD_THA, dst, offset, CoreConstants.ARP_LEN_HALEN);
		return dst;
	}

	@Meta
	public byte[] targetPa() {
		return targetPa(new byte[CoreConstants.ARP_LEN_PALEN], 0);
	}

	public byte[] targetPa(byte[] dst) {
		return targetPa(dst, 0);
	}

	public byte[] targetPa(byte[] dst, int offset) {
		buffer().get(CoreConstants.ARP_FIELD_TPA, dst, offset, CoreConstants.ARP_LEN_PALEN);
		return dst;
	}

}