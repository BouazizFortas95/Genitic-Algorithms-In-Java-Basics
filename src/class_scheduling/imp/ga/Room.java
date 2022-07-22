package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Room {

	private final int roomId;
	private final String roomNumber;
	private final int capacity;
	
	public Room(int roomId, String roomNumber, int capacity) {
		this.roomId = roomId;
		this.roomNumber = roomNumber;
		this.capacity = capacity;
	}

	/**
	 * @return the roomId
	 */
	public int getRoomId() {
		return this.roomId;
	}

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return this.roomNumber;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}
}
