BEGIN TRANSACTION;

drop sequence if exists product_sequence;
drop sequence if exists section_sequence;
drop sequence if exists store_sequence;

drop table if exists store cascade;
drop table if exists manager cascade;
drop table if exists store_manager cascade;
drop table if exists section cascade;
drop table if exists product cascade;

create sequence product_sequence start 1 increment 1;
create sequence section_sequence start 1 increment 1;
create sequence store_sequence start 1 increment 1;

create table store
(
    id       int4        not null,
    location varchar(50) not null,
    name     varchar(50) not null,
    primary key (id)
);

create table manager
(
    id   int4 not null,
    name varchar(255),
    primary key (id)
);

create table store_manager
(
    manager_id int4 not null,
    store_id   int4 not null,
    primary key (manager_id, store_id),
    foreign key (manager_id) references manager (id),
    foreign key (store_id) references store (id)
);

create table section
(
    id       int4 not null,
    name     varchar(40),
    store_id int4,
    primary key (id),
    foreign key (store_id) references store (id)
);

create table product
(
    id         int4        not null,
    name       varchar(50) not null unique,
    price      float8      not null,
    section_id int4,
    primary key (id),
    foreign key (section_id) references section (id)
);

COMMIT;