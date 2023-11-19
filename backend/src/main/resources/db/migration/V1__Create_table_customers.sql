create table if not exists customers (
    id bigint not null auto_increment primary key,
    full_name varchar(255) not null,
    access_code varchar(4) not null unique,
    expiration date default null,
    account_non_expired bit default b'1',
    role varchar(10) default 'USER'
)