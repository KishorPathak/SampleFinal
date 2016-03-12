DELIMITER //
DROP PROCEDURE if exists sp_create_emp;
CREATE PROCEDURE sp_create_emp 
(
   in p_start_id int,
   in p_end_id int,
   in p_bu varchar(50)
) 
BEGIN
	DECLARE x int;
	DECLARE dob date;
	set x = p_start_id;
	set dob = str_to_date('1975-01-01','%Y-%m-%d');
	
	while x < p_end_id do
		insert into emp_mst(emp_name,emp_bu,emp_dob,email_id) 
		values (concat('Emp_',x), p_bu, dob+interval x year, concat('abc',x,'@persistent.com'));
		set x = x + 1;
	end while;
	commit;
END; //
DELIMITER ;
