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

import java.util.Set;

import com.slytechs.protocol.Packet;
import com.slytechs.protocol.meta.Meta;
import com.slytechs.protocol.meta.Meta.MetaType;
import com.slytechs.protocol.meta.MetaResource;
import com.slytechs.protocol.pack.core.constants.CoreConstants;
import com.slytechs.protocol.pack.core.constants.CoreIdTable;
import com.slytechs.protocol.pack.core.constants.Ip4Flag;
import com.slytechs.protocol.pack.core.constants.IpType;

/**
 * Internet Protocol Version 4 (IPv4).
 * <p>
 * Internet Protocol Version 4 (IPv4) is a widely used protocol in data
 * communication over various types of networks, particularly in packet-switched
 * layer networks like Ethernet. It provides a logical connection between
 * network devices by assigning identification to each device. IPv4 uses a best
 * effort delivery model and is a connectionless protocol, meaning that neither
 * delivery nor proper sequencing or avoidance of duplicate delivery is
 * guaranteed.
 * </p>
 * <p>
 * IPv4 employs 32-bit addressing, which allows for 232 unique addresses. These
 * addresses are divided into five classes: A, B, C, D, and E. Classes A, B, and
 * C differ in the bit length used to address the network host. While Class D
 * addresses are reserved for military purposes, Class E addresses are set aside
 * for future use. IPv4 addresses are typically represented in dot-decimal
 * notation, where the address is divided into four octets and expressed in
 * decimal format, separated by periods. RFC 791 is the publication that defines
 * and specifies IPv4. There are many ways to configure IPv4 with various
 * devices, including both manual and automatic configurations, depending on the
 * network type.
 * </p>
 * 
 * @author Sly Technologies
 * @author repos@slytechs.com
 */
@MetaResource("ip4-meta.json")
public final class Ip4 extends Ip<Ip4Option> {

	/** The Constant ID. */
	public static final int ID = CoreIdTable.CORE_ID_IPv4;

	/**
	 * Instantiates a new ip 4.
	 */
	public Ip4() {
		super(ID);
	}

	/**
	 * Dsfield.
	 *
	 * @return the int
	 */
	public int dsfield() {
		return Ip4Layout.DSFIELD.getInt(buffer());
	}

	/**
	 * Dsfield dscp.
	 *
	 * @return the int
	 */
	public int dsfieldDscp() {
		return Ip4Layout.DSFIELD_DSCP.getInt(buffer());
	}

	/**
	 * Dsfield dscp code.
	 *
	 * @return the int
	 */
	public int dsfieldDscpCode() {
		return Ip4Layout.DSFIELD_DSCP_CODE.getInt(buffer());
	}

	/**
	 * Dsfield dscp info.
	 *
	 * @return the string
	 */
	String dsfieldDscpInfo() {
		StringBuilder b = new StringBuilder();

		if (dsfieldDscp() == 0)
			b.append("Default (")
					.append(dsfieldDscp())
					.append(")");

		else
			b.append(dsfieldDscp());

		return b.toString();
	}

	/**
	 * Dsfield dscp select.
	 *
	 * @return the int
	 */
	int dsfieldDscpSelect() {
		return Ip4Layout.DSFIELD_DSCP_SELECT.getInt(buffer());
	}

	/**
	 * Dsfield ecn.
	 *
	 * @return the int
	 */
	int dsfieldEcn() {
		return Ip4Layout.DSFIELD_ECN.getInt(buffer());
	}

	/**
	 * Dsfield info.
	 *
	 * @return the string
	 */
	String dsfieldInfo() {
		StringBuilder b = new StringBuilder();

		if (dsfieldDscpSelect() == 0)
			b.append("DSCP: CS").append(dsfieldDscpCode());

		else if (dsfieldDscpSelect() == 1)
			b.append("DSCP: EF").append(dsfieldDscpCode());

		else if (dsfieldDscpSelect() == 2)
			b.append("DSCP: AF").append(dsfieldDscpCode());

		if (dsfieldEcn() == 0)
			b.append(", ENC: Not-ECT");

		return b.toString();
	}

	/**
	 * Dst.
	 *
	 * @return the byte[]
	 * @see com.slytechs.protocol.pack.core.Ip#dst()
	 */
	@Meta
	@Override
	public byte[] dst() {
		return dst(new byte[CoreConstants.IPv4_ADDRESS_SIZE], 0);
	}

	/**
	 * Dst.
	 *
	 * @param dst the dst
	 * @return the byte[]
	 */
	public byte[] dst(byte[] dst) {
		return dst(dst, 0);
	}

	/**
	 * Dst.
	 *
	 * @param dst    the dst
	 * @param offset the offset
	 * @return the byte[]
	 */
	public byte[] dst(byte[] dst, int offset) {
		buffer().get(CoreConstants.IPv4_FIELD_DST, dst, offset, CoreConstants.IPv4_FIELD_DST_LEN);

		return dst;
	}

	/**
	 * Dst address.
	 *
	 * @return the ip 4 address
	 * @see com.slytechs.protocol.pack.core.Ip#dstAddress()
	 */
	@Override
	public Ip4Address dstAddress() {
		return new Ip4Address(dst());
	}

	/**
	 * Dst as int.
	 *
	 * @return the int
	 */
	public int dstAsInt() {
		return Ip4Layout.DST.getInt(buffer());
	}

