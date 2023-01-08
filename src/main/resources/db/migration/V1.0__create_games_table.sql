CREATE TABLE public.games (
	id uuid NOT NULL,
	player_name varchar NOT NULL,
	created_at bigint NOT NULL,
	closed boolean default false,
	CONSTRAINT games_pk PRIMARY KEY (id)
);
