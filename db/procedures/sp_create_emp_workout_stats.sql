DELIMITER //
DROP PROCEDURE if exists sp_create_emp_workout_stats;
CREATE PROCEDURE sp_create_emp_workout_stats 
(
   in p_from_emp_id int,
   in p_to_emp_id int,
   in p_days int
) 
begin	
	declare rec_num int default 0;
	declare l_days int;
	declare v_finished int;	
	declare l_start_pcnt int default 50;
	declare l_emp_id int;
	declare l_running_min decimal(7,2);
	declare l_cycling_min decimal(7,2);
	declare l_steps_cnt INT;
	declare l_distance decimal(7,2);
	declare l_calories FLOAT(8,2);
	declare l_cal_val FLOAT(8,2);
	declare l_start_date datetime;
	declare emp_cur CURSOR FOR select emp_id, running_min, cycling_min, steps_cnt, distinct_mtr, calories FROM emp_physician_recommendation WHERE emp_id between p_from_emp_id and p_to_emp_id order by emp_id;	 
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

	set l_days = 1;
	set l_start_date = str_to_date('20160201','%Y%m%d');
	
	label1: WHILE l_days <= p_days DO
    set l_start_pcnt = 50;
	set rec_num = 0;
	set v_finished = 0;
	
	open emp_cur;	
	get_emp:loop
		fetch emp_cur into l_emp_id, l_running_min, l_cycling_min, l_steps_cnt, l_distance, l_calories;
		
		 IF v_finished = 1 THEN 
			LEAVE get_emp;
		 END IF;
		 set rec_num = rec_num + 1;
		 
		 if l_start_pcnt >= 90 then
			set l_start_pcnt = 50;
		 end if;
		 		 
		 if rec_num = 1 then
			set l_cal_val = (l_days * 20)+980;
		 elseif rec_num = 2 then
			set l_cal_val = (l_days * 18)+1082; 	
		 elseif rec_num = 3 then
			set l_cal_val = (l_days * 16)+1184; 		
		 elseif rec_num = 4 then
			set l_cal_val = (l_days * 14)+1286; 		
		 elseif rec_num = 5 then
			set l_cal_val = (l_days * 12)+1388; 		
		 elseif rec_num = 6 then
			set l_cal_val = (l_days * 10)+1490; 		
		 elseif rec_num = 7 then
			set l_cal_val = (l_days * 8)+1592; 		
		 elseif rec_num = 8 then
			set l_cal_val = (l_days * 6)+1694; 			
		 elseif rec_num = 9 then
			set l_cal_val = (l_days * 4)+1796; 		
		 elseif rec_num = 10 then
			set l_cal_val = (l_days * 2)+1898; 		
		 elseif rec_num = 11 then
			set l_cal_val = (l_days * 0)+2000; 		
		 elseif rec_num = 12 then
			set l_cal_val = (l_days * -2)+2102; 		
		 elseif rec_num = 13 then
			set l_cal_val = (l_days * -4)+2204; 		
		 elseif rec_num = 14 then
			set l_cal_val = (l_days * -6)+2306; 		
		 elseif rec_num = 15 then
			set l_cal_val = (l_days * -8)+2408; 		
		 elseif rec_num = 16 then
			set l_cal_val = (l_days * -10)+2510; 		
		 elseif rec_num = 17 then
			set l_cal_val = (l_days * -12)+2612; 		
		 elseif rec_num = 18 then
			set l_cal_val = (l_days * -14)+2714; 		
		 elseif rec_num = 19 then
			set l_cal_val = (l_days * -16)+2816; 		
		 elseif rec_num = 20 then
			set l_cal_val = (l_days * -18)+2918; 
		 end if;
		 
		 insert into emp_workout_stats(emp_id, 
		 running_min, cycling_min, steps_cnt, distinct_mtr, calories, 
		 running_adh, cycling_adh, steps_adh, distance_adh, calories_adh, overall_adh,
		 date_created, from_period, to_period, zone_desc)
		 values (l_emp_id,
		 (l_running_min*l_start_pcnt/100),
		 (l_cycling_min*l_start_pcnt/100),
		 (l_steps_cnt*l_start_pcnt/100),
		 (l_distance*l_start_pcnt/100),
		 l_cal_val,
		 l_start_pcnt, l_start_pcnt, l_start_pcnt, l_start_pcnt, (100*l_cal_val)/l_calories, (100*l_cal_val)/l_calories,
		 CURRENT_TIMESTAMP,
		 l_start_date,
		 l_start_date,
		 (select zone_desc from zones z where ((100*l_cal_val)/l_calories) between z.zone_start and z.zone_end)
		 );
		 
		 set l_start_pcnt = l_start_pcnt + 10;

	end loop get_emp;
	close emp_cur;
    SET l_days = l_days + 1;
	set l_start_date = DATE_ADD(l_start_date,INTERVAL 1 DAY);
	END WHILE label1;
	commit;
end;//
DELIMITER ;
