-- 관리자 계정
-- 데이터베이스의 생성과 관리를 담당하는 슈퍼 유저 계정
-- 데이터베이스에 대한 모든 권한과 책임을 가지는 계정

-- 사용자 계정(일반 계정)
-- 데이터베이스에 대하여 질의(Query), 갱신, 보고서 작성 등의 작업을 수행할 수 있는 계정
-- 보안을 위해 업무에 필요한 최소한의 권한만 가지는 것을 원칙으로 함
CREATE USER KH IDENTIFIED BY KH;
--      사용자 계정 이름       사용자 계정 비밀번호
GRANT RESOURCE, CONNECT TO KH;

--  테이블 : 데이터베이스 내에서 모든 데이터는 테이블에 저장
--          행과 칼럼으로 구성되는 가장 기본적인 데이터베이스 객체

CREATE USER MEMBER IDENTIFIED BY MEMBER;
GRANT RESOURCE, CONNECT TO MEMBER;


CREATE USER BOARD IDENTIFIED BY BOARD;
GRANT RESOURCE, CONNECT TO BOARD;

REVOKE RESOURCE, CONNECT FROM BOARD;
DROP USER BOARD;