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
package com.slytechs.protocol.descriptor;

import java.lang.foreign.Addressable;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.nio.ByteBuffer;

import com.slytechs.protocol.pack.core.constants.PacketDescriptorType;
import com.slytechs.protocol.runtime.time.Timestamp;
import com.slytechs.protocol.runtime.time.TimestampUnit;
import com.slytechs.protocol.runtime.util.Detail;

/**
 * The Class PcapDescriptor.
 */
public final class PcapDescriptor extends PacketDescriptor {

	/** The Constant ID. */
	public static final int ID = PacketDescriptorType.DESCRIPTOR_TYPE_PCAP;

	/** The Constant PCAP_DESCRIPTOR_LENGTH. */
	public final static int PCAP_DESCRIPTOR_LENGTH = 24;

	/**
	 * Of address.
	 *
	 * @param header the header
	 * @param scope  the scope
	 * @return the pcap descriptor
	 */
	public static PcapDescriptor ofAddress(Addressable header, MemorySession scope) {
		return ofMemorySegment(MemorySegment.ofAddress(header.address(), PCAP_DESCRIPTOR_LENGTH, scope));
	}

	/**
	 * Of memory segment.
	 *
	 * @param headerSegment the header segment
	 * @return the pcap descriptor
	 */
	public static PcapDescriptor ofMemorySegment(MemorySegment headerSegment) {
		PcapDescriptor descriptor = new PcapDescriptor();
		ByteBuffer buffer = headerSegment.asByteBuffer();
		descriptor.bind(buffer, headerSegment);

		return descriptor;
	}

	/** The Constant EMPTY_HEADER_ARRAY. */
	private static final long[] EMPTY_HEADER_ARRAY = new long[0];

	/**
	 * Instantiates a new pcap descriptor.
	 */
	public PcapDescriptor() {
		super(PacketDescriptorType.PCAP);
	}

	/**
	 * Capture length.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#captureLength()
	 */
	@Override
	public int captureLength() {
		return PcapDescriptorLayout.CAPLEN.getUnsignedShort(buffer());
	}

	/**
	 * List headers included headers.
	 *
	 * @return the list is always empty as Pcap header descriptor provides no
	 *         information about the included packet headers
	 */
	@Override
	public long[] listHeaders() {
		return EMPTY_HEADER_ARRAY;
	}

	/**
	 * Lookup header.
	 *
	 * @param headerId the header id
	 * @param depth    the depth
	 * @return the long
	 * @see com.slytechs.protocol.HeaderLookup#lookupHeader(int, int)
	 */
	@Override
	public long lookupHeader(int headerId, int depth) {
		return CompactDescriptor.ID_NOT_FOUND;
	}

	/**
	 * Timestamp.
	 *
	 * @return the long
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#timestamp()
	 */
	@Override
	public long timestamp() {
		return PcapDescriptorLayout.CAPLEN.getLong(buffer());
	}

	/**
	 * Builds the detailed string.
	 *
	 * @param b      the b
	 * @param detail the detail
	 * @return the string builder
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#buildDetailedString(java.lang.StringBuilder,
	 *      com.slytechs.protocol.runtime.util.Detail)
	 */
	@Override
	public StringBuilder buildDetailedString(StringBuilder b, Detail detail) {
		new Timestamp(TimestampUnit.EPOCH_MILLI.convert(timestamp(), TimestampUnit.PCAP_MICRO))
				.buildString(b, detail);
		b.append(" caplen=");
		b.append(captureLength());
		b.append(" wirelen=");
		b.append(wireLength());

		return b;
	}

	/**
	 * Wire length.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#wireLength()
	 */
	@Override
	public int wireLength() {
		return PcapDescriptorLayout.WIRELEN.getUnsignedShort(buffer());
	}

	/**
	 * Clone to.
	 *
	 * @param dst the dst
	 * @return the pcap descriptor
	 */
	@Override
	public PcapDescriptor cloneTo(ByteBuffer dst) {
		return (PcapDescriptor) super.cloneTo(dst);
	}

	/**
	 * Checks if is header extension supported.
	 *
	 * @return true, if is header extension supported
	 * @see com.slytechs.protocol.HeaderLookup#isHeaderExtensionSupported()
	 */
	@Override
	public boolean isHeaderExtensionSupported() {
		return false;
	}

	/**
	 * Byte size.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#byteSize()
	 */
	@Override
	public int byteSize() {
		return PCAP_DESCRIPTOR_LENGTH;
	}

	/**
	 * Lookup header extension.
	 *
	 * @param headerId        the header id
	 * @param extId           the ext id
	 * @param depth           the depth
	 * @param recordIndexHint the record index hint
	 * @return the long
	 * @see com.slytechs.protocol.HeaderLookup#lookupHeaderExtension(int, int, int,
	 *      int)
	 */
	@Override
	public long lookupHeaderExtension(int headerId, int extId, int depth, int recordIndexHint) {
		return CompactDescriptor.ID_NOT_FOUND;
	}
}