	/**
	 * Flags.
	 *
	 * @return the int
	 */
	@Meta
	public int flags() {
		return Ip4Layout.FLAGS.getInt(buffer());
	}

	/**
	 * Flags byte.
	 *
	 * @return the int
	 */
	public int flagsByte() {
		return Ip4Layout.FLAGS_BYTE.getInt(buffer());
	}

	/**
	 * Flags df.
	 *
	 * @return the int
	 */
	public int flagsDf() {
		return Ip4Layout.FLAGS_DF.getInt(buffer());
	}

	/**
	 * Flags info.
	 *
	 * @return the string
	 */
	@Meta(MetaType.ATTRIBUTE)
	public String flagsInfo() {
		if (flagsDf() != 0)
			return "Don't fragment";

		if (flagsMf() != 0)
			return "More fragments";

		return "No flags";
	}

	/**
	 * Flags mf.
	 *
	 * @return the int
	 */
	public int flagsMf() {
		return Ip4Layout.FLAGS_MF.getInt(buffer());
	}

	/**
	 * Flags nibble.
	 *
	 * @return the int
	 */
	public int flagsNibble() {
		return Ip4Layout.FLAGS_NIBBLE.getInt(buffer());
	}

	/**
	 * Flags rb.
	 *
	 * @return the int
	 */
	public int flagsRb() {
		return Ip4Layout.FLAGS_RB.getInt(buffer());
	}

	/**
	 * Flags set.
	 *
	 * @return the sets the
	 */
	@Meta(MetaType.ATTRIBUTE)
	public Set<Ip4Flag> flagsSet() {
		return Ip4Flag.valueOfInt03(flags());
	}

	/**
	 * Frag offset.
	 *
	 * @return the int
	 */
	public int fragOffset() {
		return Ip4Layout.FRAG_OFFSET.getInt(buffer());
	}

	/**
	 * Hdr len.
	 *
	 * @return the int
	 */
	@Meta
	public int hdrLen() {
		return Ip4Layout.HDR_LEN.getInt(buffer());
	}

	/**
	 * Hdr len bytes.
	 *
	 * @return the int
	 */
	@Meta(MetaType.ATTRIBUTE)
	public int hdrLenBytes() {
		return hdrLen() * 4;
	}

	/**
	 * Identification.
	 *
	 * @return the int
	 */
	@Meta
	public int identification() {
		return Ip4Layout.ID.getUnsignedShort(buffer());
	}

	/**
	 * Checks if is reassembled.
	 *
	 * @return true, if is reassembled
	 */
	public boolean isReassembled() {
		return false;
	}

	/**
	 * Payload length.
	 *
	 * @return the int
	 * @see com.slytechs.protocol.pack.core.Ip#payloadLength()
	 */
	@Override
	public int payloadLength() {
		return totalLength() - hdrLenBytes();
	}

	/**
	 * Protocol.
	 *
	 * @return the int
	 */
	@Meta
	public int protocol() {
		return Ip4Layout.PROTO.getInt(buffer());
	}

	/**
	 * Protocol info.
	 *
	 * @return the string
	 */
	public String protocolInfo() {
		return IpType.valueOfIpType(protocol()).name();
	}

	/**
	 * Reassembled fragments.
	 *
	 * @return the packet[]
	 */
	public Packet[] reassembledFragments() {
		return new Packet[0];
	}

	/**
	 * Src.
	 *
	 * @return the byte[]
	 * @see com.slytechs.protocol.pack.core.Ip#src()
	 */
	@Override
	@Meta
	public byte[] src() {
		return src(new byte[CoreConstants.IPv4_ADDRESS_SIZE], 0);
	}

	/**
	 * Src.
	 *
	 * @param dst the dst
	 * @return the byte[]
	 */
	public byte[] src(byte[] dst) {
		return src(dst, 0);
	}

	/**
	 * Src.
	 *
	 * @param dst    the dst
	 * @param offset the offset
	 * @return the byte[]
	 */
	public byte[] src(byte[] dst, int offset) {
		byte[] debug = new byte[20];
		buffer().get(0, debug);
		buffer().get(CoreConstants.IPv4_FIELD_SRC, dst, offset, CoreConstants.IPv4_FIELD_SRC_LEN);

		return dst;
	}

	/**
	 * Src get as address.
	 *
	 * @return the ip 4 address
	 * @see com.slytechs.protocol.pack.core.Ip#srcGetAsAddress()
	 */
	@Override
	public Ip4Address srcGetAsAddress() {
		return new Ip4Address(src());
	}

	/**
	 * Src get as int.
	 *
	 * @return the int
	 */
	public int srcGetAsInt() {
		return Ip4Layout.SRC.getInt(buffer());
	}

	/**
	 * Total length.
	 *
	 * @return the int
	 */
	@Meta
	public int totalLength() {
		return Ip4Layout.TOTAL_LENGTH.getUnsignedShort(buffer());
	}

	/**
	 * Ttl.
	 *
	 * @return the int
	 */
	@Meta
	public int ttl() {
		return Ip4Layout.TTL.getUnsignedByte(buffer());
	}

	/**
	 * Version.
	 *
	 * @return the int
	 */
	@Override
	@Meta
	public int version() {
		return Ip4Layout.VERSION.getUnsignedByte(buffer());
	}
}
