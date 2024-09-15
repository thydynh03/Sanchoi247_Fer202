create database SanChoi247;
use SanChoi247;

create table users(
	uid int auto_increment primary key,
    name varchar(64),
    dob date,
    gender char,
    phone varchar(16),
    email varchar(50),
    username varchar(64),
    password varchar(128),
    avatar text,
    role char
);

create table chuSan(
	chu_san_id int not null primary key,
    foreign key (chu_san_id) references users(uid),
    website text,
    is_active tinyint,
    chu_san_address varchar(256)
);

create table loaiSan(
	loai_san_id int auto_increment primary key,
    loai_san_type varchar(50),
    so_luong_san int
);

create table loaiSanBongDa(
	loai_san_bong_da_id int auto_increment primary key,
    loai_san_bong_da_type varchar(20)
);

create table san(
	san_id int auto_increment primary key,
    name varchar(64),
    description text,
    location varchar(256),
    loai_san_id int,
    foreign key(loai_san_id) references loaiSan(loai_san_id),
    chu_san_id int,
    foreign key (chu_san_id) references chuSan(chu_san_id),
    poster text,
    banner text,
    is_approve int
);
select * from san;

create table booking(
	booking_id int auto_increment primary key,
    date timestamp,
    total int,
    uid int,
    foreign key (uid) references users(uid),
    status tinyint,
    vnpay_data json
);

create table rating(
	rating_id int auto_increment primary key,
    star int,
    chu_san_id int,
    foreign key (chu_san_id) references chuSan(chu_san_id),
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
('Phạm Thanh Tùng', '2004-03-03', 'F', '3333333333', 'tung123@gmail.com', 'e', 'e', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeQxVTWH4HAFTvY-Fts0RUTd3vnAe51-xCBA&s', 'U');
select * from users;
insert into chuSan(chu_san_id, website, is_active, chu_san_address) values
(2, 'https://www.hoangnpv.id.vn', 1, 'Đà Nẵng'),
(4, 'https://www.hoangnpv.id.vn', 1, 'Đà Nẵng');

insert into loaiSan(loai_san_type, so_luong_san) values
('Sân bóng đá', 15),
('Sân cầu lông', 12),
('Sân bóng chuyền', 5),
('Sân bóng rổ', 7);

INSERT INTO san (name, description, location, loai_san_id, chu_san_id, poster, banner, is_approve) VALUES
('Sân bóng đá A', 'Sân bóng đá ngoài trời với kích thước tiêu chuẩn.', '123 Đường ABC, Quận 1, TP. HCM', 1, 2, 'poster1.jpg', 'banner1.jpg', 1),
('Sân cầu lông B', 'Sân cầu lông trong nhà, chất lượng mặt sân tốt.', '456 Đường XYZ, Quận 2, TP. HCM', 2, 2, 'poster2.jpg', 'banner2.jpg', 0),
('Sân bóng chuyền C', 'Sân bóng chuyền ngoài trời, rộng rãi thoáng mát.', '789 Đường DEF, Quận 3, TP. HCM', 3, 4, 'poster3.jpg', 'banner3.jpg', 1),
('Sân bóng rổ D', 'Sân bóng rổ với trang thiết bị hiện đại.', '101 Đường GHI, Quận 4, TP. HCM', 4, 4, 'poster4.jpg', 'banner4.jpg', 2),
('Sân bóng đá E', 'Sân bóng đá mini với kích thước 5 người chơi.', '102 Đường LMN, Quận 5, TP. HCM', 1, 4, 'poster5.jpg', 'banner5.jpg', 3);
select * from san;