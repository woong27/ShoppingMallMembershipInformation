drop database if exists MusinsaDB;
create database MusinsaDB;
drop table if exists Musinsa;
use MusinsaDB;

create table musinsa(
	no char(4) not null,
	name char(4) not null,
	age int not null,
	phone char(15) not null,
	clothing int not null,
	acc int not null,
	sport int not null,
	pet int not null,
	total int null,
	grade char(10),
	save char(2),
	DC char(2),
    constraint pk_musinsa_no primary key (no)
);

select * from musinsa;
-- select * from musinsa where name = '장건웅';

-- update musinsa set name = '장건웅' where no = '0111';

delete from musinsa where name = '장건웅';
delete from musinsa where no = '0111';

select * from musinsa order by no desc;
select * from musinsa where total = (select max(total) from musinsa);
select max(total) from musinsa;
-- 프로시저 
drop procedure if exists procedure_update_musinsa;

delimiter //
create procedure procedure_insert_musinsa(
	IN in_no char(4),
    IN in_name char(4),
    IN in_age int,
    IN in_phone char(15),
    IN in_clothing int,
    IN in_acc int,
    IN in_sport int,
    IN in_pet int
)
begin
	-- 변수선언
	DECLARE in_total int default 0;
	DECLARE in_grade char(10) default null;
	DECLARE in_save char(2) default null;
	DECLARE in_dc char(2) default null;
    -- 총점계산
    SET in_total = in_clothing + in_acc + in_sport + in_pet;
    SET in_grade = 
		CASE
			WHEN in_total >= 2000000 THEN 'Diamond'
			WHEN in_total >= 1000000 THEN 'Platinum'
			WHEN in_total >= 500000 THEN 'Gold'
            WHEN in_total >= 200000 THEN 'Silver'
            WHEN in_total >= 100000 THEN 'Bronze'
            WHEN in_total >= 10000 THEN 'Member'
            WHEN in_total >= 2000 THEN 'Rookie'
            ELSE 'Newbie'
		END;
	SET in_save = 
		CASE
			WHEN in_total >= 2000000 THEN '4%'
			WHEN in_total >= 1000000 THEN '3%'
			WHEN in_total >= 500000 THEN '3%'
            WHEN in_total >= 200000 THEN '2%'
            WHEN in_total >= 100000 THEN '2%'
            WHEN in_total >= 10000 THEN '1%'
            WHEN in_total >= 2000 THEN '1%'
            ELSE '0%'
		END;
	SET in_dc = 
		CASE
			WHEN in_total >= 2000000 THEN '4%'
			WHEN in_total >= 1000000 THEN '4%'
			WHEN in_total >= 500000 THEN '3%'
            WHEN in_total >= 200000 THEN '3%'
            WHEN in_total >= 100000 THEN '2%'
            WHEN in_total >= 10000 THEN '2%'
            WHEN in_total >= 2000 THEN '1%'
            ELSE '1%'
		END;
        
        -- 삽입
        insert into musinsa (no, name, age, phone, clothing, acc, sport, pet)
            values(in_no, in_name, in_age, in_phone, in_clothing, in_acc, in_sport, in_pet);
		-- 수정
        update musinsa set total = in_total, grade = in_grade, save = in_save, dc = in_dc
			where no = in_no;

end //
delimiter ;

delimiter //
create procedure procedure_update_musinsa(
	IN in_no char(4),
    IN in_name char(4),
    IN in_age int,
    IN in_phone char(15),
    IN in_clothing int,
    IN in_acc int,
    IN in_sport int,
    IN in_pet int
)
begin
	-- 변수선언
	DECLARE in_total int default 0;
	DECLARE in_grade char(10) default null;
	DECLARE in_save char(2) default null;
	DECLARE in_dc char(2) default null;
    -- 총점계산
    SET in_total = in_clothing + in_acc + in_sport + in_pet;
    SET in_grade = 
		CASE
			WHEN in_total >= 2000000 THEN 'Diamond'
			WHEN in_total >= 1000000 THEN 'Platinum'
			WHEN in_total >= 500000 THEN 'Gold'
            WHEN in_total >= 200000 THEN 'Silver'
            WHEN in_total >= 100000 THEN 'Bronze'
            WHEN in_total >= 10000 THEN 'Member'
            WHEN in_total >= 2000 THEN 'Rookie'
            ELSE 'Newbie'
		END;
	SET in_save = 
		CASE
			WHEN in_total >= 2000000 THEN '4%'
			WHEN in_total >= 1000000 THEN '3%'
			WHEN in_total >= 500000 THEN '3%'
            WHEN in_total >= 200000 THEN '2%'
            WHEN in_total >= 100000 THEN '2%'
            WHEN in_total >= 10000 THEN '1%'
            WHEN in_total >= 2000 THEN '1%'
            ELSE '0%'
		END;
	SET in_dc = 
		CASE
			WHEN in_total >= 2000000 THEN '4%'
			WHEN in_total >= 1000000 THEN '4%'
			WHEN in_total >= 500000 THEN '3%'
            WHEN in_total >= 200000 THEN '3%'
            WHEN in_total >= 100000 THEN '2%'
            WHEN in_total >= 10000 THEN '2%'
            WHEN in_total >= 2000 THEN '1%'
            ELSE '1%'
		END;
        
        -- 수정
		update musinsa set clothing = in_clothing, acc = in_acc, sport = in_sport, pet = in_pet
			where no = in_no;
		-- 수정
        update musinsa set total = in_total, grade = in_grade, save = in_save, dc = in_dc
			where no = in_no;

end //
delimiter ;

drop table if exists deleteMusinsa;

create table deleteMusinsa(
	no char(4) not null,
	name char(4) not null,
	age int not null,
	phone char(15) not null,
	clothing int not null,
	acc int not null,
	sport int not null,
	pet int not null,
	total int null,
	grade char(10),
	save char(2),
	DC char(2),
	deletetime datetime
);

drop trigger trg_deleteMusinsa;

-- 삭제 트리거
delimiter // 
create trigger trg_deleteMusinsa
	after delete
    on musinsa
    for each row
begin
insert into `deleteMusinsa` values (old.no, old.name, old.age, old.phone, old.clothing, old.acc, old.sport, old.pet, 
	old.total, old.grade, old.save, old.dc, now());
end //
delimiter ;

select * from deleteMusinsa;



