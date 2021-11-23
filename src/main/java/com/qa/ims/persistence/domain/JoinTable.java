package com.qa.ims.persistence.domain;

public class JoinTable {
	
	
	private Long customerId;
	private String customerSurname;
	private Long orderId;
	private Long itemId;
	private String itemName;
	private Long itemCost; 
	private Long numItems;
	private Long totalCost;
	
	public JoinTable(Long customerId, String customerSurname, Long orderId, Long itemId, String itemName, Long itemCost, Long numItems,
			Long totalCost) {
		super();
		this.customerId = customerId;
		this.customerSurname = customerSurname;
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCost = itemCost;
		this.numItems = numItems;
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" 
				+ "\nOrder number:" + orderId +
				"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
				+ "\ncustomerId:\t\t" + customerId + "\ncustomerSurname:\t" + customerSurname 
				+ "\nitemId:\t\t\t" + itemId + "\nitemName:\t\t" + itemName + "\nitemCost:\t\t" + itemCost + "\nnumItems:\t\t" + numItems
				+ "\ntotalCost:\t\t" + totalCost ;
	}

	
	
	

}
