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

import static com.slytechs.protocol.runtime.internal.layout.BinaryLayout.*;

import com.slytechs.protocol.pack.core.constants.CoreConstants;
import com.slytechs.protocol.runtime.internal.layout.ArrayField;
import com.slytechs.protocol.runtime.internal.layout.BinaryLayout;
import com.slytechs.protocol.runtime.internal.layout.BitField;
import com.slytechs.protocol.runtime.internal.layout.EnumBitField;
import com.slytechs.protocol.runtime.internal.layout.PredefinedLayout.Int16be;
import com.slytechs.protocol.runtime.internal.layout.PredefinedLayout.Int32be;
import com.slytechs.protocol.runtime.internal.layout.PredefinedLayout.Int8;
import com.slytechs.protocol.runtime.internal.layout.PredefinedLayout.Padding;

/**
 * The Enum TcpStruct.
 */
public enum TcpStruct implements EnumBitField<TcpStruct> {

	// TCP HEADER FIELDS

	/** The src port. */
	SRC_PORT(Layout.TCP_STRUCT, "tcp.srcport"),
	
	/** The dst port. */
	DST_PORT(Layout.TCP_STRUCT, "tcp.dstport"),
	
	/** The seq. */
	SEQ(Layout.TCP_STRUCT, "tcp.seq"),
	
	/** The ack. */
	ACK(Layout.TCP_STRUCT, "tcp.ack"),
	
	/** The hdr len. */
	HDR_LEN(Layout.TCP_STRUCT, "tcp.hdr_len"),
	
	/** The reserved. */
	RESERVED(Layout.TCP_STRUCT, "tcp.res"),
	
	/** The flags. */
	FLAGS(Layout.TCP_STRUCT, "tcp.flags"),
	
	/** The win size. */
	WIN_SIZE(Layout.TCP_STRUCT, "tcp.window_size_value"),
	
	/** The checksum. */
	CHECKSUM(Layout.TCP_STRUCT, "tcp.checksum"),
	
	/** The urgent pointer. */
	URGENT_POINTER(Layout.TCP_STRUCT, "tcp.urgent_pointer"),

	// TCP OPTION FIELDS

	/** The opt kind. */
	OPT_KIND(Layout.OPTION_LAYOUT, "tcp.opt.kind"),
	
	/** The opt len. */
	OPT_LEN(Layout.OPTION_LAYOUT, "tcp.opt.len"),
	
	/** The opt mss. */
	OPT_MSS(Layout.OPTION_LAYOUT, "tcp.opt.mss"),
	
	/** The opt tssend. */
	OPT_TSSEND(Layout.OPTION_LAYOUT, "tcp.opt.ts.send_ts"),
	
	/** The opt tsrecv. */
	OPT_TSRECV(Layout.OPTION_LAYOUT, "tcp.opt.ts.recv_ts"),
	
	/** The opt win scale. */
	OPT_WIN_SCALE(Layout.OPTION_LAYOUT, "tcp.opt.win_scale");

	/**
	 * The Class Layout.
	 */
	private static class Layout {
		
		/** The Constant TCP_STRUCT. */
		private static final BinaryLayout TCP_STRUCT = unionLayout(
				structLayout(

						/* Word0 */
						Int16be.BITS_16.withName("tcp.srcport"),
						Int16be.BITS_16.withName("tcp.dstport"),

						/* Word1 */
						Int32be.BITS_32.withName("tcp.seq"),

						/* Word2 */
						Int32be.BITS_32.withName("tcp.ack"),

						/* Word3 */
						Int16be.BITS_04.withName("tcp.hdr_len"),
						Int16be.BITS_03.withName("tcp.res"),
						Int16be.BITS_09.withName("tcp.flags"),
						Int16be.BITS_16.withName("tcp.window_size_value"),

						/* Word4 */
						Int16be.BITS_16.withName("tcp.checksum"),
						Int16be.BITS_16.withName("tcp.urgent_pointer")),

				sequenceLayout(CoreConstants.TCP_HEADER_LEN / 1, Int8.BITS_08).withName("tcp.bytes"),
				sequenceLayout(CoreConstants.TCP_HEADER_LEN / 2, Int16be.BITS_16).withName("tcp.shorts"),
				sequenceLayout(CoreConstants.TCP_HEADER_LEN / 4, Int32be.BITS_32).withName("tcp.ints"));

		/** The Constant OPTION_LAYOUT. */
		private static final BinaryLayout OPTION_LAYOUT = unionLayout(
				structLayout(
						Int8.BITS_08.withName("tcp.opt.kind"),
						Int8.BITS_08.withName("tcp.opt.len")),

				structLayout(
						Padding.BITS_16,
						Int16be.BITS_16.withName("tcp.opt.mss")),

				structLayout(
						Padding.BITS_16,
						Int32be.BITS_32.withName("tcp.opt.ts.send_ts"),
						Int32be.BITS_32.withName("tcp.opt.ts.recv_ts")),

				structLayout(
						Padding.BITS_16,
						sequenceLayout(16, Int8.BITS_08).withName("tcp.opt.fastopen.cookie")),

				structLayout(
						Padding.BITS_16,
						Int8.BITS_08.withName("tcp.opt.win_scale")));

	}

	/** The Constant HEADER_BYTES. */
	public static final ArrayField HEADER_BYTES = Layout.TCP_STRUCT.arrayField("tcp.bytes");
	
	/** The Constant HEADER_SHORTS. */
	public static final ArrayField HEADER_SHORTS = Layout.TCP_STRUCT.arrayField("tcp.shorts");
	
	/** The Constant HEADER_INTS. */
	public static final ArrayField HEADER_INTS = Layout.TCP_STRUCT.arrayField("tcp.ints");
	
	/** The Constant OPT_COOKIE. */
	public static final ArrayField OPT_COOKIE = Layout.OPTION_LAYOUT.arrayField("tcp.opt.fastopen.cookie");

	/** The bits. */
	private final BitField bits;

	/**
	 * Instantiates a new tcp struct.
	 *
	 * @param layout the layout
	 * @param path   the path
	 */
	private TcpStruct(BinaryLayout layout, String path) {
		this.bits = layout.bitField(path)
				.formatted()
				.format("%d");
	}

	/**
	 * Proxy bit field.
	 *
	 * @return the bit field
	 * @see com.slytechs.protocol.runtime.internal.layout.BitField.Proxy#proxyBitField()
	 */
	@Override
	public BitField proxyBitField() {
		return bits;
	}
}
