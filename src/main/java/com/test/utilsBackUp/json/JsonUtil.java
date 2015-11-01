package com.test.utilsBackUp.json;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.utilsBackUp.other.DateUtil;
import com.test.utilsBackUp.other.StringUtil;

public class JsonUtil {
	private static DecimalFormat dfm = new DecimalFormat("##0.00");

	public static JSONObject toJSONObject(Object target) {
		if (target == null)
			return null;
		String methodName = null;
		JSONObject jsObject = new JSONObject();
		try {
			Method[] methods = target.getClass().getMethods();
			for (Method method : methods) {
				methodName = method.getName();
				if (!methodName.equals("getClass")
						&& methodName.startsWith("get")
						&& !method.isAnnotationPresent(JsonIgnore.class)
						&& method.getParameterTypes().length == 0) {
					String field = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
					Object value = method.invoke(target, new Object[] {});
					boolean isJsonData = false;
					if (method.isAnnotationPresent(JsonProperty.class)) {
						isJsonData = true;
						JsonProperty setJs = method.getAnnotation(JsonProperty.class);
						if (StringUtil.isNotBlank(setJs.value())) {
							field = setJs.value();
						}
					}
					if (value instanceof Collection) {
						if (isJsonData) {
							Collection<?> set = (Collection<?>) value;
							JSONArray jsonArray = new JSONArray();
							for (Object obj : set) {
								if (obj instanceof ToJson) {
									JSONObject jsonObj = ((ToJson) obj).toJSONObject();
									jsonArray.put(jsonObj);
								} else {
									jsonArray.put(obj);
								}
							}
							jsObject.put(field, jsonArray);
						}
					} else if (value instanceof Number) {
						if (value instanceof Double) {
							jsObject.put(field, dfm.format(value));
						} else {
							jsObject.put(field, value);
						}
					} else if (value instanceof Date) {
						String strDate = DateUtil.formatYMDHMS((Date) value);
						jsObject.put(field, strDate);
					} else if (value instanceof ToJson) {
						jsObject.put(field, ((ToJson) value).toJSONObject());
					} else {
						jsObject.put(field, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsObject;
	}

	public static JSONArray toJSONArray(Collection<?> target) {
		if (target == null) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		for (Object obj : target) {
			jsonArray.put(toJSONObject(obj));
		}
		return jsonArray;
	}

	public static JSONArray toJSONArray(Object[] target) {
		if (target == null) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		for (Object obj : target) {
			jsonArray.put(toJSONObject(obj));
		}
		return jsonArray;
	}
}
