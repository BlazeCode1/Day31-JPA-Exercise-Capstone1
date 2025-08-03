package org.example.day31jpaexercisecapstone1.Service;


import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Model.*;
import org.example.day31jpaexercisecapstone1.Repository.MerchantRepository;
import org.example.day31jpaexercisecapstone1.Repository.MerchantStockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService;
    private final MerchantStockRepository merchantStockRepository;

    public List<MerchantStock> getAllMerchantStocks(){
        return merchantStockRepository.findAll();
    }

    public int addMerchantStock(MerchantStock merchantStock){
        if(productService.getAllProducts().isEmpty()) { // checking if products are available.
            return 0;
        }
        if(merchantService.getAllMerchants().isEmpty()) {  // checking if merchants are empty
            return 1;
        }
        MerchantStock foundMerchantStock = merchantStockRepository.getById(merchantStock.getMerchant_id());
        if(foundMerchantStock.equals(merchantStock)){
            return 5;
        }



        if(productService.findById(merchantStock.getMerchant_id()) == null) return 2;
        if(merchantService.findById(merchantStock.getProduct_id()) == null) return 3;

        merchantStock.setStock_added_date(LocalDate.now());
        merchantStock.set_clearance(false);
        merchantStockRepository.save(merchantStock);

        return 4;

    }

    public int updateMerchantStock(MerchantStock merchantStock){
        if(productService.getAllProducts().isEmpty()) return 0;
        if(merchantService.getAllMerchants().isEmpty())  return 1;

        if(merchantService.findById(merchantStock.getMerchant_id()) == null) return 2;
        if(productService.findById(merchantStock.getProduct_id()) == null) return 3;
        MerchantStock oldMerchantStock =  merchantStockRepository.getById(merchantStock.getId());
        if(oldMerchantStock==null){
            return 4;
        }

        oldMerchantStock.setMerchant_id(merchantStock.getMerchant_id());
        oldMerchantStock.setProduct_id(merchantStock.getProduct_id());
        oldMerchantStock.setStock(merchantStock.getStock());
        merchantStockRepository.save(oldMerchantStock);
        return 5;
    }

    public boolean deleteMerchantStock(Integer ID){
        MerchantStock merchantStock = merchantStockRepository.getById(ID);
        if(merchantStock == null)
            return false;

        merchantStockRepository.delete(merchantStock);
        return true;
    }
//
public int addMoreStock(Integer productID, Integer merchantID, Integer amount) {
    if (productService.getAllProducts().isEmpty()) return 0;
    if (merchantService.getAllMerchants().isEmpty()) return 1;
    if (productService.findById(productID) == null) return 2;
    if (merchantService.findById(merchantID) == null) return 3;

    for (MerchantStock m : merchantStockRepository.findAll()) {
        if (m.getProduct_id().equals(productID) && m.getMerchant_id().equals(merchantID)) {
            m.setStock(m.getStock() + amount);
            merchantStockRepository.save(m);
            return 5; // Success
        }
    }

    return 4; // MerchantStock not found
}

//
    public int userBuyProduct(Integer userID, Integer productID, Integer merchantID){

        Product targetProduct = productService.findById(productID);
        Merchant targetMerchant = merchantService.findById(merchantID);
        User targetUser = userService.findById(userID);

        if(productService.getAllProducts().isEmpty()) return 0;
        if(merchantService.getAllMerchants().isEmpty()) return 1;
        if(targetProduct == null) return 2;
        if(targetMerchant == null) return 3;
        if(targetUser == null) return 4;
        if(checkIfProductInStock(productID,merchantID)) return 5;

        MerchantStock targetStock = null;




        for (MerchantStock m : merchantStockRepository.findAll()){
            if(productID.equals(m.getProduct_id()) && merchantID.equals(m.getMerchant_id())){
                targetStock = m;
                break;
            }
        }


        if(targetMerchant.isSuspended()) return 12;




        if(targetStock == null || targetProduct == null || targetUser == null ) return 6; // to be safe

        if(targetStock.getStock() == 0) return 9;
        double finalPrice = targetStock.is_clearance() ? targetStock.getClearance_price() : targetProduct.getPrice();

        if(targetUser.getBalance() < finalPrice) return 7;
        //User deductBalance
        targetUser.setBalance(targetUser.getBalance() - finalPrice);

        //Product setters
        targetProduct.setLast_sold_date(LocalDate.now());
        targetProduct.setTotal_sold(targetProduct.getTotal_sold() + 1);
        targetProduct.setTotal_revenue(targetProduct.getTotal_revenue() + finalPrice);

        //Stock setters
        targetStock.setStock(targetStock.getStock() - 1);
        targetStock.setPurchase_count(targetStock.getPurchase_count() + 1);
        return 8;

    }

    public ArrayList<Product> getOutOfStockProductsForMerchant(Integer merchantID){
        ArrayList<Integer> outOfStockIDs = new ArrayList<>();
        ArrayList<Product> outOfStockMerchants = new ArrayList<>();
        for (MerchantStock m : merchantStockRepository.findAll()){
            if(m.getMerchant_id().equals(merchantID) && m.getStock() == 0){
                outOfStockIDs.add(m.getProduct_id());
            }
        }
        for (Product p : productService.getAllProducts()){
            if(outOfStockIDs.contains(p.getID())){
                outOfStockMerchants.add(p);
            }
        }
        return outOfStockMerchants;
    }
