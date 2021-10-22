package com.siniia.app.dbo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
	
	NEW(1, "new", "New"),

	/** The ORDER PLACED. */
	ORDERPLACED(2, "orderPlaced", "OrderPlaced"),
	
	/** The ORDER DELIVERED*/
	ORDERDELIVERED (3,"orderDelivered","orderDelivered"),
	
	/** The Order Cancelled*/
	ORDERCANCELLED (4,"orderCancelled","orderCancelled"),
	
	/** The Order In Transit*/
	ORDERINTRANSIT (5,"orderInTransit","orderInTransit");
	
	/** The id. */
	private int id;

	/** The name. */
	private String name;

	/** The display. */
	private String display;

	/**
	 * Instantiates a new device status.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param display
	 *            the display
	 */
	private OrderStatus(int id, String name, String display) {
		this.id = id;
		this.name = name;
		this.display = display;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the display.
	 * 
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * Sets the display.
	 * 
	 * @param display
	 *            the new display
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/** The enum id map. */
	private static Map<Integer, OrderStatus> enumIdMap;

	/** The enum name map. */
	private static Map<String, OrderStatus> enumNameMap;

	static {
		OrderStatus[] values = OrderStatus.values();
		Map<Integer, OrderStatus> iMap = new HashMap<Integer, OrderStatus>();
		Map<String, OrderStatus> sMap = new HashMap<String, OrderStatus>();

		for (OrderStatus statusName : values) {
			iMap.put(statusName.id, statusName);
			sMap.put(statusName.name, statusName);
		}
		enumIdMap = Collections.unmodifiableMap(iMap);
		enumNameMap = Collections.unmodifiableMap(sMap);
	}

	/**
	 * From id.
	 * 
	 * @param id
	 *            the id
	 * @return the device status
	 */
	public static OrderStatus fromId(Integer id) {
		return enumIdMap.get(id);
	}

	/**
	 * From name.
	 * 
	 * @param name
	 *            the name
	 * @return the device status
	 */
	public static OrderStatus fromName(String name) {
		return enumNameMap.get(name);
	}

	/**
	 * The main method.
	 * 
	 * @param ar
	 *            the arguments
	 */
	public static void main(String ar[]) {
	}
}
