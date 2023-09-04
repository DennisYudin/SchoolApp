package dev.yudin.dao;

import dev.yudin.entities.Group;
import dev.yudin.entities.GroupsCountDTO;

import java.util.List;

public interface GroupDAO {
	List<Group> findAll();
	void save(List<String> groups);
	List<String> findAll(int amountStudents);
	List<GroupsCountDTO> findAll2(long amountStudents);
}
