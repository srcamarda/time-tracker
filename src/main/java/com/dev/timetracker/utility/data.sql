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