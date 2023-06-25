package dev.yudin.dao;

import dev.yudin.entities.Group;

import java.util.List;

public interface GroupDAO {

	List<Group> findAll();

	void save(List<Group> courses);
}
