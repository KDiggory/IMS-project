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

	public Long getCustomerId() { 
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerSurname() {
		return customerSurname;
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemCost() {
		return itemCost;
	}

	public void setItemCost(Long itemCost) {
		this.itemCost = itemCost;
	}

	public Long getNumItems() {
		return numItems;
	}

	public void setNumItems(Long numItems) {
		this.numItems = numItems;
	}

	public Long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Long totalCost) {
		this.totalCost = totalCost;
	}

	
	
	

}
