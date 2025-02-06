package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Games {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private Integer id;
	private String name;
	private Double price;
	private Date releaseDate;
	
	private Category category;
	
	public Games() {}
	public Games(Integer id, String name, Double price, Date releaseDate, Category category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.releaseDate = releaseDate;
		this.category = category;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Games other = (Games) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Game " + id + ", " + name + ", $" + String.format("%.2f", price) + ", released in " + sdf.format(releaseDate)
				+ " | " + category;
	}
	
	
}
