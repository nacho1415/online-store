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

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProductRepository productRepository;


    @Transactional
    public Like addLikeProduct (User user, AddLikeProductDto addLikeProductDto) throws Exception {
        try {
            Like like = new Like();
            // 상품이 있는지 체크
            Product product = productRepository.findProductById(addLikeProductDto.getProductId());
            // 이미 좋아요가 되어있는지 체크
            System.out.println("어디서냐");
            likeRepository.findLikeById(addLikeProductDto.getProductId(), user.getId());
            System.out.println("어디서냐44");
            like.setProduct(product);
            like.setUser(user);
            likeRepository.save(like);
            System.out.println("어디서냐555");
            return like;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void cancelLikeProduct (long userId, long likeId) throws Exception {
        try {
            Like findLike = likeRepository.findLikeByLikeId(likeId);
            likeRepository.deleteLikeById(findLike.getId());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
