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

import static com.slytechs.protocol.pack.core.constants.CoreConstants.*;

import com.slytechs.protocol.meta.Meta;
import com.slytechs.protocol.meta.Meta.MetaType;
import com.slytechs.protocol.meta.MetaResource;
import com.slytechs.protocol.pack.core.constants.CoreId;
import com.slytechs.protocol.pack.core.constants.Icmp4Type;

/**
 * The ICMP echo message is a type of ICMP message that is used to test the
 * reachability of a network device. The ICMP echo message is also known as a
 * ping request.
 * <p>
 * The ICMP echo message has the following format:
 * </p>
 * <ul>
 * <li>Type: The type field is 8 bits long and specifies the type of ICMP
 * message. The type for an ICMP echo message is 8.</li>
 * <li>Code: The code field is 8 bits long and provides additional information
 * about the ICMP message. The code for an ICMP echo message is always 0.</li>
 * <li>Checksum: The checksum field is 16 bits long and is used to verify the
 * integrity of the ICMP message. The checksum is calculated by adding the
 * 16-bit values of all the fields in the ICMP header and the data field, and
 * then taking the one's complement of the result.</li>
 * <li>Identifier: The identifier field is 16 bits long and is used to identify
 * the ICMP echo message. The identifier is typically a random number that is
 * generated by the sender.</li>
 * <li>Sequence Number: The sequence number field is 16 bits long and is used to
 * sequence the ICMP echo messages. The sequence number is incremented for each
 * ICMP echo message that is sent.</li>
 * </ul>
 * <p>
 * The ICMP echo message is sent by the sender to the destination device. The
 * destination device responds with an ICMP echo reply message. The ICMP echo
 * reply message has the same format as the ICMP echo message, except that the
 * type field is 0.
 * </p>
 * <p>
 * The time it takes for the ICMP echo reply message to arrive is called the
 * round-trip time. The round-trip time can be used to measure the performance
 * of the network between the sender and the destination device.
 * </p>
 */
@MetaResource("icmp4-echo-meta.json")
public sealed class Icmp4Echo extends Icmp4
		permits Icmp4Echo.Request, Icmp4Echo.Reply {

	/**
	 * The Enum EchoType.
	 */
	public enum EchoType {

		/** The request. */
		REQUEST,

		/** The reply. */
		REPLY
	}

	/**
	 * ICMP echo request.
	 * <p>
	 * An ICMP echo request is a type of Internet Control Message Protocol (ICMP)
	 * packet that is used to test the reachability of a remote host. It is also
	 * known as a ping packet. When a host sends an ICMP echo request to another
	 * host, the receiving host sends an ICMP echo reply back to the sender. The
	 * time it takes for the echo reply to arrive is called the round-trip time
	 * (RTT).
	 * </p>
	 */
	@MetaResource("icmp4-echo-meta.json")
	public static final class Request extends Icmp4Echo {

		/** The Constant ID. */
		public static final int ID = CoreId.CORE_ID_ICMPv4_ECHO_REQUEST;

		/**
		 * Instantiates a new request.
		 */
		public Request() {
			super(ID);
		}
	}

	/**
	 * ICMP echo reply.
	 * <p>
	 * An ICMP echo reply is a type of Internet Control Message Protocol (ICMP)
	 * packet that is used to respond to an ICMP echo request. It is also known as a
	 * ping reply. When a host receives an ICMP echo request, it sends an ICMP echo
	 * reply back to the sender. The ICMP echo reply packet has the same format as
	 * the ICMP echo request packet, except that the type field is set to 0 (Echo
	 * Reply) and the code field is set to 0.
	 * </p>
	 */
	@MetaResource("icmp4-echo-meta.json")
	public static final class Reply extends Icmp4Echo {

		/** The Constant ID. */
		public static final int ID = CoreId.CORE_ID_ICMPv4_ECHO_REPLY;

		/**
		 * Instantiates a new reply.
		 */
		public Reply() {
			super(ID);
		}
	}

	/** The ICMP echo header ID constant. */
	public static final int ID = CoreId.CORE_ID_ICMPv4_ECHO;

	/** The is request. */
	private boolean isRequest;

	/**
	 * Checks if is request from type.
	 *
	 * @param type the type
	 * @return true, if is request from type
	 */
	private static boolean isRequestFromType(int type) {
		return type == Icmp4Type.ICMPv4_TYPE_ECHO_REQUEST;
	}

	/**
	 * Instantiates a new ICMP echo header.
	 */
	public Icmp4Echo() {
		super(ID);
	}

	/**
	 * Instantiates a new ICMP echo header.
	 *
	 * @param id the id
	 */
	protected Icmp4Echo(int id) {
		super(id);
	}

	/**
	 * On bind.
	 *
	 * @see com.slytechs.protocol.pack.core.Icmp#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();

		this.isRequest = isRequestFromType(type());
	}

	/**
	 * Identifier.
	 *
	 * @return the int
	 */
	@Meta
	public int identifier() {
		return Short.toUnsignedInt(buffer().getShort(ICMPv4_ECHO_FIELD_IDENTIFIER));
	}

	/**
	 * Sequence.
	 *
	 * @return the int
	 */
	@Meta
	public int sequence() {
		return Short.toUnsignedInt(buffer().getShort(ICMPv4_ECHO_FIELD_SEQUENCE));
	}

	/**
	 * Checks if is request.
	 *
	 * @return true, if is request
	 */
	public boolean isRequest() {
		return isRequest;
	}

	/**
	 * Checks if is reply.
	 *
	 * @return true, if is reply
	 */
	public boolean isReply() {
		return !isRequest;
	}

	/**
	 * Echo type as enum.
	 *
	 * @return the echo type
	 */
	@Meta(MetaType.ATTRIBUTE)
	public EchoType echoTypeAsEnum() {
		return isRequest ? EchoType.REQUEST : EchoType.REPLY;
	}
}