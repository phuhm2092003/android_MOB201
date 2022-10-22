package fpt.edu.schoolproject.model;

public class KhoaHoc {
    private int maKhoaHoc;
    private String tenKhoaHoc;
    private int giaKhoaHoc;
    private int trangThai;

    public KhoaHoc() {
    }

    public KhoaHoc(int maKhoaHoc, String tenKhoaHoc, int giaKhoaHoc, int trangThai) {
        this.maKhoaHoc = maKhoaHoc;
        this.tenKhoaHoc = tenKhoaHoc;
        this.giaKhoaHoc = giaKhoaHoc;
        this.trangThai = trangThai;
    }

    public int getMaKhoaHoc() {
        return maKhoaHoc;
    }

    public void setMaKhoaHoc(int maKhoaHoc) {
        this.maKhoaHoc = maKhoaHoc;
    }

    public String getTenKhoaHoc() {
        return tenKhoaHoc;
    }

    public void setTenKhoaHoc(String tenKhoaHoc) {
        this.tenKhoaHoc = tenKhoaHoc;
    }

    public int getGiaKhoaHoc() {
        return giaKhoaHoc;
    }

    public void setGiaKhoaHoc(int giaKhoaHoc) {
        this.giaKhoaHoc = giaKhoaHoc;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhoaHoc{" +
                "maKhoaHoc=" + maKhoaHoc +
                ", tenKhoaHoc='" + tenKhoaHoc + '\'' +
                ", giaKhoaHoc=" + giaKhoaHoc +
                ", trangThai=" + trangThai +
                '}';
    }
}
