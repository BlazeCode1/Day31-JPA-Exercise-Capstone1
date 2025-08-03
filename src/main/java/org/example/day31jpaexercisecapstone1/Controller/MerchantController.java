package org.example.day31jpaexercisecapstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Api.ApiResponse;
import org.example.day31jpaexercisecapstone1.Model.Merchant;
import org.example.day31jpaexercisecapstone1.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors err){
        if(err.hasErrors()){
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }

        merchantService.addMerchant(merchant);
        return ResponseEntity.ok(new ApiResponse("Merchant Added Successfully"));
    }

    @PutMapping("/update/{ID}")
    public ResponseEntity<?> updateMerchant(@PathVariable Integer ID,@Valid @RequestBody Merchant merchant,Errors err){
        if(err.hasErrors()){
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }
        Boolean isUpdated = merchantService.updateMerchant(ID,merchant);
        if(isUpdated){
            return ResponseEntity.ok(new ApiResponse("Merchant Updated Successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Cannot Find Given Category ID"));
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteMerchant(@PathVariable Integer ID){
        if(merchantService.deleteMerchant(ID)){
            return ResponseEntity.ok(new ApiResponse("Merchant Deleted Successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Merchant ID Not Found"));

    }

}
