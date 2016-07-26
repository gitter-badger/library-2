CREATE TABLE users (
  id int IDENTITY PRIMARY KEY NOT NULL,
  name varchar(255) UNIQUE NOT NULL
);

CREATE TABLE books (
  id int IDENTITY PRIMARY KEY NOT NULL,
  isbn varchar(255) UNIQUE NOT NULL,
  author varchar(255) NOT NULL,
  title varchar(255) NOT NULL,
  borrower_id int,
  FOREIGN KEY (borrower_id) REFERENCES public.users(id)
);
