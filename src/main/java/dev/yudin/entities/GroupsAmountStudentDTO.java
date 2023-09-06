package dev.yudin.entities;

import java.util.Objects;

public class GroupsAmountStudentDTO {
	private String groupName;
	private int amountStudents;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getAmountStudents() {
		return amountStudents;
	}

	public void setAmountStudents(int amountStudents) {
		this.amountStudents = amountStudents;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GroupsAmountStudentDTO dto = (GroupsAmountStudentDTO) o;

		if (amountStudents != dto.amountStudents) return false;
		return Objects.equals(groupName, dto.groupName);
	}

	@Override
	public int hashCode() {
		int result = groupName != null ? groupName.hashCode() : 0;
		result = 31 * result + amountStudents;
		return result;
	}

	@Override
	public String toString() {
		return "GroupsAmountStudentDTO{" +
				"groupName='" + groupName + '\'' +
				", amountStudents=" + amountStudents +
				'}';
	}
}
