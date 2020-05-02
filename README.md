# coupon Rest API

## 개발 환경
- SPRING BOOT  
- MYSQL  
- MYBATIS  

## 문제 해결 전략 
### TABLE 구성
**테이블 명 : TN_COUPON**  
| COUPON_ID (PK) | SEND_DT | EXPIRE_DT | STATE_SE |  
| --- | --- | --- | --- |
| 202005021111CP00000223 | 2020-05-02 | 2020-06-01 | S |
| 202005021725CP00009855 |  |  | N |

- COUPON_ID : 202005021111CP00000223 (YYYYMMDDHHII + CP + 랜덤8자리)  
- STATE_SE : U(사용), S(전송완료), N(DEFAULT)  
  
### API 설명
1. Q) 랜덤한 코드 N개를 생성하여 DB에 저장  
A) 프로시저를 통해 N개만큼 코드를 생성하여 적재하도록 함  
  
2. Q) 생성된 쿠폰 중 사용자에게 1개 지급  
A) 사용하지 않은 쿠폰 조회 -> 사용자에게 지급.  
   > SEND_DT(오늘), EXPIRE_DT(한달 뒤), STATE_SE(S:전송)  
  
3. Q) 사용자에게 지급된 쿠폰 조회  
A) 사용자에게 지급되었으며 사용하지 않은 쿠폰 조회.  
   > STATE_SE(S:전송)  
  
4. Q) 지급된 쿠폰 사용 처리 (재사용 불가)  
A) 입력된 COUPON_ID 값이 유효한지 확인 -> 유효 시 쿠폰 사용 처리.  
   > STATE_SE(U)  
  
5. Q) 지급된 쿠폰 취소 서리 (재사용 가능)  
A) 입력된 COUPON_ID 값이 유효한지 확인 -> 유효 시 쿠폰 취소 처리.  
   > SEND_DT(null), EXPIRE_DT(null), STATE_SE(N)  
  
6. Q) 발급된 쿠폰 중 만료된 쿠폰  
A) 지급되고 사용하지 않은 쿠폰 중 EXPIRE_DT가 오늘인 쿠폰 ID 조회.  
  
7. Q) 발급된 쿠폰 중 만료일3일전 사용자에게 메시지를 발송  
A) 스케쥴 사용하여 매일 16:10분 수행 -> 지급되고 사용하지 않은 쿠폰 중 만료일 3일 남은 사용자에게 알림 전송  
  
## 빌드 및 실행 방법
### 환경 셋팅
### 1.DB MYSQL 설치  
   **스키마 생성**
```
username : root
pwd : 1234
스키마 : test
```
   **테이블 생성**
```
CREATE TABLE `tn_coupon` (  
  `coupon_id` varchar(22) NOT NULL,  
  `send_dt` date DEFAULT NULL,  
  `expire_dt` date DEFAULT NULL,  
  `state_se` char(1) DEFAULT 'N',  
  PRIMARY KEY (`coupon_id`)  
)
```
   **프로시저**
```
DELIMITER $$  
	DROP PROCEDURE IF EXISTS addCoupon$$  
	CREATE PROCEDURE addCoupon(IN i INT)  
	BEGIN  
		DECLARE x INT;  
		SET x = 0;  
		WHILE x < i DO  
			SET x = x + 1;  
  
			SELECT @S_COUPON_ID := CONCAT(date_format(now(), '%Y%m%d%H%i') ,'CP',  
				LPAD(CAST(CAST(rand()*10000 as unsigned) as char), 8, '0')) AS COUPONID  
			FROM DUAL;  
  
			INSERT INTO TN_COUPON (COUPON_ID, STATE_SE) VALUES (@S_COUPON_ID,'N');  
			COMMIT;  
		END WHILE;  
	END$$  
DELIMITER;  
```
  
### 2.소스 다운 
> git clone https://github.com/jeonhwajeong/couponRestAPI
  
### 3.SPRING BOOT 설치  
   다운 받은 소스 OPEN : File > >
   수행 : **프로젝트 클릭 > Run As>Spring Boot App**  

### 3.POSTMAN 설치
   Chrome App 클릭 > POSTMAN 설치 > POSTMAN 수행 > GET/POST/PUT 설정 >  URL 입력 > SEND ! 
