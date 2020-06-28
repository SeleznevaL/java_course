create table atm(
    id bigserial,
    primary key (id)
);

create table cassette(
    id bigserial,
    nominal VARCHAR(50),
    cassette_count bigint,
    capacity bigint,
    atm_id bigint,
    primary key (id),
    foreign key (atm_id) references atm(id)
);
