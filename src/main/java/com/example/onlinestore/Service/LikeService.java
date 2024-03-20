package com.example.onlinestore.Service;

import com.example.onlinestore.DTO.Like.AddLikeProductDto;
import com.example.onlinestore.Domain.Like;
import com.example.onlinestore.Domain.Product;
import com.example.onlinestore.Domain.User;
import com.example.onlinestore.Repository.LikeRepository;
import com.example.onlinestore.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProductRepository productRepository;


    public List<Product> findAllProduct () {
        return productRepository.findAllProduct();
    }

    @Transactional
    public Like addLikeProduct (User user, AddLikeProductDto addLikeProductDto) throws Exception {
        try {
            Like like = new Like();
            // 상품이 있는지 체크
            Product product = productRepository.findProductById(addLikeProductDto.getProductId());
            // 이미 좋아요가 되어있는지 체크
            System.out.println("어디서냐");
            Like findLike = likeRepository.findLikeById(addLikeProductDto.getProductId(), user.getId());
            if (findLike != null) {
                throw new Exception("이미 라이크한 상품입니다");
            }
            System.out.println("어디서냐44");

            product.setLikeCount(product.getLikeCount() + 1);
            // 좋아요숫자 증가

            like.setProduct(product);
            like.setUser(user);
            likeRepository.save(like);
            System.out.println("어디서냐555");
            return like;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public void cancelLikeProduct (long userId, long productId) throws Exception {
        try {
            System.out.println(userId + "///" + productId);
            Like like = likeRepository.findLikeById(productId, userId);
            Product product = productRepository.findProductById(productId);
            product.setLikeCount(product.getLikeCount() - 1);
            likeRepository.deleteLikeById(like.getId());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void LikeCheck (long userId, long productId) throws Exception {
        try {
            Like like = likeRepository.findLikeById(productId, userId);
            System.out.println("likeCheck " + like);
            System.out.println(userId + "qqqq" + productId);
            if (like != null) {
                throw new Exception("이미 좋아요한 유저입니다");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
