package com.hrportal.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.core5.http.NotImplementedException;

import jakarta.ws.rs.InternalServerErrorException;

public class Util {
	private Util() throws NotImplementedException {
		throw new NotImplementedException("All methods are static.");
	}

	public static Object createInstance(Class<?> cls) throws InternalServerErrorException {
		try {
			return cls.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			throw new InternalServerErrorException("Internal error");
		}
	}

	public static List<Object> createListInstance(Class<?> cls) throws InternalServerErrorException {
		try {
			return Arrays.asList(cls.getConstructor().newInstance());

		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			throw new InternalServerErrorException("Internal error");
		}
	}
}
