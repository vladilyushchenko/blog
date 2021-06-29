create table if not exists users (
    id serial primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) not null unique,
    created_at date not null,
    activated boolean not null,
    password varchar(200) not null
);

create table if not exists roles (
    id serial primary key,
    name varchar(50) not null unique
);

insert into roles (name) values ('ROLE_USER'), ('ROLE_ADMIN') on conflict do nothing;

create table if not exists  users_roles (
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

DO
'
BEGIN
    CREATE TYPE article_status AS ENUM (''PUBLIC'', ''DRAFT'');
EXCEPTION
    WHEN duplicate_object THEN null;
END;
' LANGUAGE PLPGSQL;

create table if not exists articles (
    id serial primary key,
    title varchar(100) not null,
    text varchar(2000) not null,
    author_id integer not null,
    created_at date not null,
    updated_at date,
    status article_status not null,
    foreign key(author_id) references users(id)
);

create table if not exists comments (
    id serial primary key,
    message varchar (1000) not null,
    article_id int,
    author_id int,
    created_at date not null,
    constraint article_reference foreign key (article_id) references articles(id),
    constraint users_reference foreign key (author_id) references users(id)
);

create table if not exists tags (
    id serial primary key,
    name varchar(50) not null unique
);

create table if not exists tags_articles (
    tag_id int,
    article_id int,
    constraint tags_reference foreign key (tag_id) references tags(id),
    constraint articles_reference foreign key (article_id) references articles(id)
);







