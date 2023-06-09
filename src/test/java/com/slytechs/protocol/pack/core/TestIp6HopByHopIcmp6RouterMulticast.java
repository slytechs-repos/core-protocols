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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.slytechs.protocol.HeaderNotFound;
import com.slytechs.protocol.descriptor.PacketDissector;
import com.slytechs.protocol.meta.MetaHeader;
import com.slytechs.protocol.meta.PacketFormat;
import com.slytechs.protocol.pack.core.constants.CoreConstants;
import com.slytechs.protocol.pack.core.constants.CoreId;
import com.slytechs.protocol.pack.core.constants.PacketDescriptorType;
import com.slytechs.protocol.runtime.util.Detail;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 *
 */
class TestIp6HopByHopIcmp6RouterMulticast {

	static final PacketDissector DISSECTOR = PacketDissector
			.dissector(PacketDescriptorType.TYPE2);

	static final ByteBuffer DESC_BUFFER = ByteBuffer
			.allocateDirect(CoreConstants.DESC_TYPE2_BYTE_SIZE_MAX)
			.order(ByteOrder.nativeOrder());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp(TestInfo info) throws Exception {
		DISSECTOR.reset();

		DESC_BUFFER.clear();
		while (DESC_BUFFER.remaining() > 0)
			DESC_BUFFER.put((byte) 0);

		DESC_BUFFER.clear();

		System.out.printf("---- %s ----%n", info.getDisplayName());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void ip6_RouterAlertOption_in_HopByHop() {

		var packet = CoreTestPackets.ETH_IPv6_HOP_BY_HOP_ROUTER_ALERT_ICMPv6.toPacket();
		packet.descriptor().bind(DESC_BUFFER);
		packet.setFormatter(new PacketFormat());

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		packet.descriptor().buffer().flip();

		System.out.println(packet.toString(Detail.HIGH));
		System.out.println(packet.descriptor().toString(Detail.HIGH));

		assertTrue(packet.hasHeader(CoreId.CORE_ID_IPv6_EXTENSION));

		try {
			Ip ip = packet.getHeader(new Ip());
			System.out.println(new MetaHeader(packet, ip));
		} catch (HeaderNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void ip4_RouterAlert_Option() throws HeaderNotFound {
		var packet = CoreTestPackets.ETH_IPv4_OPT_RSVP.toPacket();
		packet.descriptor().bind(DESC_BUFFER);
		packet.setFormatter(new PacketFormat());

		DISSECTOR.dissectPacket(packet);
		DISSECTOR.writeDescriptor(packet.descriptor());

		System.out.println(packet.descriptor().toString(Detail.HIGH));
//		System.out.println(packet.toString(Detail.HIGH));

	}

}
