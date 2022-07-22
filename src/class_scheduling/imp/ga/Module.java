/**
 * 
 */
package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Module {

	private final int moduleId;
	private final String moduleCode;
	private final String module;
	private final int professorIds[];

	public Module(int moduleId, String moduleCode, String module, int professorIds[]) {
		this.moduleId = moduleId;
		this.moduleCode = moduleCode;
		this.module = module;
		this.professorIds = professorIds;
	}

	/**
	 * @return the moduleId
	 */
	public int getModuleId() {
		return this.moduleId;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return this.moduleCode;
	}

	/**
	 * @return the module
	 */
	public String getModuleName() {
		return this.module;
	}

	/**
	 * @return the Random Professor Id
	 */
	public int getRandomProfessorId() {
		int professorId = professorIds[(int) (professorIds.length * Math.random())];
		return professorId;
	}
}
