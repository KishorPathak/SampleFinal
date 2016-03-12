DELIMITER //
DROP PROCEDURE if exists sp_add_emp_workout_stats;
CREATE PROCEDURE sp_add_emp_workout_stats 
(
   in p_emp_id int,
   in p_email_id varchar(100),
   in p_running_min decimal(7,2),
   in p_cycling_min decimal(7,2),
   in p_steps_cnt int,
   in p_distinct_mtr decimal(7,2),
   in p_calories decimal(7,2),
   in p_from_date timestamp,
   in p_to_date timestamp	
)
begin	
	declare l_emp_id int;
	declare l_running_min decimal(7,2);
	declare l_cycling_min decimal(7,2);
	declare l_steps_cnt INT;
	declare l_distance decimal(7,2);
	declare l_calories FLOAT(8,2);

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, 
		 @errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
		SET @full_error = CONCAT("ERROR ", @errno, " (", @sqlstate, "): ", @text);
		insert into error_log(error_desc) values(@full_error);
		commit;	
	END;

	if p_emp_id is null then
		select emp_id into l_emp_id from emp_mst where lower(email_id) = lower(p_email_id);
	end if;
	
	if p_emp_id > 0 or l_emp_id > 0 then
		select emp_id, running_min, cycling_min, steps_cnt, distinct_mtr, calories 
		into l_emp_id, l_running_min, l_cycling_min, l_steps_cnt, l_distance, l_calories
		FROM emp_physician_recommendation WHERE emp_id = ifnull(p_emp_id,l_emp_id) order by emp_phy_recommend_id desc LIMIT 1;
		
		insert into emp_workout_stats(emp_id, 
		 running_min, cycling_min, steps_cnt, distinct_mtr, calories, 
		 running_adh, cycling_adh, steps_adh, distance_adh, calories_adh, overall_adh,
		 date_created, from_period, to_period)
		 values 
		 (
			 ifnull(p_emp_id,l_emp_id),
			 p_running_min,
			 p_cycling_min,
			 p_steps_cnt,
			 p_distinct_mtr,
			 p_calories,
			 (100*p_running_min)/l_running_min,
			 (100*p_cycling_min)/l_cycling_min,
			 (100*p_steps_cnt)/l_steps_cnt,
			 (100*p_distinct_mtr)/l_distance,
			 (100*p_calories)/l_calories,
			 (100*p_calories)/l_calories,
			 CURRENT_TIMESTAMP,
			 p_from_date,
			 p_to_date,
			 (select zone_desc from zones z where ((100*p_calories)/l_calories) between z.zone_start and z.zone_end)
		 );
		commit;
	end if;
end;//

DELIMITER ;
