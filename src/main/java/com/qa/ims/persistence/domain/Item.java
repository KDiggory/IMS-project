package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item {
	
	private Long id;
	private String name;
	private String size;
	private Long cost;
	
	
	public Item(String name, String size, Long cost) {
		super();
		this.name = name;
		this.size = size;
		this.cost = cost;
	}
	
	public Item(Long id, String name, String size, Long cost) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, id, name, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(cost, other.cost) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(size, other.size);
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", size=" + size + ", cost=" + cost + "]";
	}
	
	
	
	
}


