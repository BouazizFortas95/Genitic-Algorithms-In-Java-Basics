/**
 * 
 */
package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Professor {

	private final int professorId;
	private final String professorName;

	public Professor(int professorId, String professorName) {
		this.professorId = professorId;
		this.professorName = professorName;
	}

	/**
	 * @return the professorId
	 */
	public int getProfessorId() {
		return this.professorId;
	}

	/**
	 * @return the professorName
	 */
	public String getProfessorName() {
		return this.professorName;
	}
}
