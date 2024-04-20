create table users (
	username varchar(30) primary key not null,
	full_name varchar(100),
	email varchar(50),
	cpf varchar(20),
	role varchar(20),
	plan varchar(20),
	addr_cep varchar(20),
	addr_country varchar(20),
	addr_state varchar(30),
	addr_city varchar(30),
	addr_street varchar(60),
	addr_number integer
);

create index users_username on users(username);
create index users_cpf on users(cpf);

create table tasks (
	id serial primary key,
	title varchar(50) not null,
	description varchar(200),
	tag varchar(30),
	username varchar(30) references users(username),
	start_time timestamp,
	end_time timestamp,
	active boolean
);

create index tasks_tag on tasks(tag);

create table projects (
	id serial primary key,
	title varchar(50) not null,
	description varchar(200),
	start_time timestamp,
	end_time timestamp,
	active boolean
);

create table project_users (
	id_project int references projects(id),
	username varchar(30) references users(username) 
);

create table project_tasks (
	id_project int references projects(id),
	id_task int references tasks(id)
);

create table project_tags (
	id_project int references projects(id),
	tag varchar(30)
);