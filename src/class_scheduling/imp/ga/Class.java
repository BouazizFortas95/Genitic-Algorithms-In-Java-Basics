package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Class {

	private final int classId;
	private final int groupId;
	private final int moduleId;
	private int professorId;
	private int timeslotId;
	private int roomId;

	public Class(int classId, int groupId, int moduleId) {
		this.classId = classId;
		this.moduleId = moduleId;
		this.groupId = groupId;
	}

	public void addProfessor(int professorId) {
		this.professorId = professorId;
	}

	public void addTimeslot(int timeslotId) {
		this.timeslotId = timeslotId;
	}

	/**
	 * @return the roomId
	 */
	public int getRoomId() {
		return this.roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	/**
	 * @return the classId
	 */
	public int getClassId() {
		return this.classId;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return this.groupId;
	}

	/**
	 * @return the moduleId
	 */
	public int getModuleId() {
		return this.moduleId;
	}

	/**
	 * @return the timeslotId
	 */
	public int getTimeslotId() {
		return this.timeslotId;
	}

	/**
	 * @return the professorId
	 */
	public int getProfessorId() {
		return this.professorId;
	}

	
}
