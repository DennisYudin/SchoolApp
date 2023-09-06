package dev.yudin.dao;

import dev.yudin.entities.Group;
import dev.yudin.entities.GroupsAmountStudentDTO;

import java.util.List;

public interface GroupDAO {
	List<Group> findAll();
	void save(List<String> groups);
	List<GroupsAmountStudentDTO> findAll(int amountStudents);
}
