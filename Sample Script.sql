-- 테이블 삭제 구문
DROP TABLE MEMBER;


-- 시퀀스 삭제 구문
DROP SEQUENCE SEQ_USERNO;





CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,                  -- 회원번호
    USERID VARCHAR2(15) NOT NULL UNIQUE,        -- 회원아이디
    USERPWD VARCHAR2(20) NOT NULL,              -- 회원 비밀번호
    USERNAME VARCHAR2(20) NOT NULL,             -- 회원 이름
    GENDER CHAR(1) CHECK(GENDER IN ('M','F')),  -- 성별
    AGE NUMBER,                                 -- 나이
    EMAIL VARCHAR2(30),                         -- 이메일
    PHONE CHAR(11),                             -- 전화번호
    ADDRESS VARCHAR2(100),                      -- 주소
    HOBBY VARCHAR2(50),                         -- 취미
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL    -- 회원가입일
    
);

CREATE SEQUENCE SEQ_USERNO
NOCACHE;

INSERT INTO MEMBER 
VALUES(SEQ_USERNO.NEXTVAL, 'admin', '1234', '관리자', 'M', 45,'admin@iei.or.kr', '01012345555', '서울', null, '2021-07-27');

INSERT INTO MEMBER 
VALUES(SEQ_USERNO.NEXTVAL, 'user01', 'pass01', '홍길동', null, 23,'user01@iei.or.kr', '01022223333', '부산', '등산,영화보기' , '2021-08-02');

COMMIT;

SELECT * FROM MEMBER;

CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(20),
    TDATE DATE
);

SELECT * FROM TEST;

















