DELIMITER //
DROP PROCEDURE if exists sp_create_emp_profile;
CREATE PROCEDURE sp_create_emp_profile 
(
   in p_from_emp_id int,
   in p_to_emp_id int
) 
BEGIN
	declare l_from_weight decimal(5,2);
	declare l_to_weight decimal(5,2);
	declare l_from_height decimal(5,2);
	declare l_to_height decimal(5,2);
	declare l_from_bp int;
	declare l_to_bp int;
	declare l_from_sugar int;
	declare l_to_sugar int;
	declare l_from_heartrate int;
	declare l_to_heartrate int;
	declare l_weight_interval int default 4;
	declare l_height_interval int default 1;
	declare l_bp_interval int default 7;
	declare l_sugar_interval int default 9;
	declare l_heartrate_interval int default 4;
	declare l_emp_id int;
	declare rec_num int default 0;
	declare v_finished int;	
	declare emp_cur CURSOR FOR select emp_id FROM emp_mst WHERE emp_id between p_from_emp_id and p_to_emp_id order by emp_id;	 
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
	set l_from_weight = 40;
	set l_to_weight = 120;
	set l_from_height = 60;
	set l_to_height = 75;
	set l_from_bp = 60;
	set l_to_bp = 200;
	set l_from_sugar = 120;
	set l_to_sugar = 300;
	set l_from_heartrate = 50;
	set l_to_heartrate = 140;
	set v_finished = 0;	
	
	open emp_cur;	
	get_emp:loop
		fetch emp_cur into l_emp_id;
		
		 IF v_finished = 1 THEN 
			LEAVE get_emp;
		 END IF;
		 set rec_num = rec_num + 1;
		 delete from emp_physician_recommendation where emp_id = l_emp_id;
		 delete from emp_profile where emp_id = l_emp_id;
		 
		 insert into emp_profile (emp_id, emp_profile_date, weight, height, bp, sugar, heart_rate)
		 values (l_emp_id, CURRENT_TIMESTAMP, 
		 l_from_weight +(rec_num*l_weight_interval),
		 l_from_height +(rec_num*l_height_interval),
		 l_from_bp +(rec_num*l_bp_interval),		 
		 l_from_sugar +(rec_num*l_sugar_interval),
		 l_from_heartrate +(rec_num*l_heartrate_interval)
		 );
		 
		 insert into emp_physician_recommendation (emp_id, emp_profile_id, running_min, cycling_min, steps_cnt, distinct_mtr, calories)
		 values (
		 l_emp_id, 
		 LAST_INSERT_ID(),
		 25+(rec_num*5),
		 15+(rec_num*5),
		 1000+(rec_num*10),
		 2000+(rec_num*100),
		 2000
		 );
		 
	end loop get_emp;
	close emp_cur;
	commit;
END; //
DELIMITER ;
