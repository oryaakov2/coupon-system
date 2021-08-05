package com.or_yaakov.couponsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.or_yaakov.couponsystem.data.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Coupon {
    public static final int MINIMUM_PRICE = 0;
    public static final int MINIMUM_CATEGORY = 1;
    public static final int MINIMUM_AMOUNT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Company company;
    private String title;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate startDate;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;
    private int category;
    private int amount;
    private String description;
    private double price;
    private String imageURL;
    @ManyToMany
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Customer> customers;

    public Coupon() {
        /*Empty*/
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        if (company != null) {
            this.company = company;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        }
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate != null) {
            this.endDate = endDate;
        }
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        if (category >= MINIMUM_CATEGORY) {
            this.category = category;
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount >= MINIMUM_AMOUNT) {
            this.amount = amount;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= MINIMUM_PRICE) {
            this.price = price;
        }
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        if (imageURL != null) {
            this.imageURL = imageURL;
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon)) return false;
        Coupon coupon = (Coupon) o;
        return getId() == coupon.getId();
    }

    @Override
    public String toString() {
        return String.format("Coupon{id=%d, title='%s', startDate=%s, endDate=%s, category=%d, amount=%d, price=%s}\n",
                id,
                title,
                startDate,
                endDate,
                category,
                amount,
                price);
    }

    public Coupon mergeCoupons(Coupon coupon) {
        coupon.setId(this.id);
        coupon.setCompany(this.company);
        coupon.setTitle(this.title);
        coupon.setStartDate(this.startDate);
        coupon.setEndDate(this.endDate);
        coupon.setCategory(this.category);
        coupon.setAmount(this.amount);
        coupon.setDescription(this.description);
        coupon.setPrice(this.price);
        coupon.setImageURL(this.imageURL);

        return coupon;
    }
}