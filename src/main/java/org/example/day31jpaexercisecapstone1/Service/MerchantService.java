package org.example.day31jpaexercisecapstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Model.Merchant;
import org.example.day31jpaexercisecapstone1.Repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository merchantRepository;
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    public void addMerchant(Merchant merchant) {
    merchantRepository.save(merchant);
    }


    public Boolean updateMerchant(Integer ID,Merchant merchant) {
        Merchant oldMerchant = merchantRepository.getById(ID);
        if(oldMerchant == null){
            return false;
        }
        oldMerchant.setName(merchant.getName());
        merchantRepository.save(oldMerchant);
        return true;
    }

    public Boolean deleteMerchant(Integer ID) {
        Merchant merchant = merchantRepository.getById(ID);
        if(merchant==null)
            return false;
        merchantRepository.delete(merchant);
        return true;
    }

    public Merchant findById(Integer ID){
        return merchantRepository.getById(ID);
    }

    public void saveMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }






}
