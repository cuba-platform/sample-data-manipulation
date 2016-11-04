-- begin SAMPLE_CUSTOMER
create table SAMPLE_CUSTOMER (
    ID varchar(36) not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    EMAIL varchar(100),
    GRADE integer,
    --
    primary key (ID)
)^
-- end SAMPLE_CUSTOMER
-- begin SAMPLE_DISCOUNT
create table SAMPLE_DISCOUNT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CUSTOMER_GRADE integer,
    PRODUCT_TYPE varchar(50),
    DISCOUNT double precision,
    --
    primary key (ID)
)^
-- end SAMPLE_DISCOUNT
