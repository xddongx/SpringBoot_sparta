package com.sparta.week05.utils;

import com.sparta.week05.service.ProductService;
import com.sparta.week05.dto.ItemDto;
import com.sparta.week05.models.Product;
import com.sparta.week05.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component                                      // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
@RequiredArgsConstructor                        // final 멤버 변수를 자동으로 생성합니다.
public class Scheduler {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final com.sparta.week05.utils.NaverShopSearch naverShopSearch;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")
    public void updatePrice() throws InterruptedException {
        System.out.println("가격 업데이트 실행");
        // 저장된 모든 관심 상품을 조회합니다.
        List<Product> productList = productRepository.findAll();

        for (int i=0; i < productList.size(); i++){
            TimeUnit.SECONDS.sleep(1);                           // 1초에 한 삼품씩 조회합니다.()
            Product p = productList.get(i);                             // i 번째 관심 상품을 꺼냅니다.
            String title = p.getTitle();
            String resultString = naverShopSearch.search(title);        // i 번째 관심 삼품의 제목으로 검색을 실행합니다.
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);  // i 번째 관심 상품의 겸색 결과 목록 중에서 첫 번째 결과를 꺼냅니다.
            ItemDto itemDto = itemDtoList.get(0);
            Long id = p.getId();                                        // i 번째 관심 상품 정보를 업데이트 합니다.
            productService.updateBySearch(id, itemDto);
        }

    }
}
