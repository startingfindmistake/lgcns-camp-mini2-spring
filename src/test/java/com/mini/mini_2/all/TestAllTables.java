package com.mini.mini_2.all;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mini.mini_2.facility.domain.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.domain.dto.FacilityResponseDTO;
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.facility.repository.FacilityRepository;
import com.mini.mini_2.favorite.domain.dto.FavoriteRequestDTO;
import com.mini.mini_2.favorite.domain.dto.FavoriteResponseDTO;
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.favorite.repository.FavoriteRepository;
import com.mini.mini_2.food.domain.dto.FoodRequestDTO;
import com.mini.mini_2.food.domain.dto.FoodResponseDTO;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.food.repository.FoodRepository;
import com.mini.mini_2.rest_area.domain.dto.RestAreaRequestDTO;
import com.mini.mini_2.rest_area.domain.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.repository.RestAreaRepository;
import com.mini.mini_2.review.domain.dto.ReviewRequestDTO;
import com.mini.mini_2.review.domain.dto.ReviewResponseDTO;
import com.mini.mini_2.review.domain.entity.ReviewEntity;
import com.mini.mini_2.review.repository.ReviewRepository;
import com.mini.mini_2.user.domain.dto.UserRequestDTO;
import com.mini.mini_2.user.domain.dto.UserResponseDTO;
import com.mini.mini_2.user.domain.entity.UserEntity;
import com.mini.mini_2.user.repository.UserRepository;


@SpringBootTest
// @Transactional
public class TestAllTables {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestAreaRepository restAreaRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    @Test
    public void test () {
        
        // user
        UserRequestDTO userRequest = UserRequestDTO.builder()
                                            .userEmail("minu@naver.com")
                                            .userNickname("holymoly")
                                            .password("1234")
                                            .build();
                                            
        System.out.println("user request : " + userRequest);
                                            

        UserEntity userEntity = userRepository.save(userRequest.toEntity());
        UserResponseDTO userResponse = UserResponseDTO.fromEntity(userEntity);       
        
        System.out.println("user entity : " +  userEntity);
        System.out.println("user dto : " +  userResponse);

        
        // restarea
        RestAreaRequestDTO restAreaRequest = RestAreaRequestDTO.builder()
                                                               .name("서울휴게소")
                                                               .direction("상행")
                                                               .code("000001")
                                                               .tel("01012345678")
                                                               .address("경기도 어쩌구 저쩌구")
                                                               .routeName("비내리는 호남선")
                                                               .build();
                                                               
        System.out.println("rest area request : " + restAreaRequest);
                                                               
        RestAreaEntity restAreaEntity = restAreaRepository.save(restAreaRequest.toEntity());
        RestAreaResponseDTO restAreaResponse = RestAreaResponseDTO.fromEntity(restAreaEntity);
        System.out.println("rest area entity : " + restAreaEntity);
        System.out.println("rest area dto : " + restAreaResponse);
        
        // food
        FoodRequestDTO foodRequest = FoodRequestDTO.builder()
                                                      .foodName("치킨")
                                                      .restAreaId(restAreaResponse.getRestAreaId())
                                                      .price("1000")
                                                      .isSignature("true")
                                                      .description("겉바속촉")
                                                      .build();
        FoodEntity foodEntity = foodRepository.save(foodRequest.toEntity(restAreaEntity));
        FoodResponseDTO foodResponse = FoodResponseDTO.fromEntity(foodEntity);
        System.out.println("food entity : " + foodEntity);
        System.out.println("food dto : " + foodResponse);
        
        // facility
        FacilityRequestDTO facilityRequest = FacilityRequestDTO.builder()
                                                               .name("전기차충전소")
                                                               .restAreaId(restAreaResponse.getRestAreaId())
                                                               .description("20분에 풀충")
                                                               .build();
        FacilityEntity facilityEntity = facilityRepository.save(facilityRequest.toEntity(restAreaEntity));
        FacilityResponseDTO facilityResponse = FacilityResponseDTO.fromEntity(facilityEntity);
        System.out.println("facility entity : " + facilityEntity);
        System.out.println("facility dto : " + facilityResponse);
        
        // favorite
        FavoriteRequestDTO favoriteRequest = FavoriteRequestDTO.builder()
                                                               .userId(userResponse.getUserId())
                                                               .restAreaId(restAreaResponse.getRestAreaId())
                                                               .description("호두과자가 기막힘")
                                                               .build();
        FavoriteEntity favoriteEntity = favoriteRepository.save(favoriteRequest.toEntity(userEntity, restAreaEntity));
        FavoriteResponseDTO favoriteResponse = FavoriteResponseDTO.fromEntity(favoriteEntity);
        System.out.println("favorite entity : " + favoriteEntity);
        System.out.println("favorite dto : " + favoriteResponse);
        
        // review
        ReviewRequestDTO reviewRequest = ReviewRequestDTO.builder()
                                                         .userId(userResponse.getUserId())
                                                         .restAreaId(restAreaResponse.getRestAreaId())
                                                         .rating("4.5")
                                                         .comment("아주 편해용")
                                                         .build();
        ReviewEntity reviewEntity = reviewRepository.save(reviewRequest.toEntity(userEntity, restAreaEntity));
        ReviewResponseDTO reviewResponse = ReviewResponseDTO.fromEntity(reviewEntity);
        System.out.println("review entity : " + reviewEntity);
        System.out.println("review dto : " + reviewResponse);
    }
    
    
}
