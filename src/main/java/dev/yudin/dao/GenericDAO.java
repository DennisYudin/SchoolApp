package dev.yudin.dao;


import java.util.List;

public interface GenericDAO<T> {

	List<T> findAll();

//	void save(List<T> courses);
}
