drop table if exists book_tag;
drop table if exists books;

create table books (
  isbn_13 varchar (13) primary key,
  title varchar (100),
  author varchar (80),
  publish_date date,
  price decimal (6,2),
  content bytea
);

create table book_tag (
	book_tag_id serial primary key,
	isbn_13 varchar (13),
	tag_name varchar (100),
	foreign key (isbn_13) references books (isbn_13) on delete cascade on update cascade	
);

insert into books values (
  '1111111111111',          	-- id
  'The Adventures of Steve',    -- title
  'Russell Barron', 			-- author
  current_date,    				-- publishDate
  123.50,   					-- price
  null							-- blob
);

insert into book_tag (isbn_13, tag_name) values (
	'1111111111111',
	'adventure'
);



