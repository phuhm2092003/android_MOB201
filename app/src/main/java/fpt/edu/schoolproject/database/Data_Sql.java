package fpt.edu.schoolproject.database;

public class Data_Sql {
    public static final String INSERT_USER = "INSERT INTO USER(userName, fullName, password) VALUES ('admin', 'Ho Minh Phu', 'admin')";
    public static final String INSERT_KHOAHOC = "INSERT INTO KhoaHoc (tenKhoaHoc, giaKhoaHoc, trangThai) VALUES ('Kiến thức nền tảng', '200000', '1'),  ('HTMl, CSS', '150000', '0')," +
            "('Android cơ bản', '300000', '1')," +
            "('Android nâng cao', '200000', '1')," +
            "('Kiến thức nền tảng', '200000', '1')," +
            "('HTMl, CSS', '150000', '0')," +
            "('Android cơ bản', '300000', '1')," +
            "('Android nâng cao', '200000', '1')," +
            "('React JS', '200000', '0')," +
            "('Lập trình game 2D', '100000', '0')";
}
