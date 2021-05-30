# Задание 1
use geodata;

# Задание 1.1
# Сделать запрос, в котором мы выберем все данные о городе –
# регион, страна(из трёх разных таблиц, объединяем эти таблицы, 
# учитывая, что у нас есть города в которых ид_региона =NULL). 
#########################
SELECT * from _cities as cit 
left join _regions as reg on cit.region_id=reg.region_id and cit.region_id is not null
left join _countries as cou on reg.country_id=cou.country_id;
#########################

# Задание 1.2
# Выбрать все города из Московской области.
#########################
select * from _cities
where region_id = (select region_id from _regions where title_ru like 'Московс%');
#########################


# Задание 2
use employees;

# Задание 2.1
# Выбрать среднюю зарплату по отделам + (номер и наименование отдела)
#########################
select round(AVG(salary),2), dept_emp.dept_no, departments.dept_name from salaries as t_sal 
left join dept_emp on dept_emp.emp_no = t_sal.emp_no and dept_emp.to_date = '9999-01-01'
left join departments on departments.dept_no = dept_emp.dept_no
GROUP BY dept_emp.dept_no;
#########################

# Задание 2.2
# Выбрать максимальную зарплату у сотрудника(сотрудник не должне быть уволен)
#########################
select max(salary), employees.first_name, employees.last_name, employees.birth_date from salaries as t_sal
left join dept_emp on dept_emp.emp_no = t_sal.emp_no and dept_emp.to_date = '9999-01-01'
left join employees on dept_emp.emp_no = employees.emp_no  
#########################

# Задание 2.3
# Удалить одного сотрудника, у которого максимальная зарплата
#########################
delete from employees
where emp_no = (select emp_no from salaries 
where salary = (select max(salary) from salaries) and to_date = '9999-01-01');
#########################

# Задание 2.4
# Посчитать количество сотрудников во всех отделах(Мы подсчитываем количество не уволенных сотрудников).
#########################
select dept_no, count(emp_no) from dept_emp 
where to_date = '9999-01-01' group by dept_no;
#########################

# Задание 2.5
#########################
select dept_emp.dept_no, departments.dept_name, count(dept_emp.emp_no), sum(salaries.salary)  from dept_emp 
left join departments using (dept_no)
left join salaries using (emp_no)
Where dept_emp.to_date = '9999-01-01'
and salaries.to_date = '9999-01-01'
group by dept_emp.dept_no;
#########################