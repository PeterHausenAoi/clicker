CREATE TABLE public.clicks (
	id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
	game_id uuid NOT NULL,
	created_at bigint NOT NULL,
	CONSTRAINT clicks_pk PRIMARY KEY (id),
	CONSTRAINT clicks_fk FOREIGN KEY (game_id) REFERENCES public.games(id) ON DELETE CASCADE
);
