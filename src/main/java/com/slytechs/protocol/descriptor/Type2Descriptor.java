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

import static com.slytechs.protocol.descriptor.Type2DescriptorLayout.*;

import java.nio.ByteBuffer;

import com.slytechs.protocol.pack.Pack;
import com.slytechs.protocol.pack.PackId;
import com.slytechs.protocol.pack.ProtocolPackTable;
import com.slytechs.protocol.pack.core.constants.CoreConstants;
import com.slytechs.protocol.pack.core.constants.CoreIdTable;
import com.slytechs.protocol.pack.core.constants.HashType;
import com.slytechs.protocol.pack.core.constants.L2FrameType;
import com.slytechs.protocol.pack.core.constants.PacketDescriptorType;
import com.slytechs.protocol.runtime.util.Bits;
import com.slytechs.protocol.runtime.util.Detail;

/**
 * The Class Type2Descriptor.
 *
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public class Type2Descriptor extends PacketDescriptor {

	/** The mask. */
	/* Lazy cache some common values, cleared on unbind() */
	private int mask;

	/** The hash type. */
	private int hashType;

	/** The hash 32. */
	private int hash24, hash32;

	/** The expanded header arrays. */
	private long[] expandedHeaderArrays;

	/**
	 * Instantiates a new type 2 descriptor.
	 */
	public Type2Descriptor() {
		super(PacketDescriptorType.TYPE1);
	}

	/**
	 * Bitmask.
	 *
	 * @return the int
	 */
	public int bitmask() {
		if (mask == -1)
			mask = BITMASK.getInt(buffer());

		return mask;
	}

	/**
	 * Byte size.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#byteSize()
	 */
	@Override
	public int byteSize() {
		return (recordCount() << 2) + CoreConstants.DESC_TYPE2_BYTE_SIZE_MIN;
	}

	/**
	 * Capture length.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#captureLength()
	 */
	@Override
	public int captureLength() {
		return CAPLEN.getInt(buffer());
	}

	/**
	 * Get user color.
	 *
	 * @return an opaque value assigned by the user
	 */
	public int color() {
		return COLOR.getInt(buffer());
	}

	/**
	 * Sets new color value.
	 *
	 * @param newColor new color value
	 * @return this descriptor
	 */
	public Type2Descriptor color(int newColor) {
		COLOR.setInt(newColor, buffer());

		return this;
	}

	/**
	 * Gets the record.
	 *
	 * @param index the index
	 * @return the record
	 */
	public int getRecord(int index) {
		int count = recordCount();
		if (index >= count)
			throw new IndexOutOfBoundsException();

		return buffer().getInt((index << 2) + CoreConstants.DESC_TYPE2_BYTE_SIZE_MAX);
	}

	/**
	 * Hash.
	 *
	 * @param hash     the hash
	 * @param hashType the hash type
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor hash(int hash, HashType hashType) {
		return hash24(hash, hashType.getAsInt());
	}

	/**
	 * Hash 24.
	 *
	 * @return the int
	 */
	public int hash24() {
		if (hash24 == -1)
			hash24 = HASH24.getInt(buffer());

		return hash24;
	}

	/**
	 * Hash 24.
	 *
	 * @param hash24   the hash 24
	 * @param hashType the hash type
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor hash24(int hash24, int hashType) {
		HASH24.setInt(hash24, buffer());
		HASH_TYPE.setInt(hashType, buffer());

		this.hash24 = hash24;
		this.hash32 = (hashType << 27) | hash24;
		this.hashType = hashType;

		return this;
	}

	/**
	 * Hash 32.
	 *
	 * @return the int
	 */
	public int hash32() {
		if (hash32 == -1)
			hash32 = HASH32.getInt(buffer());

		return hash32;
	}

	/**
	 * Hash 32.
	 *
	 * @param hash the hash
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor hash32(int hash) {
		HASH32.setInt(hash, buffer());

		this.hash32 = hash;
		this.hash24 = hash & Bits.BITS_24;
		this.hashType = (hash >> 27);

		return this;
	}

	/**
	 * Hash type.
	 *
	 * @return the int
	 */
	public int hashType() {
		if (hashType == -1)
			hashType = HASH_TYPE.getInt(buffer());

		return hashType;
	}

	/**
	 * A flag which indicates if this is packet is part of layer3 fragmented
	 * datagram.
	 *
	 * @return true, if it is a fragment
	 */
	public boolean isL3Fragment() {
		return Type2DescriptorLayout.L3_IS_FRAG.getBit(buffer());
	}

	/**
	 * A flag which indicates if this is the last fragment of a fragmented datagram.
	 *
	 * @return true, if it is the last fragment
	 */
	public boolean isL3LastFragment() {
		return Type2DescriptorLayout.L3_LAST_FRAG.getBit(buffer());
	}

	/**
	 * Checks if is header extension supported.
	 *
	 * @return true, if is header extension supported
	 * @see com.slytechs.protocol.HeaderLookup#isHeaderExtensionSupported()
	 */
	@Override
	public boolean isHeaderExtensionSupported() {
		return true;
	}

	/**
	 * L 2 frame type.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#l2FrameType()
	 */
	@Override
	public int l2FrameType() {
		return L2_TYPE.getInt(buffer());
	}

	/**
	 * List headers.
	 *
	 * @return the long[]
	 * @see com.slytechs.protocol.HeaderLookup#listHeaders()
	 */
	@Override
	public long[] listHeaders() {
		int recordCount = recordCount();
		expandedHeaderArrays = new long[recordCount];

		for (int i = 0; i < recordCount; i++)
			expandedHeaderArrays[i] = PackId.recordToCompactDescriptor(record(i));

		return expandedHeaderArrays;
	}

	/**
	 * Lookup extension.
	 *
	 * @param extId       the ext id
	 * @param start       the start
	 * @param recordCount the record count
	 * @return the long
	 */
	private long lookupExtension(int extId, int start, int recordCount) {

		for (int i = start; i < recordCount; i++) {
			final int record = record(i);
			final int id = PackId.decodeRecordId(record);
			final int pack = PackId.decodeRecordPackId(record);

			/* Scan until we no longer see OPTIONS records */
			if (pack != ProtocolPackTable.PACK_ID_OPTIONS)
				break;

			if (id == extId)
				return PackId.recordToCompactDescriptor(record, extId, i);
		}

		return CompactDescriptor.ID_NOT_FOUND;
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
		if (headerId == CoreIdTable.CORE_ID_PAYLOAD)
			return lookupPayloadEntirePacket();

		final int mask = bitmask();
		if (!PackId.bitmaskCheck(mask, headerId))
			return CompactDescriptor.ID_NOT_FOUND;

		final int recordCount = recordCount();
		for (int i = 0; i < recordCount; i++) {
			final int record = record(i);

			if (PackId.recordEqualsId(record, headerId) && (depth-- == 0))
				return PackId.recordToCompactDescriptor(record, headerId, i); // Record with a hint (i)!
		}

		return CompactDescriptor.ID_NOT_FOUND;
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

		/* If we have a hint, then we can skip directly to extension lookup */
		if (recordIndexHint > 0)
			return lookupExtension(extId, recordIndexHint + 1, recordCount());

		final int mask = bitmask();
		if (!PackId.bitmaskCheck(mask, headerId))
			return CompactDescriptor.ID_NOT_FOUND;

		final int recordCount = recordCount();
		for (int i = 0; i < recordCount; i++) {
			final int record = record(i);

			if (PackId.recordEqualsId(record, headerId) && (depth-- == 0)) {
				if (extId == CoreIdTable.CORE_ID_PAYLOAD)
					return lookupPayload(record);

				return lookupExtension(extId, i + 1, recordCount);
			}
		}

		return CompactDescriptor.ID_NOT_FOUND;
	}

	/**
	 * Lookup payload.
	 *
	 * @param record the record
	 * @return the long
	 */
	private long lookupPayload(int record) {
		int off = PackId.decodeRecordOffset(record);
		int len = PackId.decodeRecordSize(record);
		int poff = off + len;

		return CompactDescriptor.encode(CoreIdTable.CORE_ID_PAYLOAD, poff, captureLength() - poff);
	}

	/**
	 * Lookup payload entire packet.
	 *
	 * @return the long
	 */
	private long lookupPayloadEntirePacket() {
		return CompactDescriptor.encode(CoreIdTable.CORE_ID_PAYLOAD, 0, captureLength());
	}

	/**
	 * On bind.
	 *
	 * @see com.slytechs.protocol.runtime.MemoryBinding#onBind()
	 */
	@Override
	protected void onBind() {
		mask = hashType = hash24 = hash32 = -1;
		expandedHeaderArrays = null;
	}

	/**
	 * Record.
	 *
	 * @param index the index
	 * @return the int
	 */
	public int record(int index) {
//		return RECORD.getInt(buffer(), index);

		// TODO: Faster implementation
		return buffer().getInt(CoreConstants.DESC_TYPE2_BYTE_SIZE_MIN + (index * 4));
	}

	/**
	 * Record count.
	 *
	 * @return the int
	 */
	public int recordCount() {
//		return RECORD_COUNT.getInt(buffer());

		// TODO: Faster implementation
		return shiftr(12, 27, 0x1F);
	}

	/**
	 * Rx port.
	 *
	 * @return the int
	 */
	public int rxPort() {
		return RX_PORT.getInt(buffer());
	}

	/**
	 * Rx port.
	 *
	 * @param rxPort the rx port
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor rxPort(int rxPort) {
		RX_PORT.setInt(rxPort, buffer());

		return this;
	}

	/**
	 * Shiftr.
	 *
	 * @param byteOffset the byte offset
	 * @param bits       the bits
	 * @param mask       the mask
	 * @return the int
	 */
	private int shiftr(int byteOffset, int bits, int mask) {
		return (buffer().getInt(byteOffset) >> bits) & mask;
	}

	/**
	 * Timestamp.
	 *
	 * @return the long
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#timestamp()
	 */
	@Override
	public long timestamp() {
		return TIMESTAMP.getLong(buffer());
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
		if (detail.isLow()) {
			b.append("")
					.append("len=").append(captureLength())
					.append(", rxPort").append(rxPort())
					.append(", hash16=0x").append(Integer.toHexString(hash32() & Bits.BITS_16))
					.append(", l2=%d (%s)".formatted(l2FrameType(),
							L2FrameType.valueOf(l2FrameType())))

					.append(", rc=").append(recordCount())
					.append(", ts=\"%tT\"".formatted(timestamp()));

		} else {
			b.append("")
					.append("  timestamp=\"%tc\"%n".formatted(timestamp()))
					.append("  captureLength=%d bytes%n".formatted(captureLength()))
					.append("  wireLength=%d bytes%n".formatted(wireLength()))
					.append("  rxPort=%d%n".formatted(rxPort()));

			if (detail.isHigh())
				b.append("")
						.append("  txPort=%d%n".formatted(txPort()))
						.append("  txnow=%d%n".formatted(txNow()))
						.append("  txIgnore=%d%n".formatted(txIgnore()))
						.append("  txCrcOverride=%d%n".formatted(txCrcOverride()))
						.append("  txSetClock=%d%n".formatted(txSetClock()))
						.append("  isL3Fragment=%s%n".formatted(isL3Fragment()))
						.append("  isL3LastFragment=%s%n".formatted(isL3LastFragment()))

				;

			b.append("")
					.append("  l2FrameType=%d (%s)%n".formatted(l2FrameType(),
							L2FrameType.valueOf(l2FrameType())))

					.append("  color=%d%n".formatted(color()))
					.append("  hash=0x%08X (type=%d [%s], 24-bits=0x%06X)%n"
							.formatted(
									hash32(),
									hashType(),
									HashType.valueOf(hashType()),
									hash24()))

					.append("  recordCount=%d%n".formatted(recordCount()));

			if (detail.isHigh())
				b.append("  bitmask=0x%08X (0b%s)%n".formatted(
						bitmask(),
						Integer.toBinaryString(bitmask())));
		}

		if (detail.isHigh()) {
			int recordCount = recordCount();
			int lastId = -1;

			for (int i = 0; i < recordCount; i++) {

				int record = record(i);
				int pack = PackId.decodeRecordPackId(record);
				int id = PackId.decodeRecordId(record);
				int offset = PackId.decodeRecordOffset(record);
				int length = PackId.decodeRecordSize(record);

				if (pack != ProtocolPackTable.PACK_ID_OPTIONS) {
					lastId = id;
					b.append("    [%d]=0x%08X (id=0x%03X [%-20s], off=%2d, len=%2d)%n"
							.formatted(
									i,
									record,
									id,
									Pack.toString(id),
									offset,
									length));

				} else {
					// Protocol specific extensions/options
					b.append("    [%d]=0x%08X (id=0x%03X [%-20s], off=%2d, len=%2d)%n"
							.formatted(
									i,
									record,
									id,
									Pack.toString(lastId, id),
									offset,
									length));

				}
			}
		}

		return b;
	}

	/**
	 * Tx crc override.
	 *
	 * @return the int
	 */
	public int txCrcOverride() {
		return TX_CRC_OVERRIDE.getInt(buffer());
	}

	/**
	 * Tx crc override.
	 *
	 * @param txCrcOverride the tx crc override
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor txCrcOverride(int txCrcOverride) {
		TX_CRC_OVERRIDE.setInt(txCrcOverride, buffer());

		return this;
	}

	/**
	 * Tx ignore.
	 *
	 * @return the int
	 */
	public int txIgnore() {
		return TX_IGNORE.getInt(buffer());
	}

	/**
	 * Tx ignore.
	 *
	 * @param txIgnore the tx ignore
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor txIgnore(int txIgnore) {
		TX_IGNORE.setInt(txIgnore, buffer());

		return this;
	}

	/**
	 * Tx now.
	 *
	 * @return the int
	 */
	public int txNow() {
		return TX_NOW.getInt(buffer());
	}

	/**
	 * Tx now.
	 *
	 * @param txNow the tx now
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor txNow(int txNow) {
		TX_NOW.setInt(txNow, buffer());

		return this;
	}

	/**
	 * Tx port.
	 *
	 * @return the int
	 */
	public int txPort() {
		return TX_PORT.getInt(buffer());
	}

	/**
	 * Tx port.
	 *
	 * @param txPort the tx port
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor txPort(int txPort) {
		TX_PORT.setInt(txPort, buffer());

		return this;
	}

	/**
	 * Tx set clock.
	 *
	 * @return the int
	 */
	public int txSetClock() {
		return TX_SET_CLOCK.getInt(buffer());
	}

	/**
	 * Tx set clock.
	 *
	 * @param txSetClock the tx set clock
	 * @return the type 2 descriptor
	 */
	public Type2Descriptor txSetClock(int txSetClock) {
		TX_SET_CLOCK.setInt(txSetClock, buffer());

		return this;
	}

	/**
	 * Wire length.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.descriptor.PacketDescriptor#wireLength()
	 */
	@Override
	public int wireLength() {
		return WIRELEN.getInt(buffer());
	}

	/**
	 * With binding.
	 *
	 * @param buffer the buffer
	 * @return the type 2 descriptor
	 * @see com.slytechs.protocol.runtime.MemoryBinding#withBinding(java.nio.ByteBuffer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Type2Descriptor withBinding(ByteBuffer buffer) {
		return super.withBinding(buffer);
	}

	/**
	 * On unbind.
	 *
	 * @see com.slytechs.protocol.runtime.MemoryBinding#onUnbind()
	 */
	@Override
	protected void onUnbind() {
		super.onUnbind();
		hash24 = hash32 = hashType = mask = -1;
	}
}
