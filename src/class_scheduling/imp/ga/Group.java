package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Group {

	private final int groupId;
	private final int groupSize;
	private final int moduleIds[];

	public Group(int groupId, int groupSize, int moduleIds[]) {
		this.groupId = groupId;
		this.groupSize = groupSize;
		this.moduleIds = moduleIds;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return this.groupId;
	}

	/**
	 * @return the groupSize
	 */
	public int getGroupSize() {
		return this.groupSize;
	}

	/**
	 * @return the moduleIds
	 */
	public int[] getModuleIds() {
		return this.moduleIds;
	}
}
