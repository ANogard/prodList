create table if not exists price (
    id serial,
    price int4,
    datetime timestamp without time zone,
    product_id int4,
    primary key (id)
);

create table if not exists product (
    id serial,
    name varchar(255),
    primary key (id)
);