package com.sparta.week05.models;

import com.sparta.week05.dto.ItemDto;
import com.sparta.week05.dto.ProductMypriceRequestDto;
import com.sparta.week05.dto.ProductRequestDto;
import com.sparta.week05.utils.Timestamped;
import com.sparta.week05.utils.URLValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity                                             // DB 테이블 역할
@Getter                                             // get 함수를 일괄적으로 만들어줌
@Setter
@NoArgsConstructor                                  // 기본 생성자를 만들어줌
public class Product extends Timestamped {

    // ID 자동으로 생성 및 증가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //반드시 값을 가지도록 함
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany
    private List<Folder> folderList;

//    public Product(ProductRequestDto productRequestDto) {
//        this.title = productRequestDto.getTitle();
//        this.link = productRequestDto.getLink();
//        this.image = productRequestDto.getImage();
//        this.lprice = productRequestDto.getLprice();
//        this.myprice = 0;
//    }
    // 관심상품 생성 시 이용
    public Product(ProductRequestDto productRequestDto, Long userId) {
        // 입력값 Validation
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("회원 Id가 유효하지 않습니다.");
        }

        if (productRequestDto.getTitle() == null || productRequestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("저장할 수 있는 상품명이 없습니다.");
        }

        if (!URLValidator.urlValidator(productRequestDto.getImage())) {
            throw new IllegalArgumentException("상품 이미지 URL 포맷이 맞지 않습니다.");
        }

        if (!URLValidator.urlValidator(productRequestDto.getLink())) {
            throw new IllegalArgumentException("상품 최저가 페이지 URL 포맷이 맞지 않습니다.");
        }

        if (productRequestDto.getLprice() <= 0) {
            throw new IllegalArgumentException("상품 최저가가 0 이하입니다.");
        }

        this.userId = userId;
        this.title = productRequestDto.getTitle();
        this.link = productRequestDto.getLink();
        this.image = productRequestDto.getImage();
        this.lprice = productRequestDto.getLprice();
        this.myprice = 0;
    }

    public void updateMyprice(int price) {
        this.myprice = price;
    }

    public void updateByItemDto(ItemDto itemDto){
        this.lprice = itemDto.getLprice();
    }

    public void addFolder(Folder folder) {
        this.folderList.add(folder);
    }
}
