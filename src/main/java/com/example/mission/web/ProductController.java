package com.example.mission.product;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    // 목록(검색 포함)
    @GetMapping("/product/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("products", productRepository.findAll(keyword));
        model.addAttribute("keyword", keyword);
        return "product/list"; // ✅ templates/product/list.html
    }

    // 등록 폼
    @GetMapping("/product/add")
    public String addForm() {
        return "product/add"; // ✅ templates/product/add.html
    }

    // 등록 처리
    @PostMapping("/product/add")
    public String add(@RequestParam String name,
                      @RequestParam int price,
                      Authentication authentication) {
        String loginUsername = authentication.getName();
        productRepository.save(name, price, loginUsername);
        return "redirect:/product/list";
    }

    // 삭제 처리 (권한/소유자 검증은 서비스에서)
    @PostMapping("/product/delete/{id}")
    public String delete(@PathVariable("id") long id, Authentication authentication) {
        productService.deleteProduct(id, authentication);
        return "redirect:/product/list";
    }
}