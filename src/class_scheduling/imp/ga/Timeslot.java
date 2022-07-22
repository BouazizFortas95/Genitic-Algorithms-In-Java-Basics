package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Timeslot {

	private final int timeslotId;
	private final String timeslot;

	public Timeslot(int timeslotId, String timeslot) {
		this.timeslotId = timeslotId;
		this.timeslot = timeslot;
	}

	/**
	 * @return the timeslotId
	 */
	public int getTimeslotId() {
		return this.timeslotId;
	}

	/**
	 * @return the timeslot
	 */
	public String getTimeslot() {
		return this.timeslot;
	}

	
}
