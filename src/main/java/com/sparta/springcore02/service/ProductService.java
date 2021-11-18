package com.sparta.springcore02.service;

import com.sparta.springcore02.model.Product;
import com.sparta.springcore02.dto.ProductMypriceRequestDto;
import com.sparta.springcore02.dto.ProductRequestDto;
import com.sparta.springcore02.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    // 멤버 변수 선언
    private final ProductRepository productRepository;

    // 생성자: ProductSephrosrvice() 가 생성될 때 호출됨
//    @Autowired
//    public ProductService(ProductRepository productRepository) {
//        // 멤버 변수 생성
//        this.productRepository = productRepository;
//    }
    //회원 id로 등록된 모든 상품 조회 / 몇페이지/페이지에 몇개씩뿌러주나 / 어떤 기준 / 오름차순내림차순
    public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        //결국 페이징 할려면 pageable 만들어야한다.
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllByUserId(userId, pageable);
    }

    public Product createProduct(ProductRequestDto requestDto,Long userId){
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("해당 아이디가존재하지 않습니다.")
        );
        int myPrice = requestDto.getMyprice();
        product.updateMyPrice(myPrice);
        product.setMyprice(myPrice);
        return product;
    }
    // 모든 상품 조회 (관리자용)
    public Page<Product> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable);
    }

}