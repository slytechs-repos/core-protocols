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
package com.slytechs.protocol.meta;

import java.lang.reflect.AnnotatedElement;

import com.slytechs.protocol.meta.Meta.Formatter;
import com.slytechs.protocol.meta.Meta.MetaType;
import com.slytechs.protocol.runtime.internal.json.JsonObject;
import com.slytechs.protocol.runtime.internal.json.JsonObjectBuilder;
import com.slytechs.protocol.runtime.internal.json.JsonValue;
import com.slytechs.protocol.runtime.internal.json.JsonValue.ValueType;

/**
 * The Class MetaInfo.
 *
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 * @author Mark Bednarczyk
 */
public class MetaInfo implements Comparable<MetaInfo>, MetaInfoType {

	/**
	 * Parses the.
	 *
	 * @param element      the element
	 * @param name         the name
	 * @param jsonDefaults the json defaults
	 * @return the meta info
	 */
	public static MetaInfo parse(AnnotatedElement element, String name, JsonObject jsonDefaults) {

		if (!element.isAnnotationPresent(Meta.class) && jsonDefaults == null)
			throw new IllegalArgumentException("Meta resource or direct annotation not found [%s]"
					.formatted(name));

		JsonValue jsonMeta = null;
		if (jsonDefaults != null)
			jsonMeta = jsonDefaults.get("meta");

		MetaInfo meta = new MetaInfo();

		if (jsonMeta != null)
			parseJsonMeta(meta, jsonMeta);

		if (element.isAnnotationPresent(Meta.class))
			parseMetaAnnotation(meta, element, name);

		if (meta.abbr == null)
			meta.abbr = meta.name;

		meta.displaysInfo = DisplaysInfo.parse(element, name, jsonDefaults);
		meta.resolversInfo = ResolversInfo.parse(element, name, jsonDefaults);

		return meta;
	}

	/**
	 * Parses the json meta.
	 *
	 * @param meta      the meta
	 * @param jsonValue the json value
	 */
	private static void parseJsonMeta(MetaInfo meta, JsonValue jsonValue) {

		JsonObject jsonMeta;
		if (jsonValue.getValueType() == ValueType.OBJECT)
			jsonMeta = (JsonObject) jsonValue;
		else
			jsonMeta = new JsonObjectBuilder()
					.add("ordinal", jsonValue)
					.build();

		String name = jsonMeta.getString("name", null);
		String abbr = jsonMeta.getString("abbr", null);
		String note = jsonMeta.getString("note", null);
		int ordinal = jsonMeta.getInt("ordinal", -1);
		String typeName = jsonMeta.getString("formatter", null);

		if (name != null)
			meta.name = name;

		if (abbr != null)
			meta.abbr = abbr;

		if (note != null)
			meta.note = note;

		if (ordinal != -1)
			meta.ordinal = ordinal;

		if (typeName != null) {
			Formatter type = Formatter.valueOf(typeName);
			meta.formatter = type;
		}
	}

	/**
	 * Parses the meta annotation.
	 *
	 * @param meta    the meta
	 * @param element the element
	 * @param name    the name
	 */
	private static void parseMetaAnnotation(MetaInfo meta, AnnotatedElement element, String name) {
		Meta ma = element.getAnnotation(Meta.class);
		if (ma == null)
			return;

		if (meta.name == null)
			meta.name = name; // Default name

		/* Meta annotations override any previously JSON defined attributes */
		if (!ma.name().isBlank())
			meta.name = ma.name();

		if (!ma.abbr().isBlank())
			meta.abbr = ma.abbr();

		if (!ma.note().isBlank())
			meta.note = ma.note();

		if (ma.ordinal() != -1)
			meta.ordinal = ma.ordinal();

		if (meta.formatter == null || ma.formatter() != Formatter.NONE) {
			meta.formatter = ma.formatter();
		}

		meta.metaType = ma.value();

	}

	/** The name. */
	private String name;

	/** The abbr. */
	private String abbr;
	
	/** The note. */
	private String note = "";
	
	/** The ordinal. */
	private int ordinal = -1;
	
	/** The formatter. */
	private Formatter formatter;
	
	/** The displays info. */
	private DisplaysInfo displaysInfo;
	
	/** The resolvers info. */
	private ResolversInfo resolversInfo;
	
	/** The meta type. */
	private MetaType metaType;
	
	/**
	 * Instantiates a new meta info.
	 */
	private MetaInfo() {
	}

	/**
	 * Abbr.
	 *
	 * @return the abbr
	 */
	protected String abbr() {
		return abbr;
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MetaInfo o) {
		return ordinal() - o.ordinal();
	}

	/**
	 * Formatter.
	 *
	 * @return the type
	 */
	public Formatter formatter() {
		return formatter;
	}

	/**
	 * Gets the meta type.
	 *
	 * @param <T>  the generic type
	 * @param type the type
	 * @return the meta type
	 */
	@SuppressWarnings("unchecked")
	public <T extends MetaInfoType> T getMetaType(Class<T> type) {
		if (type == MetaInfo.class)
			return (T) this;

		if (type == DisplaysInfo.class)
			return (T) displaysInfo;

		if (type == ResolversInfo.class)
			return (T) resolversInfo;

		return null;
	}

	/**
	 * Meta type.
	 *
	 * @return the meta type
	 */
	public MetaType metaType() {
		return metaType;
	}

	/**
	 * Name.
	 *
	 * @return the name
	 */
	public String name() {
		return name;
	}

	/**
	 * Note.
	 *
	 * @return the note
	 */
	public String note() {
		return note;
	}

	/**
	 * Ordinal.
	 *
	 * @return the ordinal
	 */
	public int ordinal() {
		return ordinal;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MetaInfo [name=" + name + ", abbr=" + abbr + ", note=" + note + ", ordinal=" + ordinal + ", type="
				+ formatter + ", displaysInfo=" + displaysInfo + ", resolversInfo=" + resolversInfo + "]";
	}
}
