package dev.yudin.services.impl;

import dev.yudin.dao.GroupDAO;
import dev.yudin.entities.Group;
import dev.yudin.exceptions.DAOException;
import dev.yudin.exceptions.ServiceException;
import dev.yudin.services.GroupService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupServiceImpl implements GroupService {
	private final Logger log = LogManager.getLogger(GroupServiceImpl.class);
	private GroupDAO groupDAO;

	public GroupServiceImpl(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	@Override
	public Map<String, Integer> convert(List<Group> groups) {
		Map<String, Integer> result = new HashMap<>();

		groups.forEach(group -> result.put(group.getName(), group.getId()));

		return result;
	}

//	public List<String> getGroupsWithLessOrEquals(int amountStudents) {
//
//		return null;
//	}

	@Override
	public List<Group> findAll() {
		List<Group> groups;
		try {
			groups = groupDAO.findAll();
		} catch (DAOException ex) {
			log.error("Error during findAll()");
			throw new ServiceException("Error during findAll()");
		}
		return groups;
	}

	@Override
	public void save(List<String> groups) {
		try {
			groupDAO.save(groups);
		} catch (DAOException ex) {
			log.error("Error during save()");
			throw new ServiceException("Error during save()");
		}
	}
}
