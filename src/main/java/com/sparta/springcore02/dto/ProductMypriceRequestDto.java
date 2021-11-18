package com.sparta.springcore02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
//컴퓨터가 allargs를 만들어주므로 noargs는 쓸 필요없다.
@Getter
public class ProductMypriceRequestDto {
    private int myprice;
}
