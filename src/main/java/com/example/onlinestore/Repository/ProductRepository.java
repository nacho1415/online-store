package com.example.onlinestore.Repository;

import com.example.onlinestore.DTO.Post.CreateProductDto;
import com.example.onlinestore.DTO.Post.UpdateProductDto;
import com.example.onlinestore.DTO.User.SignUpDto;
import com.example.onlinestore.Domain.Product;
import com.example.onlinestore.Domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductRepository {

    @PersistenceContext
    EntityManager em;

    public Product findProduct (Long id) {
        return em.createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Product> findAllProduct () {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public void duplicateCheck (CreateProductDto createProductDto) {
        Long EmailDuplicateCount = em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.name = : name", Long.class)
                .setParameter("name", createProductDto.getName())
                .getSingleResult();
        if (EmailDuplicateCount > 0) {
            throw new IllegalArgumentException("name is duplicated");
        }
    }

    public void updateProductAllNullCheck (UpdateProductDto updateProductDto) {
        if (updateProductDto.getName() == null && updateProductDto.getDescription() == null && updateProductDto.getQuantity() == null && updateProductDto.getPrice() == null) {
            throw new IllegalArgumentException("업데이트하려는 내용이 누락되었습니다.");
        }
    }

    public void updateProductValidationCheck (UpdateProductDto updateProductDto) {
        if (updateProductDto.getId() == null) {
            throw new IllegalArgumentException("상품 번호가 지급되지 않았습니다");
        }
        if (updateProductDto.getPrice() != null && (updateProductDto.getPrice() < 0 || updateProductDto.getPrice() > 10000000)) {
            throw new IllegalArgumentException("올바른 값을 입력해주십시오");
        }
        if (updateProductDto.getDescription() != null && updateProductDto.getDescription().length() > 500) {
            throw new IllegalArgumentException("설명이 제한 용량을 초과했습니다.");
        }
        if (updateProductDto.getName() != null && (updateProductDto.getName().length() == 0 || updateProductDto.getName().length() > 20)) {
            throw new IllegalArgumentException("올바른 이름을 사용하여 주십시오");
        }
        if (updateProductDto.getQuantity() != null && updateProductDto.getQuantity() > 50000) {
            throw new IllegalArgumentException("상품 이름이 유효하지 않습니다.");
        }
    }

    public Product findProductById (Long id) {
        try {
            System.out.println(id + "확인111111111" + id);
            if (id == null) {
                throw new IllegalArgumentException("상품 아이디가 null입니다");
            }
            Product product = em.createQuery("SELECT p FROM Product p WHERE p.Id = :id", Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return product;
        } catch (Exception exception) {
            throw new IllegalArgumentException("상품 아이디와 일치하는 상품이 존재하지 않는다");
        }
    }

    @Transactional
    public void deleteProduct (Long id) {
        try {
            System.out.println(id + "확인용");
            em.createQuery("DELETE FROM Product p WHERE p.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new IllegalArgumentException("프로덕트를 삭제하는도중 오류가 발생했습니다");
        }
    }

    public boolean isProductOwner (Long userId, Long productId) {
        try {
            Product product = findProductById(productId);
            if (product.getUser().getId() == userId) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("상품의 주인인지 판별하는 도중 오류가 발생하였습니다");
        }
    }

    public Product updateProductWithChanges (Product product, UpdateProductDto updateProductDto) {
        if (updateProductDto.getName() != null) product.setName(updateProductDto.getName());
        if (updateProductDto.getDescription() != null) product.setDescription(updateProductDto.getDescription());
        if (updateProductDto.getPrice() != null) product.setPrice(updateProductDto.getPrice());
        if (updateProductDto.getQuantity() != null) product.setQuantity(updateProductDto.getQuantity());
        save(product);
        return product;
    }

    public void save (Product product) {
        em.persist(product);
    }
}
