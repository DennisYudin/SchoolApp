package dev.yudin.services;

import dev.yudin.entities.Group;

import java.util.List;
import java.util.Map;

public interface GroupService {

	Map<String, Integer> convert(List<Group> groups);
	List<Group> findAll();
	void save(List<String> groups);
}
