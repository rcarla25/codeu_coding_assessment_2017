// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

final class MyJSONParser implements JSONParser {

	private static final char BACKSLASH = '\\';
	private static final char QUOTE = '"';
	private static final String COLON = ":";
	private static final String COMMA = ",";
	private static final String OPEN_BRACKET = "{";
	private static final String CLOSED_BRACKET = "}";

	@Override
	public JSON parse(String in) throws IOException {
		if (in == null)
			throw new IOException("Invalid string");

		MyJSON result = new MyJSON();

		if (in.indexOf(OPEN_BRACKET) != -1 && in.lastIndexOf(CLOSED_BRACKET) != -1) {
			int comma = in.indexOf(COMMA);
			if (comma != -1) {
				String[] objects = in.split(COMMA);
				for (String pair: objects) {
					if (pair.indexOf(COLON) != -1) {
						if (pair.indexOf(OPEN_BRACKET) != -1 &&
								pair.lastIndexOf(CLOSED_BRACKET) != -1) {
							JSON res = parse(pair);
							result.setObject(pair, res);
						}
						else {
							String[] keyVal = pair.split(COLON);
							String key = keyVal[0];
							String value = keyVal[1];
							key = parseHelp(key.toCharArray(), 0, "");
							value = parseHelp(value.toCharArray(), 0, "");
							result.setString(key, value);
						}
					}
				}
			}
		}
		else {
			String str = parseHelp(in.toCharArray(), 0, "");
			result.setString(str, str);
		}

		return result;
	}

	/**
	 *  Method to recursively parse through a string to determine
	 *  if it is a valid JSON STRING object
	 * @param chars != null
	 * @param str != null
	 * @return a String representation of the parsed JSON object
	 * @throws IOException if the string violates any of the rules
	 * 		   for a valid JSON object
	 */
	private String parseHelp(char[] chars, int index, String str) throws IOException {
		if (chars == null || str == null)
			throw new IllegalArgumentException("Cannot use null char[] or null String");

		if (index == chars.length) {
			return "";
		}
		else {
			if (chars[index] == BACKSLASH) {
				if(index + 1 < chars.length) {
					char ch = chars[index + 1];
					if(ch != BACKSLASH || ch != QUOTE || ch != 't' || ch != 'n') {
						throw new IOException("Invalid");
					}
				}
			}
			else if (chars[index] == QUOTE) {
				throw new IOException("Invalid");
			}
			else {
				return str += chars[index] + parseHelp(chars, index + 1, str); 
			}
		}
		return "";
	}
}

