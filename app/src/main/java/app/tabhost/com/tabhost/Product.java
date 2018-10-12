package app.tabhost.com.tabhost;

public class Product {
    private	int	id;
    private	String ten;
    private double gia;
    private	int	soluong;
    private String dvt;
    private String ghichu;

    public Product(String ten, double gia, int soluong, String dvt, String ghichu) {
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
        this.dvt = dvt;
        this.ghichu = ghichu;
    }

    public Product(int id, String ten, double gia, int soluong, String dvt, String ghichu) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.soluong = soluong;
        this.dvt = dvt;
        this.ghichu = ghichu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
