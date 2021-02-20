package model.services;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class SellerService {

	private SellerDAO dao = DAOFactory.createSellerDAO();
	
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller seller) {
		if(seller.getId() == null) {
			dao.insert(seller);
		}else {
			dao.update(seller);
		}
	}
	
	public void remove(Seller seller) {
		if(seller.getId() != null) {
			dao.deleteById(seller.getId());
		}
	}
}
