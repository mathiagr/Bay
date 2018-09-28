package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Product implements Serializable
{
    private static final long serialVersionUID = 1L;
	 //Create elements ids automatically, incremented 1 by 1
    @Id
    @GeneratedValue
    private int id;

    private boolean published;
	private String picturePath;
    private String name;

    @ManyToOne	
    @JoinColumn(name= "productCatalog_fk")
    private ProductCatalog productCatalog;
    
	@OneToMany
	@JoinColumn(name= "product_fk")
	private List <Bid> bids;


    //Make some entityy relations between this object and these variable
    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

    public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public Bid getBidById(int bidID) {
		List<Bid> bids = this.getBids();
		for (int i = 0; i < bids.size(); i++) {
			if (bids.get(i).getId() == bidID) return bids.get(i);
		}
		return null;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public void setBid(Bid bid) {
		this.bids.add(bid);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
			return "Product id " + id + " published: " + published + " name " + name + "ProductCatalog" + productCatalog + "\n";
	}
}