//
//    //TODO: THIRD METHOD
//    //Admin
    public int triggerClearance(Integer adminID,Integer merchantID){
        if(!isUserAdmin(adminID)) return 0;
        LocalDate today = LocalDate.now();
        LocalDate cutOff = today.minusDays(45);
        boolean anyClearanceTriggered = false;
        for (MerchantStock m : merchantStockRepository.findAll()){
            if(merchantID.equals(m.getMerchant_id())) {
                if(m.getStock() <=20) continue;


                if(m.getStock_added_date() == null || !m.getStock_added_date().isBefore(cutOff)) continue;

                if(m.getProduct_id() == null) continue;
                Product targetProduct = productService.findById(m.getProduct_id());
                if(targetProduct == null) continue;

                m.set_clearance(true);
                m.setClearance_price(targetProduct.getPrice() * 0.7);
                anyClearanceTriggered = true;
            }

        if(anyClearanceTriggered){
            return 2; //clearance was triggered for at least one product.
        }

        }
        return 1; //no products met the criteria.
    }
    //Admin
    public String evaluateCommissionTier(Integer adminID,Integer merchantID){
        if(!isUserAdmin(adminID)) return "Access Denied: Only admins can evaluate Commission Tier.";
        Merchant targetMerchant = merchantService.findById(merchantID);
        double totalRevenue = 0;


        if(targetMerchant == null) return "Invalid Merchant ID.";;

        for (MerchantStock stock : merchantStockRepository.findAll()){
            if(stock.getMerchant_id().equals(merchantID)){

                if (stock.getPurchase_count() != 0) {
                    Product matchedProduct = null;
                    for (Product p : productService.getAllProducts()){
                        if(p.getID().equals(stock.getProduct_id())){
                            matchedProduct = p;
                            break;
                        }
                    }

                    if(matchedProduct != null){
                        double price = stock.is_clearance() ? stock.getClearance_price() : matchedProduct.getPrice();
                        totalRevenue += price * stock.getPurchase_count();
                    }
                }

            }
        }

        double commissionRate;
        if(totalRevenue > 10000){
            commissionRate = 0.05;
        }else if(totalRevenue > 5000) {
            commissionRate = 0.09;
        }else {
            commissionRate = 0.12;
        }
        targetMerchant.setCommission_rate(commissionRate);
        merchantService.saveMerchant(targetMerchant);
        return "Merchant " + targetMerchant.getName()
                + " has total revenue: " + totalRevenue + " SAR and tier: "
                + (commissionRate * 100) + "% commission rate applied.";
    }

    public String evaluatingMerchantViolation(Integer adminID,Integer merchantID){
        if(!isUserAdmin(adminID)) return "Access Denied: Only admins can evaluate Merchant Violation.";
        Merchant targetMerchant = merchantService.findById(merchantID);

        if(targetMerchant == null) return "Invalid Merchant ID";

        int violationCount = 0;
        for (MerchantStock ms : merchantStockRepository.findAll()){
            if(!ms.getMerchant_id().equals(merchantID)) continue;

            Product product = productService.findById(ms.getProduct_id());



            if (product == null) continue;

            ArrayList<Integer> matchingProductIDs = new ArrayList<>();

            for (Product p : productService.getAllProducts()){
                if(p.getID().equals(product.getID())){
                    matchingProductIDs.add(p.getID());
                }
            }

            int productCount = 0;
            double totalPrice = 0;

            for (MerchantStock otherStock : merchantStockRepository.findAll()){
                if(matchingProductIDs.contains(otherStock.getProduct_id())){
                    Product otherProduct = null;
                    for (Product p : productService.getAllProducts()){
                        if(otherStock.getProduct_id().equals(p.getID())){
                            otherProduct = p;
                            break;
                        }

                    }
                    if(otherProduct != null){
                        double price = otherStock.is_clearance() ? otherStock.getClearance_price() : otherProduct.getPrice();
                        totalPrice += price;
                        productCount++;
                    }
                }
            }

            if(productCount == 0) continue;

            double avgPrice = totalPrice/productCount;
            double thisPrice = ms.is_clearance() ? ms.getClearance_price() : product.getPrice();

            if(thisPrice > 1.5 * avgPrice){
                violationCount++;
            }


        }

        if (violationCount >= 5) {
            targetMerchant.setSuspended(true);
            merchantService.saveMerchant(targetMerchant);

            for (MerchantStock ms : merchantStockRepository.findAll()) {
                if (merchantID.equals(ms.getMerchant_id())) {
                    ms.setStock(0);
                    merchantStockRepository.save(ms);
                }
            }

            return "Merchant " + targetMerchant.getName()
                    + " has been suspended due to " + violationCount + " pricing violations.";
        }

        return "Merchant " + targetMerchant.getName()
                + " has " + violationCount + " pricing violations. Not suspended.";
    }
//
//
//
//
//
    public boolean isUserAdmin(Integer ID){
        User user = userService.findById(ID);
        return user.getRole().equalsIgnoreCase("admin");
    }


//    //Helper Methods



    public boolean checkIfMerchantFound(Integer ID){
        for (Merchant m : merchantService.getAllMerchants()){
            if(ID.equals(m.getID()))
                return false;
        }
        return true;
    }

    public boolean checkIfMerchantHasProductsListed(Integer merchantID){
        for (MerchantStock m : merchantStockRepository.findAll()){
            if(m.getMerchant_id().equals(merchantID))
                return false;
        }
        return true;
    }


    public boolean checkIfProductInStock(Integer productID, Integer merchantID){
        for (MerchantStock m : merchantStockRepository.findAll()){
            if(m.getProduct_id().equals(productID) && m.getMerchant_id().equals(merchantID)){
                return false;
            }
        }
        return true;
    }
//


}
