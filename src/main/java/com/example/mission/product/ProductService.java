package com.example.mission.product;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * ✅ 삭제 규칙
     * - ADMIN: 어떤 상품이든 삭제 가능
     * - USER: 본인(owner_username) 상품만 삭제 가능
     */
    @Transactional
    public void deleteProduct(long productId, Authentication authentication) {

        String owner = productRepository.findOwnerById(productId);
        if (owner == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다. id=" + productId);
        }

        String loginUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        // ADMIN이면 무조건 삭제
        if (isAdmin) {
            productRepository.deleteById(productId);
            return;
        }

        // USER면 본인 것만
        if (!owner.equals(loginUsername)) {
            throw new AccessDeniedException("본인 상품만 삭제할 수 있습니다.");
        }

        productRepository.deleteById(productId);
    }
}