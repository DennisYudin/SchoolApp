package dev.yudin.services;

import dev.yudin.entities.Group;
import dev.yudin.entities.GroupsAmountStudentDTO;

import java.util.List;
import java.util.Map;

public interface GroupsService {
	Map<String, Integer> convert(List<Group> groups);
	List<Group> findAll();
	void save(List<String> groups);
	List<GroupsAmountStudentDTO> findAll(int amountStudents);
}
