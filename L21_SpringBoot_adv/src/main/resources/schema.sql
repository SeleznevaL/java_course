create table atm(
    id bigserial,
    primary key (id)
);

create table cassette(
    id bigserial,
    nominal varchar(255),
    cassetteCount varchar(255),
    capasity integer,
    atm_id integer
    primary key (id)
    foreign key (atm_id) references  atm(id)
);