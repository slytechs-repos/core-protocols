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

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.slytechs.protocol.HeaderNotFound;
import com.slytechs.protocol.Packet;
import com.slytechs.protocol.descriptor.PacketDissector;
import com.slytechs.protocol.pack.core.constants.CoreConstants;
import com.slytechs.protocol.pack.core.constants.PacketDescriptorType;
import com.slytechs.protocol.runtime.util.HexStrings;

/**
 * VLAN header tests
 * 
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 *
 */
@Tag("osi-layer3")
@Tag("ip")
@Tag("IPv4")
class TestIp4Header {

	static final PacketDissector DISSECTOR = PacketDissector
			.dissector(PacketDescriptorType.TYPE2);

	static final ByteBuffer DESC_BUFFER = ByteBuffer
			.allocateDirect(CoreConstants.DESC_TYPE2_BYTE_SIZE_MAX)
			.order(ByteOrder.nativeOrder());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		DISSECTOR.reset();

		DESC_BUFFER.clear();
		while (DESC_BUFFER.remaining() > 0)
			DESC_BUFFER.put((byte) 0);

		DESC_BUFFER.clear();
	}

	@Test
	void test_Ip4_payloadLength() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(1500 - 20, ip4.payloadLength());
	}

	@Test
	void test_Ip4_totalLength() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(1500, ip4.totalLength());
	}

	@Test
	void test_Ip4_src() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_IP = HexStrings.parseHexString("83972081");

		assertArrayEquals(EXPECTED_IP, ip4.src());
	}

	@Test
	void test_Ip4_dst() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_IP = HexStrings.parseHexString("83972015");

		assertArrayEquals(EXPECTED_IP, ip4.dst());
	}

	@Test
	void test_Ip4_version() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(4, ip4.version());
	}

	@Test
	void test_Ip4_dsField() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.dsfield());
	}

	@Test
	void test_Ip4_dsFieldDscp() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.dsfieldDscp());
	}

	@Test
	void test_Ip4_dsFieldDscpCode() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.dsfieldDscpCode());
	}

	@Test
	void test_Ip4_dsFieldDscpSelect() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.dsfieldDscpSelect());
	}

	@Test
	void test_Ip4_dsFieldEcn() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.dsfieldEcn());
	}

	@Test
	void test_Ip4_dstByteArray() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_IP = HexStrings.parseHexString("83972015");

		assertArrayEquals(EXPECTED_IP, ip4.dst(new byte[CoreConstants.IPv4_ADDRESS_SIZE]));
	}

	@Test
	void test_Ip4_dstByteArrayInt() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_IP_PADDED = HexStrings.parseHexString("00 83972015");

		assertArrayEquals(EXPECTED_IP_PADDED, ip4.dst(new byte[CoreConstants.IPv4_ADDRESS_SIZE + 1], 1));
	}

	@Test
	void test_Ip4_dstInt() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0x83972015, ip4.dstAsInt());
	}

	@Test
	void test_Ip4_srcByteArray() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_IP = HexStrings.parseHexString("83972081");

		assertArrayEquals(EXPECTED_IP, ip4.src(new byte[CoreConstants.IPv4_ADDRESS_SIZE]));
	}

	@Test
	void test_Ip4_srcByteArrayInt() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_IP_PADDED = HexStrings.parseHexString("00 83972081");

		assertArrayEquals(EXPECTED_IP_PADDED, ip4.src(new byte[CoreConstants.IPv4_ADDRESS_SIZE + 1], 1));
	}

	@Test
	void test_Ip4_srcInt() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0x83972081, ip4.srcGetAsInt());
	}

	@Test
	void test_Ip4_flags() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0x2, ip4.flags());
	}

	@Test
	void test_Ip4_flagsByte() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0x40, ip4.flagsByte());
	}

	@Test
	void test_Ip4_flagsDf() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(1, ip4.flagsDf());
	}

	@Test
	void test_Ip4_flagsDfInfo() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals("Don't fragment", ip4.flagsInfo());
	}

	@Test
	void test_Ip4_flagsMf() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.flagsMf());
	}

	@Test
	void test_Ip4_flagsNibble() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(4, ip4.flagsNibble());
	}

	@Test
	void test_Ip4_fragOffset() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0, ip4.fragOffset());
	}

	@Test
	void test_Ip4_hdrLen() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(5, ip4.hdrLen());
	}

	@Test
	void test_Ip4_hdrLenBytes() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(20, ip4.hdrLenBytes());
	}

	@Test
	void test_Ip4_identification() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(0x3b32, ip4.identification());
	}

	@Test
	void test_Ip4_isReassembled() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertFalse(ip4.isReassembled());
	}

	@Test
	void test_Ip4_protocol() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(6, ip4.protocol());
	}

	@Test
	void test_Ip4_protocolInfo() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals("TCP", ip4.protocolInfo());
	}

	@Test
	void test_Ip4_reassembledFragments() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());
		var EXPECTED_FRAG_ARRAY = new Packet[0];

		assertArrayEquals(EXPECTED_FRAG_ARRAY, ip4.reassembledFragments());
	}

	@Test
	void test_Ip4_ttl() throws HeaderNotFound {
		var packet = CorePackets.VLAN_1500BYTES.toPacket();
		packet.descriptor().bind(DESC_BUFFER);

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		var ip4 = packet.getHeader(new Ip4());

		assertEquals(64, ip4.ttl());
	}

}
