package com.sparta.springcore02.controller;

import com.sparta.springcore02.model.Product;
import com.sparta.springcore02.model.User;
import com.sparta.springcore02.model.UserRole;
import com.sparta.springcore02.repository.UserRepository;
import com.sparta.springcore02.security.UserDetailsImpl;
import com.sparta.springcore02.service.ProductService;
import com.sparta.springcore02.dto.ProductMypriceRequestDto;
import com.sparta.springcore02.dto.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
    // 멤버 변수 선언
    private final ProductService productService;
    private final UserRepository userRepository;

    // 로그인한 회원이 등록한 상품들 조회
    @GetMapping("/api/products")
    public Page<Product> getProducts(
            //jpa 사용하면 Page 라는 객체 제공
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        page = page - 1;
        return productService.getProducts(userId, page , size, sortBy, isAsc);
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    //@AuthenticationPrincipal는 스프링 시큐리티가 보내줬던 userDetails 정보!
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)  {
        //로그인 되어있는 ID
        Long userId = userDetails.getUser().getId(); //테이블의 ID!
        Product product = productService.createProduct(requestDto, userId); //테이블 Id임 userId를 변수 지정하지않고 userDetails.getUser().getID() 로 바로 가능.
        // 응답 보내기
        return product;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto)  {
        Product product = productService.updateProduct(id, requestDto);
        return product.getId();
    }

    // (관리자용) 등록된 모든 상품 목록 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/api/admin/products")
    public Page<Product> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return productService.getAllProducts(page , size, sortBy, isAsc);
    }
}