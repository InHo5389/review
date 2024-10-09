# 리뷰 기술 과제 

## **비즈니스 요구 사항**

- 리뷰는 존재하는 상품에만 작성할 수 있습니다.
- 유저는 하나의 상품에 대해 하나의 리뷰만 작성 가능합니다.
- 유저는 1~5점 사이의 점수와 리뷰를 남길 수 있습니다.
- 사진은 선택적으로 업로드 가능합니다.
    - 사진은 S3 에 저장된다고 가정하고, S3 적재 부분은 dummy 구현체를 생성합니다. 
    (실제 S3 연동을 할 필요는 없습니다.)
- 리뷰는 '가장 최근에 작성된 리뷰' 순서대로 조회합니다.

## **기술적 요구 사항**

- Mysql 조회 시 인덱스를 잘 탈 수 있게 설계해야 합니다.
- 상품 테이블에 reviewCount 와 score 가 잘 반영되어야 한다.
- (Optional) 동시성을 고려한 설계를 해주세요. 많은 유저들이 동시에 리뷰를 작성할 때, 발생할 수 있는 문제를 고려해보세요.
- (Optional) 테스트 코드를 작성하면 좋습니다.

## **고려하지 않아도 되는 사항**

회원 가입, 로그인은 고려하지 않습니다.

- 요청 DTO 로 유저 아이디를 받고, 요청 DTO 로 들어온 유저는 항상 유효하다고 생각합니다.

상품 테이블은 주어진 명세대로 사용한다. (추가 필드를 고려하지 않는다)

```sql
CREATE TABLE IF NOT EXISTS `Product` (
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `reviewCount` BIGINT(20) NOT NULL,
    `score`       FLOAT  NOT NULL
) ENGINE = InnoDB CHARSET = utf8;
```

## **API 스펙**

### 리뷰 조회 API

- GET  /products/{productId}/reviews?cursor={cursor}&size={size}

### 리뷰 등록 API

- POST  /products/{productId}/reviews
