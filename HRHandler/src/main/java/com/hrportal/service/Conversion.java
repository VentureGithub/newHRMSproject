package com.hrportal.service;

import org.apache.commons.lang.NullArgumentException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.hrportal.util.Util;

@ComponentScan
@Service
public class Conversion<S, K> {

	private ModelMapper modelMapper;

	public Conversion(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	@SuppressWarnings("unchecked")
	public S baseConversion(Class<?> cls, K dto) {
		try {
			S entity = (S) Util.createInstance(cls);
			entity = (S) this.modelMapper.map(dto, entity.getClass());
			return entity;
		} catch (Exception e) {
			throw new NullArgumentException("error :" + e.getMessage());
		}
	}

}
