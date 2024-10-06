create database SanChoi247;
use SanChoi247;

create table users(
	uid int auto_increment primary key,
    name varchar(64),
    dob date,
    gender varchar(1),
    phone varchar(16),
    email varchar(50),
    username varchar(64),
    password varchar(128),
    avatar text,
    role varchar(1)
);

create table loaiSan(
	loai_san_id int auto_increment primary key,
    loai_san_type varchar(50)
);

create table viTriSan(
	vi_tri_san_id int auto_increment primary key,
    vi_tri_san varchar(20)
);

create table size(
	size_id int auto_increment primary key,
    size varchar(20),
    loai_san_id int,
    foreign key ( loai_san_id) references loaiSan(loai_san_id)
);

create table san(
	san_id int auto_increment primary key,
    name varchar(64),
    description text,
    location varchar(256),
    loai_san_id int,
    foreign key(loai_san_id) references loaiSan(loai_san_id),
    vi_tri_san_id int,
    foreign key (vi_tri_san_id) references viTriSan(vi_tri_san_id),
    size_id int,
    foreign key (size_id) references size(size_id),
    uid int,
    foreign key (uid) references users(uid),
    poster text,
    banner text,
    is_approve int,
    eyeview int default 0
);

create table booking(
	booking_id int auto_increment primary key,
    date timestamp,
    uid int,
    foreign key(uid) references users(uid),
    san_id int,
    foreign key (san_id) references san(san_id),
    price double,
    status tinyint,
    vnpay_data json
);

create table rating(
	rating_id int auto_increment primary key,
    star int,
    uid int,
    foreign key (uid) references users(uid),
    booking_id int,
    foreign key (booking_id) references booking(booking_id)
);

create table refundOrder(
	booking_id int auto_increment primary key,
    foreign key (booking_id) references booking(booking_id),
    total int,
    created_on timestamp,
    status tinyint,
    approved_on timestamp,
    refunded_on timestamp,
    actual_refund int
);

create table userSecret(
	id int auto_increment primary key,
    uid int,
    foreign key (uid) references users(uid),
    secret_key varchar(256),
    create_at timestamp
);

create table googleOauth(
	client_id varchar(255),
    client_secret varchar(255),
    redirect_uri varchar(255)
);

insert into users(name, dob, gender, phone, email, username, password, avatar, role) values
('Nguyễn Đình Bảo Ân', '2004-03-19', 'M', '0987689426', 'annd1903@gmail.com', 'a', 'a', 'https://image.spreadshirtmedia.com/image-server/v1/compositions/T210A2PA4301PT17X22Y0D1033855154W28793H34552/views/1,width=550,height=550,appearanceId=2,backgroundColor=000000,noPt=true/my-other-computer-is-your-computer-hacker-meme-cyb-mens-t-shirt.jpg', 'A'),
('Nguyễn Đình Thi', '2004-05-03', 'M', '000000000', 'thithithi@gmail.com', 'b', 'b', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSm64c617Ru1xbzmjJNIOzNYt5xMvNcB56l9Q&s', 'C'),
('Huỳnh Phạm Bảo Tuân', '2024-01-01', 'F', '1111111111', 'tuantuantuan@gmail.com', 'c', 'c', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROjfDTghEmMns99jBtOkpQrfBN19Xfw4W2Kg&s', 'U' ),
('Châu Thành Trung', '2004-02-02', 'M', '2222222222', 'concatre@gmail.com', 'd', 'd', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfA74jKoBCGxNadWXx6CI2uQiOrdOyh53LPQ&s', 'C'),
('Phạm Thanh Tùng', '2004-03-03', 'F', '3333333333', 'tung123@gmail.com', 'e', 'e', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeQxVTWH4HAFTvY-Fts0RUTd3vnAe51-xCBA&s', 'U'),
('ThiThiThi', '2004-05-03', 'F', '1234567890', '123@gmail.com', 'user', '123', null, 'U');


insert into loaiSan(loai_san_type) values
('Sân Bóng Đá'),
('Sân Cầu Lông'),
('Sân Bóng Chuyền'),
('Sân Bóng Rổ'),
('Sân Pickeball'),
('Sân Tenis'),
('Sân Golf');

insert into viTriSan(vi_tri_san) values
('1A'),
('1B'),
('1C'),
('1D'),
('2A'),
('2B'),
('3C'),
('4D'),
('3A'),
('3B'),
('3C'),
('3D');

insert into size(size, loai_san_id) values
('Sân 5', 1),
('Sân 6', 1),
('Sân 7', 1),
('Sân 11', 1),
('Sân tiêu chuẩn', 2),
('Sân tiêu chuẩn', 3),
('Sân tiêu chuẩn', 4),
('Sân tiêu chuẩn', 5),
('Sân tiêu chuẩn', 6),
('Sân tiêu chuẩn', 7);

INSERT INTO san (name, description, location, loai_san_id, vi_tri_san_id, size_id, uid, poster, banner, is_approve) VALUES
('Sân bóng đá Tuyên Sơn', 'Sân bóng đá ngoài trời.', '123 Đường ABC, TP. ĐN', 1, 2, 1, 4, 'https://top10vietnam.top/wp-content/uploads/2021/08/Tong-hop-10-san-bong-da-cho-thue-uy-tin-tai-Da-Nang-1-600x400.jpg', 'banner1.jpg', 1),
('Sân cầu lông Hải Châu', 'Sân cầu lông trong nhà, chất lượng mặt sân tốt.', '456 Đường XYZ, TP. ĐN', 2,2,5, 2, 'poster2.jpg', 'banner2.jpg', 1),
('Sân bóng chuyền BK', 'Sân bóng chuyền ngoài trời, rộng rãi thoáng mát.', '789 Đường DEF, TP. ĐN', 3,3,6, 4, 'poster3.jpg', 'banner3.jpg', 1),
('Sân bóng rổ Sơn Trà', 'Sân bóng rổ với trang thiết bị hiện đại.', '101 Đường GHI, TP. ĐN', 4,4,7, 4, 'poster4.jpg', 'banner4.jpg', 1),
('Sân bóng đá Chuyên Việt', 'Sân bóng đá trong nhà mát mẻ.', '102 Đường Tiểu La, TP. ĐN', 1,5,1, 4, 'poster5.jpg', 'banner5.jpg', 1);
select * from users;