package com.sparta.springcore02.service;

import com.sparta.springcore02.model.Product;
import com.sparta.springcore02.dto.ProductMypriceRequestDto;
import com.sparta.springcore02.dto.ProductRequestDto;
import com.sparta.springcore02.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    // 멤버 변수 선언
    private final ProductRepository productRepository;

    // 생성자: ProductSephrosrvice() 가 생성될 때 호출됨
    @Autowired
    public ProductService(ProductRepository productRepository) {
        // 멤버 변수 생성
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(Long userId) {
        // 멤버 변수 사용
        return productRepository.findAllByUserId(userId);
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
}