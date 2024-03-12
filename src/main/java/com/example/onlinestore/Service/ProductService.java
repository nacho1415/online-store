package com.example.onlinestore.Service;

import com.example.onlinestore.DTO.Post.CreateProductDto;
import com.example.onlinestore.DTO.Post.UpdateProductDto;
import com.example.onlinestore.Domain.Product;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.JWT.JwtTokenProvider;
import com.example.onlinestore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

//    private void validateCreateProductDto (CreateProductDto createProductDto) {
//        if (createProductDto.getName() != null && createProductDto.getName().length() <= 0) {
//            throw new IllegalArgumentException("name is valid");
//        }
//        if (createProductDto.getDescription() != null && createProductDto.getDescription().length() <= 0) {
//
//        }
//        if (createProductDto.getQuantity() != null && createProductDto.getQuantity() <= 0) {
//
//        }
//        if (createProductDto.getPrice() != null && createProductDto.getPrice() <= 0) {
//
//        }
//    }

    @Transactional
    public Product createProduct (User user, CreateProductDto createProductDto) throws Exception {
        try {
            productRepository.duplicateCheck(createProductDto);
            Product product = new Product();
            System.out.println(user + "확인1111111111111");
            product.setUser(user);
            product.setName(createProductDto.getName());
            product.setDescription(createProductDto.getDescription());
            product.setPrice(createProductDto.getPrice());
            product.setQuantity(createProductDto.getQuantity());
            productRepository.save(product);
            return product;
        } catch (Exception ex) {
            throw new Exception("fail reason = " + ex.getMessage());
        }
    }


    @Transactional
    public void updateProduct (UpdateProductDto updateProductDto) throws Exception {
        try {
            System.out.println(updateProductDto + "파라미터 체크");
            // 파라미터가 모두 Null인지 검사
            productRepository.updateProductAllNullCheck(updateProductDto);

            // 업데이트 내용이 올바른 입력인지 검사
            productRepository.updateProductValidationCheck(updateProductDto);

            System.out.println(updateProductDto.getId() + "확인");

            // 업데이트 내용이 있는것들 변경
            Product product = productRepository.findProductById(updateProductDto.getId());
            productRepository.updateProductWithChanges(product, updateProductDto);

        } catch (Exception ex) {
            throw new Exception("fail reason = " + ex.getMessage());
        }
    }

    public void deleteProduct (Long userId, Long productId) throws Exception {
        try {
            if (productRepository.isProductOwner(userId, productId) == false) {
                throw new IllegalArgumentException("상품의 주인이 아닙니다");
            }
            productRepository.deleteProduct(productId);
        } catch (Exception ex) {
            throw new Exception("fail reason = " + ex.getMessage());
        }
    }
    public void searchProduct () {

    }
}
