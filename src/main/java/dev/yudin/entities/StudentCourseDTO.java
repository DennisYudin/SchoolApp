package dev.yudin.entities;

public class StudentCourseDTO {

	private int studentId;
	private int courseId;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StudentCourseDTO dto = (StudentCourseDTO) o;

		if (studentId != dto.studentId) return false;
		return courseId == dto.courseId;
	}

	@Override
	public int hashCode() {
		int result = studentId;
		result = 31 * result + courseId;
		return result;
	}

	@Override
	public String toString() {
		return "StudentCourseDTO{" +
				"studentId=" + studentId +
				", courseId=" + courseId +
				'}';
	}
}
