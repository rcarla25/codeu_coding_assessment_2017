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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

final class MyJSON implements JSON {

	Map<String, JSON> objects;
	Map<String, String> values;

	
	public MyJSON() {
		objects = new HashMap<>();
		values = new HashMap<>();
	}
	
	public MyJSON(String name, JSON val) {
		this();
		objects.put(name, val);
		this.setString(name, val.getString(name));
	}
	
	public MyJSON(String str) {
		this();
		values.put(str, str);
	}

	@Override
	public JSON getObject(String name) {
		// TODO: implement this
		return objects.get(name);
	}

	@Override
	public JSON setObject(String name, JSON value) {
		// TODO: implement this
		objects.put(name, value);
		return this;
	}

	@Override
	public String getString(String name) {
		// TODO: implement this
		return values.get(name);
	}

	@Override
	public JSON setString(String name, String value) {
		// TODO: implement this
		if (objects.containsKey(name)) {
			objects.put(name, objects.get(name).setString(name, value));
		}
		else {
			MyJSON newJSON = new MyJSON(value);
			objects.put(name, newJSON);
			values.put(name, value);
		}
		return this;
	}

	@Override
	public void getObjects(Collection<String> names) {
		// TODO: implement this
		for (String name: objects.keySet()) {
			names.add(name);
		}
	}

	@Override
	public void getStrings(Collection<String> names) {
		// TODO: implement this
		for (String name: values.keySet()) {
			names.add(name);
		}
	}
}
