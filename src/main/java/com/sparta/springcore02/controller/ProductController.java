package com.sparta.springcore02.controller;

import com.sparta.springcore02.model.Product;
import com.sparta.springcore02.security.UserDetailsImpl;
import com.sparta.springcore02.service.ProductService;
import com.sparta.springcore02.dto.ProductMypriceRequestDto;
import com.sparta.springcore02.dto.ProductRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
    // 멤버 변수 선언
    private final ProductService productService;

    // 생성자: ProductController() 가 생성될 때 호출됨
    @Autowired
    public ProductController(ProductService productService) {
        // 멤버 변수 생성
        this.productService = productService;
    }

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return productService.getProducts(userId);
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    //@AuthenticationPrincipal는 스프링 시큐리티가 보내줬던 userDetails 정보!
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)  {
        //로그인 되어있는 ID
        Long userId = userDetails.getUser().getId(); //테이블의 ID!
        Product product = productService.createProduct(requestDto, userId); //테이블 Id임
        // 응답 보내기
        return product;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto)  {
        Product product = productService.updateProduct(id, requestDto);
        return product.getId();
    }

    //관리자용 등록된 상품 모두 조회
    @GetMapping("/api/admin/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}