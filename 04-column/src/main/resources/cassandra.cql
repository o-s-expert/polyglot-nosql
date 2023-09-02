CREATE KEYSPACE IF NOT EXISTS developers WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};
CREATE COLUMNFAMILY IF NOT EXISTS developers.Person (id bigint PRIMARY KEY, name text, contacts map<text, text>);
CREATE TYPE IF NOT EXISTS developers.director (name text, movies set<text> );
CREATE COLUMNFAMILY IF NOT EXISTS developers.Movie (name text PRIMARY KEY, age int,director FROZEN<director>);
create index if not exists ageIndex on developers.movie(age);