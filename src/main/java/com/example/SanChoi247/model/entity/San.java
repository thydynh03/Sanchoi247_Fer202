package com.example.SanChoi247.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class San {
//     create table san(
// 	san_id int auto_increment primary key,
//     uid int,
//     foreign key (uid) references users(uid),
//     description text,
//     address varchar(256),
//     loai_san_id int,
//     foreign key(loai_san_id) references loaiSan(loai_san_id),
//     vi_tri_san varchar(20),
//     size_id int,
//     foreign key (size_id) references size(size_id),
//     poster text,
//     banner text,
//     is_approve int,
//     eyeview int default 0
// );
    private int san_id;
    public San(LoaiSan loaiSan, String vi_tri_san, Size size, String img) {
        this.loaiSan = loaiSan;
        this.vi_tri_san = vi_tri_san;
        this.size = size;
        this.img = img;
    }
    private User user;
    private LoaiSan loaiSan;
    private String vi_tri_san;
    private Size size;
    private String img;
    private int is_approve;// 0. pending 1. Approved 2. Hide 3. Rejected 4.Request PayMent 5. Resolved\
    private int eyeview;
}
