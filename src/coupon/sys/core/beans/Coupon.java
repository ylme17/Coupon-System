package coupon.sys.core.beans;

import java.util.Date;

/**
 *  this class for create coupon object
 * @author YECHIEL.MOSHE
 *
 */
public class Coupon {

	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	/**
	 * construct coupon object and get id, title, start date, end date, amount, type, message, price and image
	 * @param id id of coupon - generated automatically from DB
	 * @param title name of coupon
	 * @param startDate the date the coupon started
	 * @param endDate the date the coupon expire
	 * @param amount the amount of coupon
	 * @param type coupon type
	 * @param message describe the coupon
	 * @param price coupon price
	 * @param image image of the item
	 */
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}
	
	/**
	 * empty constructor
	 */
	public Coupon() {
	}
	
	/**
	 * construct coupon object and get only id
	 * @param id
	 */
	public Coupon(long id) {
		super();
		this.id = id;
	}
	
	/**
	 * construct coupon object and get only name
	 * @param title
	 */
	public Coupon(String title) {
		super();
		this.title = title;
	}

	/**
	 * get id method
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * set id method
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * get title method
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set title method
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * get startDate method
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * set startDate method
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * get endDate method
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * set endDate method
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * get amount method
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * set amount method
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * get type method
	 * @return type
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * set type method
	 * @param type
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * get message method
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * set message method
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * get price method
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * set price method
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * get image method
	 * @return image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * set image method
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * to string method
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "] \n";
	}

}
